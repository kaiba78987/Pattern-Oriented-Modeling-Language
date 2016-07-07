/*
 * Licensed under the EUPL V.1.1
 * @author Estrada Martínez, F.J.
 */
package es.uah.cc.fjestrada.patternDSL.uml

import org.eclipse.uml2.uml.Generalization
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.Class

/** Builds a generalization between two classes. */
class GeneralizationBuilder extends Builder[Package, Generalization] 
with RelationBuilder[GeneralizationBuilder] {
  
  override def build[T](owner: T): Generalization = {
    val o = owner.asInstanceOf[Package]
    val subclass = o.getOwnedType(c2).asInstanceOf[Class]
    val superclass = o.getOwnedType(c1).asInstanceOf[Class]
    
    subclass.createGeneralization(superclass)
  }
  
}