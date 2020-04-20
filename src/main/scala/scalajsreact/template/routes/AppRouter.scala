package scalajsreact.template.routes

import scalajsreact.template.components.{Footer, TopNav}
import scalajsreact.template.models.Menu
import scalajsreact.template.pages.{AnimationTestPage, AnotherPage, BitmapPage, GeometryTestPage, HomePage, YetAnotherPage}
import japgolly.scalajs.react.extra.router.{Resolution, RouterConfigDsl, RouterCtl, _}
import japgolly.scalajs.react.vdom.html_<^._

object AppRouter {

  sealed trait AppPage

  case object Home extends AppPage
  case class Items(p: Item) extends AppPage
  case object Another extends AppPage
  case object Geometry extends AppPage
  case object Animation extends AppPage

  val config = RouterConfigDsl[AppPage].buildConfig { dsl =>
    import dsl._

    val itemRoutes: Rule =
      Item.routes.prefixPath_/("#items").pmap[AppPage](Items) {
        case Items(p) => p
      }

    (trimSlashes
      | staticRoute(root, Home) ~> render(HomePage())
      | staticRoute(Path("#another"), Another) ~> render(BitmapPage())
      | staticRoute(Path("#geometry"), Geometry) ~> render(GeometryTestPage())
      | staticRoute(Path("#animation"), Animation) ~> render(AnimationTestPage())
      | itemRoutes)
      .notFound(redirectToPage(Home)(Redirect.Replace))
      .renderWith(layout)
  }

  val mainMenu = Vector(
    Menu("Home", Home),
    Menu("Items", Items(Item.Info)),
    Menu("Another", Another),
    Menu("Geometry", Geometry),
    Menu("Animate", Animation)
  )

  def layout(c: RouterCtl[AppPage], r: Resolution[AppPage]) =
    <.div(
      TopNav(TopNav.Props(mainMenu, r.page, c)),
      r.render(),
      Footer()
    )

  val baseUrl = BaseUrl.fromWindowOrigin / "index.html"

  val router = Router(baseUrl, config)
}
