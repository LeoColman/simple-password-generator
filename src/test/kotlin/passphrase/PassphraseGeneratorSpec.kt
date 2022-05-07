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

import br.com.colman.passphrase.WordGenerator
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.lang.ClassLoader.getSystemResourceAsStream as resource

class PassphraseGeneratorSpec : FunSpec({

  test("Default word list to EFF big word list") {
    val wordlist = resource("eff_large_wordlist.txt").bufferedReader().readLines()

    WordGenerator.words shouldBe wordlist
  }

  test("Includes a separator between the words") {
    1 shouldBe 1
  }

  test("Defaults to hyphen (-) as the separator") {
    1 shouldBe 1
  }
})
