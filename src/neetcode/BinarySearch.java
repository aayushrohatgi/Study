package neetcode;

public class BinarySearch {

    public int search(int[] nums, int target) {
        int index = -1;
        int low = 0;
        int high = nums.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (nums[mid] == target) {
                index = mid;
                break;
            } else if (nums[mid] > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return index;
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        boolean isInMatrix = false;
        int low = 0;
        int high = matrix.length * matrix[0].length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int i = mid / matrix[0].length;
            int j = mid % matrix[0].length;
            if (matrix[i][j] == target) {
                isInMatrix = true;
                break;
            } else if (matrix[i][j] > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return isInMatrix;
    }

    public int minEatingSpeed(int[] piles, int h) {
        int low = 1;
        int high = Integer.MIN_VALUE;
        for (int pile : piles) {
            high = Math.max(high, pile);
        }

        int minK = high;
        while (low <= high) {
            int k = (low + high) / 2;
            if (canKoKoComplete(piles, h, k)) {
                minK = k;
                high = k - 1;
            } else {
                low = k + 1;
            }
        }

        return minK;
    }

    private boolean canKoKoComplete(int[] piles, int h, int k) {
        boolean canFinish = true;
        for (int pile : piles) {
//            h -= Math.ceilDiv(pile, k);
            // Gives a little better performance than Math.ceilDiv
            h -= (pile + k - 1) / k;
            if (h < 0) {
                canFinish = false;
                break;
            }
        }
        return canFinish;
    }

    public int findMin(int[] nums) {
        int lowest = nums[0];
        int low = 0;
        int high = nums.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (nums[mid] >= lowest) {
                low = mid + 1;
            } else {
                lowest = nums[mid];
                high = mid - 1;
            }
        }
        return lowest;
    }

    // We are going to find the index of the smallest. This help us to know how many rotations have been made.
    // With that we can formulate indexes to perform simple binary search - (mid + rotations) % nums.length
    public int searchRotated(int[] nums, int target) {
        int index = -1;
        int rotations = findMinIndex(nums);
        int low = 0;
        int high = nums.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (nums[(mid + rotations) % nums.length] == target) {
                index = (mid + rotations) % nums.length;
                break;
            } else if (nums[(mid + rotations) % nums.length] > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return index;
    }

    public int findMinIndex(int[] nums) {
        int lowest = nums[0];
        int lowestIndex = 0;
        int low = 0;
        int high = nums.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (nums[mid] >= lowest) {
                low = mid + 1;
            } else {
                lowest = nums[mid];
                lowestIndex = mid;
                high = mid - 1;
            }
        }
        return lowestIndex;
    }

    // This also searches in sorted rotated array but does it without need to find the minimum's index
    // Both approaches are fine
    public int searchRotatedAdv(int[] nums, int target) {
        int low = 0, high = nums.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            // Left half is sorted
            if (nums[low] <= nums[mid]) {
                if (nums[low] <= target && target < nums[mid]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
            // Right half is sorted
            else {
                if (nums[mid] < target && target <= nums[high]) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }
        return -1;
    }

}
