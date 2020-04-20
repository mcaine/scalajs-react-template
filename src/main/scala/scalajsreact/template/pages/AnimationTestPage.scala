package scalajsreact.template.pages

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom
import org.scalajs.dom.html
import threejs._

import scala.scalajs.js

object AnimationTestPage {
  import geomtest._

  case class State(title: String)

  class Backend($: BackendScope[Unit, State]) {

    private var outerRef = Ref[html.Element]

    def render(s: State) =
      <.div(
        <.div(^.id := "mikeyx2").withRef(outerRef)
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

  val component = ScalaComponent.builder[Unit]("AnimationTestPage")
    .initialState(State("Initial State for AnimationTestPage"))
    .renderBackend[Backend]
    .componentDidMount(cdm => Callback {

      val window = dom.window
      println("innerWidth is " + window.innerWidth)
      println(s"innerHeight is ${window.innerHeight}")

      val innerWidth = window.innerWidth.toLong
      val innerHeight = window.innerHeight.toLong

      val scene = new Scene()
      val camera = new PerspectiveCamera(60, innerWidth / innerHeight, 0.1, 10000)

      val renderer = webGLRenderer(innerWidth, innerHeight)
      cdm.backend.init(renderer)

      val light = new DirectionalLight()
      light.color = new threejs.Color(0xaa7700)
      //light.position.set( 0, 1, 1 ).normalize()
      val lightPos = new Vector3(0, 1, 1)
      light.position.set(0, 0, 10)
      scene.add(light)

      camera.position.z = 900
      camera.position.x = 0
      camera.position.y = 0


//      new FontLoader().load("fonts/helvetiker_regular.typeface.json", font => {
//        println("Loaded font...")

//        new TextureLoader().load("images/tup.jpg", texture => {
//          println("Loaded the texture bitmap")

          //val material = new MeshBasicMaterial(js.Dynamic.literal("map" -> texture).asInstanceOf[MeshBasicMaterialParameters])

          val objects = randomCubes(100)


          for (obj <- objects) {
            scene.add(obj)
          }

      var theta: Double = 0
      var radius = 500

      def render()  = {
        theta = theta + 0.1

        camera.position.x = radius * Math.sin( Math.PI * theta/180 )
        camera.position.y = radius * Math.sin( Math.PI * theta/180 )
        camera.position.z = radius * Math.cos( Math.PI * theta/180 )
        camera.lookAt( scene.position );
        camera.updateMatrixWorld();
        renderer.render(scene, camera)
      }

      def animate(p: Double):Unit  = {
        println("animation frame")
        window.requestAnimationFrame( animate );
        render();
      }

      animate(0)


//      })
    })
    .build



  def apply() = component()
}
