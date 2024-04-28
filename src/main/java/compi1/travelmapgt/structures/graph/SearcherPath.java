package compi1.travelmapgt.structures.graph;

import compi1.travelmapgt.exceptions.NoDataFoundException;
import compi1.travelmapgt.exceptions.NoPathException;
import compi1.travelmapgt.models.FilterSpecifications;
import compi1.travelmapgt.models.LocationInfo;
import compi1.travelmapgt.models.PathInfo;
import compi1.travelmapgt.models.Recorrido;
import compi1.travelmapgt.structures.btree.BTree;
import compi1.travelmapgt.structures.btree.BTreePage;
import compi1.travelmapgt.util.Clock;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yennifer
 */
public class SearcherPath {

    public BTree<Recorrido> findAllPaths(Grafo<LocationInfo, PathInfo> graph, LocationInfo start,
            LocationInfo finish, boolean extendedPath)
            throws NoDataFoundException {
        BTree<Recorrido> recorridos = new BTree<>(5);
        List<Integer> nodesAlreadyPass = new ArrayList<>();
        NodeNum<LocationInfo> startNode = graph.getNodeGraph(start).convertToNodeNum();
        nodesAlreadyPass.add(startNode.getNumber());
        if (!extendedPath) {
            savePath(graph,
                    startNode,
                    nodesAlreadyPass,
                    graph.getNodeGraph(finish),
                    recorridos
            );
        } else {
            saveExtendedPath(graph,
                    startNode,
                    nodesAlreadyPass,
                    graph.getNodeGraph(finish),
                    recorridos
            );
        }
        return recorridos;
    }

    private void savePath(Grafo<LocationInfo, PathInfo> grafo, NodeNum<LocationInfo> currentNode,
            List<Integer> nodesAlreadyPass, NodeGraph<LocationInfo> destinity, BTree<Recorrido> recorridos)
            throws NoDataFoundException {
        for (int i = 0; i < grafo.getNodes().getSize(); i++) {
            if (grafo.getPath(currentNode.getKey(), i) != null) {
                if (grafo.getNodeNum(i).getNumber() == destinity.getNumber()) { //si es el destino
                    List<Integer> correctPath = new ArrayList<>(nodesAlreadyPass);
                    correctPath.add(i);
                    recorridos.insert(new Recorrido(correctPath, recorridos.getSize(), grafo));
                } else {
                    if (!exist(nodesAlreadyPass, i)) {
                        List<Integer> newPath = new ArrayList<>(nodesAlreadyPass);
                        newPath.add(i);
                        savePath(grafo, grafo.getNodeNum(i), newPath, destinity, recorridos);
                    }
                }
            }
        }
    }

    private void saveExtendedPath(Grafo<LocationInfo, PathInfo> grafo, NodeNum<LocationInfo> currentNode,
            List<Integer> nodesAlreadyPass, NodeGraph<LocationInfo> destinity, BTree<Recorrido> recorridos)
            throws NoDataFoundException {
        for (int i = 0; i < grafo.getNodes().getSize(); i++) {
            if (grafo.getPath(currentNode.getKey(), i) != null || grafo.getPath(i, currentNode.getKey()) != null) {
                if (grafo.getNodeNum(i).getNumber() == destinity.getNumber()) { //si es el destino
                    List<Integer> correctPath = new ArrayList<>(nodesAlreadyPass);
                    correctPath.add(i);
                    recorridos.insert(new Recorrido(correctPath, recorridos.getSize(), grafo));
                } else {
                    if (!exist(nodesAlreadyPass, i)) {
                        List<Integer> newPath = new ArrayList<>(nodesAlreadyPass);
                        newPath.add(i);
                        saveExtendedPath(grafo, grafo.getNodeNum(i), newPath, destinity, recorridos);
                    }
                }
            }
        }
    }

    private boolean exist(List<Integer> nodesAlreadyPass, int current) {
        if (nodesAlreadyPass.isEmpty()) {
            return false;
        }
        for (Integer node : nodesAlreadyPass) {
            if (node == current) {
                return true;
            }
        }
        return false;
    }

    public Recorrido findPath(Grafo<LocationInfo, PathInfo> grafo, BTree<Recorrido> recorridos,
            FilterSpecifications filters, Clock clock) throws NoPathException {
        if (recorridos.isEmpty() || grafo.isEmpty()) {
            throw new NoPathException();
        }
        Recorrido recorrido = null;
        recorrido = evaluateWeight(recorridos.getRaiz(), recorrido, filters, clock);
        return recorrido;
    }


    private Recorrido evaluateWeight(BTreePage<Recorrido> page, Recorrido returnedPath, FilterSpecifications filters, 
            Clock clock) throws NoPathException {
        if (page != null && !page.isEmpty()) {
            
            for (Recorrido recorrido : page.getNodes()) { //encuentra los pesos de los nodos
                recorrido.findWeight(filters, clock);
                
                if(returnedPath ==  null){
                    returnedPath = recorrido;
                } else if(recorrido.getWeight() > returnedPath.getWeight() && !filters.isBestPath()){
                    returnedPath = recorrido;
                } else if(recorrido.getWeight() < returnedPath.getWeight() && filters.isBestPath()){
                    returnedPath = recorrido;
                }
            }
            
            for (int i = 0; i < page.getPunteros().size(); i++) { //evalua los punteros
                BTreePage<Recorrido> subPage = (BTreePage) page.getPunteros().get(i);
                returnedPath = evaluateWeight(subPage, returnedPath, filters, clock);
            }
        }
        return returnedPath;
    }

}
