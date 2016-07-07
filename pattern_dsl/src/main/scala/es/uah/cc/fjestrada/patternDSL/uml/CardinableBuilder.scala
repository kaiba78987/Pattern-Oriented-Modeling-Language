/*
 * Licensed under the EUPL V.1.1
 * @author Estrada Martínez, F.J.
 */
package es.uah.cc.fjestrada.patternDSL.uml

/**Defines features for builders for UML relations that can be cardinable. 
 * 
 * @tparam BuilderType The especific type of the builder which implement it.
 */

trait CardinableBuilder[BuilderType] {
  
  private var _n1 = 1
  private var _n2 = 1
  
  /** Returns the cardinality for the first element. */
  def n1 = _n1
  /** Returns the cardinality for the second element. */
  def n2 = _n2
  
  /** Sets the cardinality for the first element. */
  def n1(n: Int): BuilderType = {
    _n1 = n
    this.asInstanceOf[BuilderType]
  }
  
  /** Sets the cardinality for the second element. */
  def n2(n: Int): BuilderType = {
    _n2 = n
    this.asInstanceOf[BuilderType]
  }
  
}