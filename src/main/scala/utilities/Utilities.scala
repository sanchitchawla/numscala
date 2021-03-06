package utilities

import breeze.linalg.{DenseMatrix, DenseVector}
import breeze.numerics.{ceil, log}

object Utilities {

  /**
    * e = value and i = index
    * Want to check if e equal to predicted value
    * @param y
    * @param yPred
    * @return
    */
  def accuracy(y: DenseVector[Int], yPred: DenseVector[Int]): Double = {
    y.toArray.zipWithIndex.count {
      case (e, i) => e == yPred(i)
    } / y.length.toDouble
  }

  def logloss(y: DenseVector[Double], yPred: DenseVector[Double]): DenseVector[Double] = {
    val ones = DenseVector.ones[Double](y.length)
    y :* log(yPred) :+ (ones - y) :* log(ones - yPred)
  }

  def train_test_split(data: DenseMatrix[Double], y: DenseVector[Int], train_ratio: Double = 0.8): (DenseMatrix[Double], DenseVector[Int], DenseMatrix[Double], DenseVector[Int]) = {
    val instances_train: Int = ceil(data.rows * train_ratio).toInt
    val train: DenseMatrix[Double] = data(0 to instances_train, ::)
    val test: DenseMatrix[Double] = data(instances_train until data.rows, ::)

    val train_y = y.slice(0, ceil(y.length * train_ratio).toInt + 1)
    val test_y = y.slice(ceil(y.length * train_ratio).toInt, y.length - 1)

    (train, train_y, test, test_y)
  }

  def train_test_split_double(data: DenseMatrix[Double], y: DenseVector[Double], train_ratio: Double = 0.8): (DenseMatrix[Double], DenseVector[Double], DenseMatrix[Double], DenseVector[Double]) = {
    val instances_train: Int = ceil(data.rows * train_ratio).toInt
    val train: DenseMatrix[Double] = data(0 to instances_train, ::)
    val test: DenseMatrix[Double] = data(instances_train until data.rows, ::)

    val train_y = y.slice(0, ceil(y.length * train_ratio).toInt + 1)
    val test_y = y.slice(ceil(y.length * train_ratio).toInt, y.length - 1)

    (train, train_y, test, test_y)
  }

}
