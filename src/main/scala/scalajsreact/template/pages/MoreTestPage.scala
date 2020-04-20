package scalajsreact.template.pages

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.denigma.threejs.{DirectionalLight, PerspectiveCamera, Scene, WebGLRenderer, Color, WebGLRendererParameters}
import org.scalajs.dom
import org.scalajs.dom.html

import scala.scalajs.js

object MoreTestPage {

  import geomtest._

  case class State(title: String)

  class Backend($: BackendScope[Unit, State]) {

    private var outerRef = Ref[html.Element]

    def render(s: State) =
      <.div(
        <.div().withRef(outerRef)
      )

    // Use it
    def init(renderer: WebGLRenderer) = {
      //println("rendering...")
      // TODO don't call unsafeGet()
      outerRef.unsafeGet().appendChild(renderer.domElement)
      //      outerRef.foreach(r => {
      //        println("Appending a child...")
      //        r.appendChild(renderer.domElement)
      //      })
    }
  }

  def webGLRenderer(innerWidth: Long, innerHeight: Long) = {
    val webGLRendererParameters = js.Dynamic.literal("antialias" -> true).asInstanceOf[WebGLRendererParameters]
    val renderer: WebGLRenderer = new WebGLRenderer(webGLRendererParameters)
    renderer.setSize(innerWidth, innerHeight);
    renderer
  }

  val component = ScalaComponent.builder[Unit]("MoreTestPage")
    .initialState(State("Initial State for MoreTestPage"))
    .renderBackend[Backend]
    .componentDidMount(cdm => Callback {

      val window = dom.window
      println("innerWidth is " + window.innerWidth)
      println(s"innerHeight is ${window.innerHeight}")

      val innerWidth = window.innerWidth.toLong
      val innerHeight = window.innerHeight.toLong

//      val scene = new Scene()
      val camera = new PerspectiveCamera(60, innerWidth / innerHeight, 0.1, 10000)

      val renderer = webGLRenderer(innerWidth, innerHeight)
      cdm.backend.init(renderer)

      val scene = sceneWithLights()

      val objects = randomCubes(200)

      for (obj <- objects) {
        scene.add(obj)
      }

      var theta: Double = 0
      var radius = 2000

      def render() = {
        theta = theta + 0.1

        camera.position.x = radius * Math.sin(Math.PI * theta / 180)
        camera.position.y = radius * Math.sin(Math.PI * theta / 180)
        camera.position.z = radius * Math.cos(Math.PI * theta / 180)

        camera.lookAt(scene.position);
        camera.updateMatrixWorld();
        renderer.render(scene, camera)
      }

      def animate(p: Double): Unit = {
        //println("animation frame")
        window.requestAnimationFrame(animate);
        render();
      }

      animate(0)


      //      })
    })
    .build


  def apply() = component()
}
