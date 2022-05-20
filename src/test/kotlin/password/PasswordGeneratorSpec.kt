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

package br.com.colman.password

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.forOne
import io.kotest.matchers.char.shouldBeInRange
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldBeUnique
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldHaveLength
import io.kotest.matchers.string.shouldMatch
import kotlin.random.Random

private val oracleAcceptedSpecialCharacters = """@%+\/'!#$^?:,(){?[]~`-_.""".toList()

class PasswordGeneratorSpec : FunSpec({

  val target = PasswordGenerator()

  test("Sanity check") {
    val myRandom = Random(19980209)
    val generator = PasswordGenerator(myRandom)

    generator.generate() shouldBe "9Lrwc'iy"
    generator.generate() shouldBe "f{uzP9pc"
    generator.generate() shouldBe "aQ)owQ1J"
  }

  context("Default generator") {
    val defaultGenerator = PasswordGenerator.Default

    test("Uses AZ az characters") {
      val pw = defaultGenerator.generate(includeSpecial = false, includeNumber = false)
      pw shouldMatch Regex("[a-zA-Z]+")
    }

    test("Includes a special characters") {
      val pw = defaultGenerator.generate(includeSpecial = true, includeNumber = false)

      shouldNotThrowAny {
        pw.single { it in oracleAcceptedSpecialCharacters }
      }
    }

    test("Includes a number") {
      val pw = defaultGenerator.generate(includeSpecial = false, includeNumber = true)

      pw.forOne { it shouldBeInRange '0'..'9' }
    }
  }


  test("Generates password with the desired size") {
    target.generate(size = 12) shouldHaveLength 12
    target.generate(size = 25) shouldHaveLength 25
  }

  test("Defaults to size 8") {
    target.generate() shouldHaveLength 8
  }

  test("Generates random, different passwords") {
    val passwords = List(100) { target.generate() }
    passwords.shouldBeUnique()
  }
})