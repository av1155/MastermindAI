# MastermindAI

MastermindAI is an AI-powered Mastermind game player that can compete against human players. This README.md file provides an overview of the MastermindAI project, including details on how the level system works.

## Overview

MastermindAI is a Java-based implementation of an AI player for the classic game of Mastermind. It is designed to provide a challenging opponent for human players. The AI uses a combination of strategies to make educated guesses and progressively improve its performance across game levels. This README will walk you through the project's structure, how to use it in your own Mastermind game, and how the level system works.

## Features

* Intelligent code-breaking strategy.
* Dynamic adaptation to different game levels, including code length and digit range.
* Efficient elimination of possible solutions based on feedback from previous guesses.
* Easy integration with your Mastermind game.

## Project Structure

The MastermindAI project consists of two Java classes: `MastermindAI` and `MastermindGameWithAI`. Here's a brief overview of each class:

## MastermindAI

This class contains the AI player's logic. It has methods to generate guesses, update the game level, and evaluate feedback to eliminate possible solutions. The AI adapts to changing game conditions and aims to make optimal guesses based on available information.

## MastermindGameWithAI

This class provides a sample implementation of a Mastermind game that allows human players to compete against the MastermindAI. It handles game mechanics, user input, and the interaction between the player and the AI.

## The Level System

MastermindAI incorporates a level system that adapts to the player's performance and progressively increases the game's complexity. Here's how the level system works:

* **Code Length:** The level system increases the code length as the player advances to higher levels. The code length determines how many digits the player must guess.
* **Digit Range:** The digit range expands with each level. This change affects the possible values of the digits in the codeword and the player's guesses.
* **AI Adaptation:** The MastermindAI adjusts its guessing strategy based on the current level. It takes into account the code length and digit range to make informed guesses.
* **Player Challenge:** As the levels progress, the game becomes more challenging, requiring players to develop better strategies and tactics to win.

## Constants in `MastermindGameWithAI`

In `MastermindGameWithAI`, the following constants are used to configure the game:

- `GUESS_LENGTH`: The length of the codeword.
- `RANDOM_MIN`: The minimum value for a random digit in the codeword.
- `RANDOM_MAX`: The maximum value for a random digit in the codeword.
- `MAX_CHAR`: The maximum character representation of a random digit in the codeword.
- `MIN_CHAR`: The minimum character representation of a random digit in the codeword.
- `POSSIBLE_DIGIT_VALUES`: The number of possible digit values in the codeword.

## Getting Started

To use the MastermindAI in your own Mastermind game, follow these steps:

1) Copy the MastermindAI class into your project.

2) Integrate the MastermindAI class into your game code, as demonstrated in the provided MastermindGameWithAI class. Ensure that you create an instance of MastermindAI and interact with it in your game loop.

3) Customize the game parameters like codeLength, minDigit, and maxDigit according to your game's settings.

4) Implement your code evaluation logic within the generateFeedback method. This method should compare the player's guess to the actual codeword and return feedback to the player.

5) Run your Mastermind game with the AI player, and watch as the AI competes against human players, adapting to different game levels.

## Playing the Game

You can also run the provided `MastermindGameWithAI` class to play a game against the MastermindAI. This is a good way to observe how the AI adapts to different game levels and makes guesses.

## Conclusion

MastermindAI is a powerful tool for enhancing your Mastermind game with intelligent AI opponents. The level system adds a dynamic and progressively challenging aspect to the game, making it an engaging and competitive experience. Feel free to customize the code and integrate it into your game to provide a dynamic and exciting gaming experience.
