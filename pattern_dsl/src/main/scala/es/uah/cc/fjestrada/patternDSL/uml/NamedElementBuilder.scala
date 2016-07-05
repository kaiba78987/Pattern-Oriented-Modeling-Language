package es.uah.cc.fjestrada.patternDSL.uml

import org.eclipse.uml2.uml.VisibilityKind

trait NamedElementBuilder[BuilderType] {
  
  private var _name = ""
  private var _visibility = VisibilityKind.get(VisibilityKind.PRIVATE)
  
  def name = _name
  def name(name: String):BuilderType = {
    _name = name
    this.asInstanceOf[BuilderType]
  }
  
 def visibility = _visibility
 
 def isPublic():BuilderType = {
    _visibility = VisibilityKind.get(VisibilityKind.PUBLIC)
    this.asInstanceOf[BuilderType]
  }
  
  def isPrivate():BuilderType = {
    _visibility = VisibilityKind.get(VisibilityKind.PRIVATE)
    this.asInstanceOf[BuilderType]
  }
  
  def isProtected():BuilderType = {
    _visibility = VisibilityKind.get(VisibilityKind.PROTECTED)
    this.asInstanceOf[BuilderType]
  }
  
  def isPackage():BuilderType = {
    _visibility = VisibilityKind.get(VisibilityKind.PACKAGE)
    this.asInstanceOf[BuilderType]
  }
  
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