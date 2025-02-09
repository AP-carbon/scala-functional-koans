package scalafunctionalkoans

import scalafunctionalkoans.support.BlankValues._
import scalafunctionalkoans.support.KoanSuite

/*
PATTERN MATCHING

Pattern matching is a fundamental concept
in most functional programming languages.

Compared to Java, it's some kind `switch` on steroids.
It can match simple values but can also extract matching parts.
*/
class AboutPatternMatching extends KoanSuite {

  koan("Pattern matching can match simple values") {
    val number = 2

    val numberText = number match {
      case 1 => "One"
      case 2 => "Two"
      case 3 => "Three"
      case _ => "Other" // `case _` will match any other case such as `default`in Java.
    }

    numberText should be ("Two")
  }

  koan("Pattern matching can match simple values and return complex values such as tuples") {
    val number = 2

    val numberTuple = number match {
      case 1 => ("One", "Un")
      case 2 => ("Two", "Deux")
      case 3 => ("Three", "Trois")
      case _ => ("Other", "Autre")
    }

    numberTuple should be (("Two", "Deux"))
  }

  koan("Pattern matching can match more complex values such as tuples") {

    def label(animalAndQuantity: (String, Int)) = animalAndQuantity match {
      case ("dog", 1) => "One dog"
      case ("cat", 2) => "Two cats"
      case ("fish", 3) => "Three fishes"
      case _ => "Other"
    }

    label(("cat", 2)) should be ("Two cats")
    label(("dog", 10)) should be ("Other")
  }

  koan("Patter matching can use `_` as a wildcard to accept any value") {

    def label(animalAndQuantity: (String, Int)) = animalAndQuantity match {
      case ("dog", 1) => "One dog"
      case ("cat", _) => "Any number of cats"
      case ("fish", 3) => "Three fishes"
      case _ => "Other"
    }

    label(("cat", 20)) should be ("Any number of cats")
  }

  koan("Pattern matching can extract a matching part and assign it to a `val`") {

    def label(animalAndQuantity: (String, Int)) = animalAndQuantity match {
      case ("dog", 1) => "One dog"
      case ("cat", quantity) => quantity + " cats" // `quantity` is a `val` that will be assigned the second entry of matching tuple.
      case ("fish", 3) => "Three fishes"
      case _ => "Other"
    }

    label(("cat", 20)) should be ("20 cats")
  }

  koan("Pattern matching `case` can be restricted with an `if` condition") {

    def label(animalAndQuantity: (String, Int)) = animalAndQuantity match {
      case ("dog", 1) => "One dog"

      case ("cat", 1) => "One cat exactly"
      case ("cat", quantity) if (quantity < 10) => quantity + " cats"
      case ("cat", _) => "Too many cats"

      case ("fish", 3) => "Three fishes"
      case _ => "Other"
    }

    label(("cat", 1)) should be ("One cat exactly")
    label(("cat", 5)) should be ("5 cats")
    label(("cat", 20)) should be ("Too many cats")
  }

  koan("Pattern matching works with case classes") {
    // It's called a `case` class ...
    case class Dog(name: String, age: Int)

    // ... and it can be used in a `case`. :-)
    def label(dog: Dog) = dog match {
      case Dog("Rex", _) => "Good dog"
      case Dog(_, 0) => "Just born dog"
      case Dog(name, age) if age < 3 => s"Young dog called $name"
      case Dog(_, age) if age > 10 => "An old dog"
    }

    label(Dog("Rex", 5)) should be ("Good dog")
    label(Dog("Volt", 0)) should be ("Just born dog")
    label(Dog("Volt", 2)) should be ("Young dog called Volt")
    label(Dog("Volt", 14)) should be ("An old dog")
  }

  koan("Pattern matching can match case class hierarchy") {
    // Let's model an expression.
    // It can be a simple value, the sum of two expressions, or the product of two expressions.
    sealed trait Expression extends Product with Serializable
    // Think of `trait` in Scala as a more advanced kind of `interface` in Java
    final case class Const(value: Int) extends Expression
    final case class Add(a: Expression, b: Expression) extends Expression
    final case class Multiply(a: Expression, b: Expression) extends Expression

    def compute(expr: Expression): Int = expr match {
      case Const(value) => value
      case Add(a, b) => compute(a) + compute(b)
      case Multiply(a, b) => compute(a) * compute(b)
    }

    val expr1 = Add(Const(5), Const(6))
    val expr2 = Multiply(Add(Const(1), Const(2)), Const(4))

    compute(expr1) should be (11)
    compute(expr2) should be (12)
  }

  koan("Pattern matching can be used for assignments") {
    case class Point(x: Int, y: Int)

    val (x1, y1) = (1, 2)
    val Point(x2, _) = Point(10, 20)

    x1 should be (1)
    y1 should be (2)
    x2 should be (10)
  }
}
