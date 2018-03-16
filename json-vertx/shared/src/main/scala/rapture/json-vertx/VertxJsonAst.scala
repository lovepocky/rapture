/*
  Rapture, version 2.0.0. Copyright 2010-2016 Jon Pretty, Propensive Ltd.

  The primary distribution site is

    http://rapture.io/

  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
  compliance with the License. You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is
  distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and limitations under the License.
 */

package rapture.json.jsonBackends.vertx

import rapture.core._
import rapture.json._

import scala.collection.JavaConverters._

/** A type class for Jackson parsing */
private[vertx] object VertxJsonAst extends JsonAst {

  override def toString = "<VertxJsonAst>"

  import io.vertx.core.json.{JsonObject, JsonArray}
  import io.vertx.lang.scala.json.{Json => VSJson}

  override def getBoolean(boolean: Any): Boolean = boolean.asInstanceOf[Boolean]

  override def fromBoolean(boolean: Boolean): Any = boolean

  override def getString(string: Any): String = string.asInstanceOf[String]

  override def fromString(string: String): Any = string

  override def getDouble(number: Any): Double = number.asInstanceOf[Double]

  override def fromDouble(number: Double): Any = number

  override def getBigDecimal(number: Any): BigDecimal = number match {
    case n: java.lang.Integer => BigDecimal(n)
    case n: java.lang.Double  => BigDecimal(n)
  }

  override def fromBigDecimal(number: BigDecimal): Any = number

  override def isBoolean(any: Any): Boolean = any.isInstanceOf[Boolean]

  override def isString(any: Any): Boolean = any.isInstanceOf[String]

  override def isNumber(any: Any): Boolean = any.isInstanceOf[Double] || any.isInstanceOf[BigDecimal] || any.isInstanceOf[java.lang.Integer]

  override def isNull(any: Any): Boolean = any == null

  override def nullValue: Any = null

  override def isObject(any: Any): Boolean = any.isInstanceOf[JsonObject]

  override def isArray(any: Any): Boolean = any.isInstanceOf[JsonArray]

  override def getObject(obj: Any): Map[String, Any] = obj.asInstanceOf[JsonObject].getMap.asScala.toMap

  override def fromObject(obj: Map[String, Any]): Any = VSJson.obj(obj.toSeq: _*)

  override def getArray(array: Any): Seq[Any] = array.asInstanceOf[JsonArray].getList.asScala

  override def fromArray(array: Seq[Any]): Any = VSJson.arr(array)
}
