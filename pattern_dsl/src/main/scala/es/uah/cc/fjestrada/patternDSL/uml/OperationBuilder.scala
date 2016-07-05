package es.uah.cc.fjestrada.patternDSL.uml

import org.eclipse.uml2.uml.Element
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.Type
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.common.util.BasicEList

trait OperationBuilder[BuilderType] {
  
  private var _args: Map[String, String] = Map()
  def args = _args
  
  def args(args: Map[String, String]): BuilderType= {
    _args = _args ++ args
    this.asInstanceOf[BuilderType]
  }
  
  def arg(name: String, t: String): BuilderType= {
    _args = _args + (name -> t)
    this.asInstanceOf[BuilderType]
  }
  
  def extractTypes(stringList: List[String], model: Model):List[Type] = {
      if (stringList isEmpty) Nil
      else model.getOwnedType(stringList head, true, null, true) :: extractTypes(stringList tail, model)  
    }
    
  def toStringEList(list: List[String]):EList[String] = {
    var result: EList[String] = new BasicEList[String]()
    for (e <- list) result.add(e)
    result
  }
  
  def toTypeEList(list: List[Type]):EList[Type] = {
    var result: EList[Type] = new BasicEList[Type]()
    for (e <- list) result.add(e)
    result
  }
  
}

class ClassOperationBuilder extends Builder[org.eclipse.uml2.uml.Class, Operation] 
with AbstractableElementBuilder[ClassOperationBuilder]
with StaticableBuilder[ClassOperationBuilder]
with NamedElementBuilder[ClassOperationBuilder]
with TypedElementBuilder[ClassOperationBuilder]
with OperationBuilder[ClassOperationBuilder]{
  
  override def build[T](owner: T): Operation = {
    val o = owner.asInstanceOf[org.eclipse.uml2.uml.Class]
    val model = o.getModel()
    val paramNames = toStringEList(args.keys.toList)
    val paramTypes = args.values.toList
    val paramRealTypes = toTypeEList(extractTypes(paramTypes, model))
    
    var op = o.createOwnedOperation(name, paramNames, paramRealTypes)
    op.setType(model.getOwnedType(valueType, true, null, true))
        op.setVisibility(visibility)
        op.setIsAbstract(isAbstract)
        op.setIsStatic(isStatic)
        
        op
  }
}

class InterfaceOperationBuilder extends Builder[Interface, Operation] 
with AbstractableElementBuilder[InterfaceOperationBuilder]
with StaticableBuilder[InterfaceOperationBuilder]
with NamedElementBuilder[InterfaceOperationBuilder]
with TypedElementBuilder[InterfaceOperationBuilder]
with OperationBuilder[InterfaceOperationBuilder] {
  
  override def build[T](owner: T): Operation = {
    
    val o = owner.asInstanceOf[Interface]
    val model = o.getModel()
    val paramNames = toStringEList(args.keys.toList)
    val paramTypes = args.values.toList
    val paramRealTypes = toTypeEList(extractTypes(paramTypes, model))
    
    var op = o.createOwnedOperation(name, paramNames, paramRealTypes)
    op.setType(model.getOwnedType(valueType, true, null, true))
        op.setVisibility(visibility)
        op.setIsAbstract(isAbstract)
        op.setIsStatic(isStatic)
        
        op
  }
}

