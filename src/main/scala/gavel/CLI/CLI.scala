package gavel.CLI
import java.io.File
import scopt.OptionParser


/**
  * Created by afenton on 3/12/16.
  */
case class Config(
  docs: Option[File] = None, url: Option[String] = None,
  debug: Boolean = false,
  mode: String = "",
    // LDA
    numTopics: Int = -1,
    docPrior: Double = -1.0
)

object CLI extends App {

  val parser = new OptionParser[Config]("Gavel") {
    head("Gavel", "0.1")

    opt[File]('d', "docs") optional() valueName("<file>") action { (x, c) =>
      c.copy(docs = Some(x)) } text("Directory of where docs to process live")

    opt[String]('u', "url") optional() valueName("<url>") action { (x, c) =>
      c.copy(url = Some(x)) } text("URL of site to scrape to form topics from ")

    opt[Unit]("debug") hidden() action { (_, c) =>
      c.copy(debug = true) } text("this option is hidden in the usage text")

    help("help") text("prints this usage text")

    cmd("LDA") action { (_, c) =>
      c.copy(mode = "LDA") } text("lda model") children(

      opt[Int]("num-topics") abbr("k") action { (k, c) =>
       c.copy(numTopics = k) } text("Number of topics"),

      opt[Double]("doc-prior") abbr("b") action { (b, c) =>
       c.copy(docPrior = b) } text("Doc Prior")
    )
  }

  // parser.parse returns Option[C]
  parser.parse(args, Config()) match {

    case Some(config) => {
      println(config)

      config.mode match {

        case "LDA" => {
          println("Parsing Docs")
//          val (docs, vocabSize) = BleiParser.parse(config.docs.get)

          println("Running Sampler")
//          val sampler = new GibbsLDA(1.0, 1.0, 50, GibbsSettings(burnin = 2, iterations = 4, lag = 2))
//          val model = sampler.fit(docs, vocabSize)
//          println(model.assignments)
        }
        case _ => {}

      }

    }

    case None => {}
    // arguments are bad, error message will have been displayed
  }

}
