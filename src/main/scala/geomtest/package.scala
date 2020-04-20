import threejs.{BoxGeometry, Font, Material, Mesh, MeshLambertMaterial, MeshLambertMaterialParameters, MeshPhongMaterial, MeshPhongMaterialParameters, TextGeometry, TextGeometryParameters, TorusGeometry}

import scala.scalajs.js

package object geomtest {

  def material69() = {
    val meshLambertMaterialParameters = js.Dynamic.literal(
      "color" -> (Math.random() * 0xffffff).toInt
    ).asInstanceOf[MeshLambertMaterialParameters]

    val material = new MeshLambertMaterial(meshLambertMaterialParameters)
    material
  }

  def randomCubes(n: Integer) = {
    var geometry = new BoxGeometry( 20, 20, 20 );

    val cubes = for (i <- 1 to n) yield {
      val material = material69()
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
