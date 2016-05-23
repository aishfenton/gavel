package gavel.MCMC

import breeze.linalg._
import spire.syntax.cfor._

/**
  * Created by afenton on 3/12/16.
  * NB not quite right, since the "chain" is only the outer iteration, after all parameters have been cycled through
  */
class Chain[T](initial: T)(f: (T) => T) {
  val it = Iterator.iterate(initial)(f)

  def burnIn(n: Int) = { it.drop(n); this }
  def run(n: Int, lag: Int) =  { it.sliding(1, lag).map( _.head).take(n); this }

  // Return the optimal T from the chain, based on the given evaluation function.
  def optimal(eval: T => Double) = {
    it.map(eval).max
  }

  def last = it.next //.toList.last

  // Use chain to produce a MC estimate of given function (assuming function returns a Vec)
  // \frac{1}{n} \sum_x f(x)p(x)
  // TODO need to generalize this method to estimate over more general functions results than vectors
  def monteCarloEstimate[A](fn: T => DenseVector[Double]): DenseVector[Double] = {
    var sum = fn(it.next)
    var count = 1
    it.foreach { e:T =>
      sum += fn(e)
    }
    sum / count.toDouble
  }

}
