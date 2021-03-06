package scalajs.shared

import scala.scalajs.js
import scala.scalajs.js.Dynamic
import scala.scalajs.js.annotation.JSExportAll

/**
 * This class needs to be serializable (non arg constructor and with vars),
 * and also needs to be able to export all to Scala JS as Javascript object.
 */
@JSExportAll
case class Resource(var x : Int) {
  
  def this() = this(0)
}

object Resource {
  
  def toDynamic(x : Int) = {
    ResourceValidator(new Resource(x))
    Dynamic.literal(x = x).asInstanceOf[js.Object]
  }
}