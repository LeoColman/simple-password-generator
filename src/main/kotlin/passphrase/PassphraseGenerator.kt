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

import br.com.colman.passphrase.WordGenerator
import java.util.Locale
import kotlin.random.Random
import com.soywiz.krypto.SecureRandom as KryptoSecureRandom


public class PassphraseGenerator(
  private val random: Random = KryptoSecureRandom,
  private val wordGenerator: WordGenerator = WordGenerator(random = random)
) {

  public fun generate(wordAmount: Int = 3, separator: String = "-", includeNumber: Boolean = true, titleCaseWords: Boolean = true): String {
    val words = wordGenerator.generateWords(wordAmount)

    val wordsWithNumbers = if(includeNumber) {
      words.includeRandomNumberAtEndOfRandomWord()
    } else words

    val capitalizedWords = if(titleCaseWords) {
      wordsWithNumbers.capitalizeAll()
    } else wordsWithNumbers

    return capitalizedWords.joinToString(separator)
  }

  private fun List<String>.capitalizeAll() = map { it.capitalized() }
  private fun List<String>.includeRandomNumberAtEndOfRandomWord(): List<String> {
    val randomNumber = random.nextInt(0, 10)
    val randomIndex = random.nextInt(0, size)

    return mapIndexed { index, s -> if(index == randomIndex) s + randomNumber else s }
  }

  public companion object {
    public val Default: PassphraseGenerator = PassphraseGenerator()
  }
}

/**
 * Replacement for Kotlin's deprecated `capitalize()` function.
 */
private fun String.capitalized(): String {
  return this.replaceFirstChar {
    if (it.isLowerCase())
      it.titlecase(Locale.getDefault())
    else it.toString()
  }
}
