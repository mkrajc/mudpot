
import org.mudpot.text.pattern.FilePatternLoader
import org.mudpot.text.pattern.exp.ExpressionPattern

object Test {
  def main(args: Array[String]) {
    import org.mudpot.conf.Paths.Implicits.development

    val pat = new FilePatternLoader(ExpressionPattern.from)
    pat.load()

  }
}