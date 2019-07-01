package commands
import com.scala.oop.filesystem.{File, State}
import com.scala.oop.files.DirEntry




class Touch(name :String) extends CreateEntry(name) {

  override def createSpecificEntry(state: State): DirEntry =
    File.empty(state.wd.path,name)
}
