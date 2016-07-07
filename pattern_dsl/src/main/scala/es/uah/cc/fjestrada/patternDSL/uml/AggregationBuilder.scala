/*
 * Licensed under the EUPL V.1.1
 * @author Estrada Martínez, F.J.
 */
package es.uah.cc.fjestrada.patternDSL.uml

import org.eclipse.uml2.uml.AggregationKind

/** Builds an aggregation between two classifiers. */
class AggregationBuilder extends AssociationBuilder {
  
  aggregationKind(AggregationKind.SHARED_LITERAL)
  
}