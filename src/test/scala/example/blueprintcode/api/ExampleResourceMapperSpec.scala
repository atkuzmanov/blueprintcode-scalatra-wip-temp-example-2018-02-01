package example.blueprintcode.api

import org.scalatest.{MustMatchers, FlatSpec}

class ExampleResourceMapperSpec extends FlatSpec with MustMatchers {

  "ExampleResourceMapper" should "get css content type from css file" in {
    val actual = ExampleResourceMapper.exampleGetContentType("src/more.something/something.css")
    actual mustBe "text/css"
  }

  it should "return 'text/plain' for unrecognized file types" in {
    val actual = ExampleResourceMapper.exampleGetContentType("src/more.something/something.xxx")
    actual mustBe "text/plain"
  }

  it should "handle empty input" in {
    val actual = ExampleResourceMapper.exampleGetContentType("")
    actual mustBe "text/plain"
  }

  it should "get script type for something.js file" in {
    val actual = ExampleResourceMapper.exampleGetContentType("src/more.something/something.js")
    actual mustBe "application/x-javascript"
  }

  it should "get image type for *.gif file" in {
    val actual = ExampleResourceMapper.exampleGetContentType("images/.something.gif")
    actual mustBe "image/gif"
  }

  it should "get image type for jpg" in {
    val actual = ExampleResourceMapper.exampleGetContentType("images/.something.jpg")
    actual mustBe "image/jpeg"
  }

  it should "get impage type for jpeg" in {
    val actual = ExampleResourceMapper.exampleGetContentType("images/.something.jpeg")
    actual mustBe "image/jpeg"
  }

  it should "get image type for png" in {
    val actual = ExampleResourceMapper.exampleGetContentType("/something.png")
    actual mustBe "image/png"
  }
}

