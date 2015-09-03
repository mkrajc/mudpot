

import org.mudpot.io.Files

object Test {
  def main(args: Array[String]) {

    val file = Files.fromPath("./test/abc.txt")
    file.getParent.createFile("/abc/cde.txt").createFull()



  }
}