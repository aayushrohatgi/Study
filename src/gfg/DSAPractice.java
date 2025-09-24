package gfg;

public class DSAPractice {

    public long countSubArrayProductLessThanK(long arr[], int n, long k) {
        int count = 0;
        long[] maxProductFromIndex = new long[n];
        for (int i = 0; i < n; i++) {
            maxProductFromIndex[i] = arr[i];
            if (maxProductFromIndex[i] < k) {
                count++;
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n - i; j++) {
                maxProductFromIndex[i] = maxProductFromIndex[i] * arr[i + j];
                if (maxProductFromIndex[i] >= k) {
                    break;
                } else {
                    count++;
                }
            }
        }
        return count;
    }
}
