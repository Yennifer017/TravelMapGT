package compi1.travelmapgt.structures;

import compi1.travelmapgt.exceptions.NoDataFoundException;
import compi1.travelmapgt.exceptions.RepeatedDataException;

/**
 *
 * @author yennifer
 * @param <K>
 * @param <T>
 */
public class Graph<K extends Comparable<K>, T extends Comparable<T>> {

    private final int ORDER_TREE = 5;

    private BTree<K> nodes;
    private BTree<GraphArista<K, T>> aristas;

    public Graph() {
        nodes = new BTree<>(ORDER_TREE);
        aristas = new BTree<>(ORDER_TREE);
    }

    public K getNode(K type) throws NoDataFoundException {
        return this.nodes.find(type);
    }
    
    public boolean isEmpty() {
        return this.nodes.isEmpty();
    }

    public void addNode(K node) throws RepeatedDataException {
        if (!nodes.exist(node)) {
            nodes.insert(node);
            aristas.insert(new GraphArista<>(node));
        } else {
            throw new RepeatedDataException();
        }
    }

    public void addArista(K from, K to, T contentVertex) throws NoDataFoundException, RepeatedDataException {
        if(nodes.exist(to) && nodes.exist(from)){
            aristas.find(new GraphArista<>(from))
                    .addDestinity(new WeightGraph<>(to, contentVertex));
        }else {
            throw new NoDataFoundException();
        }
    }
    
    public BTree<GraphArista<K, T>> getAristas(){
        return this.aristas;
    }
    
    public BTree<K> getNodes(){
        return nodes;
    }
    
}
