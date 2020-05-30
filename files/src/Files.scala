import java.io.{File, FileInputStream, FileReader}

import scala.annotation.tailrec
import scala.collection.mutable.ArrayBuffer
import scala.io.Source

object Files {

  def main(args: Array[String]): Unit = {
    val activeFiles = new File("./info/efficiency").listFiles()

    var arrayBufferEfficiency = ArrayBuffer[Int]()
    print("\nBefore sort:\n")
    for (file <- activeFiles) {
      val source = Source.fromFile(file)
      val line = source.mkString
      arrayBufferEfficiency += line.toInt
      println(file + "-efficiency : " + line.toInt);
    }

    print("\nAfter sort:\n")
    for (x <- sort(arrayBufferEfficiency.toList)) {
      println(x)
    }


    val crystalFile = new File("./info/allCrystals/allCrystals.json")
    val source = Source.fromFile(crystalFile)
    val buf = source.mkString
    val counterOfNumbers = scala.collection.mutable.ListBuffer(0, 0, 0, 0, 0, 0, 0)
    for (i <- buf) {
      i match {
        case '0' => counterOfNumbers(0) += 1
        case '1' => counterOfNumbers(1) += 1
        case '2' => counterOfNumbers(2) += 1
        case '3' => counterOfNumbers(3) += 1
        case '4' => counterOfNumbers(4) += 1
        case '5' => counterOfNumbers(5) += 1
        case '6' => counterOfNumbers(6) += 1
        case _ => "not a number"
      }
    }
    var i: Integer = 0;
    println("Crystal statistics map : ")
    for (times <- counterOfNumbers) {
      println("Crystal " + i + " was activated " + times + " times")
      i += 1
    }

  }


  def sort(list: List[Int]): List[Int] = {
    @tailrec
    def helpSort(l: List[Int], reminder: List[Int]): List[Int] = {
      if (reminder.isEmpty)
        l
      else
        helpSort(reminder.filter(_ == reminder.min) ++ l, reminder.filter(_ > reminder.min))
    }
    helpSort(Nil, list)
  }


}
