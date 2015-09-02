package org.mudpot.path

import java.io.File

import org.mudpot.conf.Paths


trait PathResolver {
  def resolveToFile(path: Path)(implicit paths: Paths): File
}

class UniquePathResolver extends PathResolver {

  override def resolveToFile(path: Path)(implicit paths: Paths): File = {
    val first = path.unique.take(6)
    val dir = first.grouped(2).mkString("/")
    new File(paths.root, dir)
  }
}

class UniqueFileNamePathResolver(val name: String) extends UniquePathResolver {
  override def resolveToFile(path: Path)(implicit paths: Paths): File = new File(super.resolveToFile(path), name)
}
