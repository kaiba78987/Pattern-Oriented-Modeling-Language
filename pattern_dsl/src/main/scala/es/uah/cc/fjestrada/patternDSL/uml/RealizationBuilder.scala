/*
 * Licensed under the EUPL V.1.1
 * @author Estrada Martínez, F.J.
 */
package es.uah.cc.fjestrada.patternDSL.uml

import org.eclipse.uml2.uml.Realization
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.Class

/** Builds a realization between an interface and a class. */
class RealizationBuilder extends Builder[Package, Realization] 
with RelationBuilder[RealizationBuilder] {
  
  override def build[T](owner: T): Realization = {
    val o = owner.asInstanceOf[Package]
    val subclass = o.getOwnedType(c2).asInstanceOf[Class]
    val interface = o.getOwnedType(c1).asInstanceOf[Interface]
    
    subclass.createInterfaceRealization(subclass.getName() + " realizes " + interface.getName(), interface)
  }
  
}