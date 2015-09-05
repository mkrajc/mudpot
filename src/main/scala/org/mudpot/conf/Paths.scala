package org.mudpot.conf

import org.mudpot.io.{Directories, Directory}

trait Paths {
  def root: Directory
  def confDir: Directory = root.createDir("conf")
  def parserDir: Directory = confDir.createDir("parser")

}


object Paths {

  case object ProdPaths extends Paths {
    override val root: Directory = ???
  }

  case object DevPaths extends Paths {
    override val root: Directory = Directories.fromPath("src/test/resources/data")
  }

  object Implicits {
    implicit lazy val development: Paths = DevPaths
  }

}



