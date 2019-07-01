package commands

import com.scala.oop.commands.Command
import com.scala.oop.filesystem.State

class Pwd extends Command{
  override def apply(state: State): State = state.setMessage(state.wd.path)

}
