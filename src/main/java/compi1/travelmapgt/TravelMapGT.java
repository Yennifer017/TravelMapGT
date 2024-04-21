
package compi1.travelmapgt;

import compi1.travelmapgt.graphviz.BTreeGrapher;
import compi1.travelmapgt.structures.BTree;
import java.io.IOException;

/**
 *
 * @author yennifer
 */
public class TravelMapGT {

    public static void main(String[] args) {
        BTree<Integer> tree = new BTree<>(5);
        tree.insert(10);
        tree.insert(30);
        tree.insert(5);
        tree.insert(6);
        tree.insert(8);
        tree.insert(20);
        tree.insert(2);
        tree.insert(40);
        tree.insert(50);
        tree.insert(11);
        tree.insert(23);
        tree.insert(41);
        tree.insert(100);
        tree.insert(102);
        tree.insert(405);
        tree.insert(300);
        tree.insert(200);
        tree.insert(210);
        tree.insert(305);
        tree.insert(01);
        tree.insert(230);
        tree.insert(400);
        tree.insert(401);
        /*tree.insert(500);
        tree.insert(501);
        tree.insert(502);
        tree.insert(510);
        tree.insert(505);
        tree.insert(520);
        tree.insert(530);
        tree.insert(540);
        tree.insert(550);
        tree.insert(560);*/

        System.out.println("Se ha terminado de insertar");
        BTreeGrapher bTreeGrapher = new BTreeGrapher();
        try {
            bTreeGrapher.graph("../", "graphviz", tree);
            System.out.println("Se ha terminado de generar el grafico");
        } catch (IOException ex) {
            System.out.println("No se pudo generar el archivo");
            System.out.println(ex);
            ex.printStackTrace();
        }
    }
}
