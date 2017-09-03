package gavel.lda

//import gavel.MCMC.Chain

import scala.util.Random
import scala.collection.{mutable => mut}

case class GibbsSettings(iterations: Int = 500,
                         burnin: Int = 100,
                         lag: Int = 10)

//case class LDAModel(assignments: Array[Array[Int]],
//                    ndz: DenseMatrix[Int],
//                    nvz: DenseMatrix[Int]) {
//  // Get doc-topic distribution for document index
//  def docTopicDistribution(di: Int) = {
//    ndz(di)
//  }
//
//  // Get topic-word distribution for given topic index
//  def topicWordDistribution(wi: Int) = {
//
//    DenseVector.zeros[Double](10)
//  }
//}
//
///**
//  * Standard Gibbs Sampler version of Latent Dirichlet Allocation
//  * Created by Aish Fenton on 2/21/16.
//  */
//class GibbsLDA(alpha: Double,
//               beta: Double,
//               noTopics: Int,
//               settings: GibbsSettings) {
//
//  def fit(docs: IndexedSeq[SparseVector[Int]], vocabSize: Int): LDAModel = {
//    chain(docs, vocabSize)
//      .burnIn(settings.burnin)
//      .run(settings.iterations, settings.lag)
//      .last
//  }
//
//  // Random starting point for sampling
//  private def initState(docs: IndexedSeq[SparseVector[Int]], V: Int) = {
//    val assignments = Array.ofDim[Array[Int]](docs.length)
//    val nvz = DenseMatrix.zeros[Int](V, noTopics) // Vocab-Topic counts
//    val ndz = DenseMatrix.zeros[Int](docs.length, noTopics) // Doc-Topic counts
//    val nz = DenseVector.zeros[Int](noTopics) // Number of words assigned to topic
//
//    cforRange(0 until docs.length) { di =>
//      val words = docs(di)
//      assignments(di) = Array.ofDim[Int](words.activeSize)
//
//      cforRange(0 until words.activeSize) { wi =>
//        val vi = words.valueAt(wi)
//        val z = Random.nextInt(noTopics)
//
//        assignments(di)(wi) = z
//        nvz(vi, z) += 1
//        ndz(di, z) += 1
//        nz(z) += 1
//      }
//    }
//
//    (assignments, nvz, ndz, nz)
//  }
//
//  // Runs the Gibbs sampler and inferer topics.
//  private def chain(docs: IndexedSeq[SparseVector[Int]], V: Int) = {
//    val (assignments, nvz, ndz, nz) = initState(docs, V)
//
//    val initModel = LDAModel(assignments, nvz, ndz)
//    new Chain(initModel)({ model: LDAModel =>
//      print(".")
//      cforRange(0 until docs.length) {
//        di =>
//          val docAssignments = model.assignments(di)
//          val words = docs(di)
//
//          cforRange(0 until words.activeSize) { wi =>
//            val vi = words.valueAt(wi)
//            val oldZ = docAssignments(wi)
//
//            // Remove current topic
//            nvz(vi, oldZ) -= 1
//            ndz(di, oldZ) -= 1
//            nz(oldZ) -= 1
//
//            // Resample topics
//            val pz = conditionalLikelihood(vi, di, nvz, ndz, nz)
//            val newZ = pz.draw
//
//            // Add topic
//            nvz(vi, newZ) += 1
//            ndz(di, newZ) += 1
//            nz(newZ) += 1
//            docAssignments(wi) = newZ
//          }
//      }
//
//      // NB, model is stateful here so we just emit the same model, but design allows for immutable implementation
//      model
//    })
//
//  }
//
//  /**
//    * Sample a new topic according to:
//    *   $$ P(z_i = j | \mathbf{z_{-i}}, \mathbf{w}) \propto \frac{n_{z_j}^{w_j} + \beta}{n_{z_j}^{.} + W\beta} \frac{n_{z_j}^{d_j} + \alpha}{n_{.}^{d_j} + K\alpha} $$
//    * In practice we drop the final denominator since it isn't dependent on z_i
//    */
//  private def conditionalLikelihood(vi: Int,
//                                    di: Int,
//                                    nvz: DenseMatrix[Int],
//                                    ndz: DenseMatrix[Int],
//                                    nz: DenseVector[Int]) = {
//    val vocabSize = nvz.rows
//    val K = nz.length
//    val pz = DenseVector.zeros[Double](K)
//    var i = 0
//    val C = this.beta * vocabSize
//    while (i < K) {
//      val wordInTopic = (nvz(vi, i) + this.beta) / (nz(i) + C)
//      val topicInDoc = ndz(di, i) + this.alpha
//
//      if (topicInDoc < 0) println("td", topicInDoc, ndz(di, i))
//      if (wordInTopic < 0) println("wt", wordInTopic, nvz(vi, i), nz(i), C)
//      pz(i) = wordInTopic * topicInDoc
//      i += 1
//    }
//
//    // Normalization is done by Multinomial automatically.
//    Multinomial(pz)
//  }
//
//  private def vocabCounts(
//      docs: IndexedSeq[SparseVector[Int]]): MMap[Int, Int] = {
//    val wordCounts = MMap[Int, Int]()
//    docs.foreach { d =>
//      d.activeKeysIterator.foreach { w =>
//        val c = wordCounts.getOrElse(w, 0)
//        wordCounts.update(w, c + 1)
//      }
//    }
//    wordCounts
//  }
//
//}
