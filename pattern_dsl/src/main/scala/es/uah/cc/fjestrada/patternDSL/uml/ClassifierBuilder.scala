package es.uah.cc.fjestrada.patternDSL.uml

import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Feature

trait ClassifierBuilder[BuilderType] {
  
  private var _features: List[Builder[Classifier, Feature]] = Nil
  
  def features = _features
  
  def features(f: List[Builder[Classifier, Feature]]):BuilderType = {
    _features = f
    this.asInstanceOf[BuilderType]
  }

  def feature(feature: Builder[Classifier, Feature]): BuilderType= {
    _features = feature :: _features
    this.asInstanceOf[BuilderType]
  }
  
}

abstract class AbstractClassifierBuilder[+ClassifierType] extends Builder[Package, ClassifierType] {
  
}