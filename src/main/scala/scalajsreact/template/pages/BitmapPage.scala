package scalajsreact.template.pages

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.html
import threejs._

import scala.scalajs.js

object BitmapPage {

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

  def textMeshWithMaterial(font: Font, textStr: String, material: Material) = {
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


    var text = new Mesh(textGeometry, material)
    text
  }

  val component = ScalaComponent.builder[Unit]("BitmapPage")
    .initialState(State("Initial State for BitmapPage"))
    .renderBackend[Backend]
    .componentDidMount(cdm => Callback {
      val innerWidth = 1900
      val innerHeight = 768

      val scene = new Scene()
      val camera = new PerspectiveCamera(60, innerWidth / innerHeight, 0.1, 1000)

      val renderer = webGLRenderer(innerWidth, innerHeight)
      cdm.backend.init(renderer)

      val light = new DirectionalLight()
      light.color = new threejs.Color(0xaa7700)
      //light.position.set( 0, 1, 1 ).normalize()
      val lightPos = new Vector3(0, 1, 1)
      light.position.set(0, 0, 10)
      scene.add(light)

      camera.position.z = 40
      camera.position.x = 34
      camera.position.y = 5

      //addImageBitmap(,  )

      val fontLoader = new FontLoader()
      fontLoader.load("fonts/Pacifico_Regular.json", font => {
        //println("Loaded font...")
        new TextureLoader().load("images/tup.jpg", texture => {
            //println("Loaded the texture bitmap")
            val material = new MeshBasicMaterial( js.Dynamic.literal( "map" -> texture ).asInstanceOf[MeshBasicMaterialParameters] )
            scene.add(textMeshWithMaterial(font, "wtf Bitmap page", material))
            renderer.render(scene, camera)
          })
      })
    })
    .build

  def apply() = component()
}
