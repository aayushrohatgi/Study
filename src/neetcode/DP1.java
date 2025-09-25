package neetcode;

import dtos.Trie;
import dtos.TrieNode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DP1 {

    public int climbStairs(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i < n + 1; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    public int minCostClimbingStairs(int[] cost) {
        int[] dp = new int[cost.length + 1];
        dp[0] = 0;
        dp[1] = 0;
        for (int i = 2; i < cost.length + 1; i++) {
            int oneStep = i - 1;
            int twoStep = i - 2;
            dp[i] = Math.min(dp[oneStep] + cost[oneStep], dp[twoStep] + cost[twoStep]);
        }
        return dp[cost.length];
    }

    public int rob(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        /*
        dp[i][0] = max robbed value till now including ith house
        dp[i][1] = max robbed value till now excluding ith house
         */
        int[][] dp = new int[nums.length][2];
        dp[0][0] = nums[0];
        dp[0][1] = 0;
        dp[1][0] = nums[1];
        dp[1][1] = nums[0];
        for (int i = 2; i < nums.length; i++) {
            int prevIndex = i - 1;
            dp[i][0] = dp[prevIndex][1] + nums[i];
            dp[i][1] = Math.max(dp[prevIndex][0], dp[prevIndex][1]);
        }
        int lastIndex = nums.length - 1;
        return Math.max(dp[lastIndex][0], dp[lastIndex][1]);
    }

    public int rob2(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }
        int robFrom0 = rob(Arrays.copyOfRange(nums, 0, nums.length - 1));
        int robFrom1 = rob(Arrays.copyOfRange(nums, 1, nums.length));
        return Math.max(robFrom0, robFrom1);
    }

    public String longestPalindrome(String s) {
        String largestP = String.valueOf(s.charAt(0));
        for (int i = 0; i < s.length() - 1; i++) {
            for (int j = 0; j < i + 1; j++) {
                String substring = s.substring(j, j + s.length() - i);
                if (CommonLogics.isPalindrome(substring)) {
                    return substring;
                }
            }
        }
        return largestP;
    }

    public String longestPalindromeBottomUpDP(String s) {
        int sLength = s.length();
        if (s.length() == 1) {
            return s;
        }
        boolean[][] dp = new boolean[sLength][sLength];
        int start = 0;
        int end = 1;
        for (int i = 0; i < s.length(); i++) {
            dp[i][i] = true;
        }
        for (int i = 2; i <= sLength; i++) {
            for (int j = 0; j <= sLength - i; j++) {
                int lastPlusOne = j + i;
                int lastCharOfCurrentSubString = lastPlusOne - 1;
                boolean areTerminalCharsMatching = s.charAt(j) == s.charAt(lastCharOfCurrentSubString);
                if (areTerminalCharsMatching && (i == 2 || dp[j + 1][lastPlusOne - 2])) {
                    dp[j][lastCharOfCurrentSubString] = true;
                    start = j;
                    end = lastPlusOne;
                }
            }
        }
        return s.substring(start, end);
    }

    public int countSubstrings(String s) {
        int palindromes = 0;
        if (s.length() == 1) {
            return 1;
        }
        boolean[][] dp = new boolean[s.length()][s.length()];
        for (int i = 0; i < s.length(); i++) {
            dp[i][i] = true;
            palindromes++;
        }
        for (int i = 2; i <= s.length(); i++) {
            for (int j = 0; j <= s.length() - i; j++) {
                int lastPlusOne = j + i;
                int lastCharOfCurrentSubString = lastPlusOne - 1;
                if (s.charAt(j) == s.charAt(lastCharOfCurrentSubString) && (i == 2 || dp[j + 1][lastPlusOne - 2])) {
                    dp[j][lastCharOfCurrentSubString] = true;
                    palindromes++;
                }
            }
        }
        return palindromes;
    }

    public int numDecodings(String s) {
        // no need to create an array for dp as we only need last 2 index results
        // dp[0] = 1 is edge case;
        // our dp index is to be considered as 1-index not 0-index
        int prev2 = 1; // dp[0] = 1 (empty string)
        int prev1 = s.charAt(0) != '0' ? 1 : 0; // dp[1]

        for (int i = 2; i <= s.length(); i++) {
            int curr = 0;

            // Single digit
            if (s.charAt(i - 1) != '0') {
                curr += prev1;
            }

            // Two digits
            int twoDigit = (s.charAt(i - 2) - '0') * 10 + (s.charAt(i - 1) - '0');
            if (twoDigit >= 10 && twoDigit <= 26) {
                curr += prev2;
            }

            prev2 = prev1;
            prev1 = curr;
        }

        return prev1;
    }

    public int coinChange(int[] coins, int amount) {

        int[] dp = new int[amount + 1];
        Arrays.fill(dp, -1);
        dp[0] = 0;

        for (int i = 1; i < dp.length; i++) {
            for (int coinValue : coins) {
                int remaining = i - coinValue;
                if (remaining == 0) {
                    dp[i] = 1;
                    break;
                }
                if (remaining > 0 && dp[remaining] != -1) {
                    dp[i] = dp[i] == -1 ? dp[remaining] + 1 : Math.min(dp[i], dp[remaining] + 1);
                }
            }
        }

        return dp[amount];
    }

    /**
     * Finds the maximum product of any contiguous subarray.
     * Uses dynamic programming to track:
     * - maxEndingHere: the largest product ending at the current index
     * - negativeCarry: the smallest (most negative) product ending here,
     * which could become the maximum if multiplied by another negative
     * Swaps maxEndingHere and negativeCarry when encountering a negative number.
     * why? - because the max may come from negativeCarry, if not then we also need to start fresh subarray
     */
    public int maxProduct(int[] nums) {
        int result = nums[0], maxEndingHere = nums[0], negativeCarry = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < 0) {
                int temp = maxEndingHere;
                maxEndingHere = negativeCarry;
                negativeCarry = temp;
            }
            maxEndingHere = Math.max(nums[i], maxEndingHere * nums[i]);
            negativeCarry = Math.min(nums[i], negativeCarry * nums[i]);
            result = Math.max(result, maxEndingHere);
        }
        return result;
    }

    public boolean wordBreak(String s, List<String> wordDict) {
        Set<Integer> failedDpSet = new HashSet<>();
        // Trie is a minor performance improvement, can also be done with hashset for cleaner solution
        Trie dictionary = new Trie();
        for (String word : wordDict) {
            dictionary.insert(word);
        }
        return isWordBreakPossible(s, 0, failedDpSet, dictionary);
    }

    public boolean isWordBreakPossible(String s, int startIndex, Set<Integer> failedDpSet, Trie dictionary) {
        // if we are trying to check word break possible from here it means its already possible
        // this handles completion
        if (startIndex == s.length()) {
            return true;
        }
        if (failedDpSet.contains(startIndex)) {
            return false;
        }
        TrieNode currentTrieNode = dictionary.getRoot();
        for (int i = startIndex; i < s.length(); i++) {
            char key = s.charAt(i);
            if (currentTrieNode.getChildren().containsKey(key)) {
                currentTrieNode = currentTrieNode.getChildren().get(key);
                if (currentTrieNode.isEndOfWord()) {
                    if (isWordBreakPossible(s, i + 1, failedDpSet, dictionary)) {
                        return true;
                    }
                }
            } else {
                failedDpSet.add(startIndex);
                break;
            }
        }
        // This is important as we have traversed all the string and still not found a solution
        failedDpSet.add(startIndex);
        return false;
    }

    /*
        dp[i] = true if the substring s[0..i-1] can be segmented into dictionary words.
        Base case: dp[0] = true (empty string is “segmented”).
        For each position i, check all possible previous cuts j < i:
        If dp[j] is true and s[j..i-1] is in the dictionary → dp[i] = true.

        dict.contains(s.substring(j, i)) - can be avoided if we use trie
     */
    public boolean wordBreakBottomUp(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true; // empty string

        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && dict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break; // found a valid segmentation for s[0..i-1]
                }
            }
        }

        return dp[s.length()];
    }

    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1); // every element alone is a subsequence of length 1
        dp[nums.length - 1] = 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] < nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        int maxLen = 0;
        for (int len : dp) {
            maxLen = Math.max(maxLen, len);
        }
        return maxLen;
    }

    /*
        If the sum of the array elements is not even, we can immediately return false.
        Think in terms of recursion, where we try to build a subset with a sum equal to half of the total sum.
        If we find such a subset, the remaining elements will automatically form another subset with the same sum
     */
    public boolean canPartition(int[] nums) {
        Map<String, Boolean> dpMap = new HashMap<>();
        int sum = 0;
        for (var num : nums) {
            sum += num;
        }
        if ((sum & 1) == 1) {
            return false;
        } else {
            return isPossibleToPartWithGivenSum(nums, 0, sum / 2, dpMap);
        }
    }

    /*
        check if skipping or including this index allows to create the desired sum
     */
    private boolean isPossibleToPartWithGivenSum(int[] nums, int startIndex, int desiredSum,
                                                 Map<String, Boolean> dpMap) {
        if (desiredSum == 0) {
            return true;
        }
        if (startIndex >= nums.length || desiredSum < 0) {
            return false;
        }
        String key = startIndex + "," + desiredSum;
        if (dpMap.containsKey(key)) {
            return dpMap.get(key);
        }
        boolean isPossible = isPossibleToPartWithGivenSum(nums, startIndex + 1, desiredSum, dpMap)
                || isPossibleToPartWithGivenSum(nums, startIndex + 1, desiredSum - nums[startIndex], dpMap);
        dpMap.put(key, isPossible);
        return isPossible;
    }
}
