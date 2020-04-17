package scalajsreact.template.components.items

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object Item2Data {

  val component =
    ScalaComponent.builder.static("Item2")(<.div("This is Item2 Page and i have edited it")).build

  def apply() = component().vdomElement
}
