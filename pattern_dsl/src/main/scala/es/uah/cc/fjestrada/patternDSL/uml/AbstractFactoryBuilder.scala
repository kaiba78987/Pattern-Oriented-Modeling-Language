/*
 * Licensed under the EUPL V.1.1
 * @author Estrada Martínez, F.J.
 */
package es.uah.cc.fjestrada.patternDSL.uml

import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.Class

/** Builds an abstract factory pattern. */
class AbstractFactoryBuilder extends Builder[Package, Unit] 
with NamedElementBuilder[AbstractFactoryBuilder] {
  
  private var _products: List[String] = Nil
  private var _factories: List[String] = Nil
  
  /** Returns the products which will be produced. */
  def products = _products
  /** Returns the factories which will be produced. */
  def factories = _factories
  
  /** Sets the products which will be produced. */
  def products(p: List[String]): AbstractFactoryBuilder = {
    _products = p
    this
  }
  
  /** Sets the factories which will be produced. */
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