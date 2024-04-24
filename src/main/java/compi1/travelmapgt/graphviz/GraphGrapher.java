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
public class GraphGrapher{
    
    private final static String ENTER = "\n";
    private final static String IDENTATION = "    ";
    private final static String COLOR_SELECTED = "\"#4ffffc\"";
    
    private ImageGenerator generator;

    public GraphGrapher() {
        this.generator = new ImageGenerator();
}

    public void graph(String finalPath, String nameFile, Grafo<LocationInfo, PathInfo> grafo) throws IOException {
        generator.generateImg(finalPath, nameFile, getCode(grafo));
    }

    private String getCode(Grafo<LocationInfo, PathInfo> grafo){
        String code = "digraph {" + ENTER;
        code += IDENTATION + "graph [size=\"500,500\", ratio=fill];" + ENTER;
        code += IDENTATION + "node [style=filled]" + ENTER;
        code += IDENTATION + "rankdir=LR;" + ENTER;
        code += defineNodes(grafo);
        code += generateConnection(grafo);  
        code += "}";
        return code;
    }
    
    private String defineNodes(Grafo<LocationInfo, PathInfo> grafo){
        BTree<NodeGraph<LocationInfo>> nodesTree = grafo.getNodes();
        return graphTreeNode(nodesTree.getRaiz());
    }
    
    private String graphTreeNode(BTreePage<NodeGraph<LocationInfo>> page){
        if(page == null || page.isEmpty()){
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
            if(node.getKey().isActive()){
                code += " fillcolor=" + COLOR_SELECTED;
            }
            code += "]" + ENTER;
        }   
        
        return code;
    }
    
    private String generateConnection(Grafo<LocationInfo, PathInfo> grafo){
        int limit = grafo.getNodes().getSize();
        if(grafo.isEmpty()){
            return "";
        } else {
            String code = "";
            for (int i = 0; i < limit; i++) {
                code += IDENTATION + i + "-> {";
                for (int j = 0; j < limit; j++) {
                    if(grafo.getPaths().get(i).get(j) != null){
                        code += j + " ";
                    }
                }
                code += "}" + ENTER;
            }
            return code;
        }
    }

}
