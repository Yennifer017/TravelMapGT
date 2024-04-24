
package compi1.travelmapgt.structures.graph;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author yennifer
 */
@Getter
@Setter
public class NodeNum<K> implements Comparable<NodeNum<K>>{
    private K key;
    private int number;

    public NodeNum(K key, int number) {
        this.key = key;
        this.number = number;
    }

    public NodeNum(K key) {
        this.key = key;
        this.number = -1;
    }
    
    public NodeNum(int number){
        this.number = number;
    }

    @Override
    public int compareTo(NodeNum<K> t) {
        return Integer.compare(this.number, t.number);
    }

}
