import mikey.threejs.Font
import org.denigma.threejs.{BoxGeometry, Color, DirectionalLight, Material, Mesh, MeshBasicMaterial, MeshBasicMaterialParameters, MeshLambertMaterial, MeshLambertMaterialParameters, MeshPhongMaterial, MeshPhongMaterialParameters, Object3D, PerspectiveCamera, Scene, TextGeometry, TextGeometryParameters, TorusGeometry, Vector3, WebGLRenderer, WebGLRendererParameters}

import scala.scalajs.js

package object geomtest {

  def sceneWithLights() = {
    val scene = new Scene()
    val light = new DirectionalLight()
    light.color = new Color(0xff0000)
    light.position.set(0, 0, 100)

    val light2 = new DirectionalLight()
    light2.color = new Color(0x00ff00)
    light2.position.set(0, 100, 0)

    val light3 = new DirectionalLight()
    light3.color = new Color(0x0000ff)
    light3.position.set(100, 0, 0)

    scene.add(light)
    scene.add(light2)
    scene.add(light3)

    scene
  }

  def randomMeshLambert() = {
    val meshLambertMaterialParameters = js.Dynamic.literal(
      "color" -> (Math.random() * 0xffffff).toInt
    ).asInstanceOf[MeshLambertMaterialParameters]

    val material = new MeshLambertMaterial(meshLambertMaterialParameters)
    material
  }

  def randomCubes(n: Integer) = {
    var geometry = new BoxGeometry( 50, 50, 50 );

    val cubes = for (i <- 1 to n) yield {
      val material = randomMeshLambert()
      val obj = new Mesh(geometry, material)

      obj.position.x = Math.random() * 800 - 400
      obj.position.y = Math.random() * 800 - 400
      obj.position.z = Math.random() * 800 - 400

      obj.rotation.x = Math.random() * 2 * Math.PI
      obj.rotation.y = Math.random() * 2 * Math.PI
      obj.rotation.z = Math.random() * 2 * Math.PI

      //obj.scale.x = Math.random() + 0.5
      //obj.scale.y = Math.random() + 0.5
      //obj.scale.z = Math.random() + 0.5

      obj
    }

    cubes
  }

  def randomText(font: Font, textStr: String, n: Integer) = {

    val objects = for (i <- 1 to n) yield {
      val material = randomMeshLambert()

      val textGeometryParameters = js.Dynamic.literal(
         "font" -> font,
         "size" -> (50 + 50 * Math.random()),
         "height" -> (5 + 5 * Math.random())
      ).asInstanceOf[TextGeometryParameters]

      var textGeometry = new TextGeometry(textStr, textGeometryParameters)

      val obj = new Mesh(textGeometry, material)
      obj.position.x = Math.random() * 800 - 400
      obj.position.y = Math.random() * 800 - 400
      obj.position.z = Math.random() * 800 - 400

      obj.rotation.x = Math.random() * 2 * Math.PI
      obj.rotation.y = Math.random() * 2 * Math.PI
      obj.rotation.z = Math.random() * 2 * Math.PI

      obj.scale.x = Math.random() + 0.5
      obj.scale.y = Math.random() + 0.5
      obj.scale.z = Math.random() + 0.5

      obj
    }

    objects
  }

  def textMesh(font: Font, textStr: String) = {
    val textGeometryParameters = js.Dynamic.literal(
      "font" -> font,
      "size" -> 25,
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
      "size" -> 10,
      "height" -> 5
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
}
