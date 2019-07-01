package commands

import com.scala.oop.commands.Command
import com.scala.oop.files.{DirEntry, Directory}
import com.scala.oop.filesystem.State

import scala.annotation.tailrec

class Cd(dir: String) extends Command{



  def doFindEntry(root: Directory, path: String):DirEntry = {

    @tailrec
    def findEntryHelper(currentDirectory: Directory, path: List[String]): DirEntry = {
      if(path.isEmpty || path.head.isEmpty) currentDirectory
      else if(path.tail.isEmpty) currentDirectory.findEntry(path.head)
      else {
        val nextDir = currentDirectory.findEntry(path.head)
        if(nextDir == null || !nextDir.isDirectory) null
        else findEntryHelper(nextDir.asDirectory,path.tail)
      }

    }

    @tailrec
    def collapseRelativeTokens(path: List[String], result: List[String]):List[String] = {
      if(path.isEmpty) result
      else if ("."equals(path.head)) collapseRelativeTokens(path.tail,result)
      else if ("..".equals(path.head)){
        if ( result.isEmpty) null
        else collapseRelativeTokens(path.tail,result.init)
      }
      else  collapseRelativeTokens(path.tail,result :+ path.head)
    }

    // 1. tokens
   val tokens: List[String] = path.substring(1).split(Directory.SEPARATOR).toList

    // 1.5 eliminate / collapse relative tokens

    val newTokens = collapseRelativeTokens(tokens,List())

    // 2. navigate to the correct entry
    findEntryHelper(root,tokens)
  }

  override def apply(state: State): State = {
    /*
    cd /something/.../
    cd a/b/c/ = relative  to the current working directory



     */

    //1. Find Root
    val root = state.root
    val wd = state.wd

    // 2. Find the absolute path of the directory i want to cd  to
    val absolutePath =
      if ( dir.startsWith(Directory.SEPARATOR)) dir
    else if (wd.isRoot) wd.path + dir
    else wd.path + Directory.SEPARATOR + dir

    // 3 . Find the directory to cd to, given the path
val destinationDirectory = doFindEntry(root,absolutePath)
    // 4. change the state given thee new directory
    if (destinationDirectory==null || destinationDirectory.isDirectory)
      state.setMessage(dir + " : no such directory")
    else
      State(root,destinationDirectory.asDirectory)
  }
}
