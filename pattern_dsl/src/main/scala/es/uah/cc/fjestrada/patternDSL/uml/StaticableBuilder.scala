/*
 * Licensed under the EUPL V.1.1
 * @author Estrada Martínez, F.J.
 */
package es.uah.cc.fjestrada.patternDSL.uml

/**Defines features for builders for UML elements that can be static. 
 * 
 * @tparam BuilderType The especific type of the builder which implement it.
 */

trait StaticableBuilder[BuilderType] {
  
  private var _static = false
  /** Returns if it is static. */
  def isStatic = _static
  /** Sets if it is static. */
  def setIsStatic(st: Boolean) = {
    _static = st
    this.asInstanceOf[BuilderType]
  }
   
}