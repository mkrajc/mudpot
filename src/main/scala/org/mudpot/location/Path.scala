package org.mudpot.location

import java.security.MessageDigest

case class Path(parts: List[String]) {

  def take(n: Int): Path = Path(parts.take(n))

  def prepend(head: String): Path = Path(head :: parts)

  def append(tail: String): Path = Path(parts ::: List(tail))

  def parent: Path = if (parts.isEmpty) this else Path(parts.init)

  override def toString: String = parts.mkString("/", "/", "")

  lazy val unique: String = Path.sha(toString)

}

object Path {
  private val digest = MessageDigest.getInstance("SHA")

  def sha(s: String): String = digest.digest(s.getBytes).map("%02x".format(_)).mkString
}
