package es.uah.cc.fjestrada.patternDSL.uml

import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.Class

class AbstractFactoryBuilder extends Builder[Package, Unit] 
with NamedElementBuilder[AbstractFactoryBuilder] {
  
  private var _products: List[String] = Nil
  private var _factories: List[String] = Nil
  
  def products = _products
  def factories = _factories
  
  def products(p: List[String]): AbstractFactoryBuilder = {
    _products = p
    this
  }
  
  def factories(f: List[String]): AbstractFactoryBuilder = {
    _factories = f
    this
  }
  
  override def build[T](owner: T): Unit = {
    val o = owner.asInstanceOf[Package]
    
    var absFactory = o.getOwnedType(name + "Factory").asInstanceOf[Interface]
    if (absFactory == null) absFactory = o.createOwnedInterface(name + "Factory")
    var prs: List[Interface] = Nil
    for (p <- products) {
      var pr = o.getOwnedType(p).asInstanceOf[Interface]
      if (pr == null) pr = o.createOwnedInterface(p)
      new InterfaceOperationBuilder().name("create" + p).valueType(p).build(absFactory)
      prs = pr :: prs
    }
    
    for (f <- factories) {
      var fact = o.getOwnedType(f + name + "Factory").asInstanceOf[Class]
      if (fact == null) fact = o.createOwnedClass(f + name + "Factory", false)
      fact.createInterfaceRealization("", absFactory)
      
      for (p <- prs) {
        var pr = o.getOwnedType(f + p.getName()).asInstanceOf[Class]
        if (pr == null) pr = o.createOwnedClass(f + p.getName(), false)
        pr.createInterfaceRealization("", p)
      }
    }
  }
  
}