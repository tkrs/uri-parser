package uriparser

import fastparse.Parsed.Success
import fastparse._
import org.scalatest.FunSpec

class UriParserSpec extends FunSpec {

  describe("grammar") {
    it("should be parsed a URI string") {
      val ts = List(
        "g:h",
        "g",
        "./g",
        "g/",
        "/g",
        "//g",
        "?y",
        "g?y",
        "#s",
        "g#s",
        "g?y#s",
        ";x",
        "g;x",
        "g;x?y#s",
        "g;x?y=1#s",
        "g;x?y=1&x=2#s",
        "g+.-://a.b.c/x?y=1?&x=2/#s/?",
        "g+.-://f7s0aj@a.b.c/x?y=1?&x=2/#s/?",
        "",
        ".",
        "./",
        "src/test",
        "../",
        "../g",
        "src",
        "src",
        "../../g",
        "http://x_y_z.com",
        "proto://x_y_z.com/a_?x=1#yul",
        "/x.y.z.com/o"
      )

      ts.foreach { t =>
        val Success(uri, _) = parse(t, UriParser.grammar(_))
        println(uri)
      }
    }
  }
}
