package org.mudpot.conf

trait Paths {
  def root: String
}


object Paths {

  case object ProdPaths extends Paths {
    override val root: String = ???
  }

  case object DevPaths extends Paths {
    override val root: String = "src/test/resources/data"
  }

  object Implicits {
    implicit lazy val development: Paths = DevPaths
  }

}



