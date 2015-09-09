package org.mudpot.io

import java.io.{FileInputStream, InputStream}

import scala.io.Source

object Loader {
  def loadSource(file: File): Source = Source.fromFile(file.toJavaFile)
  def loadInputStream(file: File): InputStream = new FileInputStream(file.toJavaFile)

}
