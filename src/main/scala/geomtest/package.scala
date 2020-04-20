import threejs.{Font, Material, Mesh, MeshPhongMaterial, MeshPhongMaterialParameters, TextGeometry, TextGeometryParameters, TorusGeometry}

import scala.scalajs.js

package object geomtest {

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
