import org.scalatest.funsuite.AnyFunSuite

object ScalaTestingStyles

class TestMethods extends AnyFunSuite {

  test("Strip a comma character from the string value") {
    val givenInput = "foo,"
    val expectedOutput = Some("foo")
    assert(stripSelectedStrings(givenInput) === expectedOutput)
  }

  test("Strip a semicolon character from the string value") {
    val givenInput = "bar;"
    val expectedOutput = Some("bar")
    assert(stripSelectedStrings(givenInput) === expectedOutput)
  }

  test("Strip method does not work with empty positional argument.") {
    val givenInput = ""
    assert(stripSelectedStrings(givenInput).isEmpty)
  }


  test("splitIntoSingleWords should split a string into words") {
    val inputText = "foo bar goo"
    val expectedOutput = Some(Array("foo", "bar", "goo"))
    assert(splitIntoSingleWords(inputText) == expectedOutput)
  }
}
