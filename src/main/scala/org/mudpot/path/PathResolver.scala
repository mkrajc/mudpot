package org.mudpot.path


import org.mudpot.conf.Paths
import org.mudpot.io._


trait PathResolver[A <: FSNode] {
  def resolveToFile(path: Path)(implicit paths: Paths): A
}

class UniquePathResolver extends PathResolver[Directory] {

  override def resolveToFile(path: Path)(implicit paths: Paths): Directory = {
    val first = path.unique.take(6)
    val dir = first.grouped(2).mkString("/","/","")
    Directories.fromPath(paths.root.getPath + dir)
  }
}

class UniqueFileNamePathResolver(val name: String) extends PathResolver[File] {
  private val dirResolver = new UniquePathResolver

  override def resolveToFile(path: Path)(implicit paths: Paths): File = dirResolver.resolveToFile(path).createFile(name)
}
