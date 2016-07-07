package es.uah.cc.fjestrada.patternDSL.patterns

import java.io.FileReader

import org.junit.runner.RunWith
import org.scalatest.Suite
import org.scalatest.junit.JUnitRunner

import es.uah.cc.fjestrada.patternDSL.uml.AbstractionBuilder
import es.uah.cc.fjestrada.patternDSL.uml.AggregationBuilder
import es.uah.cc.fjestrada.patternDSL.uml.AssociationBuilder
import es.uah.cc.fjestrada.patternDSL.uml.AttributeBuilder
import es.uah.cc.fjestrada.patternDSL.uml.BuilderPatternBuilder
import es.uah.cc.fjestrada.patternDSL.uml.ClassBuilder
import es.uah.cc.fjestrada.patternDSL.uml.ClassOperationBuilder
import es.uah.cc.fjestrada.patternDSL.uml.CompossitionBuilder
import es.uah.cc.fjestrada.patternDSL.uml.FactoryMethodBuilder
import es.uah.cc.fjestrada.patternDSL.uml.InterfaceBuilder
import es.uah.cc.fjestrada.patternDSL.uml.InterfaceOperationBuilder
import es.uah.cc.fjestrada.patternDSL.uml.NamedElementBuilder
import es.uah.cc.fjestrada.patternDSL.uml.PrototypeBuilder
import es.uah.cc.fjestrada.patternDSL.uml.SingletonBuilder
import org.scalatest.Specs
import org.scalatest.Suites

@RunWith(classOf[JUnitRunner])
class ParserSuite extends Suites {
  
  val basePath = "src/test/resources/"
  val parser = new DSLParser()
  
  def testArg() {
    val reader = new FileReader(basePath + "arg.poml")
   val res = parser.parseAll(parser.arg, reader)
   assert(res.successful)
   assert(res.get._1 === "x")
   assert(res.get._2 === "int")
  }
  
  def testNoneArgs() {
    val reader = new FileReader(basePath + "none_args.poml")
   val res = parser.parseAll(parser.args, reader)
   assert(res.successful)
   val m = res.get
   assert(m.size === 0)
  }
  
  def testSingleArgs() {
    val reader = new FileReader(basePath + "single_args.poml")
   val res = parser.parseAll(parser.args, reader)
   assert(res.successful)
   val m = res.get
   assert(m.size === 1)
    val z = m.get("z")
    z match {
      case Some(t) => assert(t === "double")
      case None => assert(false, "No hay parámetro z declarado.")
    }
    
  }
  
  def testManyArgs() {
    val reader = new FileReader(basePath + "many_args.poml")
   val res = parser.parseAll(parser.args, reader)
   assert(res.successful)
   val m = res.get
   assert(m.size === 3)
   val x = m.get("x")
   x match {
      case Some(t) => assert(t === "int")
      case None => assert(false, "No hay parámetro x declarado.")
    }
    val a = m.get("a")
    a match {
      case Some(t) => assert(t === "String")
      case None => assert(false, "No hay parámetro a declarado.")
    }
    val b = m.get("b")
    b match {
      case Some(t) => assert(t === "boolean")
      case None => assert(false, "No hay parámetro b declarado.")
    } 
  }
  
  def testMethod1() {
    val reader = new FileReader(basePath + "method1.poml")
   val res = parser.parseAll(parser.cMethod, reader)
   assert(res.successful)
   val builder = res.get
    val builderCheck = new ClassOperationBuilder().name("clear").valueType("void").setIsStatic(true)
    assert(builder.name === builderCheck.name)
    assert(builder.valueType === builderCheck.valueType)
  }
 
  def testMethod2() {
    val reader = new FileReader(basePath + "method2.poml")
   val res = parser.parseAll(parser.cMethod, reader)
   assert(res.successful)
   val builder = res.get
    val builderCheck = new ClassOperationBuilder().name("resize").valueType("void").arg("altura", "int").arg("anchura", "int").isPublic()
    assert(builder.name === builderCheck.name)
    assert(builder.valueType === builderCheck.valueType)
    assert(builder.args.size === builderCheck.args.size)
    assert(builder.args.get("altura") === builderCheck.args.get("altura"))
    assert(builder.args.get("anchura") === builderCheck.args.get("anchura"))
  }
 
