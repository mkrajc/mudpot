package org.mudpot.conf

import com.typesafe.config.ConfigFactory


class TestConfigurable(val val_cons: Int, var def_cons: Int) extends Configurable[TestConfigurable] {

  var def_ = 10
  val val_ = 100
  lazy val lazy_val = 1000;

  override def configure(configuration: Configuration): TestConfigurable = {
    this
  }

  override def toString: String = s"$val_ $val_cons $lazy_val $def_cons $def_"
}


object TestConfigurable {

  import scala.reflect.runtime.universe._

  def main(args: Array[String]) {
    /*val t = new TestConfigurable().configure(new Configuration {
      override def getInt(key: String): Int = 500
    })*/

    val t = new TestConfigurable(5, 50)
    t.def_ = 4
    val i = 88
    val x: Any = i

    val mir = runtimeMirror(getClass.getClassLoader).reflect(t)

    println(t + "---")
    getMethods[TestConfigurable].foreach(m => {
      println(m.name.decodedName.toString.stripSuffix("_="))
      val typ = m.returnType
      println(typ)
      println(typ.getClass)
      /*val y = x.asInstanceOf[typ.type ]
      println(typ + " " + x + " " + y)*/
      mir.reflectMethod(m)(x)

    })

    getFields[TestConfigurable].foreach(f => {
      println(f.name.decodedName.toString + "  "  + f.typeSignature)
    })

    println(t + " ---")

    val test = ConfigFactory.load("test")

    val f = ConfigFactory.load("abc/test").withFallback(test)
    println(f.getBoolean("test.boolean"))
    println(f.getInt("test.int"))

    val c = f.getConfig("test")
    println(c.getBoolean("boolean"))
    println(c.getInt("int"))

  }

  def getMethods[T: TypeTag] = typeOf[T].members.collect {
    case m: MethodSymbol if m.isSetter => m
  }.toList

  def getFields[T: TypeTag] = typeOf[T].members.collect {
    case m: TermSymbol if m.isTerm => m
  }.toList

}