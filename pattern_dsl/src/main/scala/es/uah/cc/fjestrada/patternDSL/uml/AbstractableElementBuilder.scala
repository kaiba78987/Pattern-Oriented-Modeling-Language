/*
 * Licensed under the EUPL V.1.1
 * @author Estrada Martínez, F.J.
 */
package es.uah.cc.fjestrada.patternDSL.uml

/**Defines features for builders for UML elements that can be abstract. 
 * 
 * @tparam BuilderType The especific type of the builder which implement it.
 */
trait AbstractableElementBuilder[BuilderType] {
  
  private var _abstract = false

  /** Returns if the element will be abstract. */
  def isAbstract = _abstract
  /** Sets if the element will be abstract. */
  def setIsAbstract(abs: Boolean):BuilderType = {
    _abstract = abs
    this.asInstanceOf[BuilderType]
  }
   
}