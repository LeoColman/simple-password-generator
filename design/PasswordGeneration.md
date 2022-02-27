# Requirements

## zxcvbn

Password must pass zxcvbn test

- https://www.usenix.org/system/files/conference/usenixsecurity16/sec16_paper_wheeler.pdf
- https://github.com/dropbox/zxcvbn
- https://github.com/nulab/zxcvbn4j

## Kotlin Multiplatform

Generator must be written in Kotlin-MPP, targetting at minimum JS, IOS, Android and JVM

### Java compatibility?

Making a Kotlin lib compatible with Java is usually pretty straight-forward, such as adding @JvmStatics and @JvmNames to
what we want exposed to Java. Might be an effort we're not willing to take in the start

# Examples

## BitWarden JSLib

Bitwarden has a password generation service which seems as configurable as we want, and as simple as necessary.

https://github.com/bitwarden/jslib/blob/master/common/src/services/passwordGeneration.service.ts

# Artifacts

## Password generator

The main goal of this repository is to provide this password generator, with convenient Kotlin methods to generate
passwords, pass-phrases and hashes.

### API Details

Simple functions with simple attributions

```
fun generatePassphrase(): String
fun generatePassword(): String
```

#### Configurations

We should inspire the customization possibilities from bitwarden, and perhaps even the same defaults. As a bitwarden
user, their defaults feel very natural to me.

##### Password

| Field                      | Description                                                                                       | Default |
|----------------------------|---------------------------------------------------------------------------------------------------|---------|
| Length                     | How many characters you want                                                                      | 14      |
| Allow Ambiguous Characters | Should the generator remove from character pool ambiguous characters (`l`, `I`, `O`, `0`, `1`)?   | false   |
| Allow Numbers              | Should the generator include numeric characters?                                                  | true    |
| Minimum Numbers            | If numbers are allowed, at least how many should be present in the password?                      | 1       |
| Uppercase                  | Should uppercase characters be included in the password?                                          | true    |
| Minimum Uppercase          | If uppercase characters are allowed, at least how many should be present in the password?         | 1       |
| Lowercase                  | Should lowercase characters be included in the password?                                          | true    |
| Minimum Lowercase          | If lowercase characters are allowed, at least how many should be present in the password?         | 1       |      
| Special Characters         | Should special characters `-`, `_`, `@`, `.`, `?`, `!` `,`, `{`, `}` be included in the password? | true    |
| Minimum special            | If special characters are allowed, at least how many should be present in the password?           | 1       |

##### Passphrase

| Field            | Description                                            | Default            |
|------------------|--------------------------------------------------------|--------------------|
| Number of words  | How many words to generate                             | 3                  |
| Word list        | Which words should be used to seed the phrase          | EFF Long Word List |
| Word Separator   | Which character to use to separate each of the words   | `-`                |
| Capitalize Words | Should each word be capitalized?                       | true               |
| Include a number | Include a random number at the end of one of the words | true               |

----

## zxcvbn kotlin

To test artifact #1 we must find a zxcvbn implementation in Kotlin. We found it implemented in Java, but to bring it to
MPP we'll probably have to reimplement everything.

This artifact can be released to maven central and then added to https://github.com/dropbox/zxcvbn as the 'kotlin(mpp)
implementation'

## Bcrypt/Hashing/??

It might make sense to add some more crypto-related quickies to this repository as well. Such as bcrypting or hashing a
password.

