package scalajsreact.template.pages

import japgolly.scalajs.react.{CtorType, _}
import japgolly.scalajs.react.component.Scala.Component
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom
import org.scalajs.dom.raw.HTMLElement
import scalacss.DevDefaults._
import scalacss.ScalaCssReact._
import threejs.{BoxGeometry, DirectionalLight, FontLoader, Mesh, MeshBasicMaterial, MeshPhongMaterial, MeshPhongMaterialParameters, PerspectiveCamera, Scene, TextGeometry, TextGeometryParameters, Vector3, WebGLRenderer, WebGLRendererParameters}

import scala.scalajs.js

object AnotherPage {

  object Style extends StyleSheet.Inline {
    import dsl._
    val content: StyleA = style(textAlign.center,
                        fontSize(50.px),
                        minHeight(450.px),
                        paddingTop(40.px))
  }

  val component: Component[Unit, Unit, Unit, CtorType.Nullary] =

    ScalaComponent.builder[Unit]("TodoList")
      //.initialState(State(Nil, ""))
      .render_P(p => <.div(^.id:="mikey", Style.content, "MORE STUFF FROM MIKE"))
      .componentDidMount(s => Callback.lift(() => {
        println("ANOTHER PAGE componentDidMount()")

        val innerWidth = 1024
        val innerHeight = 768


        val scene = new Scene()
        val camera = new PerspectiveCamera(75, innerWidth / innerHeight, 0.1, 1000)
        //var camera = new PerspectiveCamera( 75, window.innerWidth/window.innerHeight, 0.1, 1000 );

        val webGLRendererParameters = js.Dynamic.literal(
          "antialias" -> true
        ).asInstanceOf[WebGLRendererParameters]

        val renderer: WebGLRenderer = new WebGLRenderer(webGLRendererParameters)
        renderer.setSize(innerWidth, innerHeight);

        val gameHTMLContainer = dom.document.getElementById("mikey").asInstanceOf[HTMLElement]

        //this.mount.appendChild( renderer.domElement )
        gameHTMLContainer.appendChild(renderer.domElement)

        val geometry = new BoxGeometry(1, 1, 1)
        //val geometry2 = new TorusKnotGeometry(2.5);
        val geometry2 = new BoxGeometry(1, 1, 1)
        val material = new MeshBasicMaterial()
        material.color = new threejs.Color(0xffff00)

        val fontLoader = new FontLoader()



        //fontLoader.load("https://rawgit.com/mrdoob/three.js/dev/examples/fonts/helvetiker_regular.typeface.json", (f: threejs.Font) => {
        //fontLoader.load("fonts/helvetiker_regular.typeface.json", (f: threejs.Font) => {
        fontLoader.load("fonts/Pacifico_Regular.json", (f: threejs.Font) => {

          println("Loaded font...")

          val textGeometryParameters = js.Dynamic.literal(
            "font" -> f,
            "size" -> 2.5,
            "height" -> 2
          ).asInstanceOf[TextGeometryParameters]

          var textGeometry = new TextGeometry("Mike is the Best!", textGeometryParameters)


          val meshPhongMaterialParameters = js.Dynamic.literal(
            "color" -> 0xaaaaaa,
            "specular" -> 0x773333,
            "shininess" -> 50
          ).asInstanceOf[MeshPhongMaterialParameters]

          var textMaterial = new MeshPhongMaterial(meshPhongMaterialParameters)

          var text = new Mesh(textGeometry, textMaterial)
          scene.add(text)

          val light = new DirectionalLight();
          light.color = new threejs.Color(0xaa7700)
          //light.position.set( 0, 1, 1 ).normalize()
          val lightPos = new Vector3(0, 1, 1)
          light.position.set(0, 0, 10)
          scene.add(light)

          camera.position.z = 30;
          camera.position.x = 10;
          camera.position.y = 5;

          renderer.render(scene, camera);
        })


      })).build

    //ScalaComponent.builder
    //  .static("AnotherPage")(<.div(^.id:="mikey", Style.content, "Another page by MIKE"))
    //  .build

  def apply() = component()
}
