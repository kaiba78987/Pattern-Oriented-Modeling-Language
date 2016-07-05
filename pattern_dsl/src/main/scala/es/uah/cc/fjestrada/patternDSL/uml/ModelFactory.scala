package es.uah.cc.fjestrada.patternDSL.uml

import org.eclipse.uml2.uml._

object ModelFactory {
  
  def apply(name: String): Model = {
    var model = UMLFactory.eINSTANCE.createModel()
      model.setName(name)
     model.createOwnedPrimitiveType("int")
     model.createOwnedPrimitiveType("float")
     model.createOwnedPrimitiveType("string")
     model.createOwnedPrimitiveType("boolean")
     model
  }
  
}