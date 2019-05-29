package uriparser

import java.util.StringTokenizer
import java.net.URLDecoder
import java.nio.charset.StandardCharsets.UTF_8

import fastparse.Parsed.{Failure, Success}

import scala.collection.mutable

final case class Uri(scheme: String = "",
                     authority: Option[Authority] = None,
                     path: String = "",
                     query: Option[String] = None,
                     fragment: Option[String] = None) {

  def decodedQuery: Map[String, Seq[String]] = query match {
    case Some(s) if s.isEmpty =>
      Map.empty
    case Some(s) =>
      val tokenizer = new StringTokenizer(s, "&")

      val m = mutable.Map.empty[String, Seq[String]]

      def go(): Unit =
        if (!tokenizer.hasMoreTokens)
          ()
        else {
          val kv     = tokenizer.nextToken()
          val (k, v) = kv.span(_ != '=')
          val kk     = if (k.isEmpty) "" else URLDecoder.decode(k, UTF_8)
          val vv     = if (v.isEmpty) "" else URLDecoder.decode(v.tail, UTF_8)

          val vvv = m.getOrElse(kk, Nil)
          m.update(kk, vvv :+ vv)
          go()
        }

      go()

      m.toMap

    case _ =>
      Map.empty
  }

}

object Uri {
  val empty = Uri()

  def parse(s: String): Either[String, Uri] = fastparse.parse(s, UriParser.grammar(_)) match {
    case Success(value, _)    => Right(value)
    case t @ Failure(_, _, _) => Left("Failed to parse the URI: \"" + s + "\", cause: " + t.msg)
  }
}

final case class Authority(userInfo: Option[String], host: String, port: Option[Int])
final case class Query(raw: String, decoded: Map[String, Seq[String]])
