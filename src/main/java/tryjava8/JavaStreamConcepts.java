package tryjava8;

import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;

public class JavaStreamConcepts {
    public static void main(String[] args) {
        List<String> list =
                Arrays.asList("I","am","a","beginer","in","kafka","stream","concepts",
                        "happy","to","learn");

        Spliterator<String> firstSplit = list.spliterator();
        Spliterator<String> secondSplit = firstSplit.trySplit();
        Spliterator<String> thirdSplit = secondSplit.trySplit();
        //list.stream().or
        firstSplit.forEachRemaining(System.out::print);
        secondSplit.forEachRemaining(System.out::print);
        thirdSplit.forEachRemaining(System.out::print);
    }
}
