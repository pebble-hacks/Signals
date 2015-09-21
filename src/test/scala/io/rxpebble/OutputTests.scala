package io.rxpebble

import org.scalatest.{Matchers, WordSpec}


class OutputTests extends WordSpec with Matchers {
  "A struct" should {
    "have correct definitions generated" in {
      val correct =
        """typedef struct type {
          |  Layer layer;
          |} type;""".stripMargin
      Output.generateDefinition("type", Seq(Lexer.StructField("layer", "Layer"))) should be(correct)
    }
    "have correct declarations generated" in {
      val correct =
        """struct type;
          |typedef struct type type;""".stripMargin
      Output.generateForwardTypeDeclaration("type", Seq(Lexer.StructField("layer", "Layer"))) should be(correct)
    }
  }
  "An alias" should {
    "have correct declarations generated" in {
      val correct =
        """typedef type_from type_to;""".stripMargin
      Output.generateAlias("type_from", "type_to") should be(correct)
    }
  }
  "Signal handlers" should {
    "have correct static declarations" in {
      val correct =
        """static Window *window;
          |
          |static uint64_t sig_test;""".stripMargin
      val testResult = Output.generateStaticDeclarations(
        Lexer.Understood(Map.empty, Map.empty, Seq(Lexer.Signal("sig_test", "uint64_t")), Map.empty)
      )
      testResult should be(correct)
    }
  }
}
