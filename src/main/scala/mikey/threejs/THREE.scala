package mikey.threejs

// Pure guesswork all of this, needs doing correctly :-/

import org.denigma.threejs.{BufferGeometry, ExtrudeGeometry, Geometry, Shape, TextGeometryParameters}

import scala.scalajs.js
import scala.scalajs.js.annotation._

@js.native
@JSGlobal("THREE.FontLoader")
class FontLoader extends js.Object {
  def this(showStatus: Boolean = js.native) = this()
  def load(url: String, onLoad: js.Function1[Font, Unit] = js.native, onProgress: js.Function1[js.Any, Unit] = js.native, onError: js.Function1[js.Any, Unit] = js.native): Font = js.native
  def parse(json: String): js.Dynamic = js.native
  var onLoad: js.Function0[Unit] = js.native
  var onProgress: js.Function3[js.Any, Double, Double, Unit] = js.native
  var onError: js.Function0[Unit] = js.native
  //def isFont = true
}

@js.native
@JSGlobal("THREE.Font")
class Font extends js.Object {
}

@js.native
@JSName("THREE.BoxBufferGeometry")
class BoxBufferGeometry extends BufferGeometry {
  def this(width: Double, height: Double, depth: Double, widthSegments: Double = js.native, heightSegments: Double = js.native, depthSegments: Double = js.native) = this()
  var parameters: js.Any = js.native
  var widthSegments: Double = js.native
  var heightSegments: Double = js.native
  var depthSegments: Double = js.native
}

@js.native
@JSName("THREE.ConeBufferGeometry")
class ConeBufferGeometry extends BufferGeometry {
  def this(width: Double, height: Double, depth: Double) = this()
}

@js.native
@JSName("THREE.TextBufferGeometry")
class TextBufferGeometry extends ExtrudeBufferGeometry {
  def this(text: String, TextGeometryParameters: TextGeometryParameters = js.native) = this()
}

@js.native
@JSName("THREE.ExtrudeBufferGeometry")
class ExtrudeBufferGeometry extends Geometry {
  def this(shape: Shape) = this()
  def this(shape: Shape, options: js.Any) = this()
  def this(shapes: js.Array[Shape]) = this()
  def this(shapes: js.Array[Shape], options: js.Any) = this()

  def addShapeList(shapes: js.Array[Shape], options: js.Any = js.native): Unit = js.native
  def addShape(shape: Shape, options: js.Any = js.native): Unit = js.native
}