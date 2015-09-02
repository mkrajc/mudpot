package org.mudpot.utils

import java.io.File

object FileUtils {
  def relativePath(parent: File, child: File): String = {
    parent.toURI.relativize(child.toURI).getPath
  }

  implicit def string2file(name: String): File = new File(name)

}
