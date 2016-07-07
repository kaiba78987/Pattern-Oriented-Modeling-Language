/*
 * Licensed under the EUPL V.1.1
 * @author Estrada Martínez, F.J.
 */
package es.uah.cc.fjestrada.patternDSL.uml

import org.eclipse.uml2.uml.VisibilityKind

/**Defines features for builders for UML elements that can be named. 
 * 
 * @tparam BuilderType The especific type of the builder which implement it.
 */

trait NamedElementBuilder[BuilderType] {
  
  private var _name = ""
  private var _visibility = VisibilityKind.get(VisibilityKind.PRIVATE)
  
  /** Returns the name. */
  def name = _name
  /** Sets the name. */
  def name(name: String):BuilderType = {
    _name = name
    this.asInstanceOf[BuilderType]
  }
  
 /** Returns the visibility. */
  def visibility = _visibility
 /** Sets visibility to public. */
 def isPublic():BuilderType = {
    _visibility = VisibilityKind.get(VisibilityKind.PUBLIC)
    this.asInstanceOf[BuilderType]
  }
  /** Sets the visibility to private. */
  def isPrivate():BuilderType = {
    _visibility = VisibilityKind.get(VisibilityKind.PRIVATE)
    this.asInstanceOf[BuilderType]
  }
  /** Sets the visibility to protected. */
  def isProtected():BuilderType = {
    _visibility = VisibilityKind.get(VisibilityKind.PROTECTED)
    this.asInstanceOf[BuilderType]
  }
  /** Sets the visibility to package. */
  def isPackage():BuilderType = {
    _visibility = VisibilityKind.get(VisibilityKind.PACKAGE)
    this.asInstanceOf[BuilderType]
  }
  /** Sets the visibility. */
  def setVisibility(visibility: String):BuilderType = {
    visibility match {
      case "public" => isPublic()
      case "private" => isPrivate()
      case "protected" => isProtected()
      case "package" => isPackage()
    }
    this.asInstanceOf[BuilderType]
  }
  
  }