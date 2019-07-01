package files

import com.scala.oop.files.{DirEntry, Directory}
import filesystem.FileSystemException

class File(override val parentPath : String, override val name: String, contents: String) extends DirEntry(parentPath,name){
  override def asDirectory: Directory =
    throw new FileSystemException("File cannot be converted to a directory")

  def asFile : File = this

  override def getType: String = "File"

  object  File {
    def empty(parentPath : String, name : String): File = {
      new File (parentPath, name, "")
    }
  }

  override def isDirectory: Boolean = false

  override def isFile: Boolean = true
}
