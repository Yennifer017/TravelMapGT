package compi1.travelmapgt.graphviz;

import compi1.travelmapgt.models.LocationInfo;
import compi1.travelmapgt.models.PathInfo;
import compi1.travelmapgt.structures.btree.BTree;
import compi1.travelmapgt.structures.btree.BTreePage;
import compi1.travelmapgt.structures.graph.Grafo;
import compi1.travelmapgt.structures.graph.NodeGraph;
import java.io.IOException;

/**
 *
 * @author yennifer
 */
public class GraphGrapher {

    private final static String ENTER = "\n";
    private final static String IDENTATION = "    ";
    private final static String COLOR_SELECTED = "\"#4ffffc\"";

    public static final int GAS_WEIGHT = 1, WALKING_WEIGHT = 2, DISTANCE_WEIGHT = 3, GAS_DISTACE_WEIGHT = 4,
            WALKING_DISTANCE_WEIGHT = 5, DISTANCE_TIME_W = 6, DISTANCE_TIME_TRAFIC_W = 7, NOTHING = -1;

    private ImageGenerator generator;

    public GraphGrapher() {
        this.generator = new ImageGenerator();
    }

    public void graph(String finalPath, String nameFile, Grafo<LocationInfo, PathInfo> grafo) throws IOException {
        generator.generateSmallImg(finalPath, nameFile, getCode(grafo, NOTHING));
    }

    public void graphWithWeight(String finalPath, String nameFile, Grafo<LocationInfo, PathInfo> grafo,
            int typeWeight) throws IOException {
        generator.generateSmallImg(finalPath, nameFile, getCode(grafo, typeWeight));
    }

    private String getCode(Grafo<LocationInfo, PathInfo> grafo, int typeWeight) {
        String code = "digraph {" + ENTER;
        code += IDENTATION + "graph [ratio=1]" + ENTER;
        code += IDENTATION + "node [style=filled]" + ENTER;
        code += IDENTATION + "rankdir=LR;" + ENTER;
        code += defineNodes(grafo);
        code += generateConnection(grafo, typeWeight);
        code += "}";
        return code;
    }

    private String defineNodes(Grafo<LocationInfo, PathInfo> grafo) {
        BTree<NodeGraph<LocationInfo>> nodesTree = grafo.getNodes();
        return graphTreeNode(nodesTree.getRaiz());
    }

    private String graphTreeNode(BTreePage<NodeGraph<LocationInfo>> page) {
        if (page == null || page.isEmpty()) {
            return "";
        }
        String code = "";
        for (int i = 0; i < page.getPunteros().size(); i++) { //graficar sus punteros
            BTreePage subPage = (BTreePage) page.getPunteros().get(i);
            code += graphTreeNode(subPage);
        }

        for (NodeGraph<LocationInfo> node : page.getNodes()) { //graficar sus nodos
            code += IDENTATION + node.getNumber() + "[shape=circle label=\"" + node.getNumber()
                    + "-" + node.getKey().getKeyLocation() + "\"";
            if (node.getKey().isActive()) {
                code += " fillcolor=" + COLOR_SELECTED;
            }
            code += "]" + ENTER;
        }

        return code;
    }

    private String generateConnection(Grafo<LocationInfo, PathInfo> grafo, int typeWeight) {
        int limit = grafo.getNodes().getSize();
        if (grafo.isEmpty()) {
            return "";
        } else {
            String code = "";

            for (int i = 0; i < limit; i++) {
                if (typeWeight < 0) { //sin pesos
                    code += IDENTATION + i + "-> {";
                    for (int j = 0; j < limit; j++) {
                        if (grafo.getPaths().get(i).get(j) != null) {
                            code += j + " ";
                        }
                    }
                    code += "}" + ENTER;
                } else { //con pesos
                    for (int j = 0; j < limit; j++) {
                        if (grafo.getPaths().get(i).get(j) != null) {
                            code += IDENTATION + i + "->" + j;
                            code += getWeightLabel(typeWeight, grafo.getPaths().get(i).get(j));
                            code += ENTER;
                        }
                    }
                }
            }

            return code;
        }
    }

    private String getWeightLabel(int type, PathInfo pathInfo) {
        String code = " [label=\"";
        switch (type) {
            case GAS_WEIGHT ->
                code += pathInfo.getCostGas();
            case WALKING_WEIGHT ->
                code += pathInfo.getCostWalking();
            case DISTANCE_WEIGHT ->
                code += pathInfo.getDistance();
            case GAS_DISTACE_WEIGHT ->
                code += "g" + pathInfo.getCostGas() + "\nd" + pathInfo.getDistance();
            case WALKING_DISTANCE_WEIGHT ->
                code += "f" + pathInfo.getCostWalking() + "\nd" + pathInfo.getDistance();
            case DISTANCE_TIME_W ->
                code += "d" + pathInfo.getDistance() + "\nt" + pathInfo.getAverageTimeWalking();
            case DISTANCE_TIME_TRAFIC_W ->
                code += "d" + pathInfo.getDistance() + "\nt" + pathInfo.getAverageTimeWalking()
                        + "\n p" + pathInfo.getProbabilityTrafic();
            default -> {
                return "";
            }
        }
        return code + "\"]";
    }

}
