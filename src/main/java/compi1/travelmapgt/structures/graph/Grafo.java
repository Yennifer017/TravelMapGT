package compi1.travelmapgt.structures.graph;

import compi1.travelmapgt.exceptions.NoDataFoundException;
import compi1.travelmapgt.exceptions.RepeatedDataException;
import compi1.travelmapgt.structures.btree.BTree;
import java.util.ArrayList;
import java.util.LinkedList;
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
    private BTree<NodeNum<K>> nodesByNumber;
    private List<List<W>> paths;

    public Grafo() {
        nodes = new BTree<>(5);
        paths = new LinkedList<>();
        nodesByNumber = new BTree<>(5);
    }

    public void addNode(K key) throws RepeatedDataException {
        NodeGraph<K> nodeGraph = new NodeGraph<>(key, nodes.getSize());
        NodeNum<K> nodeNum = new NodeNum<>(key, nodes.getSize());
        if (!nodes.exist(nodeGraph)) {
            nodes.insert(nodeGraph);
            paths.add(new ArrayList<>());
            refillList(paths.get(paths.size()-1), paths.size());
            updateAllLists();
            //insertar segun el numero
            nodesByNumber.insert(nodeNum);
        } else {
            throw new RepeatedDataException();
        }
    }

    private void refillList(List<W> list, int number) {
        for (int i = 0; i < number; i++) {
            list.add(null);
        }
    }
    
    private void updateAllLists(){
        for (int i = 0; i < paths.size()-1; i++) {
            paths.get(i).add(null);
        }
    }

    public void addPath(K fromKey, K toKey, W weight) throws NoDataFoundException {
        NodeGraph nodeFrom = new NodeGraph(fromKey);
        NodeGraph nodeTo = new NodeGraph(toKey);
        nodeFrom.setNumber(nodes.find(nodeFrom).getNumber());
        nodeTo.setNumber(nodes.find(nodeTo).getNumber());
        paths.get(nodeFrom.getNumber()).remove(nodeTo.getNumber());
        paths.get(nodeFrom.getNumber()).add(nodeTo.getNumber(), weight);
    }
    
    public boolean isEmpty(){
        return nodes.isEmpty();
    }
    
    public K getNode(K key) throws NoDataFoundException{
        return nodes.find(new NodeGraph<>(key)).getKey();
    }
    
    public K getNode(int code) throws NoDataFoundException{
        return nodesByNumber.find(new NodeNum<>(code)).getKey();
    }
    
    protected NodeNum<K> getNodeNum(int code) throws NoDataFoundException{
        NodeNum<K> nodeNum = new NodeNum<>(code);
        return nodesByNumber.find(nodeNum);
    }
    
    protected NodeGraph<K> getNodeGraph(K key) throws NoDataFoundException{
        NodeGraph<K> nodeGraph = new NodeGraph<>(key);
        return nodes.find(nodeGraph);
    }
    
    public W getPath(K fromKey, K toKey) throws NoDataFoundException{
        NodeGraph nodeFrom = new NodeGraph(fromKey);
        NodeGraph nodeTo = new NodeGraph(toKey);
        nodeFrom.setNumber(nodes.find(nodeFrom).getNumber());
        nodeTo.setNumber(nodes.find(nodeTo).getNumber());
        return paths.get(nodeFrom.getNumber()).get(nodeTo.getNumber());
    }
    
    public W getPath(int codeFrom, int codeTo) throws IndexOutOfBoundsException{
        return paths.get(codeFrom).get(codeTo);
    }
    
    public W getPath(K fromKey, int codeTo) 
            throws NoDataFoundException, IndexOutOfBoundsException {
        NodeGraph nodeFrom = new NodeGraph(fromKey);
        nodeFrom.setNumber(nodes.find(nodeFrom).getNumber());
        return paths.get(nodeFrom.getNumber()).get(codeTo);
    }
    
    public W getPath(int codeFrom, K toKey) throws NoDataFoundException, IndexOutOfBoundsException{
        NodeGraph nodeTo = new NodeGraph(toKey);
        nodeTo.setNumber(nodes.find(nodeTo).getNumber());
        return paths.get(codeFrom).get(nodeTo.getNumber());
    }

}
