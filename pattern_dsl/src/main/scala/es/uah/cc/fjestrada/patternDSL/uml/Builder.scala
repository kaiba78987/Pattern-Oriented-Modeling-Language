/*
 * Licensed under the EUPL V.1.1
 * @author Estrada Martínez, F.J.
 */
package es.uah.cc.fjestrada.patternDSL.uml

import org.eclipse.uml2.uml.Element

/** Defines the trait to implement a builder.
 *  
 *  @tparam Owner The owner of the element built.
 *  @tparam Element The type of element built.
 */
trait Builder[+Owner, +Element] {
  
  def build[T >: Owner](owner: T): Element
  
}
