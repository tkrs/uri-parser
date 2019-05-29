package uriparser

import java.net.URLDecoder
import java.nio.charset.StandardCharsets.UTF_8
import java.util.StringTokenizer

import fastparse._
import fastparse.NoWhitespace._
import fastparse.Parsed.{Failure, Success}

import scala.collection.mutable

/**
  * [[https://tools.ietf.org/html/rfc3986 rfc3986]]
  */
object UriParser {

  def grammar[_: P]: P[Uri] = P(uriReference ~ End)

  def uri[_: P]: P[Uri] =
    P(scheme ~ ":" ~ hierPart ~ ("?" ~ query).? ~ ("#" ~ fragment).?).map {
      case (scheme, (authority, path), query, fragment) =>
        Uri(scheme, authority, path, query, fragment)
    }

  def hierPart[_: P]: P[(Option[Authority], String)] =
    P(
      ("//" ~ authority.map(Some(_)) ~ pathAbempty) |
        pathAbsolute.map(v => None -> v) |
        pathRootless.map(v => None -> v) |
        pathEmpty.map(v => None    -> v))

  def uriReference[_: P]: P[Uri] = P(uri | relativeRef)

  // def absoluteUri[_: P]: P[Uri] =
  //   P(scheme ~ ":" ~ hierPart ~ ("?" ~ query).?).map {
  //     case (scheme, (authority, path), query) =>
  //       Uri(scheme, authority, path, query, None)
  //   }

  def relativeRef[_: P]: P[Uri] =
    P(relativePart ~ ("?" ~ query).? ~ ("#" ~ fragment).?).map {
      case (authority, path, query, fragment) =>
        Uri("", authority, path, query, fragment)
    }

  def relativePart[_: P]: P[(Option[Authority], String)] =
    P(
      ("//" ~ authority.map(Some(_)) ~ pathAbempty) |
        pathAbsolute.map(v => None -> v) |
        pathNoscheme.map(v => None -> v) |
        pathEmpty.map(v => None    -> v))

  def pathAbempty[_: P]: P[String]  = P(("/" ~ segment).rep.!)
  def pathAbsolute[_: P]: P[String] = P(("/" ~ (segmentNZ ~ ("/" ~ segment).rep).?).!)
  def pathNoscheme[_: P]: P[String] = P((segmentNZNC ~ ("/" ~ segment).rep).!)
  def pathRootless[_: P]: P[String] = P((segmentNZ ~ ("/" ~ segment).rep).!)
  def pathEmpty[_: P]: P[String]    = P("".!)

  // format: off
  def ipv6address[_: P]: P[Unit] = P(
      (                                            (h16 ~ ":").rep(min = 6, max = 6) ~ ls32) |
      (                                     "::" ~ (h16 ~ ":").rep(min = 5, max = 5) ~ ls32) |
      (                            h16 .? ~ "::" ~ (h16 ~ ":").rep(min = 4, max = 4) ~ ls32) |
      (((h16 ~ ":").rep(max = 1) ~ h16).? ~ "::" ~ (h16 ~ ":").rep(min = 3, max = 3) ~ ls32) |
      (((h16 ~ ":").rep(max = 2) ~ h16).? ~ "::" ~ (h16 ~ ":").rep(min = 2, max = 2) ~ ls32) |
      (((h16 ~ ":").rep(max = 3) ~ h16).? ~ "::" ~  h16 ~ ":"                        ~ ls32) |
      (((h16 ~ ":").rep(max = 4) ~ h16).? ~ "::" ~                                     ls32) |
      (((h16 ~ ":").rep(max = 5) ~ h16).? ~ "::" ~                                     h16 ) |
      (((h16 ~ ":").rep(max = 6) ~ h16).? ~ "::"                                           )
  )
  // format: on

