/*
 * Copyright 2022 Leonardo Colman Lopes, Luiz Neves Porto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either press or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package br.com.colman.passphrase

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldNotContainDuplicates
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.checkAll
import java.security.SecureRandom
import kotlin.random.Random
import java.lang.ClassLoader.getSystemResourceAsStream as resource

class WordGeneratorSpec : FunSpec({

  context("Word list") {
    test("Default word list to EFF big word list") {
      val wordlist = resource("eff_large_wordlist.txt").bufferedReader().readLines()

      BigWordList shouldBe wordlist
      WordGenerator.words shouldBe wordlist
    }
  }

  context("Word generator") {
    val target = WordGenerator

    test("Generates exactly how many words I expect") {
      target.generateWords(5) shouldHaveSize 5
      target.generateWords(1) shouldHaveSize 1
    }

    test("Generates 3 words by default") {
      target.generateWords() shouldHaveSize 3
    }

    test("Generates words only from wordlist") {
      val generatedWords = List(1000) { target.generateWords(100) }.flatten()

      WordGenerator.words shouldContainAll generatedWords
    }

    test("Doesn't repeat words in the same generation") {
      val generatedLists = List(1000) { target.generateWords(1000) }

      generatedLists.forAll {
        it.shouldNotContainDuplicates()
      }
    }

    test("Doesn't exhaust word pool when running multiple times") {
      shouldNotThrowAny {
        List(WordGenerator.words.size) { target.generateWords(WordGenerator.words.size) }
      }
    }

    test("Throws an exception when trying to generate < 1 word") {
      Arb.int(max = 0).checkAll {
        shouldThrowAny { target.generateWords(it) }
      }
    }

    test("Throws an exception if trying to generate more words than available") {
      shouldThrowAny { target.generateWords(target.words.size + 1) }
    }
  }

  context("Generator constructor function") {
    test("Uses the list passed as argument") {
      val arg = listOf("a")
      val gen = WordGenerator(arg)
      gen.words shouldBe arg
    }

    test("Has BigWordList as the default word list") {
      val gen = WordGenerator()
      gen.words shouldBe BigWordList
    }
  }

  context("Random number generation") {
    test("Uses the provided random as numbers source") {
      val lists = List(1000) { WordGenerator(random = Random(1)).generateWords(100) }

      lists.forAll {
        it shouldBe lists.first()
      }
    }
  }
})
