package org.mudpot.conf

trait Configurable[T] {
  self: T with Configurable[T] =>
  def configure(configuration: Configuration): T
}
