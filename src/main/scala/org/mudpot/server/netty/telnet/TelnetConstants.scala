package org.mudpot.server.netty.telnet

/**
 * Constants used by the Telnet protocol.
 */
object TelnetConstants {

  // TERMINAL
  val NUL: Int = 0
  val BEL: Int = 7
  val BS: Int = 8
  val HT: Int = 9
  val LF: Int = 10
  val VT: Int = 11
  val FF: Int = 12
  val CR: Int = 13
  val ESC: Int = 27

  // COMMANDS
  val SE: Int = 240
  val NOP: Int = 241
  val DM: Int = 242
  val BRK: Int = 243
  val IP: Int = 244
  val AO: Int = 245
  val AYT: Int = 246
  val EC: Int = 247
  val EL: Int = 248
  val GA: Int = 249
  val SB: Int = 250
  val WILL: Int = 251
  val WONT: Int = 252
  val DO: Int = 253
  val DONT: Int = 254
  val IAC: Int = 255

  // OPTIONS
  val ECHO: Int = 1
  val SUPPRESS_GA: Int = 3
  val STATUS: Int = 5
  val TIMING_MARK: Int = 6
  val TERM_TYPE: Int = 24
  val WINDOW_SIZE: Int = 31
  val TERM_SPEED: Int = 32
  val REMOTE_FLOW_CTRL: Int = 33
  val LINE_MODE: Int = 34
  val ENV_VARIABLES: Int = 36
}
