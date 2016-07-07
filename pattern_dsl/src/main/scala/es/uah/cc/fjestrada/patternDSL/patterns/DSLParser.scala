/*
 * Licensed under the EUPL V.1.1
 * @author Estrada Martínez, F.J.
 */
package es.uah.cc.fjestrada.patternDSL.patterns

import scala.util.parsing.combinator._
import org.eclipse.uml2.uml._
import es.uah.cc.fjestrada.patternDSL.uml._

/** Defines the parsers to compile a pattern oriented modeling language file. */
class DSLParser extends JavaTokenParsers {
  
  /** Regexp for visibility tokens. */
  val visibility: Parser[String] = """(public)|(private)|(protected)|(package)""".r
  /** Regexp for abstract tokens. */
  val _abstract: Parser[String] = """abstract""".r
  /** Regexp for cardinality tokens in relations. */
  val cardinality: Parser[String] = """([0-9]+|(many))""".r
  /** Regexp for generalizations and realizations. */
  val extension: Parser[String] = """<\|\-\-""".r
  /** Regexp for compossition relations. */
  val compossition: Parser[String] = """\*\-\-""".r
  /** Regexp for aggregation relations. */
  val aggregation: Parser[String] = """o\-\-""".r
  /** Regexp for association relations. */
  val association: Parser[String] = """\-\-""".r
  
  /** Parser for model elements.
   *  
   *  Returns the generated model.
   */
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
  
  /** Parser for the pattern area.
   *  
   *  @return the builder for the pattern area.
   */
  def patternArea: Parser[PatternAreaBuilder] = 
    "patterns" ~ "{" ~ rep(pattern) ~ "}" ^^ {
    case "patterns" ~ "{" ~ patterns ~ "}" => new PatternAreaBuilder().patterns(patterns)
  }
    
    /** Parser for the relation area.
     * 
     * @return the builder for the relation area.
     */
    def relationArea:Parser[RelationAreaBuilder] = 
      "relations" ~ "{" ~ rep(abstraction | assoc) ~ "}" ^^ {
    case "relations" ~ "{" ~ rels ~ "}" => new RelationAreaBuilder().relations(rels)
  }
      
      /** Parser for the classifiers area.
       *  
       *  @return the builder for the classifier area.
       */
      def classifierArea: Parser[ClassifierAreaBuilder] = 
        "classifiers" ~ "{" ~ rep(_class | interface) ~ "}" ^^ {
    case "classifiers" ~ "{" ~ classifiers ~ "}" => new ClassifierAreaBuilder().classifiers(classifiers)
  }
  
  /** Parser for any pattern. */
      def pattern: Parser[Builder[Any, Any]] = (singleton | prototype | factoryMethod | builder | abstractFactory)
      
      /** Parser for singleton pattern. */
      def singleton: Parser[SingletonBuilder] = 
        "singleton" ~ ident ^^ {
        case "singleton" ~ name => new SingletonBuilder().name(name)
      }
      
      /** Parser for prototype pattern. */
      def prototype: Parser[PrototypeBuilder] = 
        "prototype" ~ ident ^^ {
        case "prototype" ~ name => new PrototypeBuilder().name(name)
      }

/** Parser for factory method pattern.*/
      def factoryMethod: Parser[FactoryMethodBuilder] =
        "factory method" ~ ident ~ "{" ~ rep1(patternMethod) ~ "}" ^^ {
        case "factory method" ~ name ~ "{" ~ methods ~ "}" => new FactoryMethodBuilder().name(name).features(methods).asInstanceOf[FactoryMethodBuilder]
      }
      
      /** Parser for builder pattern. */
      def builder: Parser[BuilderPatternBuilder] =
        "builder" ~ ident ~ "{" ~ rep1(patternMethod) ~ "}" ^^ {
        case "builder" ~ name ~ "{" ~ methods ~ "}" => new BuilderPatternBuilder().name(name).features(methods).asInstanceOf[BuilderPatternBuilder]
      }

/** Parser for abstract factory pattern. */
      def abstractFactory: Parser[AbstractFactoryBuilder] =
        "abstract factory" ~ ident ~ "products" ~ "[" ~ repsep(ident, ",") ~ "]" ~ "factories" ~ "[" ~ repsep(ident, ",") ~ "]" ^^ {
        case "abstract factory" ~ name ~ "products" ~ "[" ~ p ~ "]" ~ "factories" ~ "[" ~ f ~ "]" => new AbstractFactoryBuilder().name(name).products(p).factories(f)
        }
      
      /** Parser for methods included into a pattern.
       *  
       *  These methods are a quite different of standard methods, because they don't declare visibility and other properties.
       */
      def patternMethod: Parser[ClassOperationBuilder] = 
        ident ~ "(" ~ args ~ ")" ^^ {
    case name ~ "(" ~ args ~ ")" => new ClassOperationBuilder().name(name).args(args)
  }
      
      /** Parser for any kind of associations. */
      def assoc: Parser[AssociationBuilder] = 
        ident ~ cardinal ~ assocType ~ cardinal ~ ident ~ ":" ~ ident ^^ {
        case c1 ~ card1 ~ rel ~ card2 ~ c2 ~ ":" ~ name => rel.c1(c1).c2(c2).nav1(true).nav2(false).name(name).n1(card1).n2(card2)
      }
      
      /** Parser for the association type. 
       *  
       *  Returns the correct association kind (association, aggregation or compossition). */
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
      
      /** Parser for abstraction (generalization or realization).
       *  
       *  It dinamically differences between both types of abstraction by checking the classifiers involved.
       */
      def abstraction: Parser[AbstractionBuilder] =
        ident ~ extension ~ ident ^^ {
        case parent ~ extension ~ child => new AbstractionBuilder().c1(parent).c2(child)
      }
  
  /** Parser for the cardinality. */
  def cardinal: Parser[Int] =
    cardinality ^^ {
      case c => {
          if (c == "many") Int.MaxValue
          else c.toInt
      }
  }
  
  /** Parser for classes. */ 
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
      
      /** Parser for interfaces. */
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
      
      /** Parser for attributes. */
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
      
      /** Parser for methods owned by a class. */
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
      
      /** Parser for methods owned by an interface. */
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
   
  /** Parser for arguments. */
      def args: Parser[Map[String, String]] = 
        repsep(arg, ",") ^^ {
    case args => Map() ++ args
  }
      
      /** Parser for an argument. */
  def arg: Parser[(String, String)] = 
    ident ~ ":" ~ ident ^^ {
    case name ~ ":" ~ _type => (name, _type)
  }
  
}