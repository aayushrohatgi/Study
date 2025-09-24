package company;

import dtos.LinkedNode;
import dtos.TreeNode;

import java.util.*;

public class LinkedIn {

    /**
     * Write a function that given a list of integers (the integral numbers in (...., -1, 0, 1, ....) )
     * returns the sum of the contiguous subsequence with maximum sum.
     * Thus, given the sequence a=(1, 2, -4, 1, 3, -2, 3, -1) it should return 5.
     */
    public int maxSubarraySum(int[] arr) {
        int sum = arr[0];
        int sumTillNow = arr[0];
        for (int i = 1; i < arr.length; i++) {
            sumTillNow = Math.max(arr[i], sumTillNow + arr[i]);
            sum = Math.max(sum, sumTillNow);
        }
        return sum;
    }

    /**
     * Given a sorted array of unique elements R, which are letters of the English alphabet, and an input character x.
     * The elements in R are sorted with the least element appearing first. Find the minimum r in R such that r > x.
     * If there is no r > x, find the first element of the array (wrap around).
     */
    public char findMinimumInArrayGreaterThanX(char[] R, char x) {
        for (char c : R) {
            if (c > x) {
                return c;
            }
        }
        return R[0];
    }

    /**
     * Given the standard mapping from English letters to digits on a phone keypad
     * (1 → ""  2 -> a,b,c  3 -> d,e,f  4 -> g,h,i  5 -> j,k,l  6 -> m,n,o  7 -> p,q,r,s  8 -> t,u,v  9 -> w,x,y,z),
     * write a program that outputs all words that can be formed from any n-digit phone number from
     * the list of given KNOWN_WORDS considering the mapping mentioned above.
     * KNOWN_WORDS= ['careers', 'hiring', 'interview', 'linkedgo']
     */
    public List<String> getAllPossibleWords(int numbers, List<String> KNOWN_WORDS) {
        Set<String> KNOWN_WORDS_SET = new HashSet<>(KNOWN_WORDS);
        Map<Integer, List<String>> numberToCharsMap = Map.of(
                1, List.of(),
                2, List.of("a", "b", "c"),
                3, List.of("d", "e", "f"),
                4, List.of("g", "h", "i"),
                5, List.of("j", "k", "l"),
                6, List.of("m", "n", "o"),
                7, List.of("p", "q", "r", "s"),
                8, List.of("t", "u", "v"),
                9, List.of("w", "x", "y", "z")
        );

        List<Integer> digits = new ArrayList<>();
        while (numbers != 0) {
            int n = numbers % 10;
            digits.add(n);
            numbers = numbers / 10;
        }

        List<String> possibleWords = new ArrayList<>();
        generatePossibleWords(digits, numberToCharsMap, possibleWords, KNOWN_WORDS_SET, digits.size() - 1,
                new StringBuilder());
        return possibleWords;
    }

    private void generatePossibleWords(List<Integer> digits, Map<Integer, List<String>> numberToCharsMap,
                                       List<String> possibleWords, Set<String> KNOWN_WORDS_SET,
                                       int currentIndex, StringBuilder stringBuilder) {
        if (currentIndex < 0) {
            String word = stringBuilder.toString();
            if (KNOWN_WORDS_SET.contains(word)) {
                possibleWords.add(word);
            }
        } else {
            List<String> chars = numberToCharsMap.get(digits.get(currentIndex));
            for (String aChar : chars) {
                stringBuilder.append(aChar);
                generatePossibleWords(digits, numberToCharsMap, possibleWords, KNOWN_WORDS_SET,
                        currentIndex - 1, stringBuilder);
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }
        }
    }

