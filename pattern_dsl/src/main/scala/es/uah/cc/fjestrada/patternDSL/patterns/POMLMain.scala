/*
 * Licensed under the EUPL V.1.1
 * @author Estrada Martínez, F.J.
 */

package es.uah.cc.fjestrada.patternDSL.patterns

import java.io.FileReader
import java.io.IOException

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil

/** Entry point for the application. */
object POMLMain extends DSLParser {
  
  def main(args: Array[String]) {
   val reader = new FileReader(args(0))
   val m = parseAll(model, reader).get
   val uri = URI.createURI(args(1))
   val resourceSet = new ResourceSetImpl()
   // UMLResourcesUtil.init(resourceSet)
   UMLResourcesUtil.initGlobalRegistries()
   val resource = resourceSet.createResource(uri)
   resource.getContents().add(m)
   try {
     resource.save(null)
     println("Compilación completada con éxito")
     } catch {
       case e: IOException => err(e.getMessage())
       }
  }
  
}