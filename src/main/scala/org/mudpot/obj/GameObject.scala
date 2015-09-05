package org.mudpot.obj

import org.mudpot.path.Path

trait GameObject {
  def location: Path
}

trait ObservableObject extends GameObject {
  def lookAt: String
}