package neetcode;

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
}
