package org.mudpot.conf

import org.mudpot.path.Path

class SomeConf(val path: Path, var enabled: Boolean) extends Conf {

}

object SomeConf {
  def main(args: Array[String]) {
    val d = new SomeConf(Path("/a/b"), false)
    d.show
  }
}
