package neetcode;

import java.util.*;

public class TwoPointers {

    public boolean isPalindrome(String s) {
        boolean isPalindrome = true;
        int i = 0;
        int j = s.length() - 1;

        while (i < j) {
            int left = s.charAt(i);

            if (left >= 97) {
                left = left - 97;
                if (left >= 26) {
                    i++;
                    continue;
                }
            } else if (left >= 65) {
                left = left - 65;
                if (left >= 26) {
                    i++;
                    continue;
                }
            } else if (left < 48 || left > 57) {
                i++;
                continue;
            }

            int right = s.charAt(j);

            if (right >= 97) {
                right = right - 97;
                if (right >= 26) {
                    j--;
                    continue;
                }
            } else if (right >= 65) {
                right = right - 65;
                if (right >= 26) {
                    j--;
                    continue;
                }
            } else if (right < 48 || right > 57) {
                j--;
                continue;
            }

            if (left != right) {
                isPalindrome = false;
                break;
            } else {
                i++;
                j--;
            }
        }
        return isPalindrome;
    }

    public boolean isPalindromeMostBeautiful(String s) {
        int i = 0, j = s.length() - 1;

        while (i < j) {
            while (i < j && !Character.isLetterOrDigit(s.charAt(i))) {
                i++;
            }
            while (i < j && !Character.isLetterOrDigit(s.charAt(j))) {
                j--;
            }

            if (Character.toLowerCase(s.charAt(i)) != Character.toLowerCase(s.charAt(j))) {
                return false;
            }

            i++;
            j--;
        }

        return true;
    }

    public boolean isPalindromeMiddleGround(String s) {
        int i = 0, j = s.length() - 1;

        while (i < j) {
            char ci = s.charAt(i);
            char cj = s.charAt(j);

            // Skip non-alphanumeric characters
            if (!isAlphaNumeric(ci)) {
                i++;
                continue;
            }
            if (!isAlphaNumeric(cj)) {
                j--;
                continue;
            }

            // Normalize to lowercase
            if (toLowerCase(ci) != toLowerCase(cj)) {
                return false;
            }

            i++;
            j--;
        }

        return true;
    }

    private boolean isAlphaNumeric(char c) {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9');
    }

    private char toLowerCase(char c) {
        if (c >= 'A' && c <= 'Z') {
            return (char) (c + 32); // Convert uppercase to lowercase
        }
        return c;
    }

    public int[] twoSum(int[] numbers, int target) {
        Map<Integer, Integer> occurence = new HashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            int key = target - numbers[i];
            if (occurence.containsKey(key)) {
                return new int[]{occurence.get(key), i + 1};
            } else {
                occurence.put(numbers[i], i + 1);
            }
        }
        return null;
    }

    public int[] twoSumNeededByNeetCode(int[] numbers, int target) {
        int[] result = new int[2];
        for (int i = 0; i < numbers.length - 1; i++) {
            int pairNeeded = target - numbers[i];
            for (int j = numbers.length; j > i ; j--) {
                if (pairNeeded == numbers[j]) {
                    result[0] = i + 1;
                    result[1] = j + 1;
                    return result;
                } else if (pairNeeded > numbers[j]) {
                    break;
                }
            }
        }
        return result;
    }

    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> results = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            if (i != 0 && nums[i] == nums[i - 1]) { // skip duplicates for i
                continue;
            }
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                int twoSum = nums[left] + nums[right];
                int threeSum = nums[i] + twoSum;
                if (threeSum == 0) {
                    results.add(List.of(nums[i], nums[left], nums[right]));
                    left++; right--;
                    while (left < right && nums[left] == nums[left - 1]) {
                        left++;   // skip duplicates
                    }
                    while (left < right && nums[right] == nums[right + 1]) {
                        right--; // skip duplicates
                    }
                } else if (threeSum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return results;
    }

    public int maxArea(int[] heights) {
        int max = 0;
        int left = 0, right = heights.length - 1;
        while (left < right) {
            boolean isLeftSmaller = heights[left] < heights[right];
            if (isLeftSmaller) {
                max = Math.max(max, (right - left) * heights[left]);
                left++;
            } else {
                max = Math.max(max, (right - left) * heights[right]);
                right--;
            }
        }
        return max;
    }

    /*
        Water is trapped between bars.
        The water above a bar at index i is determined by the shorter of the tallest bars to its left and right.

        We avoid precomputing maxLeft[] and maxRight[] arrays
        and instead compute them on-the-fly using two pointers (left and right) and two variables (leftMax and rightMax).

        While left < right

        check if height[left] < height[right]:
            This means the limiting factor for water is on the left side.
            If height[left] >= leftMax, update leftMax.
            Else, water is trapped: leftMax - height[left].
            Move left++.

        If height[right] <= height[left]:
            This means the limiting factor is on the right side.
            If height[right] >= rightMax, update rightMax.
            Else, water is trapped: rightMax - height[right].
            Move right--.
     */
    public int trap(int[] height) {
        int trappedWater = 0, left = 0, leftMax = 0, right = height.length - 1, rightMax = 0;
        while (left < right) {
            if (height[left] > height[right]) {
                if (height[right] >= rightMax) {
                    rightMax = height[right];
                } else {
                    trappedWater += rightMax - height[right];
                }
                right--;
            } else {
                if (height[left] >= leftMax) {
                    leftMax = height[left];
                } else {
                    trappedWater += leftMax - height[left];
                }
                left++;
            }
        }
        return trappedWater;
    }

}
