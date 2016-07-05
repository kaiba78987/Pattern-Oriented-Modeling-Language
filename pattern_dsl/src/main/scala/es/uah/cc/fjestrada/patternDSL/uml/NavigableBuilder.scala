package es.uah.cc.fjestrada.patternDSL.uml

trait NavigableBuilder[BuilderType] {
  
  private var _nav1 = false
  private var _nav2 = true
  
  def nav1 = _nav1
  def nav2 = _nav2
  
  def nav1(isNavigable: Boolean): BuilderType = {
    _nav1 = isNavigable
    this.asInstanceOf[BuilderType]
  }
 
  def nav2(isNavigable: Boolean): BuilderType = {
    _nav2 = isNavigable
    this.asInstanceOf[BuilderType]
  }
 
}