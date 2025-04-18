package neetcode;

import dtos.IntegerFrequency;

import java.util.*;

public class ArrayAndHashing {
    public boolean hasDuplicate(int[] nums) {
        boolean hasDuplicate = false;
        Set<Integer> occurrences = new HashSet<>();
        for (int num : nums) {
            if (occurrences.contains(num)) {
                hasDuplicate = true;
                break;
            } else {
                occurrences.add(num);
            }
        }
        return hasDuplicate;
    }

    public boolean isAnagram(String s, String t) {
        boolean isAnagram = true;
        if (s.length() != t.length()) {
            isAnagram = false;
        } else {
            Map<Character, Integer> charCountMap = new HashMap<>();
            for (char ch : s.toCharArray()) {
                charCountMap.put(ch, charCountMap.getOrDefault(ch, 0) + 1);
            }

            for (char ch : t.toCharArray()) {
                if (!charCountMap.containsKey(ch)) {
                    isAnagram = false;
                    break;
                } else {
                    int count = charCountMap.get(ch) - 1;
                    if (count < 0) {
                        isAnagram = false;
                        break;
                    } else {
                        charCountMap.put(ch, count);
                    }
                }
            }
        }
        return isAnagram;
    }

    public int[] twoSum(int[] nums, int target) {
        int[] twoSums = new int[2];
        Map<Integer, Integer> occurenceMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (occurenceMap.containsKey(target - nums[i])) {
                twoSums[0] = i;
                twoSums[1] = occurenceMap.get(target - nums[i]);
                break;
            } else {
                occurenceMap.put(nums[i], i);
            }
        }
        return twoSums;
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for (String s : strs) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String sorted = new String(chars);
            map.computeIfAbsent(sorted, _ -> new ArrayList<>()).add(s);
        }

        return new ArrayList<>(map.values());
    }

    // There is an even better solution to this, we can use bucket sort to do this
    // create a buckets frequency -> letters that have that frequency,
    // collect k traversing from the largest bucket to small
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }
        Queue<IntegerFrequency> maxHeap = new PriorityQueue<>((f1, f2) -> Integer.compare(f2.getFrequency(), f1.getFrequency()));
        for (int num : frequencyMap.keySet()) {
            var entry = new IntegerFrequency(num, frequencyMap.get(num));
            maxHeap.add(entry);
        }

        int[] returnArr = new int[k];
        for (int i = 0; i < k; i++) {
            returnArr[i] = maxHeap.poll().getKey();
        }
        return returnArr;
    }

    public String encode(List<String> strs) {
        String encoded = null;
        if (!strs.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            builder.append(strs.get(0));
            for (int i = 1; i < strs.size(); i++) {
                builder.append("..").append(strs.get(i));
            }
            encoded = builder.toString();
        }
        return encoded;
    }

    public List<String> decode(String str) {
        if (null == str) {
            return new ArrayList<>();
        } else if (str.isEmpty()) {
            return List.of(str);
        } else {
            return Arrays.stream(str.split("\\.\\.")).toList();
        }
    }

    public int[] productExceptSelf(int[] nums) {
        int[] productArray = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            productArray[i] = 1;
        }
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (i != j) {
                    productArray[j] *= nums[i];
                }
            }
        }
        return productArray;
    }

    public int[] productExceptSelfDp(int[] nums) {
        int[] productArray = new int[nums.length];
        // stores product of numbers before it
        int[] dpLeft = new int[nums.length];
        // stores the product of numbers after it
        int[] dpRight = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            if (i == 0) {
                dpLeft[i] = 1;
                dpRight[i] = 1;
            } else {
                dpLeft[i] = dpLeft[i - 1] * nums[i - 1];
                dpRight[dpRight.length - 1 - i] = dpRight[dpRight.length - i] * nums[nums.length - i];
            }
        }

        for (int i = 0; i < nums.length; i++) {
            productArray[i] = dpLeft[i] * dpRight[i];
        }
        return productArray;
    }

    public boolean isValidSudoku(char[][] board) {
        boolean isValid = true;

        Set<Character>[][] boxes = (Set<Character>[][]) new HashSet[3][3];
        List<Set<Character>> rows = new ArrayList<>();
        List<Set<Character>> columns = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boxes[i][j] = new HashSet<>();
            }
        }
        for (int i = 0; i < board.length; i++) {
            rows.add(new HashSet<>());
            columns.add(new HashSet<>());
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != '.') {
                    var row = rows.get(i);
                    var column = columns.get(j);
                    var box = boxes[i / 3][j / 3];
                    if (row.contains(board[i][j]) || column.contains(board[i][j]) || box.contains(board[i][j])) {
                        isValid = false;
                        break;
                    }
                    row.add(board[i][j]);
                    column.add(board[i][j]);
                    box.add(board[i][j]);
                }
            }
        }

        return isValid;
    }

    // This is a better solution to above and more descriptive, wow
    //    public boolean isValidSudoku(char[][] board) {
    //        Set seen = new HashSet();
    //        for (int i=0; i<9; ++i) {
    //            for (int j=0; j<9; ++j) {
    //                char number = board[i][j];
    //                if (number != '.')
    //                    if (!seen.add(number + " in row " + i) ||
    //                            !seen.add(number + " in column " + j) ||
    //                            !seen.add(number + " in block " + i/3 + "-" + j/3))
    //                        return false;
    //            }
    //        }
    //        return true;
    //    }

    public int longestConsecutive(int[] nums) {
        Set<Integer> checkList = new HashSet<>();
        for (int num : nums) {
            checkList.add(num);
        }

        int maxLength = 0;
        for (int num : checkList) {
            if (!checkList.contains(num - 1)) {
                int currentLength = 1;
                int next = num;
                while (true) {
                    next++;
                    if (checkList.contains(next)) {
                        currentLength++;
                    } else {
                        break;
                    }
                }
                maxLength = Math.max(maxLength, currentLength);
            }
        }

        return maxLength;
    }
}
