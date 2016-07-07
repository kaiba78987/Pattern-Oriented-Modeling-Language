/*
 * Licensed under the EUPL V.1.1
 * @author Estrada Martínez, F.J.
 */
package es.uah.cc.fjestrada.patternDSL.uml

import org.eclipse.uml2.uml.Relationship
import org.eclipse.uml2.uml.Package

/** Builds all relation defined in. */
class RelationAreaBuilder extends Builder[Package, List[Relationship]] {
  
  private var _relations: List[Builder[Any, Any]] = Nil
  
  def relations = _relations
  
  def relations(rels: List[Builder[Any, Any]]): RelationAreaBuilder = {
    _relations = rels
    this
  }
  
  override def build[T](owner: T): List[Relationship] = {
    val o = owner.asInstanceOf[Package]
    var l: List[Relationship] = Nil
    for (r <- relations) l = r.build(o).asInstanceOf[Relationship] :: l
    l
  }
  
}