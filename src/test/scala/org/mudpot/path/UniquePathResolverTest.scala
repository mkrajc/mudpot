package org.mudpot.path

import org.mudpot.utils.FileUtils._
import org.scalatest.{FlatSpec, Matchers}


class UniquePathResolverTest extends FlatSpec with Matchers {

  import org.mudpot.conf.Paths.Implicits.development

  "UniquePathResolver" should "return file structure using unique from path" in {
    val pr = new UniquePathResolver()

    val path: Path = Path("/a/b")
    val dir = pr.resolveToFile(path)
    val uniq = path.unique

    val rel = relativePath(development.root.toJavaFile, dir.toJavaFile).filterNot(_ == '/')

    uniq should startWith(rel)
  }


}
