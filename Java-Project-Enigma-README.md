# Java Enigma Machine Simulator

A command-line simulation of the Enigma cipher machine written in Java.

The program models the major components of an Enigma machine, including configurable alphabets, permutations, fixed rotors, moving rotors, reflectors, rotor stepping, ring settings, and plugboard connections. It can encrypt or decrypt text using machine and message settings supplied through configuration files.

## Project Highlights

This project demonstrates:

* Object-oriented design and inheritance
* Polymorphic rotor behavior
* Permutation and cycle notation
* Stateful encryption
* Command-line file processing
* Input validation and exception handling
* Unit and integration testing
* Java package organization

## Features

* Supports custom alphabets
* Loads rotor definitions from a configuration file
* Supports moving rotors, fixed rotors, and reflectors
* Simulates rotor advancement and notch behavior
* Applies forward and reverse signal conversion
* Supports plugboard permutations
* Processes multiple machine settings in one input file
* Encrypts and decrypts using the same conversion process
* Formats output into groups of five characters
* Accepts standard input and output or named files

## Technologies

* Java
* JUnit
* GNU Make
* Object-oriented programming

## Project Structure

```text
Java-Project-Enigma/
├── Enigma Project/
│   ├── enigma/
│   │   ├── Alphabet.java
│   │   ├── EnigmaException.java
│   │   ├── FixedRotor.java
│   │   ├── Machine.java
│   │   ├── Main.java
│   │   ├── MovingRotor.java
│   │   ├── Permutation.java
│   │   ├── Reflector.java
│   │   ├── Rotor.java
│   │   ├── MovingRotorTest.java
│   │   ├── PermutationTest.java
│   │   ├── TestUtils.java
│   │   ├── UnitTest.java
│   │   └── Makefile
│   ├── testing/
│   │   ├── correct/
│   │   ├── error/
│   │   ├── test-correct
│   │   ├── test-error
│   │   └── Makefile
│   └── Makefile
└── README.md
```

## Core Components

### `Alphabet`

Defines the valid characters supported by the machine and converts between characters and numeric indexes.

### `Permutation`

Represents character mappings using cycle notation. It performs forward and inverse permutations and supports wrapping numeric positions within the alphabet.

### `Rotor`

Provides the base behavior shared by all rotor types, including settings, permutations, and forward and backward signal conversion.

### `MovingRotor`

Extends `Rotor` with advancement behavior and notch positions. Moving rotors rotate during message conversion.

### `FixedRotor`

A rotor that participates in signal conversion but does not rotate.

### `Reflector`

A specialized fixed rotor that reverses the signal through the machine. A reflector must map characters in pairs and cannot rotate.

### `Machine`

Coordinates the installed rotors, rotor settings, plugboard, rotor advancement, and complete character conversion path.

### `Main`

Reads configuration and message files, configures the machine, processes text, and writes formatted output.

## Requirements

Install the following tools before running the project:

* Java Development Kit
* GNU Make
* JUnit dependencies required by the supplied course test environment

Verify that Java is installed:

```bash
java -version
javac -version
```

## Installation

Clone the repository:

```bash
git clone https://github.com/jrmar/Java-Project-Enigma.git
cd Java-Project-Enigma/"Enigma Project"
```

Compile the project:

```bash
make
```

## Running the Program

The application accepts between one and three command-line arguments:

```bash
java enigma.Main <configuration-file> [input-file] [output-file]
```

### Arguments

| Argument | Required | Description |
|---|---:|---|
| `configuration-file` | Yes | Defines the alphabet, rotor count, pawls, and available rotors |
| `input-file` | No | Contains machine settings and messages; standard input is used when omitted |
| `output-file` | No | Receives the processed text; standard output is used when omitted |

### Run with Input and Output Files

```bash
java enigma.Main path/to/config.conf path/to/input.in path/to/output.out
```

### Run with Standard Input

```bash
java enigma.Main path/to/config.conf
```

