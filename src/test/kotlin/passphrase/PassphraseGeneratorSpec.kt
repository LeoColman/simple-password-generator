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

import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.forAll
import io.kotest.inspectors.forNone
import io.kotest.matchers.char.shouldBeInRange
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotContainADigit
import io.kotest.matchers.string.shouldStartWith
import kotlin.random.Random

class PassphraseGeneratorSpec : FunSpec({

  test("Sanity check") {
    val random = Random(19980209)
    val generator = PassphraseGenerator(random)

    val results = List(4) { generator.generate(3, "&") }

    results[0] shouldBe "Preppy&Resilient&Mutiny1"
    results[1] shouldBe "Scaling4&Diffusion&Prominent"
    results[2] shouldBe "Sake&Cocoa&Passivism9"
    results[3] shouldBe "Dingbat7&Disrupt&Mandolin"
  }

  context("Default Generator") {
    val defaultPassphraseGenerator = PassphraseGenerator.Default

    test("Generate passphrases using words from the big f list") {
      val generatedWords = defaultPassphraseGenerator.generate(titleCaseWords = false).findWords()

      BigWordList shouldContainAll generatedWords
    }

    test("Generate 3 words by default") {
      val generated = defaultPassphraseGenerator.generate()

      generated.findWords() shouldHaveSize 3
    }

    test("Generate as many words as I want") {
      val generated = defaultPassphraseGenerator.generate(wordAmount = 18)
      generated.findWords() shouldHaveSize 18
    }

    test("Separates words with hyphens by default") {
      val generated = defaultPassphraseGenerator.generate()
      val separators = generated.findSeparators()
      separators shouldBe listOf("-", "-")
    }

    test("Lets me separate words with a custom separator") {
      val generated = defaultPassphraseGenerator.generate(separator = "*")
      val separators = generated.findSeparators()
      separators shouldBe listOf("*", "*")
    }

    test("Adds a random number at the end of one of the words") {
      val generated = defaultPassphraseGenerator.generate()
      val number = generated.single { it.isDigit() }

      number shouldBeInRange '0'..'9'
    }

    test("Allows me to disable numbers") {
      val generated = defaultPassphraseGenerator.generate(includeNumber = false)
      generated.shouldNotContainADigit()
    }

    test("Capitalizes words") {
      val generated = defaultPassphraseGenerator.generate()
      val words = generated.findWords()
      words.forAll {
        it shouldStartWith capitalLetterRegex
      }
    }

    test("Allows me to disable word capitalization") {
      val generated = defaultPassphraseGenerator.generate(titleCaseWords = false)
      val words = generated.findWords()
      words.forNone {
        it shouldStartWith capitalLetterRegex
      }
    }
  }
})

private fun String.findWords() = Regex("[a-zA-Z]+").findAll(this).map { it.value }.toList()
private fun String.findSeparators() = Regex("[^\\d\\w]+").findAll(this).map { it.value }.toList()

private val capitalLetterRegex = "[A-Z]".toRegex()
