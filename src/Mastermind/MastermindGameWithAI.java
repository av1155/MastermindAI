package Mastermind;

import java.util.Scanner;
import Mastermind.MastermindAI;

public class MastermindGameWithAI {

    // Length of the codeword
    final static int GUESS_LENGTH = 4;

    // Maximum value for a random digit in the codeword
    final static int RANDOM_MAX = 6; // Maximum of 9 (breaks the game if above 9).

    // Minimum value for a random digit in the codeword
    final static int RANDOM_MIN = 1; // Maximum of 8 (breaks the game if above 8).

    // <--------------------- ONLY CHANGE CONSTANTS ABOVE --------------------->

    // Maximum character representation of a random digit in the codeword
    final static char MAX_CHAR = RANDOM_MAX + '0';

    // Minimum character representation of a random digit in the codeword
    final static char MIN_CHAR = RANDOM_MIN + '0';

    // Number of possible digit values in the codeword
    final static int POSSIBLE_DIGIT_VALUES = (RANDOM_MAX - RANDOM_MIN) + 1;

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);

        // Initialize the AI instance with the current game level
        MastermindAI ai = new MastermindAI(GUESS_LENGTH, RANDOM_MIN, RANDOM_MAX);

        playMastermindGameWithAI(keyboard, ai);

        keyboard.close();
    }

    public static boolean isGuessValid(String guess, int length_level, int digit_level) {
        // Check if the length of the guess is not equal to the expected length.
        if (guess.length() != (GUESS_LENGTH + length_level)) {
            return false;  // Return false if the length is incorrect
        }

        for (int i = 0; i < (GUESS_LENGTH + length_level); i++) {
            // Extract each character into variable `digit` from the guess at positions 0 to (3 + `length_level`) (or 1 to (4 + `length_level`) in terms of length).
            char digit = guess.charAt(i);

            // Check if the character stored in `digit` is outside the valid range (1 to (6 + `digit_level`)).
            if (digit < MIN_CHAR || digit > MAX_CHAR + (digit_level + '0')) {
                return false; // Return false if an invalid digit is found
            }
        }

        // If all checks pass, return true to indicate a valid guess
        return true;
    }

    public static String generateCodeword(int length_level, int digit_level) {
        String codeword;
        StringBuilder builder = new StringBuilder();

        // Generate random numbers based on the current level; then append each to the StringBuilder builder.
        for (int i = 0; i < (GUESS_LENGTH + length_level); i++) {
            // Formula for random numbers: MIN + (int) (Math.random() * ((MAX + 1) - MIN));
            int random_number = RANDOM_MIN + (int) (Math.random() * (((RANDOM_MAX + digit_level) + 1) - RANDOM_MIN));
            builder.append(random_number);
        }

        // Change the StringBuilder back to a string
        codeword = builder.toString();

        return codeword;
    }

    public static String generateFeedback(String guess, String codeword) {
        // Implement your code evaluation logic here.
        // Compare the guess to the codeword and return feedback.

        StringBuilder feedback = new StringBuilder();
        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == codeword.charAt(i)) {
                feedback.append("X"); // X represents a hit.
            } else {
                feedback.append("O"); // O represents a miss.
            }
        }

        return feedback.toString();
    }

    public static void playMastermindGameWithAI(Scanner keyboard, MastermindAI ai) {
        int digit_level = 0;
        int length_level = 0;
        int level_counter = 1;
        String level_increase_decision;

        String replay_decision = "y";

        while (replay_decision.charAt(0) == 'y') {
            printGameHeader(level_counter);

            String codeword = generateCodeword(length_level, digit_level);

            revealCodeword(keyboard, codeword);

            int rounds_counter = 1;
            int hit_counter = 0;
            int miss_counter;

            String aiGuess;

            while (hit_counter < (GUESS_LENGTH + length_level)) {
                System.out.printf("Round = %d | Length: %d | Range: %d to %d | Your guess (0 to stop): ",
                        rounds_counter, (GUESS_LENGTH + length_level), RANDOM_MIN, (RANDOM_MAX + digit_level));
                String playerGuess = keyboard.next();

                if (playerGuess.equals("0")) {
                    System.out.printf("The codeword was %s. \n", codeword);
                    break;
                }

                boolean guess_check = isGuessValid(playerGuess, length_level, digit_level);

                if (!guess_check) {
                    System.out.println("An invalid guess.");
                    continue;
                }

                hit_counter = 0;
                miss_counter = 0;

                int[] codeword_digit_count = new int[(POSSIBLE_DIGIT_VALUES + digit_level)];
                int[] guess_digit_count = new int[(POSSIBLE_DIGIT_VALUES + digit_level)];

                for (int i = 0; i < (GUESS_LENGTH + length_level); i++) {
                    if (playerGuess.charAt(i) == codeword.charAt(i)) {
                        hit_counter++;
                    } else {
                        int codeword_digit = codeword.charAt(i) - '0';
                        int guess_digit = playerGuess.charAt(i) - '0';
                        codeword_digit_count[codeword_digit - 1]++;
                        guess_digit_count[guess_digit - 1]++;
                    }
                }

                for (int d = 0; d < (POSSIBLE_DIGIT_VALUES + digit_level); d++) {
                    miss_counter += Math.min(codeword_digit_count[d], guess_digit_count[d]);
                }

                if (hit_counter == (GUESS_LENGTH + length_level)) {
                    System.out.printf("You've got it! Level %d completed! \n", level_counter);
                    continue;
                }

                System.out.printf("%d hits, %d misses. \n", hit_counter, miss_counter);
                rounds_counter++;

                // Calculate codeLength, minDigit, and maxDigit based on the current level
                int codeLength = GUESS_LENGTH + length_level; // Adjusted for the current level
                int minDigit = RANDOM_MIN;
                int maxDigit = RANDOM_MAX + digit_level;

                // AI's turn to guess
                aiGuess = ai.generateGuess(generateFeedback(playerGuess, codeword), codeLength, minDigit, maxDigit);

                // Update the AI instance with the current level information
                ai.updateGameLevel(codeLength, minDigit, maxDigit);

                System.out.println("AI's guess: " + aiGuess);

                // Evaluate AI's guess
                hit_counter = 0;
                miss_counter = 0;
                for (int i = 0; i < (GUESS_LENGTH + length_level); i++) {
                    if (aiGuess.charAt(i) == codeword.charAt(i)) {
                        hit_counter++;
                    }
                }

                for (int d = 0; d < (POSSIBLE_DIGIT_VALUES + digit_level); d++) {
                    miss_counter += Math.min(codeword_digit_count[d], guess_digit_count[d]);
                }

                if (hit_counter == (GUESS_LENGTH + length_level)) {
                    System.out.printf("AI has guessed the codeword! You reached level %d! \n", level_counter);
                    break;
                }
            }

            System.out.print("\nAnother game (y/n)? ");
            replay_decision = keyboard.next().toLowerCase();

            if (replay_decision.charAt(0) != 'y') {
                System.out.printf("\nThank you for playing! You reached level %d! \n", level_counter);
            }

            if ((replay_decision.charAt(0) == 'y') && (hit_counter == (GUESS_LENGTH + length_level))) {
                System.out.print("Do you want to increase the level (y/n)? ");
                level_increase_decision = keyboard.next().toLowerCase();

                if (level_increase_decision.charAt(0) == 'y') {
                    level_counter++;
                    if (digit_level < (9 - RANDOM_MAX)) {
                        digit_level++;
                    }
                    length_level++;
                }
            }

            System.out.println();
        }
    }

    public static void printGameHeader(int level_counter) {
        System.out.println("---- Let's play the game of Mastermind. ----");
        System.out.printf("----------------[ Level %d ]-----------------\n", level_counter);
    }

    public static void revealCodeword(Scanner in, String codeword) {
        System.out.print("Reveal the codeword (y/n)? ");
        String reveal_codeword_decision = in.next().toLowerCase();

        if (reveal_codeword_decision.charAt(0) == 'y') {
            System.out.printf("The codeword is %s. \n", codeword);
        }
    }
}
