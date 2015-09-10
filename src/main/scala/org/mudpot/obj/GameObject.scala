package org.mudpot.obj

import org.mudpot.conf.Paths
import org.mudpot.io.{Loader, Directories, Files}
import org.mudpot.path.Path
import org.mudpot.props.{PropertiesTypeProperties, TypedProperties}

trait GameObject {
  def location: Path
}

trait ObservableObject extends GameObject {
  def lookAt: String
}

trait PropertyObject extends GameObject with TypedProperties

trait FilePropertyObject extends PropertyObject {
  private lazy val underlying = load(location)

  protected def load(location: Path): TypedProperties

  override def getString(key: String): Option[String] = underlying.getString(key)

  override def getInt(key: String): Option[Int] = underlying.getInt(key)

}

abstract class PropertiesFilePropertyObject extends FilePropertyObject {
  override protected def load(location: Path): TypedProperties = {
    val file = Paths.Implicits.development.objDir.createFile(location.toString + ".properties")
    new PropertiesTypeProperties(Loader.loadProperties(file))
  }
}

