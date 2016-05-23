package gavel.collection

/**
  * Created by afenton on 3/12/16.
  */
object Implicits {

  implicit class RichSeq[T](s: Seq[T]) {

    // ZipWithIndex.foreach is horrendiously slow. This is ??TODO times faster.
    def foreachi(f: (T,Int) => Unit): Unit = {
      var i = 0
      while (i < s.size) {
        f(s(i), i)
        i += 1
      }
    }

  }

}

