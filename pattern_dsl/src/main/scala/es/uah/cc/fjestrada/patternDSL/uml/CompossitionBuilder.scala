/*
 * Licensed under the EUPL V.1.1
 * @author Estrada Martínez, F.J.
 */
package es.uah.cc.fjestrada.patternDSL.uml

import org.eclipse.uml2.uml.AggregationKind

/** Builds a compossition relation between two classifiers. */
class CompossitionBuilder extends AssociationBuilder{
  
  aggregationKind(AggregationKind.COMPOSITE_LITERAL)
  
}