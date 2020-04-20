package scalajsreact.template.pages

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.html
import scalacss.DevDefaults._
import org.denigma.threejs.{BoxGeometry, Color, DirectionalLight, Material, Mesh, MeshBasicMaterial, MeshBasicMaterialParameters, MeshLambertMaterial, MeshLambertMaterialParameters, MeshPhongMaterial, MeshPhongMaterialParameters, Object3D, PerspectiveCamera, Scene, TextGeometry, TextGeometryParameters, TorusGeometry, Vector3, WebGLRenderer, WebGLRendererParameters}
import mikey.threejs.FontLoader
import mikey.threejs.Font

import scala.scalajs.js

object YetAnotherPage {

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
      val camera = new PerspectiveCamera(60, innerWidth / innerHeight, 0.1, 1000)

      val renderer = webGLRenderer(innerWidth, innerHeight)
      cdm.backend.init(renderer)

      val light = new DirectionalLight()
      light.color = new Color(0xaa7700)
      //light.position.set( 0, 1, 1 ).normalize()
      val lightPos = new Vector3(0, 1, 1)
      light.position.set(0, 0, 10)
      scene.add(light)

      camera.position.z = 20
      camera.position.x = 34
      camera.position.y = 5

      val fontLoader = new FontLoader()
      fontLoader.load("fonts/Old computer St_Regular.json", font => {
        println("Loaded font...")
        scene.add(textMesh(font, "yet another page"))
        renderer.render(scene, camera)
      })
    })
    .build

  def apply() = component()
}
