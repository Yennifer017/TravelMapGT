package compi1.travelmapgt.structures;

import compi1.travelmapgt.exceptions.RepeatedDataException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author yennifer
 * @param <K>
 * @param <T>
 */
public class GraphArista<K extends Comparable<K>, T extends Comparable<T>> implements Comparable<GraphArista<K,T>> {

    private K keyFrom;
    private List<WeightGraph<K, T>> weights;

    public GraphArista(K keyFrom) {
        this.keyFrom = keyFrom;
        weights = new ArrayList<>();
    }
    
    public void addDestinity(WeightGraph<K, T> weightGraph) throws RepeatedDataException{
        Collections.sort(weights);
        int find = Collections.binarySearch(weights, weightGraph);
        if(find < 0){
            this.weights.add(weightGraph);
        } else {
            throw new RepeatedDataException();
        }
    }
    
    public List<WeightGraph<K, T>> getIdsTo(){
        return this.weights;
    }

    @Override
    public int compareTo(GraphArista<K, T> t) {
        return keyFrom.compareTo(t.keyFrom);
    }
    
    public K getKeyFrom(){
        return this.keyFrom;
    }

    
}
