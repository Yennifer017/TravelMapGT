package compi1.travelmapgt.graphviz;

import compi1.travelmapgt.structures.BTree;
import compi1.travelmapgt.structures.BTreePage;
import compi1.travelmapgt.structures.Graph;
import compi1.travelmapgt.structures.GraphArista;
import compi1.travelmapgt.structures.WeightGraph;
import java.io.IOException;
import java.util.LinkedList;

/**
 *
 * @author yennifer
 * @param <K>
 * @param <T>
 */
public class GraphGrapher<K extends Comparable<K>, T extends Comparable<T>> {

    private final static String ENTER = "\n";
    private final static String IDENTATION = "    ";

    private ImageGenerator generator;

    public GraphGrapher() {
        this.generator = new ImageGenerator();
    }

    public void graph(String finalPath, String nameFile, Graph graph) throws IOException {
        generator.generateImg(finalPath, nameFile, getCode(graph));
    }

    private String getCode(Graph graph) {
        String code = "digraph {" + ENTER;
        code += IDENTATION + "rankdir=LR;" + ENTER;
        code += graficatePath(graph.getAristas().getRaiz());
        code += "}";
        return code;
    }

    private String graficatePath(BTreePage<GraphArista<K, T>> pageAristas) {
        if (!pageAristas.isEmpty()) {
            String code = "";
            LinkedList<GraphArista<K, T>> list = pageAristas.getNodes();
            if (!pageAristas.getPunteros().isEmpty()) {
                for (BTreePage<GraphArista<K, T>> puntero : pageAristas.getPunteros()) {
                    code +=  graficatePath(puntero);
                }
            }
            for (GraphArista<K, T> graphVertex : list) {
                for (WeightGraph<K, T> idTo : graphVertex.getIdsTo()) {
                    String key = idTo.getIdToNode().toString().replace(" ", "");
                    code += IDENTATION + graphVertex.getKeyFrom().toString().replace(" ", "")
                            + "->" + key + ENTER;
                }
            }
            return code;
        } else {
            return "";
        }
    }
    
    public void graphConditional(String finalPath, String nameFile, Graph<String, String> graph) 
            throws IOException {
        String code = "digraph {" + ENTER;
        code += IDENTATION + "rankdir=LR;" + ENTER;
        code += IDENTATION + declarateVariables(graph.getNodes()) + ENTER;
        code += graficatePathConditional(graph.getAristas().getRaiz());
        code += "}";
        generator.generateImg(finalPath, nameFile, code);
    }
    
    private String declarateVariables(BTree<String> nodos){
        return "";
    }
    
    private String graficatePathConditional(BTreePage<GraphArista<String, String>> pageAristas) {
        //declarar las aritas con colores
        if (!pageAristas.isEmpty()) {
            String code = "";
            LinkedList<GraphArista<String, String>> list = pageAristas.getNodes();
            if (!pageAristas.getPunteros().isEmpty()) {
                for (BTreePage<GraphArista<String, String>> puntero : pageAristas.getPunteros()) {
                    code +=  graficatePathConditional(puntero);
                }
            }
            for (GraphArista<String, String> graphVertex : list) {
                for (WeightGraph<String, String> idTo : graphVertex.getIdsTo()) {
                    String key = idTo.getIdToNode().replace(" ", "");
                    code += IDENTATION + graphVertex.getKeyFrom().replace(" ", "")
                            + "->" + key + ENTER;
                }
            }
            return code;
        } else {
            return "";
        }
    }

}
