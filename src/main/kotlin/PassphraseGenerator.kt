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

public class PassphraseGenerator(
  private val words: Collection<String>
) {

  public fun generate(wordAmount: Int = 3): String {
    require(wordAmount in 1..words.size)
    return words.shuffled().take(wordAmount).joinToString()
  }
}
