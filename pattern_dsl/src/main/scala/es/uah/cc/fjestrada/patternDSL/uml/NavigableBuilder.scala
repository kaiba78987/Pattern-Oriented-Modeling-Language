/*
 * Licensed under the EUPL V.1.1
 * @author Estrada Martínez, F.J.
 */
package es.uah.cc.fjestrada.patternDSL.uml

/**Defines features for builders for UML relations that can be navigable. 
 * 
 * @tparam BuilderType The especific type of the builder which implement it.
 */

trait NavigableBuilder[BuilderType] {
  
  private var _nav1 = false
  private var _nav2 = true
  
  /** returns if the first element is navigable. */
  def nav1 = _nav1
  /** Returns if the second element is navigable. */
  def nav2 = _nav2
  /**Sets if the first element is navigable. */
  def nav1(isNavigable: Boolean): BuilderType = {
    _nav1 = isNavigable
    this.asInstanceOf[BuilderType]
  }
 /** Sets if the second element is navigable. */
  def nav2(isNavigable: Boolean): BuilderType = {
    _nav2 = isNavigable
    this.asInstanceOf[BuilderType]
  }
 
}