# word-processor

## Table of Contents
1. [General Info](#general-info)
2. [Technologies](#technologies)
3. [Installation and build](#installation-and-build)
4. [Usage](#usage)
5. [Assumptions](#assumptions)
### General Info
***
Application used to get the top N words by their occurrence from a file.

## Technologies
***
* [Java](https://openjdk.java.net/projects/jdk/11/): Version 21
* [JUnit](https://junit.org/junit5/): Version 5
* [Mockito](https://site.mockito.org/): Version 5.15.2

## Installation and build
***
```
$ git clone git@github.com:sgvlad/word_processor.git
$ run Main.java
```
## Assumptions
***
- The program should be executed on a machine with enough memory to accommodate small-medium files in memory.
- The language of the file is English and does not contain corrupted or non UTF-8 characters.
- Contractions will be separated and counted. e.G: "It's" and "it is" is the same thing.
- The output values are not ordered. If they required to be ordered they just need to be sorted.
