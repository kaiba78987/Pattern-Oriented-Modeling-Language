/*
 * Licensed under the EUPL V.1.1
 * @author Estrada Martínez, F.J.
 */
package es.uah.cc.fjestrada.patternDSL.uml

import org.eclipse.uml2.uml.Package

/** Builds all the pattern defined in. */
class PatternAreaBuilder extends Builder[Package, Unit] {
  
  private var _patterns: List[Builder[Any, Any]] = Nil
  
  def patterns = _patterns
  
  def patterns(p: List[Builder[Any, Any]]): PatternAreaBuilder = {
    _patterns = p
    this
  }
  
  override def build[T](owner: T): Unit = {
    val o = owner.asInstanceOf[Package]
    for (p <- patterns) p.build(o)
  }
  
}