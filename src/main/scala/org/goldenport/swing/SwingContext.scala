package org.goldenport.swing

import org.goldenport.record.{RecordContext, DefaultRecordContext}
import java.io.File

/**
 * @since   Feb. 18, 2012
 * @version Feb. 18, 2012
 * @author  ASAMI, Tomoharu
 */
trait SwingContext {
  def recordContext: RecordContext
  def userDir: File
  def labelFileChooser: String = "ファイルを開く"
  def labelFileNotOpened: String = "ファイルを開けませんでした。"
}

object DefaultSwingContext extends SwingContext {
  val recordContext = DefaultRecordContext
  val userDir = new File(System.getProperty("user.dir"))
}
