/*
 * Licensed under the EUPL V.1.1
 * @author Estrada Martínez, F.J.
 */
package es.uah.cc.fjestrada.patternDSL.uml

import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Feature

/** Defines common features for classifiers. */
trait ClassifierBuilder[BuilderType] {
  
  private var _features: List[Builder[Classifier, Feature]] = Nil
  
  /** Returns the features defined for the classifier. */
  def features = _features
  /** Sets the features defined for the classifier. */
  def features(f: List[Builder[Classifier, Feature]]):BuilderType = {
    _features = f
    this.asInstanceOf[BuilderType]
  }
/** Adds a feature for the classifier. */
  def feature(feature: Builder[Classifier, Feature]): BuilderType= {
    _features = feature :: _features
    this.asInstanceOf[BuilderType]
  }
  
}

/** Defines a generic classifier builder.
 *  
 *  @tparam ClassifierType The classifier type to build.
 */
abstract class AbstractClassifierBuilder[+ClassifierType] extends Builder[Package, ClassifierType] {
  
}