package org.mudpot.path

import org.scalatest.FlatSpec

class UniqueFileNamePathResolverTest extends FlatSpec {

  import org.mudpot.conf.Paths.Implicits.development

  "UniqueFileNamePathResolver" should "return file structure using unique from path" in {
    val name: String = "test.conf"
    val resolver = new UniqueFileNamePathResolver(name)
    val path: Path = Path("/a/b")
    val file = resolver.resolveToFile(path)
    println(file.getAbsolutePath)
    assert(file.getAbsolutePath.endsWith(name), "resolved file does not end with desired name")
  }
}
