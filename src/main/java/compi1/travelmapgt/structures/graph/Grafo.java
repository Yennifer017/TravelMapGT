package compi1.travelmapgt.structures.graph;

import compi1.travelmapgt.exceptions.NoDataFoundException;
import compi1.travelmapgt.exceptions.RepeatedDataException;
import compi1.travelmapgt.structures.btree.BTree;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 *
 * @author yennifer
 * @param <K> key for the node
 * @param <W> weight for the paths node
 */
@Getter
public class Grafo<K extends Comparable<K>, W> {

    private BTree<NodeGraph<K>> nodes;
    private List<List<W>> paths;

    public Grafo() {
        nodes = new BTree<>(5);
        paths = new ArrayList<>();
    }

    public void addNode(K key) throws RepeatedDataException {
        NodeGraph<K> nodeGraph = new NodeGraph<>(key, nodes.getSize());
        if (!nodes.exist(nodeGraph)) {
            nodes.insert(nodeGraph);
            paths.add(new ArrayList<>());
            refillList(paths.get(paths.size()-1), paths.size());
        } else {
            throw new RepeatedDataException();
        }
    }

    private void refillList(List<W> list, int number) {
        for (int i = 0; i < number; i++) {
            list.add(null);
        }
    }

    public void addPath(K fromKey, K toKey, W weight) throws NoDataFoundException {
        NodeGraph nodeFrom = new NodeGraph(fromKey);
        NodeGraph nodeTo = new NodeGraph(toKey);
        nodeFrom.setNumber(nodes.find(nodeFrom).getNumber());
        nodeTo.setNumber(nodes.find(nodeTo).getNumber());
        paths.get(nodeFrom.getNumber()).add(nodeTo.getNumber(), weight);
    }
    
    public boolean isEmpty(){
        return nodes.isEmpty();
    }
    
    public K getNode(K key) throws NoDataFoundException{
        return nodes.find(new NodeGraph<>(key)).getKey();
    }
    
    public W getPath(K fromKey, K toKey) throws NoDataFoundException{
        NodeGraph nodeFrom = new NodeGraph(fromKey);
        NodeGraph nodeTo = new NodeGraph(toKey);
        nodeFrom.setNumber(nodes.find(nodeFrom).getNumber());
        nodeTo.setNumber(nodes.find(nodeTo).getNumber());
        return paths.get(nodeFrom.getNumber()).get(nodeTo.getNumber());
    }

}
