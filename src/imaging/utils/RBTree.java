package imaging.utils;

import java.util.ArrayList;
import java.util.List;

public class RBTree<T extends Comparable<T>> {
    private Node<T> root;
    private List<T> traversal;

    public RBTree() {
        this.traversal = new ArrayList<T>();
    }

    public void insert(T key) {
        root = insertInternal(key, root);
        root.color = Color.BLACK;
    }

    public List<T> traverse() {
        traverseInternal(root);
        return traversal;
    }

    private void traverseInternal(Node<T> h) {
        if (h == null) {
            return;
        }
        traverseInternal(h.left);
        traversal.add(h.value);
        traverseInternal(h.right);
    }

    private boolean isRed(Node<T> h) {
        return h != null && h.color == Color.RED;
    }

    private Node<T> rotateLeft(Node<T> h) {
        var x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = Color.BLACK;
        return x;
    }

    private Node<T> rotateRight(Node<T> h) {
        var x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = Color.RED;
        return x;
    }

    private void flipColors(Node<T> h) {
        h.color = Color.RED;
        h.left.color = Color.BLACK;
        h.right.color = Color.BLACK;
    }

    private Node<T> insertInternal(T key, Node<T> h) {
        if (h == null) {
            return new Node<T>(key, Color.RED);
        }

        if (key.compareTo(h.value) < 0) {
            h.left = insertInternal(key, h.left);
        } else {
            h.right = insertInternal(key, h.right);
        }

        if (isRed(h.right) && !isRed(h.left)) {
            h = rotateLeft(h);
        }
        if (isRed(h.left) && isRed(h.left.left)) {
            h = rotateRight(h);
        }
        if (isRed(h.left) && isRed(h.right)) {
            flipColors(h);
        }
        return h;
    }

    private enum Color { RED, BLACK }

    private static class Node<T extends Comparable<T>> {
        private T value;
        private Node<T> right;
        private Node<T> left;
        private Color color;

        public Node(T value, Color color) {
            this.value = value;
            this.color = color;
        }

        public Node(T value) {
            this.value = value;
        }
    }
}
