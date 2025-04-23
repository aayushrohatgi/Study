package neetcode;

import dtos.Pair;

import java.util.*;

public class Backtracking {

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> subsets = new ArrayList<>();
        subsets.add(new ArrayList<>());
        for (int num : nums) {
            List<List<Integer>> currentSubsets = new ArrayList<>(subsets);
            for (var subset : currentSubsets) {
                List<Integer> newList = new ArrayList<>(subset);
                newList.add(num);
                subsets.add(newList);
            }
        }
        return subsets;
    }

    public static List<List<Integer>> subsetsBacktracking(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        generateSubsets(0, nums, new ArrayList<>(), result);
        return result;
    }

    private static void generateSubsets(int start, int[] nums, List<Integer> current, List<List<Integer>> result) {
        result.add(new ArrayList<>(current)); // ensures that when we backtrack the lists in results are not altered
        for (int i = start; i < nums.length; i++) {
            current.add(nums[i]);
            generateSubsets(i + 1, nums, current, result);
            current.remove(current.size() - 1); // backtrack - this is the backTracking step
            // removes the last entry from current, hence allowing for a new branch to explore
            // visualization is hard in this as recursive stack is what is creating a tree here for a 1D structure
        }
    }

    public List<List<Integer>> combinationSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        generateCombinations(0, nums, target, new ArrayList<>(), 0, result);
        return result;
    }

    private void generateCombinations(int start, int[] nums, int target, ArrayList<Integer> current, int sumTillNow,
                                      List<List<Integer>> result) {
        if (sumTillNow == target) {
            result.add(new ArrayList<>(current));
        } else if (sumTillNow < target) {
            for (int i = start; i < nums.length; i++) {
                current.add(nums[i]);
                generateCombinations(i + 1, nums, target, current, sumTillNow + nums[i], result);
                current.remove(current.size() - 1);
            }
        }
    }

    // we have to sort the candidates, its important in multiple ways
    // 1. it allows for cleaner solution
    // 2. it allows for efficient backtracking, as we do not have to look at duplicate branches
    // 3. even if we use a visited type hashset, we may still end up adding duplicate sets
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        generateCombinationsNoDuplicates(0, candidates, target, new ArrayList<>(), 0, result);
        return result;
    }

    private void generateCombinationsNoDuplicates(int start, int[] candidates, int target, ArrayList<Integer> current,
                                                  int sumTillNow, List<List<Integer>> result) {
        if (sumTillNow == target) {
            result.add(new ArrayList<>(current));
        } else if (sumTillNow < target) {
            for (int i = start; i < candidates.length; i++) {
                // This condition is very important, it allows processing of nums that may result in duplicate combinations
                // while allowing for duplicate numbers to be processed
                // Think of it this way, if we have chosen a path, and we have to further take a path, do not process similar looking paths twice.
                if (i > start && candidates[i] == candidates[i - 1]) continue;
                current.add(candidates[i]);
                generateCombinationsNoDuplicates(i + 1, candidates, target, current,
                        sumTillNow + candidates[i], result);
                current.remove(current.size() - 1);
            }
        }
    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        generatePermutations(nums, result, new ArrayList<>(), new HashSet<>());
        return result;
    }

    private void generatePermutations(int[] nums, List<List<Integer>> result, List<Integer> current,
                                      Set<Integer> currentlyAdded) {
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
        } else {
            for (int num : nums) {
                if (!currentlyAdded.contains(num)) {
                    current.add(num);
                    currentlyAdded.add(num);
                    generatePermutations(nums, result, current, currentlyAdded);
                    current.remove(current.size() - 1);
                    currentlyAdded.remove(num);
                }
            }
        }
    }

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        generateSubsetsDup(0, nums, new ArrayList<>(), result);
        return result;
    }

    private static void generateSubsetsDup(int start, int[] nums, List<Integer> current, List<List<Integer>> result) {
        result.add(new ArrayList<>(current)); // ensures that when we backtrack the lists in results are not altered
        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1]) continue;
            current.add(nums[i]);
            generateSubsetsDup(i + 1, nums, current, result);
            current.remove(current.size() - 1);
        }
    }

    public boolean exist(char[][] board, String word) {
        boolean wordExists = false;
        Set<String> included = new HashSet<>();
        if (!word.isEmpty() && board.length > 0 && board[0].length > 0) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    wordExists = checkForWord(board, word, 0, i, j, included);
                    if (wordExists) {
                        break;
                    }
                }
                if (wordExists) {
                    break;
                }
            }
        }
        return wordExists;
    }


    private boolean checkForWord(char[][] board, String word, int index, int i, int j, Set<String> included) {
        String indexStr = i + "," + j;
        if (included.contains(indexStr) || i < 0 || j < 0 || i >= board.length || j >= board[i].length) {
            return false;
        } else {
            if (board[i][j] == word.charAt(index)) {
                if (index + 1 == word.length()) {
                    return true;
                } else {
                    included.add(indexStr);
                    boolean wordExist = checkForWord(board, word, index + 1, i, j + 1, included);
                    if (!wordExist) {
                        wordExist = checkForWord(board, word, index + 1, i, j - 1, included);
                    }
                    if (!wordExist) {
                        wordExist = checkForWord(board, word, index + 1, i + 1, j, included);
                    }
                    if (!wordExist) {
                        wordExist = checkForWord(board, word, index + 1, i - 1, j, included);
                    }
                    included.remove(indexStr);
                    return wordExist;
                }
            } else {
                return false;
            }
        }
    }

    public List<List<String>> partition(String s) {
        List<List<String>> palindromeSubstrings = new ArrayList<>();
        generatePalindromicSubstrings(s, palindromeSubstrings, 0, new ArrayList<>());
        return palindromeSubstrings;
    }

    private void generatePalindromicSubstrings(String s, List<List<String>> palindromeSubstrings, int start,
                                               List<String> current) {
        if (s.length() == start) {
            palindromeSubstrings.add(new ArrayList<>(current));
            return;
        }
        for (int i = 1; i < s.length() - start + 1; i++) {
            var subString = s.substring(start, start + i);
            if (isPalindrome(subString)) {
                current.add(subString);
                generatePalindromicSubstrings(s, palindromeSubstrings, start + i, current);
                current.remove(current.size() - 1);
            }
        }
    }

    private boolean isPalindrome(String str) {
        boolean isPalindrome = true;
        int i = 0;
        int j = str.length() - 1;

        while (i < j && isPalindrome) {
            isPalindrome = str.charAt(i) == str.charAt(j);
            i++;
            j--;
        }
        return isPalindrome;
    }

    public List<String> letterCombinations(String digits) {
        Map<Character, List<Character>> keyMap = new HashMap<>();
        keyMap.put('2', List.of('a', 'b', 'c'));
        keyMap.put('3', List.of('d', 'e', 'f'));
        keyMap.put('4', List.of('g', 'h', 'i'));
        keyMap.put('5', List.of('j', 'k', 'l'));
        keyMap.put('6', List.of('m', 'n', 'o'));
        keyMap.put('7', List.of('p', 'q', 'r', 's'));
        keyMap.put('8', List.of('t', 'u', 'v'));
        keyMap.put('9', List.of('w', 'x', 'y', 'z'));

        List<String> results = new ArrayList<>();
        generatePossibleWords(digits, results, keyMap, 0, new StringBuilder());
        return results;
    }

    // Using StringBuilder gives a significant performance benefit
    // in backtrack step if using string a new substring has to be created to replace the current
    // this is much easier in string builder where last char can be simply removed
    private void generatePossibleWords(String digits, List<String> results, Map<Character, List<Character>> keyMap,
                                       int start, StringBuilder current) {
        if (current.length() == digits.length() && !current.isEmpty()) {
            results.add(current.toString());
            return;
        }
        if (start < digits.length()) {
            char number = digits.charAt(start);
            for (char ch : keyMap.get(number)) {
                current.append(ch);
                generatePossibleWords(digits, results, keyMap, start + 1, current);
                current.deleteCharAt(current.length() - 1);
            }
        }
    }

    public List<List<String>> solveNQueens(int n) {
        List<List<String>> results = new ArrayList<>();
        if (n == 1) {
            results.add(List.of("Q"));
        } else {
            List<Pair> queens = new ArrayList<>();
            generateNQueenSolutions(n, results, new ArrayList<>(), 0, queens);
        }
        return results;
    }

    private void generateNQueenSolutions(int n, List<List<String>> results, List<String> current, int row, List<Pair> queens) {
        if (current.size() == n) {
            results.add(new ArrayList<>(current));
        } else {
            for (int j = 0; j < n; j++) {
                if (isValidSquareForQueen(row, j, queens)) {
                    StringBuilder currentBuilder = new StringBuilder();
                    for (int k = 0; k < n; k++) {
                        if (k == j) {
                            currentBuilder.append("Q");
                        } else {
                            currentBuilder.append(".");
                        }
                    }
                    current.add(currentBuilder.toString());
                    queens.add(new Pair(row, j));
                    generateNQueenSolutions(n, results, current, row + 1, queens);
                    current.remove(current.size() - 1);
                    queens.remove(queens.size() - 1);
                }
            }
        }
    }

    private boolean isValidSquareForQueen(int i, int j, List<Pair> queens) {
        boolean isValid = true;
        for (Pair queen : queens) {
            if (j == queen.getY() || i == queen.getX() || queen.getX() + queen.getY() == i + j
                    || queen.getX() - i == queen.getY() - j) {
                isValid = false;
                break;
            }
        }
        return isValid;
    }
}
