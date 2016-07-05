package es.uah.cc.fjestrada.patternDSL.uml

import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Package

class BuilderPatternBuilder extends ClassBuilder {
  
  override def build[T](owner:T): Class = {
    val o = owner.asInstanceOf[Package]
    var elem = o.getOwnedType(name)
    var builder = o.getOwnedType(name + "Builder").asInstanceOf[Class]
    if (elem == null) elem = o.createOwnedInterface(name)
    if (builder == null) builder = o.createOwnedClass(name + "Builder", false)
    
    for (f <- features) {
      val o = f.asInstanceOf[ClassOperationBuilder]
      o.valueType(name + "Builder").isPublic().build(builder)
    }
    
    val buildMethod = new ClassOperationBuilder().name("build").valueType(name).isPublic().build(builder)
    builder
  }
  
}