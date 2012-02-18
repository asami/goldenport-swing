package org.goldenport.swing

import scala.swing._
import scala.swing.event.ButtonClicked
import java.io.IOException

/**
 * @since   Feb. 18, 2012
 * @version Feb. 18, 2012
 * @author  ASAMI, Tomoharu
 */
trait PropertyField extends Component {
  def textValue: String
}

class FilePropertyField(implicit context: SwingContext) extends FlowPanel with PropertyField {
  private val _text_input = new TextField() {
  }

  contents += new Button("File") {
    reactions += {
      case ButtonClicked(s) => _open_file
    }
  }
  contents += _text_input

  def textValue = _text_input.text

  private def _open_file {
    val fc = _get_file_chooser
    fc.title = context.labelFileChooser
      fc.showOpenDialog(this) match {
      case FileChooser.Result.Approve => {
        try {
          if (fc.selectedFile.exists) {
            _text_input.text = fc.selectedFile.toString
          } else {
            _text_input.text = fc.selectedFile.toString // XXX
          }
        } catch {
          case e: IOException =>
            Dialog.showMessage(this, context.labelFileNotOpened + "\n" + e.getMessage)
        }
      }
      case FileChooser.Result.Cancel => ;
      case FileChooser.Result.Error => ;
    }
  }

 private def _get_file_chooser = {
    val dir = context.userDir
    new FileChooser(dir) {
//        fileFilter = new FileNameExtensionFilter("テキストファイル", "txt")
//        fileFilter = new FileNameExtensionFilter("Scala / Ruby", "scala", "rb")
        //TODO FileChooser#accessory= / JFileChooser#setAccessory
    }
  }
}
