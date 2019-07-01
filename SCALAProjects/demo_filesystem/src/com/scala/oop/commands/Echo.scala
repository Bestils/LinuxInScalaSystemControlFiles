package commands

import com.scala.oop.commands.Command
import com.scala.oop.files.Directory
import com.scala.oop.filesystem.State
import files.File


import scala.annotation.tailrec


class Echo(args: Array[String]) extends Command {

  def createContent(args: Array[String], topIndex: Int): String = {
    @tailrec
    def createContentHelper(currentIndex: Int, accumulator: String): String = {
      if (currentIndex >= topIndex) accumulator
      else createContentHelper(currentIndex + 1, accumulator + " " + args(currentIndex))
    }
  }

  def getRootAfterEcho(currentDirectory: Directory, path: List[String], contents: String, append: Boolean): Directory = {
if(path.isEmpty)  currentDirectory
else if (path.tail.isEmpty){
  val dirEntry = currentDirectory.findEntry(path.head)

  if(dirEntry==null) currentDirectory.addEntry(new File(currentDirectory.path,path.head,contents))
  else if (dirEntry.isDirectory) currentDirectory
  else if (append){
    currentDirectory.replaceEntry(path.head,dirEntry.asFile.appendContents(contents) )
  }
  else {
    currentDirectory.replaceEntry(path.head,dirEntry.asFile.setContents(contents) )
  }
}else {
   val nextDirectory = currentDirectory.findEntry(path.head).asDirectory
  val newNextDirectory = getRootAfterEcho(nextDirectory,path.tail,contents,append)
}
  }

  def doEcho(state: State, contents: String, filename: String, append: Boolean): State = {
    if (filename.contains(Directory.SEPARATOR))
      state.setMessage("Echo : Filename must not  contain separators")
    else {
      val newRoot: Directory = getRootAfterEcho(state.root,state.wd.getAllFoldersInPath :+ filename,contents,append)
      if (newRoot == state.root)
        state.setMessage(filename + ": no such file")


      else
        State(newRoot, newRoot.findDescendant(state.wd.getAllFoldersInPath))
    }
  }

  override def apply(state: State): State = {
    if (args.isEmpty) this
    else if (args.tail.isEmpty) state.setMessage(args(0))
    else {
      val operator = args(args.length - 2)
      val filename = args(args.length - 1)
      val contents = createContent(args, args.length - 2)
      if (operator.equals('>'))
        doEcho(state, contents, filename, append = false)
      else if (operator.equals(">>"))
        doEcho(state, contents, filename, append = true)
      else {
        state.setMessage(createContent(args, args.length))
      }
    }
  }
}
