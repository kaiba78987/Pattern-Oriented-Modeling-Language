/*
 * Licensed under the EUPL V.1.1
 * @author Estrada Martínez, F.J.
 */
package es.uah.cc.fjestrada.patternDSL.uml

import org.eclipse.uml2.uml._

/** Creates empty models. */
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