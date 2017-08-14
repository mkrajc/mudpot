package org.mudpot.text.engine

trait EngineFactory {
  def create(user: String): Engine
}
