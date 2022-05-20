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

import java.security.SecureRandom
import kotlin.random.Random
import kotlin.random.asKotlinRandom


/**
 *
 * Special characters obtained from Oracle list of allowed special characters in password
 * https://docs.oracle.com/cd/E11223_01/doc.910/e11197/app_special_char.htm#MCMAD416
 */
public class PasswordGenerator(
  private val random: Random = SecureRandom().asKotlinRandom(),
  private val normalCharacters: List<Char> = ('a'..'z').toList() + ('A'..'Z').toList(),
  private val numericCharacters: List<Char> = ('0'..'9').toList(),
  private val specialCharacters: List<Char> = """@%+\/'!#$^?:,(){?[]~`-_.""".toList()
) {

  public fun generate(
    size: Int = 8,
    includeSpecial: Boolean = true,
    includeNumber: Boolean = true
  ): String {
    val specialCharactersToGenerate = if (includeSpecial) 1 else 0
    val numbersToGenerate = if (includeNumber) 1 else 0
    val normalCharactersToGenerate = size - numbersToGenerate - specialCharactersToGenerate

    val numbers = List(numbersToGenerate) { numericCharacters.random(random) }
    val specialCharacters = List(specialCharactersToGenerate) { specialCharacters.random(random) }
    val normalCharacters = List(normalCharactersToGenerate) { normalCharacters.random(random) }

    return (numbers + specialCharacters + normalCharacters).shuffled(random).joinToString("")
  }

  public companion object {
    public val Default: PasswordGenerator = PasswordGenerator()
  }
}