  def testMethod3() {
    val reader = new FileReader(basePath + "method3.poml")
   val res = parser.parseAll(parser.cMethod, reader)
   assert(res.successful)
   val builder = res.get
    val builderCheck = new ClassOperationBuilder().name("colorear").valueType("void").arg("color", "Color").isPublic().setIsAbstract(true)
    assert(builder.name === builderCheck.name)
    assert(builder.valueType === builderCheck.valueType)
    assert(builder.args.size === builderCheck.args.size)
    assert(builder.args.get("altura") === builderCheck.args.get("altura"))
    assert(builder.args.get("anchura") === builderCheck.args.get("anchura"))
    assert(builder.isAbstract === builderCheck.isAbstract)
  }
 
  def testAtb1() {
    val reader = new FileReader(basePath + "atb1.poml")
   val res = parser.parseAll(parser.atb, reader)
   assert(res.successful)
   val builder = res.get
    val builderCheck = new AttributeBuilder().name("x").valueType("int")
    assert(builder.name === builderCheck.name)
    assert(builder.valueType === builderCheck.valueType)
    assert(builder.visibility === builderCheck.visibility)
    assert(builder.isStatic === builderCheck.isStatic)
  }
 
  def testAtb2() {
    val reader = new FileReader(basePath + "atb2.poml")
   val res = parser.parseAll(parser.atb, reader)
   assert(res.successful)
   val builder = res.get
    val builderCheck = new AttributeBuilder().name("x").valueType("int").isPrivate()
    assert(builder.name === builderCheck.name)
    assert(builder.valueType === builderCheck.valueType)
    assert(builder.visibility === builderCheck.visibility)
    assert(builder.isStatic === builderCheck.isStatic)
  }
 
  def testAtb3() {
    val reader = new FileReader(basePath + "atb3.poml")
   val res = parser.parseAll(parser.atb, reader)
   assert(res.successful)
   val builder = res.get
    val builderCheck = new AttributeBuilder().name("x").valueType("int").isPublic().setIsStatic(true)
    assert(builder.name === builderCheck.name)
    assert(builder.valueType === builderCheck.valueType)
    assert(builder.visibility === builderCheck.visibility)
    assert(builder.isStatic === builderCheck.isStatic)
  }
  
  def testEmptyInterface() {
    val reader = new FileReader(basePath + "empty_interface.poml")
   val res = parser.parseAll(parser.interface, reader)
   assert(res.successful)
   val builder = res.get
    val builderCheck = new InterfaceBuilder().name("Empty").setVisibility("public")
    assert(builder.name === builderCheck.name)
    assert(builder.visibility === builderCheck.visibility)
    }
  
  def testInterface() {
    val reader = new FileReader(basePath + "interface.poml")
   val res = parser.parseAll(parser.interface, reader)
   assert(res.successful)
   val builder = res.get
   val m1 = new InterfaceOperationBuilder().name("clear").valueType("void")
   val m2 = new InterfaceOperationBuilder().name("resize").valueType("void").setVisibility("public").arg("height", "int").arg("width", "int")
    val builderCheck = new InterfaceBuilder().name("Example").features(List(m1, m2))
    assert(builder.name === builderCheck.name)
    assert(builder.visibility === builderCheck.visibility)
    assert(builder.features.size === builderCheck.features.size)
    }
  
  def testEmptyClass() {
    val reader = new FileReader(basePath + "empty_class.poml")
   val res = parser.parseAll(parser._class, reader)
   assert(res.successful)
   val builder = res.get
    val builderCheck = new ClassBuilder().name("Empty").setIsAbstract(true)
    assert(builder.name === builderCheck.name)
    assert(builder.visibility === builderCheck.visibility)
    assert(builder.isAbstract === builderCheck.isAbstract)
    }
  
