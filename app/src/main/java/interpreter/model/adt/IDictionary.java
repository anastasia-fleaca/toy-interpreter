package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.Map;

public interface IDictionary<K,V> {
    void put(K x, V y);
    void update(K x, V y) throws ADTException;
    V lookup(K x) throws ADTException;
    boolean isDefined(K x);
    void remove(K key) throws ADTException;
    Map<K, V> toMap();
}
