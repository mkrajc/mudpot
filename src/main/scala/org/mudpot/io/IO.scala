package org.mudpot.io

import java.io
import java.io.IOException
import java.net.URI
import java.nio.file.Paths

trait Directory extends FSNode {
  def files: List[File]

  def dirs: List[Directory]

  def list: List[FSNode]

  /**
   * Returns file in directory.
   *
   * @param filename name of file
   * @return file in directory
   */
  def createFile(filename: String): File

  def createDir(dirname: String): Directory

}

trait File extends FSNode

trait FSNode {
  def create(): Boolean

  /**
   * Creates file and also parent if not exists.
   * @return true if created
   */
  def createFull(): Boolean

  def exist(): Boolean

  def delete(): Boolean

  def toUri: URI

  def toJavaFile: java.io.File

  def getParent: Directory

  def getName: String

  def getPath: String

  def isDirectory: Boolean

  def isFile: Boolean
}

private abstract class AbstractFSNode(private val javaFile: java.io.File) extends FSNode {
  override def isFile: Boolean = javaFile.isFile

  override def isDirectory: Boolean = javaFile.isDirectory

  override def exist(): Boolean = javaFile.exists()

  override def getName: String = javaFile.getName

  override def getParent: Directory = Directories.fromFile(javaFile.getParentFile)

  override def delete(): Boolean = javaFile.delete()

  override def toUri: URI = javaFile.toURI

  override def toJavaFile: io.File = javaFile

  override def getPath: String = javaFile.getAbsolutePath

}

object Files {

  def fromFile(file: java.io.File): File = {
    if (file.isFile || !file.exists()) {
      new FileImpl(file)
    } else throw new IOException("File is not a file: " + file)
  }

  def fromPath(filePath: String): File = {
    val file: io.File = new io.File(filePath)
    fromFile(file)
  }

  private class FileImpl(private val javaFile: java.io.File) extends AbstractFSNode(javaFile) with File {

    override def createFull(): Boolean = {
      getParent.createFull()
      create()
    }

    override def toString: String = s"File(${javaFile.getPath})"

    override def create(): Boolean = javaFile.createNewFile()

  }

}

object Directories {
  def fromFile(dirFile: java.io.File): Directory = {
    if (dirFile.isDirectory || !dirFile.exists()) {
      new DirectoryImpl(dirFile)
    } else throw new IOException("File is not directory: " + dirFile)
  }

  def fromPath(dirPath: String): Directory = {
    val dirFile: io.File = new io.File(dirPath)
    fromFile(dirFile)
  }

  lazy val userDir: Directory = Directories.fromPath(System.getProperty("user.dir"))

  def getRoot[F <: FSNode](fSNode: F): Directory = {
    val path = Paths.get(fSNode.toUri)
    Directories.fromFile(path.toFile)
  }

  private class DirectoryImpl(private val javaFile: java.io.File) extends AbstractFSNode(javaFile) with Directory {

    override def createFile(filename: String): File = Files.fromFile(new io.File(toJavaFile, filename))

    override def createDir(dirname: String): Directory = Directories.fromFile(new io.File(toJavaFile, dirname))

    override def files: List[File] = javaFile.listFiles().toList.filter(_.isFile).map(Files.fromFile)

    override def dirs: List[Directory] = javaFile.listFiles().toList.filter(_.isDirectory).map(Directories.fromFile)

    override def list: List[FSNode] = {
      val listFiles: List[io.File] = javaFile.listFiles().toList
      listFiles.map(file => {
        if (file.isDirectory) {
          Directories.fromFile(file)
        } else {
          Files.fromFile(file)
        }
      })
    }

    override def toString: String = s"Dir(${javaFile.getPath})"

    override def createFull(): Boolean = javaFile.mkdirs()

    override def create(): Boolean = javaFile.mkdir()

  }

}

