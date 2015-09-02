package org.mudpot.path

import org.mudpot.utils.FileUtils._
import org.scalatest.FlatSpec


class UniquePathResolverTest extends FlatSpec {

  import org.mudpot.conf.Paths.Implicits.development

  "UniquePathResolver" should "return file structure using unique from path" in {
    val pr = new UniquePathResolver()

    val path: Path = Path("/a/b")
    val dir = pr.resolveToFile(path)
    val uniq = path.unique

    val rel = relativePath(development.root, dir).filterNot(_ == '/')

    assert(uniq.startsWith(rel), "relative path of returned file does not start as unique of path")
  }


}
