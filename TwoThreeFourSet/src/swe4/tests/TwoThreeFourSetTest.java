package swe4.tests;

import static java.lang.StrictMath.exp;
import static java.lang.StrictMath.round;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import org.junit.Test;
import swe4.collections.SortedTreeSet;
import swe4.collections.TwoThreeFourTreeSet;

public class TwoThreeFourSetTest extends SortedTreeSetTestBase {
    public class cstObject implements Comparable<cstObject> {
        public String value = "DEFAULT";

        @Override
        public int compareTo(cstObject o) {
            cstObject other = (cstObject) o;
            String oString = ((cstObject) o).value;
            return value.compareTo(oString);
        }
    }
  @Override
  protected <T extends Comparable<T>> TwoThreeFourTreeSet<T> createSet(Comparator<T> comparator) {
    return new TwoThreeFourTreeSet<T>(comparator);
  }

  @Test
  public void testHeight() {
    final int NELEMS = 10000;
    SortedTreeSet<Integer> set = createSet();

    for (int i=1; i<=NELEMS; i++) {
      set.add(i);
    }
      int h = set.height();
      int n = set.size();
      assertTrue("height(set) <= ld(size(set))+1", h <= Math.log((double)n)/Math.log(2.0)+1);

  }

  //some more tests

  @Test
  public void cstTest1String() {
      TwoThreeFourTreeSet<String> set = new TwoThreeFourTreeSet<>((i1, i2) -> i1.compareTo(i2));
      set.add("ipsum");
      set.add("Lorem");
      Iterator<String> iterator = set.iterator();
      assertEquals("Lorem", iterator.next());
      assertEquals("ipsum", iterator.next());
      assertFalse(iterator.hasNext());
  }

    @Test
    public void cstTest2Iterator() {
        TwoThreeFourTreeSet<Double> set = new TwoThreeFourTreeSet<>(null);
        Random rand = new Random();

        for (int i = 1; i <= 100; i++) {
            set.add(rand.nextDouble());
        }

        Iterator<Double> iterator = set.iterator();

        for (int i = 1; i <= 100; i++) {
            assertTrue(set.contains(iterator.next()));
        }
    }

    @Test
    public void cstTest3HeightRdm() {
        TwoThreeFourTreeSet<Integer> set = new TwoThreeFourTreeSet<>(null);
        set.add(2);
        set.add(6);
        set.add(4);
        set.add(7);
        set.add(3);
        set.add(5);
        set.add(1);
        set.add(8);
        set.add(-513);
        set.add(965);

        assertEquals(3, set.height());
    }

    @Test
    public void cstTest4CstObject() {
        TwoThreeFourTreeSet<cstObject> mainSet = new TwoThreeFourTreeSet<>(null);
        cstObject tmpDef = new cstObject();
        mainSet.add(tmpDef);

        assertTrue(mainSet.contains(tmpDef));

        assertEquals(1, mainSet.height());
    }

    @Test
    public void cstTest5Get() {
        TwoThreeFourTreeSet<Integer> set = new TwoThreeFourTreeSet<>(null);

        assertEquals(0, set.size());

        set.add(2);
        set.add(6);
        set.add(4);
        set.add(7);
        set.add(3);
        set.add(5);
        set.add(1);
        set.add(8);
        set.add(-513);
        set.add(965);

        Integer i = -513;
        assertEquals((Integer)2, (Integer) set.get(2));
        assertEquals((Integer)7, (Integer) set.get(7));
        assertEquals(i, (Integer) set.get(-513));
        assertEquals((Integer)965, (Integer) set.get(965));

        assertFalse(set.contains(1000));
        assertFalse(set.contains(-514));
    }

    @Test
    public void cstTest6AddDouble() {
        TwoThreeFourTreeSet<Integer> set = new TwoThreeFourTreeSet<>(null);

        assertEquals(0, set.size());

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

        assertEquals(10, set.size());
    }

