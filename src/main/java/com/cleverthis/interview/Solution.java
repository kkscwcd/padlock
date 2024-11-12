package com.cleverthis.interview;
import java.util.ArrayList;
import java.util.List;
import com.cleverthis.interview.padlock.PadlockImpl;

/**
 * This is a placeholder class showing a simple boilerplate.
 * This class is not required, so you can replace with your own architecture.
 */
public class Solution {
    /**
     * Entry point for solving the padlock. Generates all possible combinations
     * of passcodes and tries each one.
     *
     * @param padlock The padlock instance we are trying to unlock.
     */
    public void solve(PadlockImpl padlock) {
        int numpadSize = padlock.getNumpadSize();

        // Prepare a list of all button indices (0 to numpadSize - 1)
        List<Integer> buttons = new ArrayList<>();
        for (int i = 0; i < numpadSize; i++) {
            buttons.add(i);
        }

        // Start the recursive cracking with initial empty sequence
        if (attemptUnlock(padlock, new ArrayList<>(), buttons)) {
            System.out.println("Passcode cracked successfully!");
        } else {
            System.err.println("Failed to crack the passcode.");
        }
    }

    /**
     * Recursive method that generates permutations of digits and tests each permutation on the padlock.
     *
     * @param padlock        The padlock instance to test the passcode on.
     * @param currentSequence Current sequence of button indices being tested.
     * @param remainingDigits Digits that haven't yet been used in the current sequence.
     * @return True if the correct passcode is found, otherwise false.
     */
    private boolean attemptUnlock(PadlockImpl padlock, List<Integer> currentSequence, List<Integer> remainingDigits) {
        // Base case: test the current permutation when it has the full length of numpad
        if (remainingDigits.isEmpty()) {
            return tryPasscode(padlock, currentSequence);
        }

        // Recursive case: try adding each remaining digit and test the resulting sequence
        for (int i = 0; i < remainingDigits.size(); i++) {
            int nextDigit = remainingDigits.get(i);

            // Add the digit to the current sequence and recurse
            currentSequence.add(nextDigit);
            List<Integer> nextRemaining = new ArrayList<>(remainingDigits);
            nextRemaining.remove(i);

            if (attemptUnlock(padlock, currentSequence, nextRemaining)) {
                return true; // Stop if the passcode is correct
            }

            // Backtrack: remove the last digit to try a new permutation
            currentSequence.remove(currentSequence.size() - 1);
        }

        return false; // No correct passcode found in this branch
    }

    /**
     * Writes the entire sequence to the padlock and tests it.
     *
     * @param padlock  The padlock instance.
     * @param sequence The sequence to write and test.
     * @return True if the passcode is correct, otherwise false.
     */
    private boolean tryPasscode(PadlockImpl padlock, List<Integer> sequence) {
        for (int i = 0; i < sequence.size(); i++) {
            padlock.writeInputBuffer(i, sequence.get(i));
        }
        return padlock.isPasscodeCorrect();
    }
}
