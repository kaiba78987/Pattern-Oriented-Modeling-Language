package es.uah.cc.fjestrada.patternDSL.uml

import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Package

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