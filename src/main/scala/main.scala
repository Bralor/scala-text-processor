import scala.io.Source
import java.nio.file.{Paths, Files}

@main
/** The project's notes
  * ---
  *
  * Here comes the notes:
  * 1. Read the TXT file,
  * 2. split the whole string,
  * 3. trim the undesired characters (',.:;'),
  * 4. count the lowercase words,
  * 5. count the uppercase words,
  * to-do: count all the lowercase words,
 **/
def main(): Unit = {
  val testFilePath: String = "src/main/resources/txt_sample.txt"

  val fileContent = readFileContent(filePath = testFilePath)

  val wordCounts = for {
    loadedText <- fileContent
    separatedWords <- splitIntoSingleWords(stringContent = loadedText)
    wordCounts <- mapAllWordsLength(allWords = separatedWords)
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

def stripSelectedStrings(stringContent: String,
                         filtered: String = ",;:.",
                         replacements: String = ""): Option[String] = {
  if (stringContent.isEmpty || filtered.isEmpty) {
    None
  } else {
    val filterPattern = s"^[${filtered}]+|[${filtered}]+$$"
    Some(stringContent.replaceAll(filterPattern, replacements).trim)
  }
}

def splitIntoSingleWords(stringContent: String,
                         separator: String = "\\s+",
                         stripFunc: (String, String, String) => Option[String] =
                         stripSelectedStrings): Option[Array[String]] = {
  if (stringContent.isEmpty) {
    None
  } else {
    val separatedWords = stringContent.split(separator)
    val cleanSeparated = separatedWords.map(
      separatedWord => stripSelectedStrings(separatedWord).getOrElse(separatedWord)
    )
    Some(cleanSeparated)
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
