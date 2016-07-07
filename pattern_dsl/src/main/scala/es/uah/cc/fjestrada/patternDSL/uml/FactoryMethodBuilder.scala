/*
 * Licensed under the EUPL V.1.1
 * @author Estrada Martínez, F.J.
 */
package es.uah.cc.fjestrada.patternDSL.uml

import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Package

/** Builds a factory method pattern. */
class FactoryMethodBuilder extends ClassBuilder {
  
  override def build[T](owner:T): Class = {
    val o = owner.asInstanceOf[Package]
    var elem = o.getOwnedType(name)
    var factory = o.getOwnedType(name + "Factory").asInstanceOf[Class]
    if (elem == null) elem = o.createOwnedInterface(name)
    if (factory == null) factory = o.createOwnedClass(name + "Factory", false)
    
    for (f <- features) {
      val o = f.asInstanceOf[ClassOperationBuilder]
      o.valueType(name).isPublic().build(factory)
    }
    factory
  }
  
}