  def testClass() {
    val reader = new FileReader(basePath + "class.poml")
   val res = parser.parseAll(parser._class, reader)
   assert(res.successful)
   val builder = res.get
   val a1 = new AttributeBuilder().name("height").valueType("int")
   val a2 = new AttributeBuilder().name("width").valueType("int")
   val m1 = new ClassOperationBuilder().name("clear").valueType("void").isPublic()
   val m2 = new ClassOperationBuilder().name("resize").valueType("void").arg("height", "int").arg("width", "int")
   val m3 = new ClassOperationBuilder().name("setOpacity").valueType("void").isPublic().arg("opacity", "int").arg("width", "int")
    val builderCheck = new ClassBuilder().name("Canvas").isPublic().features(List(a1, a2, m1, m2, m3))
    assert(builder.name === builderCheck.name)
    assert(builder.visibility === builderCheck.visibility)
    assert(builder.isAbstract === builderCheck.isAbstract)
    assert(builder.features.size === builderCheck.features.size)
  }
  
  def testClassifierArea() {
    val reader = new FileReader(basePath + "classifier_area.poml")
   val res = parser.parseAll(parser.classifierArea, reader)
   assert(res.successful)
   val builder = res.get
   assert(builder.classifiers.size == 2)
    assert(builder.classifiers(0).asInstanceOf[NamedElementBuilder[Any]].name === "Canvas")
    assert(builder.classifiers(1).asInstanceOf[NamedElementBuilder[Any]].name === "Color")
  }
  
  def testAbstraction() {
    val reader = new FileReader(basePath + "abstraction.poml")
   val res = parser.parseAll(parser.abstraction, reader)
   assert(res.successful)
   val builder = res.get
   assert(builder.isInstanceOf[AbstractionBuilder])
    assert(builder.c1 === "Color")
    assert(builder.c2 === "ColorImpl")
  }
  
  def testAssociation() {
    val reader = new FileReader(basePath + "association.poml")
   val res = parser.parseAll(parser.assoc, reader)
   assert(res.successful)
   val builder = res.get
   assert(builder.isInstanceOf[AssociationBuilder])
    assert(builder.c1 === "Points")
    assert(builder.c2 === "Line")
  }
  
  def testAggregation() {
    val reader = new FileReader(basePath + "aggregation.poml")
   val res = parser.parseAll(parser.assoc, reader)
   assert(res.successful)
   val builder = res.get
   assert(builder.isInstanceOf[AggregationBuilder])
    assert(builder.c1 === "Points")
    assert(builder.c2 === "Line")
  }
  
  def testCompossition() {
    val reader = new FileReader(basePath + "compossition.poml")
   val res = parser.parseAll(parser.assoc, reader)
   assert(res.successful)
   val builder = res.get
   assert(builder.isInstanceOf[CompossitionBuilder])
    assert(builder.c1 === "Points")
    assert(builder.c2 === "Line")
  }
  
  def testSingleton() {
    val reader = new FileReader(basePath + "singleton.poml")
   val res = parser.parseAll(parser.singleton, reader)
   assert(res.successful)
   val builder = res.get
   assert(builder.isInstanceOf[SingletonBuilder])
    assert(builder.name === "MySingleton")
  }
  
  def testPrototype() {
    val reader = new FileReader(basePath + "prototype.poml")
   val res = parser.parseAll(parser.prototype, reader)
   assert(res.successful)
   val builder = res.get
   assert(builder.isInstanceOf[PrototypeBuilder])
    assert(builder.name === "MyPrototype")
  }
  
  def testFactoryMethod() {
    val reader = new FileReader(basePath + "factory_method.poml")
   val res = parser.parseAll(parser.factoryMethod, reader)
   assert(res.successful)
   val builder = res.get
   assert(builder.isInstanceOf[FactoryMethodBuilder])
    assert(builder.name === "Color")
    assert(builder.features.size === 6)
  }
  
  def testBuilder() {
    val reader = new FileReader(basePath + "builder.poml")
   val res = parser.parseAll(parser.builder, reader)
   assert(res.successful)
   val builder = res.get
   assert(builder.isInstanceOf[BuilderPatternBuilder])
    assert(builder.name === "Casa")
    assert(builder.features.size === 7)
  }
  
}