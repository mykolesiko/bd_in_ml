import breeze.io.{CSVReader,CSVWriter}
import breeze.linalg.{DenseMatrix, DenseVector}


import breeze.linalg._
import java.io._

//val d = new File("./heart1.csv")
//print(d.getAbsolutePath())

val mat=csvread(new File("./winequality-red.csv"),';')

val size  = mat.rows
val train_size = 1000//(size * 0.8)

val X_train = mat(0 to train_size, 0 to -2)
val X_test = mat(train_size to -1, 0 to -2)
val y_train = mat(0 to train_size, -1)
val y_test = mat(train_size to -1, -1)

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


var model = new LinearRegression()
model.fit(X_train, y_train)
print(y_test)
var y_pred = (model.predict(X_test))
print(y_pred)
print(calc_mse(y_pred, y_test))


csvwrite(new File("D://winequality-red_result.csv"), y_pred.asDenseMatrix, ';')


