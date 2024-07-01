import scala.io.Source
import java.nio.file.{Paths, Files}

@main
/** The project's notes
 * ---
 *
 * Here comes the notes:
 * 1. Read the TXT file
 * 2. split the whole string
 * 3. count the lowercase words
 */
def main(): Unit = {
  val testFilePath: String = "src/main/resources/txt_sample.txt"
  val fileContent = readFileContent(filePath = testFilePath)

  val wordCounts = for {
    loadedText <- fileContent
    words <- splitIntoSingleWords(stringContent = loadedText)
    wordCounts <- mapAllWordsLength(allWords = words)
  } yield wordCounts

  wordCounts match {
    case None => println("Some exceptions has occured.")
    case Some(wordMap) => for ((key, value) <- wordMap) println(s"$key: $value")
  }
}

def readFileContent(filePath: String): Option[String] = {
  if (filePath.isEmpty || !Files.exists(Paths.get(filePath))) {
    None
  } else {
    Some(Source.fromFile(filePath).mkString)
  }
}

def splitIntoSingleWords(stringContent: String,
                         separator: String = "\\s+"): Option[Array[String]] = {
  if (stringContent.isEmpty) {
    None
  } else {
    Some(stringContent.split(separator))
  }
}

def mapAllWordsLength(allWords: Array[String]): Option[Map[String, Int]] = {
  if (allWords.isEmpty) {
    None
  } else {
    Some(allWords.foldLeft(Map.empty[String, Int]) {
      (count, word) => count + (word -> (count.getOrElse(word, 0) + 1))
    })
  }
}