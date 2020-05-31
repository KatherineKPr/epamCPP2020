import java.io.File

import scala.collection.mutable.ListBuffer
import scala.io.Source

object lab7 {
  val crystalFile = new File("./info/allCrystals/allCrystals.json")
  val source = Source.fromFile(crystalFile)
  val crystalsBuffer = source.mkString
  val sequenceCounter = scala.collection.mutable.ListBuffer(0, 0, 0, 0, 0, 0, 0)
  val crystalBufferCounter = scala.collection.mutable.ListBuffer(0, 0, 0, 0, 0, 0, 0)
  for (i <- crystalsBuffer) {
    i match {
      case '0' => sequenceCalculator(0, crystalBufferCounter, sequenceCounter)
      case '1' => sequenceCalculator(1, crystalBufferCounter, sequenceCounter)
      case '2' => sequenceCalculator(2, crystalBufferCounter, sequenceCounter)
      case '3' => sequenceCalculator(3, crystalBufferCounter, sequenceCounter)
      case '4' => sequenceCalculator(4, crystalBufferCounter, sequenceCounter)
      case '5' => sequenceCalculator(5, crystalBufferCounter, sequenceCounter)
      case '6' => sequenceCalculator(6, crystalBufferCounter, sequenceCounter)
      case _ => "not a number"
    }
  }
  var index: Integer = 0;
  var generalSequenceCount = 0
  for (count <- sequenceCounter) {
    generalSequenceCount += count
  }
  println("Count of all sequences: ")
  for (count <- sequenceCounter) {
    println("Sequence of " + index + " crystal was activated " + count + " times. It's percentage of total sequences: "
      + count * 100 / generalSequenceCount + " % ")
    index += 1
  }

  def sequenceCalculator(index: Int, crystalBufferCounter: ListBuffer[Int], sequenceCounter: ListBuffer[Int]): Unit = {
    crystalBufferCounter(index) += 1
    if (crystalBufferCounter(index) == 3) {
      sequenceCounter(index) += 1
      crystalBufferCounter(index) = 0
    }
  }
}


