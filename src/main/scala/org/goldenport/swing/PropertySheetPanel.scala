package org.goldenport.swing

import scalaz._
import Scalaz._
import scala.swing._
import org.goldenport.util.ArrayMap
import org.goldenport.record._
import scala.swing.event.KeyPressed
import scala.swing.event.Key
import java.util.concurrent.CopyOnWriteArrayList
import java.io.IOException
import scala.swing.event.ButtonClicked

/**
 * @since   Feb. 16, 2012
 * @version Feb. 18, 2012
 * @author  ASAMI, Tomoharu
 */
class PropertySheetPanel(val contract: RecordSchema)(implicit context: SwingContext) extends GridBagPanel() {
  private val _input_fields = new ArrayMap[String, Component]
  private val _message_fields = new ArrayMap[String, Label]
  private val _handlers = new CopyOnWriteArrayList[() => Unit]

  var y = 0
  for (f <- contract.fields) {
    val cl = new Constraints {
      gridx = 0
      gridy = y
    }
    val ci = new Constraints {
      gridx = 1
      gridy = y
    }
    val cm = new Constraints {
      gridx = 0
      gridy = y + 1
      gridwidth = 2
    }
    layout += _create_label(f) -> cl
    layout += _create_input_field(f) -> ci
    layout += _create_message_field(f) -> cm
    y += 2
  }

  private def _create_label(rf: RecordField) = {
    new Label(rf.name) {
      tooltip = rf.name // summary
    }
  }
  
  private def _create_input_field(rf: RecordField) = {
    rf.datatype match {
      case XString => _create_input_field_text(rf)
      case XBase64Binary => _create_input_field_file(rf)
      case XHexBinary => _create_input_field_file(rf)
      case _ => _create_input_field_text(rf)
    }
  }

  private def _create_input_field_text(rf: RecordField) = {
    val tf = new TextField() {
      listenTo(keys)
      reactions += {
        case KeyPressed(_, Key.Enter, _, _) => _validate(rf, text)
      }
    }    
    _input_fields += rf.name -> tf
    tf
  }

  private def _create_input_field_file(rf: RecordField) = {
    new FilePropertyField()(context)
  }

  private def _create_message_field(rf: RecordField) = {
    val mf = new Label("message")
    _message_fields += rf.name -> mf
    mf.visible = false
    mf
  }

  private def _validate(rf: RecordField, text: String) = {
    rf.validate(text, context.recordContext) match {
      case Success(s) => _message_clear(rf.name)
      case Failure(e) => {
        _message_warning(rf.name, "XXX")
      } 
    }
  }

  private def _message_clear(name: String) {
    _message_fields.get(name).foreach(_.visible = false)
  }

  private def _message_warning(name: String, message: String) {
    _message_fields.get(name).foreach { v =>
      v.text = message
      v.visible = true
    }
  }

  // XXX
  def getRecord() = {
    _input_fields
  }

  def getPropertyStringList(): List[(String, String)] = {
    for ((n, c) <- _input_fields.toList) yield {
      (n, _get_string_value(c))
    }
  }

  private def _get_string_value(c: Component): String = {
    c match {
      case t: TextField => t.text
    }
  }

  def addPropertyChangeHandler(h: () => Unit) {
    _handlers.add(h)
  }

  private def _fire() {
    for (f <- _handlers) {
      f.apply()
    }
  }
}