    @Test
    public void cstTest7TestSizesAfterInsert() {
        TwoThreeFourTreeSet<Integer> set = new TwoThreeFourTreeSet<>(null);

        assertEquals(0, set.size());
        set.add(2);
        assertEquals(1, set.size());
        set.add(2);
        assertEquals(1, set.size());
        set.add(6);
        assertEquals(2, set.size());
        set.add(6);
        assertEquals(2, set.size());
        set.add(4);
        assertEquals(3, set.size());
        set.add(4);
        assertEquals(3, set.size());
        set.add(7);
        assertEquals(4, set.size());
        set.add(3);
        set.add(5);
        set.add(1);
        set.add(8);
        set.add(-513);
        set.add(965);
        assertEquals(10, set.size());
    }

    @Test
    public void cstTest8TestHeightRandom() {
        TwoThreeFourTreeSet<Integer> set = new TwoThreeFourTreeSet<>(null);

        assertEquals(0, set.size());
        set.add(2);
        assertEquals(1, set.height());
        set.add(2);
        assertEquals(1, set.height());
        set.add(6);
        assertEquals(1, set.height());
        set.add(6);
        assertEquals(1, set.height());
        set.add(4);
        assertEquals(1, set.height());
        set.add(4);
        assertEquals(1, set.height());
        set.add(7);
        assertEquals(2, set.height());
        set.add(3);
        set.add(5);
        set.add(1);
        set.add(8);
        set.add(-513);
        set.add(965);
        assertEquals(3, set.height());
    }

    @Test
    public void cstTest9InsertOnlySmallerElements() {
        TwoThreeFourTreeSet<Integer> set = new TwoThreeFourTreeSet<>(null);
        for(int i = 1000; i > 0; i--) {
            set.add(i);
        }

        assertEquals(1000, set.size());
    }

