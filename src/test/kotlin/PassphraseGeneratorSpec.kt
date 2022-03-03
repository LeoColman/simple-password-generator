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
package br.com.colman.passphrase

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.forExactly
import io.kotest.inspectors.forNone
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContainOnlyOnce
import io.kotest.property.Arb
import io.kotest.property.arbitrary.Codepoint
import io.kotest.property.arbitrary.az
import io.kotest.property.arbitrary.string
import io.kotest.property.arbitrary.take

class PassphraseGeneratorSpec : FunSpec({
  context("Amount of words") {
    val words = Arb.string(3, 30, Codepoint.az()).take(100).toSet()

    val target = PassphraseGenerator(words)

    test("Generates the amount of words I expect") {
      val result = target.generate(5)
      words.forExactly(5) {
        result shouldContainOnlyOnce it
      }
    }

    test("Generates 3 words by default") {
      val result = target.generate()
      words.forExactly(3) {
        result shouldContainOnlyOnce it
      }
    }

    test("Doesn't contain extra words from outside wordlist") {
      var result = target.generate(30)

      words.forEach { result = result.replace(it, "") }

      result.toList().forNone { it.isLetter() shouldBe true }
    }

    test("Doesn't repeat words") {
      var result = target.generate(30)

      words.forEach { result = result.replaceFirst(it, "") }

      result.toList().forNone { it.isLetter() shouldBe true }
    }

    test("Doesn't remove words from word pool for next generate") {
      repeat(100) {
        target.generate(30)
      }
    }

    test("Throws errors if I request an out of bounds amount of words") {
      shouldThrowAny { target.generate(words.size + 1) }
      shouldThrowAny { target.generate(0) }
      shouldThrowAny { target.generate(-1) }
    }

    test("Parameter order") {
      TODO("We must check the paremeter order to avoid breaking clients")
    }
  }
})
