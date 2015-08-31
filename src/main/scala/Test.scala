import org.mudpot.engine.SimpleEngine
import org.mudpot.engine.cmd.DateCommand

import scala.io.StdIn

object Test {
  def main(args: Array[String]) {
    println("test")

    val engine = new SimpleEngine
    engine.addCommand(new DateCommand)

    var line: String = ""
    while (true)  {
      line = StdIn.readLine()
      println(engine.handle(line))
      if ("exit" == line) {
        return
      }
    }


  }
}