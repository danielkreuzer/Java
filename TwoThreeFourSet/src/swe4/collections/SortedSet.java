package swe4.collections;

import java.util.Comparator;
import java.util.Iterator;

public interface SortedSet<T> extends Iterable<T> {
    boolean add(T elem);        // Fügt elem in den Set ein, falls elem noch nicht
                                // im Set enthalten war. In diesem Fall wird
                                // true zurückgegeben. Sonst false.
    T get(T elem);              // Gibt eine Referenz auf das Element im Set
                                // zurück das gleich zu elem ist und null, wenn
                                // ein derartiges Element nicht existiert.
    boolean contains(T elem);   // Gibt zurück, ob ein zu elem gleiches Element
                                // im Set existiert.
    int size();                 // Gibt die Anzahl der Element im Set zurück.
    T first();                  // Gibt das kleinstes Element im Set zurück.
    T last();                   // Gibt das größtes Element im Set zurück.
    Comparator<T> comparator(); // Liefert den Comparator oder null, wenn
                                // „natürliche Sortierung“ verwendet wird.
    Iterator<T> iterator();     // Gibt einen Iterator zurück, der die Elemente
                                // im Set in aufsteigender Reihenfolge besucht.
    //Debug
    void printTree();
}
