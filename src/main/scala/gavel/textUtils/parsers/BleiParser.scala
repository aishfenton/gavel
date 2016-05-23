package gavel.textUtils.parsers

import java.io.File

import breeze.linalg.SparseVector

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

/**
  * Created by afenton on 3/26/16.
  */
object BleiParser {

  def parse(file: File) = {
    val lines = Source.fromFile(file).getLines()

    val docsCount = lines.next.toInt
    val vocabSize = lines.next.toInt
    val NNZ = lines.next.toInt

    val docs = ArrayBuffer[SparseVector[Int]]()
    var vec: SparseVector[Int] = null
    var prevDocId: Option[Int] = None

    lines.foreach { l =>
      val tokens = l.split(" ")
      val docId = tokens(0).toInt
      val vobabId = tokens(1).toInt
      val count = tokens(2).toInt

      if (prevDocId.isEmpty || docId != prevDocId.get) {
        vec = SparseVector.zeros[Int](vocabSize)
        docs.append(vec)
      }
      vec(vobabId - 1) = count
      prevDocId = Some(docId)
    }

    (docs, vocabSize)
  }

}
