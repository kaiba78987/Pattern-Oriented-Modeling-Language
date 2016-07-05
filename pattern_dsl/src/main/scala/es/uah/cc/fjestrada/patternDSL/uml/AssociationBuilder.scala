package es.uah.cc.fjestrada.patternDSL.uml

import org.eclipse.uml2.uml.AggregationKind
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.Association
import org.eclipse.uml2.uml.Type

class AssociationBuilder extends Builder[Package, Association]
with RelationBuilder[AssociationBuilder] 
with NavigableBuilder[AssociationBuilder] 
with CardinableBuilder[AssociationBuilder] {
  
  private var _aggregationKind = AggregationKind.NONE_LITERAL
  private var _name = ""
  
  def aggregationKind = _aggregationKind
  def name = _name
  
  def aggregationKind(k: AggregationKind): AssociationBuilder = {
    _aggregationKind = k
    this
  }
  
  def name(n: String): AssociationBuilder = {
    _name = n
    this
  }
  
  override def build[T](owner: T): Association = {
    val o = owner.asInstanceOf[Package]
    var a = o.getOwnedType(c1)
    var b = o.getOwnedType(c2)
    
    if (a == null) a = o.createOwnedInterface(c1)
    if (b == null) b = o.createOwnedInterface(c2)
    
    b.createAssociation(nav1, aggregationKind, name, 0, n1, a, nav2, aggregationKind, name, 0, n2)
  }
  
}