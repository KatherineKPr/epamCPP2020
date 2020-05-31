import java.io.{File, FileInputStream, FileReader}

import scala.annotation.tailrec
import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.io.Source

object lab5 {

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
