package ru.pa4ok.library.util;

public class Tuple<S,V>
{
    private S first;
    private V second;

    public Tuple(S first, V second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "Tuple{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }

    public S getFirst() {
        return first;
    }

    public void setFirst(S first) {
        this.first = first;
    }

    public V getSecond() {
        return second;
    }

    public void setSecond(V second) {
        this.second = second;
    }
}
