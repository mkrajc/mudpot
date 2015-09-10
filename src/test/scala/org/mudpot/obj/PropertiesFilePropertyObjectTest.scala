package org.mudpot.obj

import org.mudpot.path.Path
import org.scalatest.{Matchers, FlatSpec}


class PropertiesFilePropertyObjectTest extends FlatSpec with Matchers {

  it should "load file and read properties" in {
    import org.mudpot.conf.Paths.Implicits.development
    val p = new PropertiesFilePropertyObject() {
      override def location: Path = Path("/PropertiesFilePropertyObjectTest")
    }
    p.getString("hello") should be(Some("world"))
  }

}