  def ipvFuture[_: P]: P[Unit]      = P("v" ~ hexDig.rep(1) ~ "." ~ (unreserved | subDelims | ":").rep(1))
  def ipLiteral[_: P]: P[String]    = P("[" ~ (ipv6address | ipvFuture).! ~ "]")
  def ipv4address[_: P]: P[Unit]    = P(decOctet ~ "." ~ decOctet ~ "." ~ decOctet ~ "." ~ decOctet)
  def regName[_: P]: P[Unit]        = P((unreserved | pctEncoded | subDelims).rep)
  def host[_: P]: P[String]         = P(ipLiteral | ipv4address.! | regName.!)
  def port[_: P]: P[Int]            = P(digit.rep.!.map(_.toInt))
  def userInfo[_: P]: P[String]     = P((unreserved | pctEncoded | subDelims | ":").rep(1).!)
  def authority[_: P]: P[Authority] = P((userInfo ~ "@").? ~ host ~ (":" ~ port).?).map((Authority.apply _).tupled)
  def query[_: P]: P[Query]         = P((pchar | "/" | "?").rep.!.map(decodeQuery))
  def scheme[_: P]: P[String]       = P((alpha ~ (alpha | digit | "+" | "-" | ".").rep(1).?).!)
  def fragment[_: P]: P[String]     = P((pchar | "/" | "?").rep.!)

  def decOctet[_: P]: P[Unit] =
    P(
      digit |
        (CharIn("1-9") ~ digit) |
        (CharIn("1") ~ digit ~ digit) |
        (CharIn("2") ~ CharIn("0-4") ~ digit) |
        (CharIn("2") ~ CharIn("5") ~ CharIn("0-5")))

  def h16[_: P]: P[Unit]  = P(hexDig.rep(min = 1, max = 4))
  def ls32[_: P]: P[Unit] = P((h16 ~ ":" ~ h16) | ipv4address)

  def digit[_: P]: P[Unit]      = P(CharIn("0-9"))
  def alpha[_: P]: P[Unit]      = P(CharIn("a-zA-Z"))
  def hexDig[_: P]: P[Unit]     = P(digit | CharIn("a-fA-F"))
  def unreserved[_: P]: P[Unit] = P(alpha | digit | "-" | "." | "_" | "~")
  def pctEncoded[_: P]: P[Unit] = P("%" ~ hexDig ~ hexDig)
  def subDelims[_: P]: P[Unit]  = P("!" | "$" | "&" | "'" | "(" | ")" | "*" | "+" | "," | ";" | "=")

  def pchar[_: P]: P[Unit]       = P(unreserved | pctEncoded | subDelims | ":" | "@")
  def segment[_: P]: P[Unit]     = P(pchar.rep)
  def segmentNZ[_: P]: P[Unit]   = P(pchar.rep(1))
  def segmentNZNC[_: P]: P[Unit] = P((unreserved | pctEncoded | subDelims | "@").rep(1))

  private def decodeQuery(s: String): Query = {
    val tokenizer = new StringTokenizer(s, "&")

    def go(acc: mutable.Builder[(String, String), Map[String, String]]): Map[String, String] =
      if (!tokenizer.hasMoreTokens)
        acc.result()
      else {
        val kv     = tokenizer.nextToken()
        val (k, v) = kv.span(_ != '=')
        val kk     = if (k.isEmpty) "" else URLDecoder.decode(k, UTF_8)
        val vv     = if (v.isEmpty) "" else URLDecoder.decode(v.tail, UTF_8)
        go(acc += kk -> vv)
      }

    Query(s, go(Map.newBuilder))
  }

}

final case class Uri(scheme: String = "",
                     authority: Option[Authority] = None,
                     path: String = "",
                     query: Option[Query] = None,
                     fragment: Option[String] = None)

object Uri {
  val empty = Uri()

  def parse(s: String): Either[String, Uri] = fastparse.parse(s, UriParser.grammar(_)) match {
    case Success(value, _)    => Right(value)
    case t @ Failure(_, _, _) => Left("Failed to parse the URI: \"" + s + "\", cause: " + t.msg)
  }
}

final case class Authority(userInfo: Option[String], host: String, port: Option[Int])
final case class Query(raw: String, decoded: Map[String, String])
