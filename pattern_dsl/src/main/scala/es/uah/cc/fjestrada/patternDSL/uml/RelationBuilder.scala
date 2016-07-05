package es.uah.cc.fjestrada.patternDSL.uml

trait RelationBuilder[BuilderType] {
  
  private var _c1: String = null
  private var _c2: String = null
  
  def c1 = _c1
  def c2 = _c2
  
  def c1(c: String): BuilderType = {
    _c1 = c
    this.asInstanceOf[BuilderType]
  }
  
  def c2(c: String): BuilderType = {
    _c2 = c
    this.asInstanceOf[BuilderType]
  }
  
}