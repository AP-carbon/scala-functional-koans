package scalafunctionalkoans

import scalafunctionalkoans.support.BlankValues._
import scalafunctionalkoans.support.KoanSuite

/*
PARTIAL FUNCTIONS

Most functions are not defined for every value they take as parameter.
For example, square root is only defined for positive values.

In Scala, a partial function is a function that can anwser
the 'are you defined for this value?' question.
There is a strong relationship between pattern matching
and partial functions (examples coming).
*/
class AboutPartialFunctions extends KoanSuite {

  koan("A partial function has to explain for which values it's defined") {
    val inverse: PartialFunction[Int, Double] = new PartialFunction[Int, Double] {
      // Inverse is only defined for non-zero values
      def isDefinedAt(n: Int): Boolean = n != 0

      // This is how we calculate the inverse of n
      def apply(n: Int): Double = 1.0 / n
    }

    inverse.isDefinedAt(0) should be (false)
    inverse.isDefinedAt(2) should be (true)
    inverse(2) should be (.5)
  }

  koan(
    """A partial function can be defined by a set of `case`
      | that look a lot like a pattern matching expression,
      | but without the `match` keyword and what's just before.
      | Such a partial function will then be only defined
      | for values that match one of the cases.""") {

    val label: PartialFunction[Int, String] = {
      case 1 => "One"
      case 2 => "Two"
      case 3 => "Three"
    }

    label.isDefinedAt(1) should be (true)
    label.isDefinedAt(2) should be (true)
    label.isDefinedAt(3) should be (true)
    label.isDefinedAt(100) should be (false)

    label(1) should be ("One")
    label(2) should be ("Two")
    label(3) should be ("Three")
  }

  koan(
    """A partial function can actually use
      | the full power of pattern matching without limitation""") {

    case class Dog(name: String, age: Int)

    val label: PartialFunction[Dog, String] = {
      case Dog("Rex", 10) => "My good old Rex"
      case Dog("Volt", age) if age > 0 && age <= 5 => "Young dog called Volt"
    }

    label.isDefinedAt(Dog("Rex", 10)) should be (true)
    label.isDefinedAt(Dog("Rex", 13)) should be (false)
    label.isDefinedAt(Dog("Volt", 3)) should be (true)
    label.isDefinedAt(Dog("Volt", 10)) should be (false)

    label(Dog("Rex", 10)) should be ("My good old Rex")
    label(Dog("Volt", 3)) should be ("Young dog called Volt")
  }
}
