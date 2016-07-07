/*
 * Licensed under the EUPL V.1.1
 * @author Estrada Martínez, F.J.
 */
package es.uah.cc.fjestrada.patternDSL.uml

import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.Interface

/** Builds an interface. */
class InterfaceBuilder extends AbstractClassifierBuilder[Interface] 
with NamedElementBuilder[InterfaceBuilder]
with ClassifierBuilder[InterfaceBuilder]{
  
  override def build[T](owner: T): Interface = {
    val o = owner.asInstanceOf[Package]
    var i: Interface = null
      if (o.getOwnedType(name) != null) i = o.getOwnedType(name).asInstanceOf[Interface]
      else i = o.createOwnedInterface(name)
    i.setVisibility(visibility)
    for (f <- features) f.build(i)
    i
  }
  
}