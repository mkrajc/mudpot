package org.mudpot.props

trait TypedProperties {
  def getString(key: String): Option[String]

  def getInt(key: String): Option[Int]
}
