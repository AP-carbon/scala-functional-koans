package scalafunctionalkoans

import scalafunctionalkoans.support.BlankValues._
import scalafunctionalkoans.support.KoanSuite

class AboutImmutableSets extends KoanSuite {

  koan("Set behaves like a function returning a boolean") {
    val ids = Set(1, 2, 3)

    ids(1) should be (true)
    ids(2) should be (true)
    ids(3) should be (true)

    ids(100) should be (false)
  }

  koan("Two sets can be compared for equality ignoring order (as expected for sets)") {
    val s1 = Set(1, 2, 3)
    val s2 = Set(10, 20, 30)
    val s3 = Set(3, 2, 1)

    (s1 == s2) should be (false)
    (s1 == s3) should be (true)
    (s1 eq s3) should be (false)
  }

  koan("It can be tested whether or not a set contains an element") {
    val s = Set(1, 2, 3)

    s.contains(2) should be (true)
    s.contains(100) should be (false)
  }

  koan("An element can be added to a set and return a new set") {
    val ids = Set(1, 2, 3)

    val newIds = ids + 4

    ids should be (Set(1,2,3))
    newIds should be (Set(1,2,3, 4))
  }

  koan("Elements can be added to a set and return a new set") {
    val names = Set("Peter", "John", "Mary")

    val newNames = names ++ Seq("Paul", "Luke")

    names should be (Set("Peter", "Mary", "John"))
    newNames should be (Set("Peter", "Mary", "John", "Luke", "Paul"))
  }

  koan("An element can be removed from a set and return a new set") {
    val ids = Set(1, 2, 3)

    val newIds = ids - 3

    ids should be (Set(1,2, 3))
    newIds should be (Set(1,2))
  }

  koan("Elements can be removed from a set and return a new set") {
    val names = Set("Peter", "John", "Mary")

    val newNames = names -- Seq("John", "Mary")

    names should be (Set("Peter", "Mary", "John"))
    newNames should be (Set("Peter"))
  }

  koan("Union of two sets will create a new set") {
    val englishNames = Set("Peter", "John", "Mary")
    val frenchNames = Set("Paul", "Jean", "Marie")

    val names = englishNames union frenchNames // calling union method without '.', '(' and ')'

    names should be (Set("Paul", "Jean", "Marie", "John", "Mary", "Peter"))
  }

  koan("Intersection of two sets will create a new set") {
    val before = Set(1, 2, 3, 4, 5)
    val after = Set(2, 4, 8)

    val retainedIds = before intersect after

    retainedIds should be (Set(4, 2))
  }

  koan("Difference of two sets will create a new set") {
    val before = Set(1, 2, 3, 4, 5)
    val after = Set(2, 4, 8)

    val deletedIds = before diff after
    val newIds = after diff before

    deletedIds should be (Set(1, 3, 5))
    newIds should be (Set( 8))
  }

  koan("Sets can be filtered") {
    val ids = Set(1, 2, 3)
    val names = Set("Peter", "John", "Mary")

    val filteredIds = ids.filter(_ < 3)
    val filteredNames = names.filter(_.startsWith("M"))

    filteredIds should be (Set(1, 2))
    filteredNames should be (Set("Mary"))
  }

  koan("Elements of set can be transformed (mapped)") {
    val ids = Set(1, 2, 3)
    val names = Set("Peter", "John", "Mary")

    val idStrings = ids.map(_.toString)
    val nameSizes = names.map(_.length)

    idStrings should be (Set("1", "2", "3"))
    nameSizes should be (Set(5, 4))
  }

  koan("Items of set can be individually transformed to set that are then merged") {
    val ids = Set(1, 2, 3)
    val firstNames = Set("Peter", "John", "Mary")

    val extendedIds = ids.flatMap(id => Set(id + 10, id + 20))
    val fullNames = firstNames.flatMap(firstName => Set(s"$firstName Jones", s"$firstName Simpson"))

    extendedIds should be (Set(11, 21, 22, 12, 13, 23))
    fullNames should be (Set("Peter Jones", "John Jones", "Mary Jones", "Peter Simpson", "John Simpson", "Mary Simpson"))
  }

  koan("Sets can be used in a `for` comprehension") {
    val firstNames = Set("Peter", "Paul", "Mary")
    val lastNames = Set("Jones", "Simpson", "Jackson")

    val fullNames = for {
      firstName <- firstNames // first generator
      if firstName.startsWith("P") // first filter
      lastName <- lastNames // second generator
      if lastName.startsWith("J") // second filter
    } yield s"$firstName $lastName"

    fullNames should be (Set("Peter Jones", "Peter Jackson", "Paul Jones", "Paul Jackson"))
  }
}
