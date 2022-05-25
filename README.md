# Simple Password Generator

[![Build](https://github.com/LeoColman/simple-password-generator/actions/workflows/build.yml/badge.svg)](https://github.com/LeoColman/simple-password-generator/actions/workflows/build.yml)
[![Detekt (Lint)](https://github.com/LeoColman/simple-password-generator/actions/workflows/detekt.yml/badge.svg)](https://github.com/LeoColman/simple-password-generator/actions/workflows/detekt.yml)
[![Tests](https://github.com/LeoColman/simple-password-generator/actions/workflows/tests.yml/badge.svg)](https://github.com/LeoColman/simple-password-generator/actions/workflows/tests.yml)


[![GitHub](https://img.shields.io/github/license/LeoColman/simple-password-generator)](https://github.com/LeoColman/simple-password-generator/blob/master/LICENSE) 

[![Maven Central](https://img.shields.io/maven-central/v/br.com.colman/simple-password-generator)](https://search.maven.org/search?q=g:br.com.colman)

Simple Password Generator tries to be a simple library to include in your project if you just want some quick password
generation.

# Including in your project

This library is available at Maven Central. You might need to include it in your repositories:

```
repositories {
    mavenCentral()
}
```

You can then add it as a dependency to your project:

[![Maven Central](https://img.shields.io/maven-central/v/br.com.colman/simple-password-generator)](https://search.maven.org/search?q=g:br.com.colman)

```
dependencies {
    implementation("br.com.colman:simple-password-generator:VERSION")
}
```

# Basic Usage

```
PasswordGenerator.Default.generate()    // Gives you a 8 character string with a number and a special character
PassphraseGenerator.Default.generate()  // Gives you a 3 random words string separated by a hyphen, including a number 
```

# Advanced configuration

All pieces of this api have sensible defaults. You might not like them, and thus want to customize them.

## Passphrase

```kotlin
val customPassphraseGenerator = PassphraseGenerator(
  random = MyCustomRandomSource(), // Defaults to SecureRandom
  wordGenerator = MyCustomWordGenerator {}  // Defaults to words from Big F Word List
)
```

```kotlin
val randomPassphrase: String = customPassphraseGenerator.generate(
  wordAmount = 5, // Defaults to 3
  separator = "&", // Defaults to "-"
  includeNumber = false, // Defaults to true
  titleCaseWords = false, // Defaults to true
)
```

## Password

```kotlin
val customPasswordGenerator = PasswordGenerator(
  random = MyCustomRandomSource(),    // Defaults to SecureRandom
  normalCharacters = listOf('x', 'y', 'z'),    // Defaults to A-Z and a-z
  numericCharacters = listOf('1', '2', '3'),   // Defaults to 0-9
  specialCharacters = listOf('$', '/')         // Defaults to @%+\/'!#$^?:,(){?[]~`-_.   
)

```

```kotlin
val randomPassword: String = customPasswordGenerator.generate(
  size = 12, // Defaults to 8
  includeSpecial = false, // Defaults to true
  includeNumber = false, // Defaults to true
)
```

