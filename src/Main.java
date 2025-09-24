import neetcode.DP1;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DP1 dp1 = new DP1();
        System.out.println(dp1.wordBreak("aaaaaaa", List.of("aa", "aaaa")));
    }
}