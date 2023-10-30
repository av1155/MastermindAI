package Mastermind;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

public class MastermindAI {

    private int codeLength;
    private int minDigit;
    private int maxDigit;
    private List<String> possibleSolutions;
    private List<String> previousGuesses;

    public MastermindAI(int codeLength, int minDigit, int maxDigit) {
        // Constructor to initialize the AI with code length, min, and max digit values.
        this.codeLength = codeLength;
        this.minDigit = minDigit;
        this.maxDigit = maxDigit;
        this.possibleSolutions = generateAllPossibleSolutions(codeLength);
        this.previousGuesses = new ArrayList<>();
    }

    private int countHits(String guess, String secretCode) {
        // Count the number of correct digits in the correct positions.
        int hits = 0;
        for (int i = 0; i < codeLength; i++) {
            if (guess.charAt(i) == secretCode.charAt(i)) {
                hits++;
            }
        }
        return hits;
    }

    private int countMisses(String guess, String secretCode) {
        // Count the number of correct digits in the wrong positions.
        int misses = 0;
        for (int i = 0; i < codeLength; i++) {
            char digit = guess.charAt(i);
            if (secretCode.contains(String.valueOf(digit)) && guess.indexOf(digit) != secretCode.indexOf(digit)) {
                misses++;
            }
        }
        return misses;
    }

    private String evaluateGuess(String guess, String secretCode) {
        // Evaluate a guess and return feedback in the format "X hits, Y misses."
        int hits = countHits(guess, secretCode);
        int misses = countMisses(guess, secretCode);
        return hits + " hits, " + misses + " misses";
    }

    private int countEliminations(List<String> possibleSolutions, String guess, String feedback) {
        // Count the number of possible solutions that are eliminated by a guess and its feedback.
        int count = 0;
        for (String possibleSolution : possibleSolutions) {
            String fb = evaluateGuess(possibleSolution, guess);
            if (!fb.equals(feedback)) {
                count++;
            }
        }
        return count;
    }

    public String generateGuess(String feedback, int codeLength, int minDigit, int maxDigit) {
        List<String> newPossibleSolutions = new ArrayList<>();

        if (previousGuesses.isEmpty()) {
            // If there are no previous guesses, generate a random guess.
            Random random = new Random();
            return generateRandomGuess(codeLength, minDigit, maxDigit);
        }

        int guessLength = codeLength;
        String bestGuess = "";
        int minEliminations = Integer.MAX_VALUE;

        // Initialize a map to store the counts of each type of feedback (e.g., "2 hits, 1 misses").
        Map<String, Integer> feedbackCounts = new HashMap<>(); // Use the imported Map and HashMap classes

        for (String possibleSolution : possibleSolutions) {
            feedbackCounts.clear(); // Clear the feedback counts for each possible solution.

            for (String guess : previousGuesses) {
                String fb = evaluateGuess(possibleSolution, guess);

                // Count the occurrences of each type of feedback.
                feedbackCounts.put(fb, feedbackCounts.getOrDefault(fb, 0) + 1);
            }

            // Calculate the total eliminations for this possible solution.
            int totalEliminations = feedbackCounts.values().stream().mapToInt(Integer::intValue).sum();

            if (totalEliminations < minEliminations) {
                minEliminations = totalEliminations;
                bestGuess = possibleSolution;
            }
        }

        possibleSolutions.remove(bestGuess);
        previousGuesses.add(bestGuess);

        return bestGuess;
    }

    private List<String> generateAllPossibleSolutions(int length) {
        // Generate all possible code combinations recursively.
        List<String> solutions = new ArrayList<>();
        generateAllPossibleSolutionsRecursive("", length, solutions, 0);
        return solutions;
    }

    private void generateAllPossibleSolutionsRecursive(String current, int length, List<String> solutions, int currentLevel) {
        if (length == 0) {
            solutions.add(current);
            return;
        }

        for (int i = minDigit; i <= maxDigit; i++) {
            if (currentLevel < length) {
                generateAllPossibleSolutionsRecursive(current + i, length - 1, solutions, currentLevel + 1);
            }
        }
    }

    private String generateRandomGuess(int codeLength, int minDigit, int maxDigit) {
        // Generate a random guess within the specified digit range.
        Random random = new Random();
        StringBuilder guess = new StringBuilder();
        for (int i = 0; i < codeLength; i++) {
            guess.append(random.nextInt(maxDigit - minDigit + 1) + minDigit);
        }
        return guess.toString();
    }

    public void updateGameLevel(int codeLength, int minDigit, int maxDigit) {
        // Update the game level with new code length and digit range.
        this.codeLength = codeLength;
        this.minDigit = minDigit;
        this.maxDigit = maxDigit;
        this.possibleSolutions = generateAllPossibleSolutions(codeLength);
        this.previousGuesses.clear();
    }

    public static void main(String[] args) {
        int codeLength = 4; // Change this to match your game's code length
        int minDigit = 1; // Change to the appropriate min digit
        int maxDigit = 6; // Change to the appropriate max digit
        MastermindAI ai = new MastermindAI(codeLength, minDigit, maxDigit);

        // Simulate a game loop
        for (int i = 0; i < 10; i++) {
            String feedback = "0 hits, 0 misses"; // Replace this with actual feedback from the player's guess.
            String aiGuess = ai.generateGuess(feedback, codeLength, minDigit, maxDigit);
            System.out.println("AI's guess: " + aiGuess);
        }
    }
}
