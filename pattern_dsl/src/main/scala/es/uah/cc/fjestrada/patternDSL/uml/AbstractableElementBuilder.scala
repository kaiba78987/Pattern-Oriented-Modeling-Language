package es.uah.cc.fjestrada.patternDSL.uml

trait AbstractableElementBuilder[BuilderType] {
  
  private var _abstract = false
  
  def isAbstract = _abstract
  
  def setIsAbstract(abs: Boolean):BuilderType = {
    _abstract = abs
    this.asInstanceOf[BuilderType]
  }
   
}