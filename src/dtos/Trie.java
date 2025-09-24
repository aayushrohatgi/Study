package dtos;

public class Trie {

    private final TrieNode root = new TrieNode();

    // Insert a word into the Trie
    public void insert(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            current = current.getChildren().computeIfAbsent(c, _ -> new TrieNode());
        }
        current.setEndOfWord(true);
    }

    public TrieNode getRoot() {
        return root;
    }
}