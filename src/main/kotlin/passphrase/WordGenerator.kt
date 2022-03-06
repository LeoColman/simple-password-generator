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

import kotlin.random.Random
import com.soywiz.krypto.SecureRandom as KryptoSecureRandom

/**
 * Works as a constructor function for [WordGenerator]
 *
 * When one needs to customize how [WordGenerator] will work, this function
 * can be used to tweak the parameters.
 * The default values are good enough for most use cases.
 */
public fun WordGenerator(
  words: Collection<String> = BigWordList,
  random: Random = KryptoSecureRandom
): WordGenerator = object : WordGenerator {
  override val words = words
  override val random = random
}

public interface WordGenerator {

  /**
   * Word pool
   *
   * [WordGenerator] will get random words from this collection to generate
   * words.
   */
  public val words: Collection<String>
    get() = BigWordList

  /**
   * How to roll the dice for the word list
   *
   * Uses by default a SecureRandom provided by [Krypto library]
   * Not usually necessary to change, as there are not many scenarios
   * where you need an specific random source.
   *
   * Implementations are encouraged to keep the default KryptoSecureRandom
   */
  public val random: Random
    get() = KryptoSecureRandom

  /**
   * Generates [wordAmount] words from [words]
   *
   * Random numbers are provided by [random]
   *
   * [wordAmount] Must be strictly in [1, word.size]
   */
  public fun generateWords(wordAmount: Int = 3): List<String> {
    require(wordAmount in 1..words.size)
    return words.shuffled(random).take(wordAmount)
  }

  /**
   * Functions as a default implementation of [WordGenerator].
   * Added to the companion object so that:
   *  1 - The companion object can be extended
   *  2 - [WordGenerator] can be used statically
   *  3 - A sensible default value is provided to generate words without hassle
   */
  public companion object : WordGenerator
}
