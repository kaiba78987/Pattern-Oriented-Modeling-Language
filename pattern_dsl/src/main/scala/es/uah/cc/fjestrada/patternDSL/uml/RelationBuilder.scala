/*
 * Licensed under the EUPL V.1.1
 * @author Estrada Martínez, F.J.
 */
package es.uah.cc.fjestrada.patternDSL.uml

/**Defines features for builders for relations. 
 * 
 * @tparam BuilderType The especific type of the builder which implement it.
 */

trait RelationBuilder[BuilderType] {
  
  private var _c1: String = null
  private var _c2: String = null
  
  /** The first classifier. */
  def c1 = _c1
  /** The second classifier. */
  def c2 = _c2
  
  /** Sets the first classifier. */
  def c1(c: String): BuilderType = {
    _c1 = c
    this.asInstanceOf[BuilderType]
  }
  /** Sets the second classifier. */
  def c2(c: String): BuilderType = {
    _c2 = c
    this.asInstanceOf[BuilderType]
  }
  
}