
package compi1.travelmapgt.models;

import compi1.travelmapgt.exceptions.NoDataFoundException;
import compi1.travelmapgt.structures.graph.Grafo;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author yennifer
 */
@Getter @Setter
public class Recorrido implements Comparable<Recorrido>{
    private List<Integer> recorrido;
    private int code;
    private Grafo<LocationInfo, PathInfo> graph;
    private int weight;

    public Recorrido(List<Integer> recorrido, int code, Grafo<LocationInfo, PathInfo> graph) {
        this.recorrido = recorrido;
        this.code = code;
        this.graph = graph;
    }
    

    @Override
    public int compareTo(Recorrido t) {
        return Integer.compare(this.code, t.code);
    }
    
    @Override
    public String toString(){
        String label = "";
        for (int i = 0; i < recorrido.size(); i++) {
            try {
                label += graph.getNode(recorrido.get(i)).getKeyLocation();
                if(i != recorrido.size() -1){
                    label += "-";
                }
            } catch (NoDataFoundException ex) {
                System.out.println("Desde la muestra del recorrido");
                System.out.println(ex);
                /*no deberia pasar*/
            }
        }
        return label;
    }
}
