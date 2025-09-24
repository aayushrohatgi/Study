package neetcode;

public class CommonLogics {

    public static boolean isPalindrome(String substring) {
        boolean isPalindrome = true;
        int i = 0;
        int j = substring.length() - 1;
        while (i < j) {
            if (substring.charAt(i) != substring.charAt(j)) {
                isPalindrome = false;
                break;
            }
            i++;
            j--;
        }
        return isPalindrome;
    }
}
