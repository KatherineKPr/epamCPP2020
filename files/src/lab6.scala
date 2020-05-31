import java.io.File
import scala.io.Source

object lab6 {
  val crystalFile = new File("./info/allCrystals/allCrystals.json")
  val source = Source.fromFile(crystalFile)
  val crystalsBuffer = source.mkString
  val crystalKindCounter = scala.collection.mutable.ListBuffer(0, 0, 0, 0, 0, 0, 0)
  for (i <- crystalsBuffer) {
    i match {
      case '0' => crystalKindCounter(0) += 1
      case '1' => crystalKindCounter(1) += 1
      case '2' => crystalKindCounter(2) += 1
      case '3' => crystalKindCounter(3) += 1
      case '4' => crystalKindCounter(4) += 1
      case '5' => crystalKindCounter(5) += 1
      case '6' => crystalKindCounter(6) += 1
      case _ => "not a number"
    }
  }
  var i: Integer = 0;
  println("Crystal statistics map : ")
  for (times <- crystalKindCounter) {
    println("Crystal " + i + " was activated " + times + " times")
    i += 1
  }
}