    @Test
    public void cstTest10RandSortedCompare() {
        int test1 = 10;
        int test2 = 100;
        int test3 = 1000;
        int test4 = 10000;
        int test5 = 200000;
        int test6 = 300000;
        int test7 = 400000;
        int test8 = 500000;
        int test9 = 600000;


        TwoThreeFourTreeSet<Integer> set1Sorted = new TwoThreeFourTreeSet<>(null);
        for (int i=1; i<=test1; i++) {
            set1Sorted.add(i);
        }
        TwoThreeFourTreeSet<Integer> set2Sorted = new TwoThreeFourTreeSet<>(null);
        for (int i=1; i<=test2; i++) {
            set2Sorted.add(i);
        }
        TwoThreeFourTreeSet<Integer> set3Sorted = new TwoThreeFourTreeSet<>(null);
        for (int i=1; i<=test3; i++) {
            set3Sorted.add(i);
        }
        TwoThreeFourTreeSet<Integer> set4Sorted = new TwoThreeFourTreeSet<>(null);
        for (int i=1; i<=test4; i++) {
            set4Sorted.add(i);
        }
        TwoThreeFourTreeSet<Integer> set5Sorted = new TwoThreeFourTreeSet<>(null);
        for (int i=1; i<=test5; i++) {
            set5Sorted.add(i);
        }
        TwoThreeFourTreeSet<Integer> set6Sorted = new TwoThreeFourTreeSet<>(null);
        for (int i=1; i<=test6; i++) {
            set6Sorted.add(i);
        }
        TwoThreeFourTreeSet<Integer> set7Sorted = new TwoThreeFourTreeSet<>(null);
        for (int i=1; i<=test7; i++) {
            set7Sorted.add(i);
        }
        TwoThreeFourTreeSet<Integer> set8Sorted = new TwoThreeFourTreeSet<>(null);
        for (int i=1; i<=test8; i++) {
            set8Sorted.add(i);
        }
        TwoThreeFourTreeSet<Integer> set9Sorted = new TwoThreeFourTreeSet<>(null);
        for (int i=1; i<=test9; i++) {
            set9Sorted.add(i);
        }

        Random rand = new Random();

        TwoThreeFourTreeSet<Integer> set1Random = new TwoThreeFourTreeSet<>(null);
        for (int i = 1; i <= test1; i++) {
            set1Random.add(rand.nextInt());
        }
        TwoThreeFourTreeSet<Integer> set2Random = new TwoThreeFourTreeSet<>(null);
        for (int i = 1; i <= test2; i++) {
            set2Random.add(rand.nextInt());
        }
        TwoThreeFourTreeSet<Integer> set3Random = new TwoThreeFourTreeSet<>(null);
        for (int i = 1; i <= test3; i++) {
            set3Random.add(rand.nextInt());
        }
        TwoThreeFourTreeSet<Integer> set4Random = new TwoThreeFourTreeSet<>(null);
        for (int i = 1; i <= test4; i++) {
            set4Random.add(rand.nextInt());
        }
        TwoThreeFourTreeSet<Integer> set5Random = new TwoThreeFourTreeSet<>(null);
        for (int i = 1; i <= test5; i++) {
            set5Random.add(rand.nextInt());
        }
        TwoThreeFourTreeSet<Integer> set6Random = new TwoThreeFourTreeSet<>(null);
        for (int i = 1; i <= test6; i++) {
            set6Random.add(rand.nextInt());
        }
        TwoThreeFourTreeSet<Integer> set7Random = new TwoThreeFourTreeSet<>(null);
        for (int i = 1; i <= test7; i++) {
            set7Random.add(rand.nextInt());
        }
        TwoThreeFourTreeSet<Integer> set8Random = new TwoThreeFourTreeSet<>(null);
        for (int i = 1; i <= test8; i++) {
            set8Random.add(rand.nextInt());
        }
        TwoThreeFourTreeSet<Integer> set9Random = new TwoThreeFourTreeSet<>(null);
        for (int i = 1; i <= test9; i++) {
            set9Random.add(rand.nextInt());
        }

        assertEquals(test1, set1Sorted.size());
        assertEquals(test2, set2Sorted.size());
        assertEquals(test3, set3Sorted.size());
        assertEquals(test4, set4Sorted.size());
        assertEquals(test5, set5Sorted.size());
        assertEquals(test6, set6Sorted.size());
        assertEquals(test7, set7Sorted.size());
        assertEquals(test8, set8Sorted.size());
        assertEquals(test9, set9Sorted.size());

        assertTrue(test1 >= set1Random.size());
        assertTrue(test2 >= set2Random.size());
        assertTrue(test3 >= set3Random.size());
        assertTrue(test4 >= set4Random.size());
        assertTrue(test5 >= set5Random.size());
        assertTrue(test6 >= set6Random.size());
        assertTrue(test7 >= set7Random.size());
        assertTrue(test8 >= set8Random.size());
        assertTrue(test9 >= set9Random.size());

        //To compare
        System.out.println(set1Sorted.height());
        System.out.println(set2Sorted.height());
        System.out.println(set3Sorted.height());
        System.out.println(set4Sorted.height());
        System.out.println(set5Sorted.height());
        System.out.println(set6Sorted.height());
        System.out.println(set7Sorted.height());
        System.out.println(set8Sorted.height());
        System.out.println(set9Sorted.height());
        System.out.println();
        System.out.println(set1Random.size());
        System.out.println(set2Random.size());
        System.out.println(set3Random.size());
        System.out.println(set4Random.size());
        System.out.println(set5Random.size());
        System.out.println(set6Random.size());
        System.out.println(set7Random.size());
        System.out.println(set8Random.size());
        System.out.println(set9Random.size());
        System.out.println();
        System.out.println(set1Random.height());
        System.out.println(set2Random.height());
        System.out.println(set3Random.height());
        System.out.println(set4Random.height());
        System.out.println(set5Random.height());
        System.out.println(set6Random.height());
        System.out.println(set7Random.height());
        System.out.println(set8Random.height());
        System.out.println(set9Random.height());
    }
}






















