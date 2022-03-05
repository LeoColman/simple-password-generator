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

public fun WordGenerator(words: Collection<String>): WordGenerator = object : WordGenerator {
  override val words: Collection<String> = words
}

public interface WordGenerator {
  // TODO create issue in Kotlin repository about explicit apis
  // This public modifier isn't necessary, as it's obligatorily public.
  public val words: Collection<String>
    get() = BigWordList

  public fun generateWords(wordAmount: Int = 3): List<String> {
    require(wordAmount in 1..words.size)
    return words.shuffled().take(wordAmount)
  }

  public companion object : WordGenerator
}
