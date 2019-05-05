package swe4.collections;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class TwoThreeFourTreeSet<T extends Comparable<T>> implements SortedTreeSet<T>, SortedSet<T> {
    //Components
    private static class Node<T> {
        private T       value1;
        private T       value2;
        private T       value3;

        private Node<T> node1;
        private Node<T> node2;
        private Node<T> node3;
        private Node<T> node4;

        public Node(T value1, T value2, T value3, Node<T> node1, Node<T> node2, Node<T> node3, Node<T> node4) {
            this.value1 = value1;
            this.value2 = value2;
            this.value3 = value3;
            this.node1 = node1;
            this.node2 = node2;
            this.node3 = node3;
            this.node4 = node4;
        }

        public Node(Node<T> node) {
            this.node1 = node.node1;
            this.node2 = node.node2;
            this.node3 = node.node3;
            this.node4 = node.node4;
            this.value1 = node.value1;
            this.value2 = node.value2;
            this.value3 = node.value3;
        }

        public Node() {
        }
    }

    private class TwoThreeFOurTreeSetIterator implements Iterator<T> {
        private Stack<T> parents = new Stack<>();
        private Stack<T> rightOrder = new Stack<>();

        private void initIteratorRecursive(Node<T> node) {
            if(node != null) {
                initIteratorRecursive(node.node1);
                parents.push(node.value1);
                initIteratorRecursive(node.node2);
                if(node.value2 != null) {
                    parents.push(node.value2);
                    initIteratorRecursive(node.node3);
                    if(node.value3 != null) {
                        parents.push(node.value3);
                        initIteratorRecursive(node.node4);
                    }
                }
            }
        }

        public TwoThreeFOurTreeSetIterator(Node<T> root) {
            initIteratorRecursive(root);
            int size = parents.size();
            for (int i = 0; i < size; i++) {
                rightOrder.push(parents.pop());
            }
        }

        @Override
        public boolean hasNext() {
            return ! rightOrder.empty();
        }

        @Override
        public T next() {
            if(! hasNext()) throw new NoSuchElementException();

            T curr = rightOrder.pop();

            return curr;
        }
    }

    private Comparator<T> comparator = null;
    private int size = 0;
    private int height = 1;
    private Node<T> root = null;


    //Implementation of TwoThreeFourTreeSet<T>
    public TwoThreeFourTreeSet(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    @Override
    public int height() {
        //Debug
        //System.out.println(height);
        return height;
    }



    private Node<T> splitNode(Node<T> node) {
        if(node.equals(root)) {
            //by switching root, a new layer is added
            height++;
        }
        Node<T> newRight = new Node<>(node.value3, null, null, node.node3, node.node4, null, null);
        node.node3 = null;
        node.node4 = null;
        T tmp = node.value2;
        node.value2 = null;
        node.value3 = null;
        Node<T> newRoot = new Node<>(tmp, null, null, node, newRight, null, null);
        return newRoot;
    }

    private void insert(Node<T> parent, T elem, Node<T> rightNode) {
        if(comparator != null) {
            if (parent.value1 != null &&
                    parent.value2 == null &&
                    comparator.compare(elem, parent.value1) < 0) {
                parent.value2 = parent.value1;
                parent.node3 = parent.node2;
                parent.value1 = elem;
                parent.node2 = rightNode;
            } else if (parent.value1 != null &&
                    parent.value2 != null &&
                    parent.value3 == null &&
                    comparator.compare(elem, parent.value1) < 0) {
                parent.value3 = parent.value2;
                parent.node4 = parent.node3;
                parent.value2 = parent.value1;
                parent.node3 = parent.node2;
                parent.value1 = elem;
                parent.node2 = rightNode;
            } else if (parent.value1 != null &&
                    parent.value2 == null &&
                    !(comparator.compare(elem, parent.value1) < 0)) {
                parent.value2 = elem;
                parent.node3 = rightNode;
            } else if (parent.value2 != null &&
                    parent.value3 == null &&
                    comparator.compare(elem, parent.value2) < 0) {
                parent.value3 = parent.value2;
                parent.node4 = parent.node3;
                parent.value2 = elem;
                parent.node3 = rightNode;
            } else {
                parent.value3 = elem;
                parent.node4 = rightNode;
            }
        } else {
            if (parent.value1 != null &&
                    parent.value2 == null &&
                    elem.compareTo(parent.value1) < 0) {
                parent.value2 = parent.value1;
                parent.node3 = parent.node2;
                parent.value1 = elem;
                parent.node2 = rightNode;
            } else if (parent.value1 != null &&
                    parent.value2 != null &&
                    parent.value3 == null &&
                    elem.compareTo(parent.value1) < 0) {
                parent.value3 = parent.value2;
                parent.node4 = parent.node3;
                parent.value2 = parent.value1;
                parent.node3 = parent.node2;
                parent.value1 = elem;
                parent.node2 = rightNode;
            } else if (parent.value1 != null &&
                    parent.value2 == null &&
                    !(elem.compareTo(parent.value1) < 0)) {
                parent.value2 = elem;
                parent.node3 = rightNode;
            } else if (parent.value2 != null &&
                    parent.value3 == null &&
                    elem.compareTo(parent.value2) < 0) {
                parent.value3 = parent.value2;
                parent.node4 = parent.node3;
                parent.value2 = elem;
                parent.node3 = rightNode;
            } else {
                parent.value3 = elem;
                parent.node4 = rightNode;
            }
        }
    }

    private void addRecursive(Node<T> parent, T elem) {
        if(comparator != null) {
            if(parent.value1 == null || ! (comparator.compare(elem, parent.value1) > 0)) {
                if(parent.node1 == null) {
                    insert(parent, elem, null);
                } else if(parent.node1.value3 == null) {
                    addRecursive(parent.node1, elem);
                } else {
                    Node<T> newParent = splitNode(parent.node1);
                    parent.value1 = newParent.value1;
                    parent.node2 = newParent.node2;
                    if(comparator.compare(elem, parent.value1) < 0) {
                        addRecursive(parent.node1, elem);
                    } else {
                        addRecursive(parent.node2, elem);
                    }
                }
            } else if (parent.value2 == null || ! (comparator.compare(elem, parent.value2) > 0)) {
                if(parent.node2 == null) {
                    insert(parent, elem, null);
                } else if(parent.node2.value3 == null) {
                    addRecursive(parent.node2, elem);
                } else {
                    Node<T> newParent = splitNode(parent.node2);
                    parent.value2 = newParent.value1;
                    parent.node3 = newParent.node2;
                    if(comparator.compare(elem, parent.value2) < 0) {
                        addRecursive(parent.node2, elem);
                    } else {
                        addRecursive(parent.node3, elem);
                    }
                }
            } else {
                if(parent.node3 == null) {
                    insert(parent, elem, null);
                } else if(parent.node3.value3 == null) {
                    addRecursive(parent.node3, elem);
                } else {
                    Node<T> newParent = splitNode(parent.node3);
                    parent.value3 = newParent.value1;
                    parent.node4 = newParent.node2;
                    if(comparator.compare(elem, parent.value3) < 0) {
                        addRecursive(parent.node3, elem);
                    } else {
                        addRecursive(parent.node4, elem);
                    }
                }
            }
        } else {
            if(parent.value1 == null || ! (elem.compareTo(parent.value1) > 0)) {
                if(parent.node1 == null) {
                    insert(parent, elem, null);
                } else if(parent.node1.value3 == null) {
                    addRecursive(parent.node1, elem);
                } else {
                    Node<T> newParent = splitNode(parent.node1);
                    parent.value3 = parent.value2;
                    parent.node4 = parent.node3;
                    parent.value2 = parent.value1;
                    parent.node3 = parent.node2;
                    parent.value1 = newParent.value1;
                    parent.node2 = newParent.node2;
                    if(elem.compareTo(parent.value1) < 0) {
                        addRecursive(parent.node1, elem);
                    } else {
                        addRecursive(parent.node2, elem);
                    }
                }
            } else if (parent.value2 == null || ! (elem.compareTo(parent.value2) > 0)) {
                if(parent.node2 == null) {
                    insert(parent, elem, null);
                } else if(parent.node2.value3 == null) {
                    addRecursive(parent.node2, elem);
                } else {
                    Node<T> newParent = splitNode(parent.node2);
                    parent.value3 = parent.value2;
                    parent.node4 = parent.node3;
                    parent.value2 = newParent.value1;
                    parent.node3 = newParent.node2;
                    if(elem.compareTo(parent.value2) < 0) {
                        addRecursive(parent.node2, elem);
                    } else {
                        addRecursive(parent.node3, elem);
                    }
                }
            } else {
                if(parent.node3 == null) {
                    insert(parent, elem, null);
                } else if(parent.node3.value3 == null) {
                    addRecursive(parent.node3, elem);
                } else {
                    Node<T> newParent = splitNode(parent.node3);
                    parent.value3 = newParent.value1;
                    parent.node4 = newParent.node2;
                    if(elem.compareTo(parent.value3) < 0) {
                        addRecursive(parent.node3, elem);
                    } else {
                        addRecursive(parent.node4, elem);
                    }
                }
            }
        }
    }

    @Override
    public boolean add(T elem) {
        if(contains(elem)) {
            return false;
        }

        //treat root
        if(root == null) {
            Node<T> node = new Node<>();
            node.value1 = elem;
            root = node;
            size++;
            return true;
        }
        if(root.value1 != null &&
                root.value2 != null &&
                root.value3 != null) {
            root = splitNode(root);
        }
        addRecursive(root, elem);
        size++;
        return true;
    }

    private T getRecursive(Node<T> node, T elem) {
        if(node == null) {
            return null;
        }
        if(elem != null) {
            if (node.value1 != null && node.value1.equals(elem)) {
                return node.value1;
            }
            if (node.value2 != null && node.value2.equals(elem)) {
                return node.value2;
            }
            if (node.value3 != null && node.value3.equals(elem)) {
                return node.value3;
            }
        }
        if(comparator != null) {
            if(node.value1 != null && comparator.compare(elem, node.value1) < 0) { // <0 smaller than
                return getRecursive(node.node1, elem);
            } else if(node.value2 != null && comparator.compare(elem, node.value2) < 0) {
                return getRecursive(node.node2, elem);
            } else if(node.value2 == null && node.node2 != null) {
                return getRecursive(node.node2, elem);
            } else if(node.value3 != null && comparator.compare(elem, node.value3) < 0) {
                return getRecursive(node.node3, elem);
            } else if(node.value3 == null && node.node3 != null) {
                return getRecursive(node.node3, elem);
            } else {
                return getRecursive(node.node4, elem);
            }
        } else {
            if(node.value1 != null && elem.compareTo(node.value1) < 0) { // <0 smaller than
                return getRecursive(node.node1, elem);
            } else if(node.value2 != null && elem.compareTo(node.value2) < 0) {
                return getRecursive(node.node2, elem);
            } else if(node.value2 == null && node.node2 != null) {
                return getRecursive(node.node2, elem);
            } else if(node.value3 != null && elem.compareTo(node.value3) < 0) {
                return getRecursive(node.node3, elem);
            } else if(node.value3 == null && node.node3 != null) {
                return getRecursive(node.node3, elem);
            } else {
                return getRecursive(node.node4, elem);
            }
        }

    }

    @Override
    public T get(T elem) {
        return getRecursive(root, elem);
    }

    @Override
    public boolean contains(T elem) {
        return get(elem) != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T first() {
        if (root == null) {
            throw new NoSuchElementException();
        }
        Node<T> actualPos = root;
        while (actualPos.node1 != null) {
            actualPos = actualPos.node1;
        }
        return actualPos.value1;
    }


    @Override
    public T last() {
        if (root == null) {
            throw new NoSuchElementException();
        }
        Node<T> actualPos = root;
        while (actualPos != null) {
            if(actualPos.node4 == null && actualPos.value3 != null) {
                return actualPos.value3;
            } else if(actualPos.node4 != null) {
                actualPos = actualPos.node4;
            } else if(actualPos.node3 == null && actualPos.value2 != null) {
                return actualPos.value2;
            } else if(actualPos.node3 != null) {
                actualPos = actualPos.node3;
            } else if(actualPos.node2 == null && actualPos.value1 != null) {
                return actualPos.value1;
            } else if(actualPos.node2 != null) {
                actualPos = actualPos.node2;
            }
        }
        return null;
    }

    @Override
    public Comparator<T> comparator() {
        return comparator;
    }

    @Override
    public Iterator<T> iterator() {
        return new TwoThreeFOurTreeSetIterator(root);
    }

    private void printRecursive(Node node) {
        if(node != null) {
            printRecursive(node.node1);
            System.out.print(node.value1 + " ");
            printRecursive(node.node2);
            if(node.value2 != null) {
                System.out.print(node.value2 + " ");
                printRecursive(node.node3);
                if(node.value3 != null) {
                    System.out.print(node.value3 + " ");
                    printRecursive(node.node4);
                }
            }
        }
    }

    public void printTree() {
        System.out.println();
        printRecursive(root);
        System.out.println();
    }
}
