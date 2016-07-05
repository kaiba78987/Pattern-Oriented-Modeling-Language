package es.uah.cc.fjestrada.patternDSL.uml

import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.Abstraction
import org.eclipse.uml2.uml.Relationship

class AbstractionBuilder extends Builder[Package, Relationship] 
with RelationBuilder[AbstractionBuilder] {
  
  override def build[T](owner: T): Relationship = {
    val o = owner.asInstanceOf[Package]
    var parent = o.getOwnedType(c1)
    var child = o.getOwnedType(c2)
    
    if (parent == null) parent = o.createOwnedInterface(c1)
    if (child == null) child = o.createOwnedClass(c2, true)
    
    if (parent.isInstanceOf[Class]) new GeneralizationBuilder().c1(c1).c2(c2).build(o)
    else new RealizationBuilder().c1(c1).c2(c2).build(o)
  }
  
}