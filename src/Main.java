import neetcode.ArrayAndHashing;
import neetcode.Backtracking;


public class Main {
    public static void main(String[] args) {
//        LearnSerialization learnSerialization = new LearnSerialization();
//        learnSerialization.whenSerializingAndDeserializing_ThenObjectIsTheSame();
        Backtracking backtracking = new Backtracking();
        System.out.println(backtracking.combinationSum2(new int[]{9, 2, 2, 4, 6, 1, 5}, 8));
    }
}