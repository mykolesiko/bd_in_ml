package test


import breeze.io.{CSVReader,CSVWriter}
import breeze.linalg.{DenseMatrix, DenseVector}

import breeze.linalg._
import java.io._



object Main {
  class LinearRegression {

    var coeff : DenseVector[Double] = DenseVector[Double]()
    def fit(X_train: DenseMatrix[Double], y_train: DenseVector[Double] ) : Unit = {
      DenseMatrix.horzcat(X_train, DenseMatrix.ones[Double](y_train.length, 1))
      coeff = (pinv(X_train.t * X_train) * X_train.t * y_train)

    }
    def predict(X_test: DenseMatrix[Double]) : DenseVector[Double] = {
      DenseMatrix.horzcat(X_test, DenseMatrix.ones[Double](X_test.rows, 1))
      return (X_test * coeff)
    }
  }

  def calc_mse(y1 : DenseVector[Double] , y2 : DenseVector[Double]): Double = {
    var sum = 0.0
    for ( i <- 0 to y1.length - 1) {
      sum = (sum + (y1(i) - y2(i)) * (y1(i) - y2(i)))
    }
    return (sum)
  }


  def main (args : Array[String]) : Unit = {
      //val d = new File("./heart1.csv")
      //print(d.getAbsolutePath())

      val mat1=csvread(new File("./winequality_train.csv"),';')
      val mat2=csvread(new File("./winequality_test.csv"),';')

      //val size  = mat.rows
      //val train_size = 1000//(size * 0.8)

      val X_train = mat1(::, 0 to -2)
      val X_test = mat2(::, 0 to -2)
      val y_train = mat1(::, -1)
      val y_test = mat2(::, -1)

      var model = new LinearRegression()
      model.fit(X_train, y_train)
      println(f"proper values of wine quality $y_test")
      var y_pred = (model.predict(X_test))
      println(f"proper values of wine quality $y_pred")
      val metrics = calc_mse(y_pred, y_test)
      println(f"MSE = $metrics")


      csvwrite(new File("./winequality_result.csv"), y_pred.asDenseMatrix, ';')
    }
}
