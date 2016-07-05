package es.uah.cc.fjestrada.patternDSL.uml

trait TypedElementBuilder[BuilderType] {
  
  private var _type = "void"
  
  def valueType = _type  
  
    def valueType(t: String):BuilderType= {
    _type = t
    this.asInstanceOf[BuilderType]
  }
  
  }