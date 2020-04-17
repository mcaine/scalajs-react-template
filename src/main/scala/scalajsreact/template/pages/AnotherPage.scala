package scalajsreact.template.pages

import japgolly.scalajs.react.{CtorType, _}
import japgolly.scalajs.react.component.Scala.Component
import japgolly.scalajs.react.vdom.html_<^._
import scalacss.DevDefaults._
import scalacss.ScalaCssReact._

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
      })).build

    //ScalaComponent.builder
    //  .static("AnotherPage")(<.div(^.id:="mikey", Style.content, "Another page by MIKE"))
    //  .build

  def apply() = component()
}
