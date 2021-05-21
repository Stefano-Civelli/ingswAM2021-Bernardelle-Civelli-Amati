package it.polimi.ingsw.utility;

import java.util.Objects;

public class Pair<K, V> {
    private final K key;
    private final V value;


    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public Pair(Pair<K, V> pair){
        this.key = pair.getKey();
        this.value = pair.getValue();
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "(" +
                (this.key != null ? this.key.toString() : "null") +
                ", " +
                (this.value != null ? this.value.toString() : "null") +
                ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.key, this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(this.key, pair.getKey()) && Objects.equals(this.value, pair.getValue());
    }
}