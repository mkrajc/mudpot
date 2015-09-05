package org.mudpot.io

import scala.io.Source

object Loader {
  def loadSource(file: File): Source = Source.fromFile(file.toJavaFile)

}
