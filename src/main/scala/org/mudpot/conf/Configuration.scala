package org.mudpot.conf


trait Configuration {
  def getInt(key: String): Int
}
