package akka.http.scaladsl.marshallers.boopickle

import java.nio.ByteBuffer

import akka.http.scaladsl.marshalling.PredefinedToEntityMarshallers
import akka.http.scaladsl.marshalling.ToEntityMarshaller
import akka.http.scaladsl.model.MediaType
import akka.http.scaladsl.model.MessageEntity
import akka.http.scaladsl.unmarshalling.FromEntityUnmarshaller
import akka.http.scaladsl.unmarshalling.PredefinedFromEntityUnmarshallers
import akka.http.scaladsl.model.ContentTypeRange
import boopickle.Default

trait BooPickleSupport extends PredefinedToEntityMarshallers with PredefinedFromEntityUnmarshallers {
  import Default._

  val booPickleMediaType = MediaType.applicationBinary("boopickle", MediaType.Compressible, "boopickle")

  implicit def booPickleMarshaller[T](implicit pickler: Pickler[T]): ToEntityMarshaller[T] = ByteArrayMarshaller.wrap(booPickleMediaType) {
    in: T ⇒ Pickle.intoBytes(in).array()
  }

  implicit def booPickleUnmarshaller[T](implicit pickler: Pickler[T]): FromEntityUnmarshaller[T] = byteArrayUnmarshaller.forContentTypes(ContentTypeRange(booPickleMediaType.toContentType)).map {
    in: Array[Byte] ⇒ Unpickle[T].fromBytes(ByteBuffer.wrap(in))
  }
}
