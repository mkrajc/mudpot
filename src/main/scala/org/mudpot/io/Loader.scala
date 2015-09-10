package org.mudpot.io

import java.io.{FileInputStream, InputStream}
import java.util.Properties

import scala.io.Source

object Loader {
  def loadSource(file: File): Source = Source.fromFile(file.toJavaFile)
  def loadInputStream(file: File): InputStream = new FileInputStream(file.toJavaFile)
  def loadProperties(file: File): Properties = {
    val props = new Properties()
    props.load(loadInputStream(file))
    props
  }

}
