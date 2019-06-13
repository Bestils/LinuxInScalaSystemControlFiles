package com.scala.oop.commands
import com.scala.oop.files.Directory
import com.scala.oop.filesystem.State

class Mkdir(name:String) extends Command {

  def checkIllegal(name: String): Boolean = {
    name.contains(".")
  }



  def doMkdir(state: State, name: String): State = {
    ???
  }

  override def apply(state: State): State = {
    val wd = state.wd
    if ( wd.hasEntry(name)){
      state.setMessage("Entry "+name+" already exists!")
    }
    else if (name.contains(Directory.SEPARATOR)){
      //mkdir some
    }
    else  if  ( checkIllegal(name)){
      state.setMessage(name+ " : illegal entry name!")
    }
    else {
      doMkdir(state,name)
    }
  }

}
