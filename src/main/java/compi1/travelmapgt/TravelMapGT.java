
package compi1.travelmapgt;

import compi1.travelmapgt.graphviz.GraphGrapher;
import compi1.travelmapgt.structures.Graph;

/**
 *
 * @author yennifer
 */
public class TravelMapGT {

    public static void main(String[] args) {
        Graph<String, Integer> grafo = new Graph<>();
        GraphGrapher<String, Integer> grapher = new GraphGrapher<>();
        try {
            grafo.addNode("Nodo1");
            grafo.addNode("Nodo2");
            grafo.addNode("Nodo3");
            grafo.addNode("Nodo4");
            grafo.addNode("Nodo5");
            grafo.addNode("Nodo6");
            grafo.addNode("Nodo7");
            grafo.addNode("Nodo8");
            grafo.addNode("Nodo9");
            grafo.addNode("Nodo10");
            System.out.println("Se terminaron de agregar los nodos");
            grafo.addArista("Nodo10", "Nodo9", 1);
            grafo.addArista("Nodo8", "Nodo9", 2);
            grafo.addArista("Nodo10", "Nodo8", 2);
            grafo.addArista("Nodo8", "Nodo7", 2);
            grafo.addArista("Nodo7", "Nodo6", 1);
            grafo.addArista("Nodo6", "Nodo7", 2);
            grafo.addArista("Nodo7", "Nodo5", 1);
            grafo.addArista("Nodo5", "Nodo6", 2);
            grafo.addArista("Nodo10", "Nodo6", 2);
            grafo.addArista("Nodo6", "Nodo4", 2);
            grafo.addArista("Nodo4", "Nodo3", 1);
            grafo.addArista("Nodo2", "Nodo3", 3);
            grafo.addArista("Nodo3", "Nodo2", 1);
            grafo.addArista("Nodo1", "Nodo2", 2);
            grafo.addArista("Nodo1", "Nodo3", 2);
            grafo.addArista("Nodo10", "Nodo3", 2);
            System.out.println("se terminaron de ingresar las aristas");
            grapher.graph("../", "grafos", grafo);
            System.out.println("Se exporto correctamente");
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
