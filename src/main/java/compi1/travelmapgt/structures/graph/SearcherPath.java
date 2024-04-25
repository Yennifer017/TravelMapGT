package compi1.travelmapgt.structures.graph;

import compi1.travelmapgt.exceptions.NoDataFoundException;
import compi1.travelmapgt.models.LocationInfo;
import compi1.travelmapgt.models.PathInfo;
import compi1.travelmapgt.models.Recorrido;
import compi1.travelmapgt.structures.btree.BTree;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yennifer
 */
public class SearcherPath {

    public BTree<Recorrido> findPath(Grafo<LocationInfo, PathInfo> graph, LocationInfo start, LocationInfo finish) 
            throws NoDataFoundException {
        BTree<Recorrido> recorridos = new BTree<>(5);
        List<Integer> nodesAlreadyPass = new ArrayList<>();
        NodeNum<LocationInfo> startNode = graph.getNodeGraph(start).convertToNodeNum();
        nodesAlreadyPass.add(startNode.getNumber());
        savePath(graph, 
                startNode, 
                nodesAlreadyPass, 
                graph.getNodeGraph(finish), 
                recorridos
        );
        return recorridos;
    }

    private void savePath(Grafo<LocationInfo, PathInfo> grafo, NodeNum<LocationInfo> currentNode, 
            List<Integer> nodesAlreadyPass, NodeGraph<LocationInfo> destinity, BTree<Recorrido> recorridos) 
            throws NoDataFoundException {
        for (int i = 0; i < grafo.getNodes().getSize(); i++) {
            if(grafo.getPath(currentNode.getKey(), i) != null){
                if(grafo.getNodeNum(i).getNumber() == destinity.getNumber()){ //si es el destino
                    List<Integer> correctPath = new ArrayList<>(nodesAlreadyPass);
                    correctPath.add(i);
                    recorridos.insert(new Recorrido(correctPath, recorridos.getSize(), grafo));
                } else {
                    if(!exist(nodesAlreadyPass, i)){
                        List<Integer> newPath = new ArrayList<>(nodesAlreadyPass);
                        newPath.add(i);
                        savePath(grafo, grafo.getNodeNum(i), newPath, destinity, recorridos);
                    }
                }
            }
        }
    }
    
    private boolean exist(List<Integer> nodesAlreadyPass, int current){
        if(nodesAlreadyPass.isEmpty()){
            return false;
        }
        for (Integer node : nodesAlreadyPass) {
            if(node == current){
                return true;
            }
        }
        return false;
    }

}
