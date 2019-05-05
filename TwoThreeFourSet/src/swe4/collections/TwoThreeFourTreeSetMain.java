package swe4.collections;

import java.util.Comparator;
import java.util.Iterator;

public class TwoThreeFourTreeSetMain {



    public static void main(String[] args) {

        // DEBUG
        TwoThreeFourTreeSet<Integer> set = new TwoThreeFourTreeSet<>(null);
        set.add(2);
        set.add(2);
        set.add(6);
        set.add(6);
        set.add(4);
        set.add(4);
        set.add(7);
        set.add(3);
        set.add(5);
        set.add(1);
        set.add(8);
        set.add(-513);
        set.add(965);

        System.out.println(set.height());
        System.out.println(set.size());

        set.printTree();

        System.out.println(set.contains(1));
        System.out.println(set.contains(3));
        System.out.println(set.contains(5));

        System.out.println(set.get(1));
        System.out.println(set.get(5));
        System.out.println(set.get(3));

        set.printTree();

        for (Object integer : set) {
            System.out.println(integer);
        }

    }
}