The program will read machine settings and messages from the terminal.

### Write Output to the Terminal

```bash
java enigma.Main path/to/config.conf path/to/input.in
```

## Configuration File Format

The first line of a configuration file defines:

```text
<alphabet> <number-of-rotors> <number-of-pawls>
```

Rotor definitions follow the machine header. Each rotor definition contains:

```text
<rotor-name> <rotor-type-and-notches> <permutation-cycles>
```

Rotor types are represented as:

| Symbol | Rotor Type |
|---|---|
| `M` | Moving rotor |
| `N` | Fixed rotor |
| `R` | Reflector |

For a moving rotor, notch characters follow the `M`.

Example structure:

```text
ABCDEFGHIJKLMNOPQRSTUVWXYZ
5 3
B R (AE)(BN)(CK)(DQ)(FU)(GY)(HW)(IJ)(LO)(MP)(RX)(SZ)(TV)
Beta N (ALBEVFCYODJWUGNMQTZSKPR)(HIX)
I MQ (AELTPHQXRU)(BKNW)(CMOY)(DFG)(IV)(JZ)(S)
```

The exact rotor definitions may vary depending on the machine being simulated.

## Input File Format

A settings line begins with an asterisk:

```text
* <rotor-names> <rotor-settings> <plugboard-cycles>
```

Example:

```text
* B Beta III IV I AXLE (HQ) (EX) (IP) (TR) (BY)
FROM HIS SHOULDER HIAWATHA
TOOK THE CAMERA OF ROSEWOOD
```

The settings line selects the rotors, assigns their initial positions, and defines optional plugboard connections.

A new settings line may appear later in the file to reconfigure the machine before processing additional messages.

## Encryption and Decryption

Enigma encryption is reciprocal. Processing encrypted text again with the same rotor selection, starting positions, and plugboard settings returns the original plaintext.

For example:

```text
Plaintext  -> Enigma configuration -> Ciphertext
Ciphertext -> Same configuration   -> Plaintext
```

The rotor positions must begin at the same settings for both operations.

## Signal Path

For each character, the machine performs the following sequence:

1. Advance the moving rotors
2. Apply the plugboard permutation
3. Pass the signal through the rotors from right to left
4. Reflect the signal
5. Pass the signal back through the rotors from left to right
6. Apply the inverse plugboard permutation
7. Convert the resulting index back to a character

The output is printed in groups of five characters.

## Testing

Run all supplied tests:

```bash
make check
```

Run unit tests:

```bash
make unit
```

Run integration tests:

```bash
make integration
```

Run the style checker:

```bash
make style
```

Remove generated files:

```bash
make clean
```

Some tests and style checks depend on libraries or tools provided by the original course environment.

## Object-Oriented Design

The rotor hierarchy is the central design feature of the project:

```text
Rotor
├── FixedRotor
│   └── Reflector
└── MovingRotor
```

The shared `Rotor` abstraction defines common conversion behavior, while subclasses specialize whether a rotor rotates, reflects, or contains notches. This allows the `Machine` class to work with multiple rotor types through a common interface.

## Error Handling

The simulator checks for invalid conditions such as:

* Incorrect command-line argument counts
* Missing or unreadable files
* Truncated configuration files
* Invalid rotor descriptions
* Unknown rotor types
* Invalid machine settings
* Unsupported characters
* Invalid permutations

Errors are reported through the custom `EnigmaException` class.

## Skills Demonstrated

* Java inheritance and polymorphism
* Encapsulation and abstraction
* Modular class design
* Character-index conversion
* Mathematical permutation logic
* Stateful simulation
* File parsing
* Command-line application development
* Test-driven validation

## Limitations

This is an educational Enigma simulator rather than a modern cryptographic system. The Enigma cipher is historically significant but is not secure for protecting modern information.

The project does not provide a graphical interface or modern encryption algorithms.

## Author

Developed by [Mario R.](https://github.com/jrmar).
