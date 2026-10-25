package interpreter.model.adt;

import interpreter.exceptions.ADTException;

public interface IDictionary<K,V> {
    void put(K x, V y);
    void update(K x, V y) throws ADTException;
    V lookup(K x) throws ADTException;
    boolean isDefined(K x);
}
