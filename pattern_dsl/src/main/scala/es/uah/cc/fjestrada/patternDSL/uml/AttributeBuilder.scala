package es.uah.cc.fjestrada.patternDSL.uml

import org.eclipse.uml2.uml._

class AttributeBuilder extends Builder[org.eclipse.uml2.uml.Class, Property]
with NamedElementBuilder[AttributeBuilder] 
with TypedElementBuilder[AttributeBuilder]
with StaticableBuilder[AttributeBuilder] {
    
    override def build[T](owner: T):Property = {
      val o = owner.asInstanceOf[org.eclipse.uml2.uml.Class]
    val model = o.getModel()
    var prop = o.createOwnedAttribute(name, model.getOwnedType(valueType))
        prop.setVisibility(visibility)
        prop.setIsStatic(isStatic)
        
        prop
  }
  
}