    private class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean endOfWord = false;
    }

    private class Trie {
        private final TrieNode root = new TrieNode();

        // Insert a word into the Trie
        public void insert(String word) {
            TrieNode current = root;
            for (char c : word.toCharArray()) {
                current = current.children.computeIfAbsent(c, _ -> new TrieNode());
            }
            current.endOfWord = true;
        }

        public TrieNode getRoot() {
            return root;
        }
    }

    public List<String> getAllPossibleWordsWithTrie(String number, List<String> KNOWN_WORDS) {
        Map<Character, String> digitToChars = Map.of(
                '2', "abc",
                '3', "def",
                '4', "ghi",
                '5', "jkl",
                '6', "mno",
                '7', "pqrs",
                '8', "tuv",
                '9', "wxyz"
        );
        Trie trie = new Trie();
        for (String word : KNOWN_WORDS) {
            trie.insert(word);
        }
        List<String> results = new ArrayList<>();
        generatePossibleWordsUsingTrie(number, digitToChars, results, trie.getRoot(), 0, new StringBuilder());
        return results;
    }

    private void generatePossibleWordsUsingTrie(String number, Map<Character, String> digitToChars,
                                                List<String> possibleWords, TrieNode trieNode,
                                                int currentIndex, StringBuilder stringBuilder) {
        if (currentIndex >= number.length() && trieNode.endOfWord) {
            possibleWords.add(stringBuilder.toString());
        } else {
            String chars = digitToChars.get(number.charAt(currentIndex));
            for (char aChar : chars.toCharArray()) {
                TrieNode child = trieNode.children.get(aChar);
                if (child != null) {
                    stringBuilder.append(aChar);
                    generatePossibleWordsUsingTrie(number, digitToChars, possibleWords, child,
                            currentIndex + 1, stringBuilder);
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                }
            }
        }
    }

    /*
     * returns the shortest distance between two words
     * JobDescDistanceFinder finder = new JobDescDistanceFinder(Arrays.asList("the", "java", "skill", "is", "important", "for", "java", "development"));
     *   assert(finder.distance("important", "the") == 4);
     *   assert(finder.distance("important", "java") == 2);
     *   assert(finder.distance("java", "doesntExist") == -1);
     */
    public class JobDescDistanceFinder {
        private final Map<String, List<Integer>> wordPositions = new HashMap<>();

        public JobDescDistanceFinder(List<String> words) {
            for (int i = 0; i < words.size(); i++) {
                wordPositions.computeIfAbsent(words.get(i), k -> new ArrayList<>()).add(i);
            }
        }

        public int distance(String a, String b) {
            int distance = -1;
            if (wordPositions.containsKey(a) && wordPositions.containsKey(b)) {
                distance = Integer.MAX_VALUE;
                List<Integer> aOccurences = wordPositions.get(a);
                List<Integer> bOccurences = wordPositions.get(b);
                int i = 0, j = 0;
                // A two-pointer approach to find the shortest distance efficiently in O(m+n)
                while (i < aOccurences.size() && j < bOccurences.size()) {
                    int aVal = aOccurences.get(i);
                    int bVal = bOccurences.get(j);
                    distance = Math.min(distance, aVal > bVal ? aVal - bVal : bVal - aVal);
                    if (distance == 0) {
                        break;
                    }
                    if (aVal > bVal) {
                        j++;
                    } else {
                        i++;
                    }
                }
            }
            return distance;
        }
    }

    /**
     * Find reverse depth for given NestedInteger.
     * It is the "reverse depth" of the item in the list: e.g. for the item { 1, {4, { 6, 2 } } }
     * 1 (reverse-depth 3) . = 1 * 3 = 3
     * \
     * { 4 } (reverse-depth 2) = 4 * 2 = 8
     * \
     * { 6, 2 } (reverse-depth 1) = 6 * 1 + 2 * 1 = 8
     * =3 + 8 + 8 = 19
     */
    public int depthSumInverse(List<Object> nestedList) {
        Map<Integer, List<Integer>> levelMap = new HashMap<>();

        // Recursively build the level map
        buildLevelMap(nestedList, 1, levelMap);

        // Find max depth
        int maxDepth = levelMap.size();

        // Calculate weighted sum using reverse depth
        int totalSum = 0;
        for (Map.Entry<Integer, List<Integer>> entry : levelMap.entrySet()) {
            int currentDepth = entry.getKey();
            int reverseWeight = maxDepth - currentDepth + 1;

            for (Integer value : entry.getValue()) {
                totalSum += value * reverseWeight;
            }
        }

        return totalSum;
    }

    private void buildLevelMap(List<Object> nestedList, int currentLevel,
                               Map<Integer, List<Integer>> levelMap) {
        for (Object element : nestedList) {
            if (element instanceof Integer) {
                levelMap.computeIfAbsent(currentLevel, _ -> new ArrayList<>())
                        .add((Integer) element);
            } else if (element instanceof List) {
                @SuppressWarnings("unchecked")
                List<Object> sublist = (List<Object>) element;
                buildLevelMap(sublist, currentLevel + 1, levelMap);
            }
        }
    }

    // Integer square root
    public int mySqrt(int x) {
        if (x < 0) {
            throw new IllegalArgumentException("Cannot take square root of negative number");
        }
        if (x == 0 || x == 1) {
            return x;
        }

        // Optimize the range: square root of x cannot be more than x/2 + 1
        int left = 1, right = Math.min(x / 2 + 1, 46340); // 46340^2 < Integer.MAX_VALUE
        int result = 0;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int square = mid * mid;

            if (square == x) {
                return mid;
            } else if (square < x) {
                result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }

    // Nth largest in a list
    public int findNthLargest(List<Integer> list, int n) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (Integer num : list) {
            if (minHeap.size() < n) {
                minHeap.add(num);
            } else if (num > minHeap.peek()) {
                minHeap.poll();
                minHeap.add(num);
            }
        }
        return minHeap.peek();
    }

    // Maximum Tree Depth
    public int maxDepth(TreeNode root) {
        return calculateDepthOfTree(root, 0);
    }

    private int calculateDepthOfTree(TreeNode root, int currentDepth) {
        if (null == root) {
            return currentDepth;
        } else {
            currentDepth++;
            return Math.max(
                    calculateDepthOfTree(root.left, currentDepth),
                    calculateDepthOfTree(root.right, currentDepth)
            );
        }
    }

    // IsNumber - Returns true if the input string is a number and false otherwise
    public boolean isNumber(String number) {
        for (char c : number.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public boolean isNumberAscii(String number) {
        for (char c : number.toCharArray()) {
            if (c < '0' || c > '9') {  // ASCII: 48 to 57
                return false;
            }
        }
        return true;
    }

    // intToBin - Given a non-negative integer, produce a string representation of it in binary
    public String convertToBinary(int number) {
        if (number == 0) {
            return "0";
        }

        StringBuilder builder = new StringBuilder();
        Deque<Integer> stack = new ArrayDeque<>();

        while (number > 0) {
            stack.push(number % 2);
            number /= 2;
        }

        while (!stack.isEmpty()) {
            builder.append(stack.pop());
        }

        return builder.toString();
    }

    public String convertToBinaryBitManipulation(int number) {
        if (number == 0) return "0";

        StringBuilder builder = new StringBuilder();
        while (number > 0) {
            builder.append(number & 1);  // Extract last bit (0 or 1)
            number >>>= 1;               // Right shift = divide by 2
        }
        return builder.reverse().toString();
    }

    // Reverse a string
    public String reverseAString(String text) {
        StringBuilder builder = new StringBuilder();
        for (int i = text.length() - 1; i >= 0; i--) {
            builder.append(text.charAt(i));
        }
        return builder.toString();
    }

    //Rotate Linked List
    public LinkedNode reverseLinkedList(LinkedNode head) {
        LinkedNode prev = null;
        LinkedNode curr = head;

        while (curr != null) {
            LinkedNode next = curr.getNext();
            curr.setNext(prev);
            prev = curr;
            curr = next;
        }

        return prev;
    }

    // Configuration Brace Matching
    public boolean isValidParenthesisConfiguration(String s) {
        boolean isValid = s.length() % 2 == 0;
        if (isValid) {
            Map<Character, Character> parenthesisMap = new HashMap<>();
            parenthesisMap.put(')', '(');
            parenthesisMap.put('}', '{');
            parenthesisMap.put(']', '[');
            Stack<Character> parenthesisStack = new Stack<>();
            for (char ch : s.toCharArray()) {
                if (!parenthesisMap.containsKey(ch)) {
                    parenthesisStack.push(ch);
                } else {
                    if (!parenthesisStack.empty() && parenthesisStack.peek() == parenthesisMap.get(ch)) {
                        parenthesisStack.pop();
                    } else {
                        isValid = false;
                        break;
                    }
                }
            }
            if (isValid) {
                isValid = parenthesisStack.empty();
            }
        }
        return isValid;
    }
}
