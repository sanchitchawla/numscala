package test

import java.io.{BufferedInputStream, InputStream}
import java.util.zip.GZIPInputStream

import breeze.linalg.{DenseMatrix, DenseVector}

import scala.io.Source

object DigitLoader {
  val NUM_FEATURES = 64
  def gis(s: InputStream) = new GZIPInputStream(new BufferedInputStream(s))
  def getDoubles(vs: Array[String], norm: Boolean = true) =
    if (norm) {
      val vsd = vs.map(_.toDouble)
      val min = vsd.min
      val maxminDiff = vsd.max - min
      vsd.map(v => (v - min) / maxminDiff)
    }
    else
      vs.map(_.toDouble)

  def load(): (DenseVector[Int], DenseMatrix[Double])  = {
    val src = Source.fromInputStream(gis(getClass.getResourceAsStream("digits.csv.gz")))
    val rows = src.getLines().map(_.split(",")).toList.map(row => (row.last.toInt, getDoubles(row.take(NUM_FEATURES))))

    (DenseVector(rows.map(_._1):_*), DenseMatrix(rows.map(_._2):_*))
  }
}
