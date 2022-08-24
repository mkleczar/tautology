package org.example.tautology.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class ListNode<T> {
    private List<T> list;
    private int index;

    public ListNode(List<T> list, int index) {
        this.list = list;
        this.index = index;
    }

    public List<T> getListBefore() {
        return new ArrayList<>(list.subList(0, index));
    }
    public List<T> getListAfter() {
        return new ArrayList<>(list.subList(index + 1, list.size()));
    }
    public T getValue() {
        return list.get(index);
    }

    public ListNode<T> nextNode() {
        // TODO: check indices out of bounds
        return new ListNode<>(list, index + 1);
    }

    public ListNode<T> prevNode() {
        // TODO: check indices out of bounds
        return new ListNode<>(list, index - 1);
    }

    public static <T> ListNode<T> findMaxByComparator(List<T> list, Comparator<T> comparator) {
        int priorityIndex = 0;
        for (int t = 0; t < list.size(); ++t) {
            if (comparator.compare(list.get(t), list.get(priorityIndex)) > 0) {
                priorityIndex = t;
            }
        }
        return new ListNode<>(list, priorityIndex);
    }

    public static <T> Optional<ListNode<T>> findLast(List<T> list, Predicate<T> predicate) {
        for (int i = list.size() - 1; i >= 0; --i) {
            if (predicate.test(list.get(i))) {
                return Optional.of(new ListNode<>(list, i));
            }
        }
        return Optional.empty();
    }
}
