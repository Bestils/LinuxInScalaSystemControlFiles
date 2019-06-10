package com.scala.oop.commands
import com.scala.oop.filesystem.State

class UnkownCommand extends Command{
  override def apply(state: State): State =  state.setMessage("Command not found!")

}

