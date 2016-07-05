package es.uah.cc.fjestrada.patternDSL.patterns

import scala.util.parsing.combinator._
import org.eclipse.uml2.uml._
import es.uah.cc.fjestrada.patternDSL.uml._


class DSLParser extends JavaTokenParsers {
  
  val visibility: Parser[String] = """(public)|(private)|(protected)|(package)""".r
  val _abstract: Parser[String] = """abstract""".r
  
  val cardinality: Parser[String] = """([0-9]+|(many))""".r
  val cardinalityWraper: Parser[String] = """\\"""".r
  
  val extension: Parser[String] = """<\|\-\-""".r
  val compossition: Parser[String] = """\*\-\-""".r
  val aggregation: Parser[String] = """o\-\-""".r
  val association: Parser[String] = """\-\-""".r
  
  def cardinal: Parser[Int] =
    cardinality ^^ {
      case c => {
          if (c == "many") Int.MaxValue
          else c.toInt
      }
  }
  
  def model: Parser[Model] = 
    "model " ~ ident ~ "{" ~ patternArea ~ relationArea ~ classifierArea ~ "}" ^^ {
    case "model " ~ name ~"{" ~ patternArea ~ relationArea ~ classifierArea ~ "}" => {
      var model = ModelFactory(name)
      classifierArea.build(model)
      patternArea.build(model)
      relationArea.build(model)
      
model
    }
    }
  
  def patternArea: Parser[PatternAreaBuilder] = 
    "patterns" ~ "{" ~ rep(pattern) ~ "}" ^^ {
    case "patterns" ~ "{" ~ patterns ~ "}" => new PatternAreaBuilder().patterns(patterns)
  }
    
    def relationArea:Parser[RelationAreaBuilder] = 
      "relations" ~ "{" ~ rep(abstraction | assoc) ~ "}" ^^ {
    case "relations" ~ "{" ~ rels ~ "}" => new RelationAreaBuilder().relations(rels)
  }
      
      def classifierArea: Parser[ClassifierAreaBuilder] = 
        "classifiers" ~ "{" ~ rep(_class | interface) ~ "}" ^^ {
    case "classifiers" ~ "{" ~ classifiers ~ "}" => new ClassifierAreaBuilder().classifiers(classifiers)
  }
  
      def pattern: Parser[Builder[Any, Any]] = (singleton | prototype | factoryMethod | builder | abstractFactory)
      
      def singleton: Parser[SingletonBuilder] = 
        "singleton" ~ ident ^^ {
        case "singleton" ~ name => new SingletonBuilder().name(name)
      }
      
      def prototype: Parser[PrototypeBuilder] = 
        "prototype" ~ ident ^^ {
        case "prototype" ~ name => new PrototypeBuilder().name(name)
      }

      def factoryMethod: Parser[FactoryMethodBuilder] =
        "factory method" ~ ident ~ "{" ~ rep1(patternMethod) ~ "}" ^^ {
        case "factory method" ~ name ~ "{" ~ methods ~ "}" => new FactoryMethodBuilder().name(name).features(methods).asInstanceOf[FactoryMethodBuilder]
      }
      
      def builder: Parser[BuilderPatternBuilder] =
        "builder" ~ ident ~ "{" ~ rep1(patternMethod) ~ "}" ^^ {
        case "builder" ~ name ~ "{" ~ methods ~ "}" => new BuilderPatternBuilder().name(name).features(methods).asInstanceOf[BuilderPatternBuilder]
      }

      def abstractFactory: Parser[AbstractFactoryBuilder] =
        "abstract factory" ~ ident ~ "products" ~ "[" ~ repsep(ident, ",") ~ "]" ~ "factories" ~ "[" ~ repsep(ident, ",") ~ "]" ^^ {
        case "abstract factory" ~ name ~ "products" ~ "[" ~ p ~ "]" ~ "factories" ~ "[" ~ f ~ "]" => new AbstractFactoryBuilder().name(name).products(p).factories(f)
        }
      
      def patternMethod: Parser[ClassOperationBuilder] = 
        ident ~ "(" ~ args ~ ")" ^^ {
    case name ~ "(" ~ args ~ ")" => new ClassOperationBuilder().name(name).args(args)
  }
      
