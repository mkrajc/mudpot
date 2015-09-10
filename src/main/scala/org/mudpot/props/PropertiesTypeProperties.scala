package org.mudpot.props

import java.util.Properties

import scala.util.Try


class PropertiesTypeProperties(props: Properties) extends TypedProperties {
  override def getString(key: String): Option[String] = Option(props.getProperty(key))

  override def getInt(key: String): Option[Int] = Option(props.getProperty(key)).flatMap(s => Try(s.toInt).toOption)
}
