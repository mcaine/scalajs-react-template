package mikey.threejs

import scala.scalajs.js
import scala.scalajs.js.annotation._

@JSGlobal("THREE.FontLoader")
class FontLoader extends js.Object {
  def this(showStatus: Boolean = js.native) = this()
  def load(url: String, onLoad: js.Function1[Font, Unit] = js.native, onProgress: js.Function1[js.Any, Unit] = js.native, onError: js.Function1[js.Any, Unit] = js.native): Font = js.native
  def parse(json: String): js.Dynamic = js.native
  var onLoad: js.Function0[Unit] = js.native
  var onProgress: js.Function3[js.Any, Double, Double, Unit] = js.native
  var onError: js.Function0[Unit] = js.native
  def isFont = true
}

@JSGlobal("THREE.Font")
class Font extends js.Object {
}
