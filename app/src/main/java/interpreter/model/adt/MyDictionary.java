package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K,V> implements IDictionary<K,V> {
    private final Map<K,V> dictionary =  new HashMap<K,V>();

    @Override
    public void put(K x, V y) {
        dictionary.put(x, y);
    }

    @Override
    public V lookup(K x) throws ADTException {
        if(!dictionary.containsKey(x)) throw new ADTException("the key isn't in the dictionary");
        return dictionary.get(x);
    }

    @Override
    public void update(K x, V y) throws ADTException {
        if(!dictionary.containsKey(x)) throw new ADTException("the key isn't in the dictionary");
        dictionary.put(x, y);
    }

    @Override
    public boolean isDefined(K x){
        return dictionary.containsKey(x);
    }

    @Override
    public String toString(){
        return dictionary.toString();
    }
}

