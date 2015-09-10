package org.mudpot.props

import java.util.Properties

import org.scalatest.{FlatSpec, Matchers}


trait TypedPropertiesTest extends FlatSpec with Matchers {
  def name: String = getClass.getSimpleName

  def propsToTest(method: String): TypedProperties

  name should "testString" in {
    propsToTest("testString").getString("string").get should be("string_value")
  }

  it should "testInt" in {
    propsToTest("testInt").getInt("int").get should be(1)
  }

  it should "testIntClass" in {
    propsToTest("testIntClass").getInt("int_string") should be(None)
  }


}

class PropertiesTypedPropertiesTest extends FlatSpec with TypedPropertiesTest {

  override def propsToTest(method: String): TypedProperties = {
    val props = new Properties()
    props.put("string", "string_value")
    props.put("int", "1")
    props.put("int_string", "abc")
    new PropertiesTypeProperties(props)
  }
}
