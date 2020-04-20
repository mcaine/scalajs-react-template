package scalajsreact.template.pages

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import mikey.threejs.{Font, FontLoader}
import org.denigma.threejs.{BoxGeometry, Color, DirectionalLight, Material, Mesh, MeshBasicMaterial, MeshBasicMaterialParameters, MeshLambertMaterial, MeshLambertMaterialParameters, MeshPhongMaterial, MeshPhongMaterialParameters, Object3D, PerspectiveCamera, Scene, TextGeometry, TextGeometryParameters, TextureLoader, TorusGeometry, Vector3, WebGLRenderer, WebGLRendererParameters}
import org.scalajs.dom
import org.scalajs.dom.html

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
      "height" -> 20
    ).asInstanceOf[TextGeometryParameters]

    var textGeometry = new TextGeometry(textStr, textGeometryParameters)

    var text = new Mesh(textGeometry, material)
    text
  }

  def torushMeshFromMaterial(material: Material) = {

    val torusGeometry = new TorusGeometry(10, 3, 16, 100);

    var mesh = new Mesh(torusGeometry, material);
    mesh
  }

  def meshPhongMaterial() = {
    val meshPhongMaterialParameters = js.Dynamic.literal(
      "color" -> 0xaaaaaa,
      "specular" -> 0x773333,
      "shininess" -> 50
    ).asInstanceOf[MeshPhongMaterialParameters]
    val meshPhongMaterial = new MeshPhongMaterial(meshPhongMaterialParameters)
    meshPhongMaterial
  }

  val component = ScalaComponent.builder[Unit]("BitmapPage")
    .initialState(State("Initial State for BitmapPage"))
    .renderBackend[Backend]
    .componentDidMount(cdm => Callback {

      val window = dom.window
      println("innerWidth is " + window.innerWidth)
      println(s"innerHeight is ${window.innerHeight}")

      val innerWidth = window.innerWidth.toLong
      val innerHeight = window.innerHeight.toLong

      val scene = new Scene()
      val camera = new PerspectiveCamera(120, innerWidth / innerHeight, 0.1, 1000)

      val renderer = webGLRenderer(innerWidth, innerHeight)
      cdm.backend.init(renderer)

      val light = new DirectionalLight()
      light.color = new Color(0xaa7700)
      //light.position.set( 0, 1, 1 ).normalize()
      val lightPos = new Vector3(0, 1, 1)
      light.position.set(0, 0, 10)
      scene.add(light)

      camera.position.z = 40
      camera.position.x = 0
      camera.position.y = 0

      new FontLoader().load("fonts/Pacifico_Regular.json", font => {
        println("Loaded font...")

        new TextureLoader().load("images/tup.jpg", texture => {
          println("Loaded the texture bitmap")

          val material = new MeshBasicMaterial(js.Dynamic.literal("map" -> texture).asInstanceOf[MeshBasicMaterialParameters])

          val objects: Seq[Object3D] = Seq(
            textMeshWithMaterial(font, "i do like it", material),
            //torushMeshFromMaterial(meshPhongMaterial())
            torushMeshFromMaterial(material)
          )

          for (obj <- objects) {
            scene.add(obj)
          }

          renderer.render(scene, camera)
        })
      })
    })
    .build

  def apply() = component()
}
