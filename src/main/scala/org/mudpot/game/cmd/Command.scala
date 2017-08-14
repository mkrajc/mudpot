package org.mudpot.game.cmd

trait XCommand[EFFECT[_], VALUE]

trait XPureCommand[VALUE] extends XCommand[Nothing, VALUE]
