package com.scala.oop.files

import files.File

abstract class DirEntry(val parentPath: String, val name: String) {
  def path : String = {
val separotorIfNecessary =
  if(Directory.ROOT_PATH.equals(parentPath)) ""
else  Directory.SEPARATOR
    parentPath + separotorIfNecessary + name
  }

  def asDirectory : Directory
  def asFile:File

  def isDirectory : Boolean
  def isFile : Boolean

  def getType: String

}
