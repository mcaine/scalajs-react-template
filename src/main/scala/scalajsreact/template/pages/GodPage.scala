package scalajsreact.template.pages

import geomtest.{randomText, sceneWithLights}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import mikey.threejs.FontLoader
import org.denigma.threejs._
import org.scalajs.dom
import org.scalajs.dom.html
import scalacss.DevDefaults._
import scalajsreact.template.pages.AnimationTestPage.webGLRenderer

import scala.scalajs.js
//import threejs.{BoxGeometry, DirectionalLight, FontLoader, Mesh, MeshBasicMaterial, MeshPhongMaterial, MeshPhongMaterialParameters, PerspectiveCamera, Scene, TextGeometry, TextGeometryParameters, Vector3, WebGLRenderer, WebGLRendererParameters}

object GodPage {

  object Style extends StyleSheet.Inline {
    import dsl._
    val content: StyleA = style(textAlign.center,
                        fontSize(50.px),
                        minHeight(450.px),
                        paddingTop(40.px))
  }

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

  val component = ScalaComponent.builder[Unit]("Nother")
    .initialState(State("Title goes here"))
    .renderBackend[Backend]
    .componentDidMount(cdm => Callback {

      val window = dom.window
      println("innerWidth is " + window.innerWidth)
      println(s"innerHeight is ${window.innerHeight}")

      val innerWidth = window.innerWidth.toLong
      val innerHeight = window.innerHeight.toLong

      val scene = sceneWithLights()
      val camera = new PerspectiveCamera(60, innerWidth / innerHeight, 0.1, 10000)

      val renderer = webGLRenderer(innerWidth, innerHeight)
      cdm.backend.init(renderer)
//
//      val light = new DirectionalLight()
//      light.color = new Color(0xff0000)
//      light.position.set(0, 0, 100)
//
//
//      val light2 = new DirectionalLight()
//      light2.color = new Color(0x00ff00)
//      light2.position.set(0, 100, 0)
//
//
//      val light3 = new DirectionalLight()
//      light3.color = new Color(0x0000ff)
//      //light.position.set( 0, 1, 1 ).normalize()
//      //val lightPos2 = new Vector3(10, 11, 12)
//      light3.position.set(100, 0, 0)
//
//      scene.add(light)
//      scene.add(light2)
//      scene.add(light3)

      camera.position.z = 900
      camera.position.x = 0
      camera.position.y = 0

      new FontLoader().load("fonts/Old computer St_Regular.json", font => {
        println("Loaded font...")

        val objects = randomText(font, "MIKE", 100)

        for (obj <- objects) {
          scene.add(obj)
        }

        var theta: Double = 0
        var radius = 500

//        def postProcessing() = {
//          val scene = new Scene()
//          val camera = new OrthographicCamera( - 0.5, 0.5, 0.5, - 0.5, - 10000, 10000 )
//          camera.position.z = 100
//          scene.add(camera)
//
//          val renderTargetWidth: Double = 1000
//          val renderTargetHeight: Double = 1000
//
//
//          val pars = js.Dynamic.literal(
//            "minFilter" -> THREE.LinearFilter,
//            "magFilter" -> THREE.LinearFilter,
//            "format" -> THREE.RGBFormat
//          ).asInstanceOf[WebGLRenderTargetOptions]
//
//         // var pars = { minFilter: THREE.LinearFilter, magFilter: THREE.LinearFilter, format: THREE.RGBFormat };
//          val rtTextureColors = new WebGLRenderTarget( renderTargetWidth, renderTargetHeight, pars );
//
//
//        }

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
      })


      //      })
    })
    .build


  def apply() = component()
}
