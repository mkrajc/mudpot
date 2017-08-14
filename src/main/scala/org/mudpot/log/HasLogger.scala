package org.mudpot.log

import org.slf4j.LoggerFactory

trait HasLogger {
  lazy val logger = LoggerFactory.getLogger(getClass)
}
