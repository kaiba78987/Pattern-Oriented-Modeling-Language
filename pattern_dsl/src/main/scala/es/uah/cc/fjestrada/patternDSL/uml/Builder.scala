package es.uah.cc.fjestrada.patternDSL.uml

import org.eclipse.uml2.uml.Element

trait Builder[+Owner, +Element] {
  
  def build[T >: Owner](owner: T): Element
  
}
