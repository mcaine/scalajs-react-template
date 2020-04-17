package scalajsreact.template.pages

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.html
import scalacss.DevDefaults._
import threejs._

import scala.scalajs.js

object YetAnotherPage {

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
        <.div(^.id := "mikeyx").withRef(outerRef)
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

  def textMesh(font: Font, textStr: String) = {
    val textGeometryParameters = js.Dynamic.literal(
      "font" -> font,
      "size" -> 5,
      "height" -> 5
    ).asInstanceOf[TextGeometryParameters]

    var textGeometry = new TextGeometry(textStr, textGeometryParameters)

    val meshPhongMaterialParameters = js.Dynamic.literal(
      "color" -> 0xaaaaaa,
      "specular" -> 0x773333,
      "shininess" -> 50
    ).asInstanceOf[MeshPhongMaterialParameters]

    var textMaterial = new MeshPhongMaterial(meshPhongMaterialParameters)
    var text = new Mesh(textGeometry, textMaterial)
    text
  }

  val component = ScalaComponent.builder[Unit]("YetAnother")
    .initialState(State("Initial State for YetAnother"))
    .renderBackend[Backend]
    .componentDidMount(cdm => Callback {
      val innerWidth = 1900
      val innerHeight = 768

      val scene = new Scene()
      val camera = new PerspectiveCamera(30, innerWidth / innerHeight, 0.1, 1000)

      val renderer = webGLRenderer(innerWidth, innerHeight)
      cdm.backend.init(renderer)


      val light = new DirectionalLight();
      light.color = new threejs.Color(0xaa7700)
      //light.position.set( 0, 1, 1 ).normalize()
      val lightPos = new Vector3(0, 1, 1)
      light.position.set(0, 0, 10)
      scene.add(light)

      camera.position.z = 50;
      camera.position.x = 34;
      camera.position.y = 5;

      val fontLoader = new FontLoader()
      fontLoader.load("fonts/Old computer St_Regular.json", font => {
        println("Loaded font...")
        scene.add(textMesh(font, "another yet page"))
        renderer.render(scene, camera);
      })
    })
    .build

  def apply() = component()
}
