package compi1.travelmapgt.structures.graph;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author yennifer
 * @param <K>
 */
@Getter
@Setter
public class NodeGraph<K extends Comparable<K>> implements Comparable<NodeGraph<K>> {

    private K key;
    private int number;

    public NodeGraph(K key, int number) {
        this.key = key;
        this.number = number;
    }

    public NodeGraph(K key) {
        this.key = key;
        this.number = -1;
    }

    @Override
    public int compareTo(NodeGraph<K> t) {
        return this.key.compareTo(t.key);
    }
    
    @Override
    public String toString(){
        return this.key.toString();
    }
}
