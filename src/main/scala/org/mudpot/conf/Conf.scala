package org.mudpot.conf

import org.mudpot.path.Path


trait Conf {

  def path: Path

  def show(): Unit = {
    print(path.unique)
  }
}
