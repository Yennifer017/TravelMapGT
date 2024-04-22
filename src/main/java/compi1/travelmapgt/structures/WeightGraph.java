
package compi1.travelmapgt.structures;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author yennifer
 * @param <T>
 */
@Getter @Setter 
public class WeightGraph<K extends Comparable<K>, T > implements Comparable<WeightGraph<K,T>> {
    private K idToNode;
    private T content;
    
    public WeightGraph(K keyTo, T content){
        this.idToNode = keyTo;
        this.content = content;
    }

    @Override
    public int compareTo(WeightGraph<K,T> t) {
        return this.idToNode.compareTo(t.idToNode);
    }
}
