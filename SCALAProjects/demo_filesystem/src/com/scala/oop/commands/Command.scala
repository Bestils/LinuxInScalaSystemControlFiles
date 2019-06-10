package com.scala.oop.commands

import com.scala.oop.filesystem.State

trait Command  {

  def apply(state:State):State
}

object Command {

  def from(int: String):Command = new UnkownCommand
}