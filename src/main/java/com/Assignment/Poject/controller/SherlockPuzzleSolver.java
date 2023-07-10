package com.Assignment.Poject.controller;
import dto.TestCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SherlockPuzzleSolver {
    @PostMapping("/sherlock-puzzle")
    public String solvePuzzle(@RequestBody TestCase[] testCases) {
        StringBuilder result = new StringBuilder();

        for (TestCase testCase : testCases) {
            int N = testCase.getNumberOfItems();
            int[] items = testCase.getCostOfItems();

            boolean canOpenDoor = canOpenDoor(items);
            result.append(canOpenDoor ? "YES" : "NO").append("\n");
        }

        return result.toString();
    }

    private boolean canOpenDoor(int[] items) {
        int totalSum = 0;
        for (int item : items) {
            totalSum += item;
        }

        if (totalSum % 3 != 0) {
            return false;
        }

        int targetSum = totalSum / 3;
        boolean[][][] dp = new boolean[items.length + 1][targetSum + 1][targetSum + 1];
        dp[0][0][0] = true;

        for (int i = 1; i <= items.length; i++) {
            for (int j = 0; j <= targetSum; j++) {
                for (int k = 0; k <= targetSum; k++) {
                    dp[i][j][k] = dp[i - 1][j][k];
                    if (j >= items[i - 1]) {
                        dp[i][j][k] = dp[i][j][k] || dp[i - 1][j - items[i - 1]][k];
                    }
                    if (k >= items[i - 1]) {
                        dp[i][j][k] = dp[i][j][k] || dp[i - 1][j][k - items[i - 1]];
                    }
                }
            }
        }

        return dp[items.length][targetSum][targetSum];
    }
}
