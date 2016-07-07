/*
 * Licensed under the EUPL V.1.1
 * @author Estrada Martínez, F.J.
 */
package es.uah.cc.fjestrada.patternDSL.uml

import org.eclipse.uml2.uml.Package

/** Builds a class. */
class ClassBuilder extends AbstractClassifierBuilder[org.eclipse.uml2.uml.Class] 
with AbstractableElementBuilder[ClassBuilder]
with NamedElementBuilder[ClassBuilder]
with ClassifierBuilder[ClassBuilder] {
  
  override def build[T](owner: T): org.eclipse.uml2.uml.Class = {
    val o = owner.asInstanceOf[Package]
    var c: org.eclipse.uml2.uml.Class = null
      if (o.getOwnedType(name) != null) c = o.getOwnedType(name).asInstanceOf[org.eclipse.uml2.uml.Class]
      else c = o.createOwnedClass(name, isAbstract)
    c.setVisibility(visibility)
    for (f <- features) f.build(c)
    c
  }
  
}