package org.mudpot.path

import org.scalatest.{FlatSpec, Matchers}

class UniqueFileNamePathResolverTest extends FlatSpec with Matchers {

  import org.mudpot.conf.Paths.Implicits.development

  "UniqueFileNamePathResolver" should "return file structure using unique from path" in {
    val name: String = "test.conf"
    val resolver = new UniqueFileNamePathResolver(name)
    val path: Path = Path("/a/b")
    val file = resolver.resolveToFile(path)
    println(file.getPath)
    file.getPath should endWith(name)
  }
}
