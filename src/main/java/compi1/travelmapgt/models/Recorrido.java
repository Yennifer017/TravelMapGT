package compi1.travelmapgt.models;

import compi1.travelmapgt.MainMenu;
import compi1.travelmapgt.exceptions.NoDataFoundException;
import compi1.travelmapgt.exceptions.NoPathException;
import compi1.travelmapgt.structures.graph.Grafo;
import compi1.travelmapgt.util.Clock;
import java.time.LocalTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author yennifer
 */
@Getter
@Setter
@NoArgsConstructor
public class Recorrido implements Comparable<Recorrido> {

    private List<Integer> recorrido;
    private int code;
    private Grafo<LocationInfo, PathInfo> graph;
    private float weight;

    public Recorrido(List<Integer> recorrido, int code, Grafo<LocationInfo, PathInfo> graph) {
        this.recorrido = recorrido;
        this.code = code;
        this.graph = graph;
    }

    public void findWeight(FilterSpecifications filters, Clock clock) throws NoPathException {
        if (filters.isExtendedPath()) {
            findWeightInExtendedPath(filters);
        } else {
            findWeighInDirigitePath(filters, clock);
        }
    }

    private void findWeightInExtendedPath(FilterSpecifications filters) throws NoPathException {
        if (this.graph.isEmpty() || recorrido.isEmpty()) {
            throw new NoPathException();
        }
        for (int i = 0; i < this.recorrido.size(); i++) {
            if (i != this.recorrido.size() - 1) { //diferente al penultimo
                PathInfo pathInfo;
                try {
                    pathInfo = graph.getPath(recorrido.get(i), recorrido.get(i + 1));
                    if (pathInfo == null) {
                        pathInfo = graph.getPath(recorrido.get(i + 1), recorrido.get(i));
                    }
                    if (pathInfo == null) {
                        throw new NoPathException();
                    }
                } catch (IndexOutOfBoundsException e) {
                    throw new NoPathException();
                }
                switch (filters.getTypeFilter()) {
                    case MainMenu.RESOURCES_FILTER ->
                        this.weight += pathInfo.getCostWalking();
                    case MainMenu.DISTANCE_FILTER ->
                        this.weight += pathInfo.getDistance();
                    case MainMenu.RESOURCES_DISTANCE_FILTER ->
                        this.weight += pathInfo.getDistance() / pathInfo.getCostWalking();
                    case MainMenu.ALL_FILTER ->
                        this.weight += pathInfo.getDistance() / pathInfo.getAverageTimeWalking();
                    default ->
                        throw new AssertionError();
                }
            }
        }
    }

    private void findWeighInDirigitePath(FilterSpecifications filters, Clock clock) throws NoPathException {
        if (this.graph.isEmpty() || recorrido.isEmpty()) {
            throw new NoPathException();
        }
        LocalTime currentTime = clock.getCurrentTime();
        for (int i = 0; i < this.recorrido.size(); i++) {
            if (i != this.recorrido.size() - 1) { //diferente al penultimo
                PathInfo pathInfo = graph.getPath(recorrido.get(i), recorrido.get(i + 1));
                switch (filters.getTypeFilter()) {
                    case MainMenu.RESOURCES_FILTER ->
                        this.weight += pathInfo.getCostGas();
                    case MainMenu.DISTANCE_FILTER ->
                        this.weight += pathInfo.getDistance();
                    case MainMenu.RESOURCES_DISTANCE_FILTER ->
                        this.weight += pathInfo.getDistance() / pathInfo.getCostGas();
                    case MainMenu.ALL_FILTER -> {
                        int traficProbability = clock.calculateTraficProbability(currentTime, pathInfo);
                        this.weight += pathInfo.getDistance() / 
                                (pathInfo.getAverageTimeCar() * (1 + (traficProbability/100)));
                        if (clock.isActive()) {
                            currentTime = currentTime.plusMinutes(pathInfo.getAverageTimeCar());
                        }
                    }
                    default ->
                        throw new AssertionError();
                }
            }
        }
    }

    public void showInGraph(boolean extendedPath) {
        modifyRecorrido(true, extendedPath);
    }

    public void hiddeInGraph(boolean extendedPath) {
        modifyRecorrido(false, extendedPath);
    }

    private void modifyRecorrido(boolean active, boolean extendedPath) {
        for (int i = 0; i < recorrido.size(); i++) {
            try {
                int current = recorrido.get(i);
                this.graph.getNode(current).setActive(active);
                if (i != recorrido.size() - 1) {
                    int next = recorrido.get(i + 1);
                    try {
                        this.graph.getPath(current, next).setActive(active);
                    } catch (NullPointerException e) {
                    }
                    if (extendedPath) {
                        try {
                            this.graph.getPath(next, current).setActive(active);
                        } catch (NullPointerException e) {
                        }
                    }
                }
            } catch (NoDataFoundException e) {
                /*controlado*/
            }
        }
    }

    @Override
    public int compareTo(Recorrido t) {
        return Integer.compare(this.code, t.code);
    }

    @Override
    public String toString() {
        String label = "";
        for (int i = 0; i < recorrido.size(); i++) {
            try {
                label += graph.getNode(recorrido.get(i)).getKeyLocation();
                if (i != recorrido.size() - 1) {
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
