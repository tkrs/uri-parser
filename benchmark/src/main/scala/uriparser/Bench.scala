package uriparser

import java.net.{URI, URL}

import org.openjdk.jmh.annotations._

import scala.concurrent.duration._

@State(Scope.Thread)
@BenchmarkMode(Array(Mode.Throughput))
@Warmup(iterations = 10, time = 1)
@Measurement(iterations = 10, time = 1)
@OutputTimeUnit(SECONDS)
@Fork(2)
class Bench {

  private[this] val googleUriString =
    "https://www.google.com/search?q=%E6%BC%AB%E7%94%BB+%E3%81%8A%E3%81%99%E3%81%99%E3%82%81&oq=%E6%BC%AB%E7%94%BB+%E3%81%8A%E3%81%99%E3%81%99%E3%82%81&aqs=chrome..69i57j0l5.9590j0j9&sourceid=chrome&ie=UTF-8"

  @Benchmark
  def myParser(): Uri = {
    Uri.parse(googleUriString) match {
      case Right(v) => v
      case Left(e) => throw new Exception(e)
    }
  }

  @Benchmark
  def javaURIParser(): URI = {
    URI.create(googleUriString)
  }

  @Benchmark
  def javaURLParser(): URL = {
    new URL(googleUriString)
  }
}
