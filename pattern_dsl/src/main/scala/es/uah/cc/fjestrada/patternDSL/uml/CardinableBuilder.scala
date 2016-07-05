package es.uah.cc.fjestrada.patternDSL.uml

trait CardinableBuilder[BuilderType] {
  
  private var _n1 = 1
  private var _n2 = 1
  
  def n1 = _n1
  def n2 = _n2
  
  def n1(n: Int): BuilderType = {
    _n1 = n
    this.asInstanceOf[BuilderType]
  }
  
  def n2(n: Int): BuilderType = {
    _n2 = n
    this.asInstanceOf[BuilderType]
  }
  
}