package es.uah.cc.fjestrada.patternDSL.uml

import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.Classifier

class ClassifierAreaBuilder extends Builder[Package, List[Classifier]] {
  
  private var _classifiers: List[AbstractClassifierBuilder[Classifier]] = Nil
  
  def classifiers = _classifiers
  
  def classifiers(cs: List[AbstractClassifierBuilder[Classifier]]): ClassifierAreaBuilder = {
    _classifiers = cs
    this
  }
  
  override def build[T](owner: T):List[Classifier] = {
    val o = owner.asInstanceOf[Package]
    var l: List[Classifier] = Nil
    for (c <- classifiers) l = c.build(o)::l
    l
  }
  
}