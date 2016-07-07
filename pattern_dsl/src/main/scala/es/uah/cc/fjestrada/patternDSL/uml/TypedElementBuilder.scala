/*
 * Licensed under the EUPL V.1.1
 * @author Estrada Martínez, F.J.
 */
package es.uah.cc.fjestrada.patternDSL.uml

/**Defines features for builders for UML elements that can be typed. 
 * 
 * @tparam BuilderType The especific type of the builder which implement it.
 */

trait TypedElementBuilder[BuilderType] {
  
  private var _type = "void"
  /** Returns the type of the element. */
  def valueType = _type  
  /** Sets the type of the element. */
    def valueType(t: String):BuilderType= {
    _type = t
    this.asInstanceOf[BuilderType]
  }
  
  }