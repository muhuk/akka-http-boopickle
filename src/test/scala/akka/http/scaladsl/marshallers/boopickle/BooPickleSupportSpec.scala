package akka.http.scaladsl.marshallers.boopickle

import akka.http.scaladsl.marshalling.ToResponseMarshallable.apply
import akka.http.scaladsl.model.ContentTypes
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.server.Directive.addByNameNullaryApply
import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.server.UnsupportedRequestContentTypeRejection
import akka.http.scaladsl.testkit.ScalatestRouteTest
import boopickle.Default.generatePickler
import boopickle.Default.longPickler
import boopickle.Default.stringPickler
import boopickle.Default.Pickle
import boopickle.Default.Unpickle
import boopickle.Pickler
import org.scalatest.Finders
import org.scalatest.Matchers
import org.scalatest.WordSpec

class BooPickleSupportTest extends WordSpec with Matchers with ScalatestRouteTest with BooPickleSupport with Directives {
  case class Foo(bar: String, baz: Long)

  implicit val fooPickler: Pickler[Foo] = generatePickler[Foo]

  val booPickleContentType = booPickleMediaType.toContentType

  val testRoutes = get {
    complete(Foo("bat", 42L))
  } ~ post {
    entity(as[Foo]) {
      case Foo(bar, baz) ⇒ complete(Foo(bar + "!", baz + 1))
    }
  }

  "marshal" in {
    Get() ~> testRoutes ~> check {
      responseAs[Array[Byte]] shouldEqual Pickle.intoBytes(Foo("bat", 42L)).array()
      contentType shouldEqual booPickleContentType
    }
  }

  "unmarshal" in {
    Post("/", HttpEntity(booPickleContentType, Pickle.intoBytes(Foo("hi", 2L)).array())) ~> testRoutes ~> check {
      responseAs[Array[Byte]] shouldEqual Pickle.intoBytes(Foo("hi!", 3L)).array()
    }
  }

  "unmarshall only booPickleMediaType" in {
    Post("/", HttpEntity(ContentTypes.`application/octet-stream`, Pickle.intoBytes(Foo("hi", 2L)).array())) ~> testRoutes ~> check {
      rejection.isInstanceOf[UnsupportedRequestContentTypeRejection]
      rejection.asInstanceOf[UnsupportedRequestContentTypeRejection].getSupported.size() shouldEqual 1
      rejection.asInstanceOf[UnsupportedRequestContentTypeRejection].getSupported.iterator.next.matches(booPickleContentType)
    }
  }
}
