package es.uah.cc.fjestrada.patternDSL.uml

trait StaticableBuilder[BuilderType] {
  
  private var _static = false
  
  def isStatic = _static
  
  def setIsStatic(st: Boolean) = {
    _static = st
    this.asInstanceOf[BuilderType]
  }
   
}