      def assoc: Parser[AssociationBuilder] = 
        ident ~ cardinal ~ assocType ~ cardinal ~ ident ~ ":" ~ ident ^^ {
        case c1 ~ card1 ~ rel ~ card2 ~ c2 ~ ":" ~ name => rel.c1(c1).c2(c2).nav1(true).nav2(false).name(name).n1(card1).n2(card2)
      }
      
      def assocType: Parser[AssociationBuilder] =
        (association | aggregation | compossition) ^^ {
        case rel => {
          var builder: AssociationBuilder = null
          rel match {
            case "--" => builder = new AssociationBuilder()
            case "o--" => builder = new AggregationBuilder()
            case "*--" => builder = new CompossitionBuilder()
          }
          builder
        }
      }
      
      def abstraction: Parser[AbstractionBuilder] =
        ident ~ extension ~ ident ^^ {
        case parent ~ extension ~ child => new AbstractionBuilder().c1(parent).c2(child)
      }
      
      def _class: Parser[ClassBuilder] = 
        opt(visibility) ~ opt(_abstract) ~ "class " ~ ident ~ "{" ~ rep(atb | cMethod) ~ "}" ^^ {
    case visibility ~ abs ~ "class " ~ name ~ "{" ~ features ~ "}" => {
      val builder = new ClassBuilder().name(name).features(features)
      abs match {
        case Some("abstract") => builder.setIsAbstract(true)
        case None =>
      }
      visibility match {
        case Some(v) => builder.setVisibility(v)
        case None =>
      }
      builder
    }
  }
      
      def interface : Parser[InterfaceBuilder] = 
        opt(visibility) ~ "interface " ~ ident ~ "{" ~ rep(iMethod) ~ "}" ^^ {
    case visibility ~interface ~ name ~ "{" ~ features ~ "}" => {
      val builder = new InterfaceBuilder().name(name).features(features)
      visibility match {
        case Some(v) => builder.setVisibility(v)
        case None =>
      }
      builder
    }
  }
      
      def atb: Parser[AttributeBuilder] = 
        opt(visibility) ~ opt("static") ~ ident ~ ":" ~ ident ^^ {
    case visibility ~ static ~ name ~ ":" ~ t => {
     val builder = new AttributeBuilder().name(name).valueType(t)
     visibility match {
       case Some(v) => builder.setVisibility(v)
       case None =>
     }
     static match {
       case Some(s) => builder.setIsStatic(true)
       case None =>
     }
     builder
    }
  }
      
      def cMethod: Parser[ClassOperationBuilder] = 
        opt(visibility) ~ opt(_abstract | "static") ~ ident ~ "(" ~ args ~ ")" ~ ":" ~ ident ^^ {
    case visibility ~ absStat ~ name ~ "(" ~ args ~ ")" ~ ":" ~ t => {
      val builder = new ClassOperationBuilder().name(name).valueType(t).args(args)
      visibility match {
        case Some(v) => builder.setVisibility(v)
        case None =>
      }
      absStat match {
        case Some("abstract") => builder.setIsAbstract(true)
        case Some("static") => builder.setIsStatic(true)
        case None =>
      }
      builder
    }
  }
      
      def iMethod: Parser[InterfaceOperationBuilder] = 
        opt(visibility) ~ opt(_abstract) ~ ident ~ "(" ~ args ~ ")" ~ ":" ~ ident ^^ {
    case visibility ~ absStat ~ name ~ "(" ~ args ~ ")" ~ ":" ~ t => {
      val builder = new InterfaceOperationBuilder().name(name).valueType(t).args(args)
      visibility match {
        case Some(v) => builder.setVisibility(v)
        case None =>
      }
      absStat match {
        case Some("abstract") => builder.setIsAbstract(true)
        case None =>
      }
      builder
    }
  }
      def args: Parser[Map[String, String]] = 
        repsep(arg, ",") ^^ {
    case args => Map() ++ args
  }
      
  def arg: Parser[(String, String)] = 
    ident ~ ":" ~ ident ^^ {
    case name ~ ":" ~ _type => (name, _type)
  }
  
}