package es.uah.cc.fjestrada.patternDSL.uml

import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.VisibilityKind
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.uml2.uml.Type
import org.eclipse.uml2.uml.VisibilityKind

class PrototypeBuilder extends Builder[Package, Class] 
with NamedElementBuilder[PrototypeBuilder] {
  
  override def build[T](owner: T): Class = {
    val o = owner.asInstanceOf[Package]
    var c = o.getOwnedType(name, false, null, false).asInstanceOf[Class]
    if (c == null) c = o.createOwnedClass(name, false)
    val op = c.createOwnedOperation("clone", new BasicEList[String](), new BasicEList[Type]())
    op.setType(c)
    op.setVisibility(VisibilityKind.PUBLIC_LITERAL)
    op.setIsAbstract(false)
    op.setIsStatic(false)
    
    c
  }
  
}