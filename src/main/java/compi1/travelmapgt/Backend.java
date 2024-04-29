package compi1.travelmapgt;

import compi1.travelmapgt.exceptions.NoDataFoundException;
import compi1.travelmapgt.exceptions.NoPathException;
import compi1.travelmapgt.files.DataCollector;
import compi1.travelmapgt.files.FilesUtil;
import compi1.travelmapgt.graphviz.BTreeGrapher;
import compi1.travelmapgt.graphviz.GraphGrapher;
import compi1.travelmapgt.models.FilterSpecifications;
import compi1.travelmapgt.models.KeyMove;
import compi1.travelmapgt.models.LocationInfo;
import compi1.travelmapgt.models.PathInfo;
import compi1.travelmapgt.models.Recorrido;
import compi1.travelmapgt.structures.btree.BTree;
import compi1.travelmapgt.structures.btree.BTreePage;
import compi1.travelmapgt.structures.graph.Grafo;
import compi1.travelmapgt.structures.graph.NodeGraph;
import compi1.travelmapgt.structures.graph.SearcherPath;
import compi1.travelmapgt.util.Clock;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author yennifer
 */
public class Backend {

    private Grafo<LocationInfo, PathInfo> grafo;
    private DataCollector dataCollector;
    private FilesUtil filesUtil;
    private GraphGrapher grafoGrapher;
    private BTreeGrapher bTreeGrapher;
    private SearcherPath searchearPath;
    private JFrame fronted;

    private Clock clock;
    private Thread threadClock;

    private int globalGraphNum;
    private int recorridoNum;

    public Backend(JFrame fronted) {
        grafo = new Grafo<>();
        dataCollector = new DataCollector();
        filesUtil = new FilesUtil();
        grafoGrapher = new GraphGrapher();
        bTreeGrapher = new BTreeGrapher();
        searchearPath = new SearcherPath();
        this.fronted = fronted;
    }

    //----------------------------DATOS DE HORA----------------------------
    public void defineHour() {
        String hour = JOptionPane.showInputDialog(null, "Ingrese la hora (HH:MM):");
        if (hour != null && hour.matches("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")) {
            String[] hourData = hour.split(":");
            LocalTime newTime = LocalTime.of(Integer.parseInt(hourData[0]), Integer.parseInt(hourData[1]));
            clock.adjust(newTime);
        } else {
            JOptionPane.showMessageDialog(null, "Formato de hora incorrecto.");
        }
    }

    public void initClock(JLabel hourDisplay) {
        clock = new Clock(hourDisplay);
        threadClock = new Thread(clock);
        threadClock.start();
    }

    public Clock getClock() {
        return this.clock;
    }

    public void resumeCountHour() {
        clock.restart();
        threadClock.interrupt();
    }

    //----------------------------ARCHIVOS----------------------------
    private void showInesperatedError() {
        JOptionPane.showMessageDialog(null,
                "Ocurrio un error inesperado");
    }

    private void verificateWarningsFiles() {
        if (!dataCollector.getWarnings().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "**ADVERTENCIAS**\n" + dataCollector.getWarningsStr(),
                    "Advertencias",
                    JOptionPane.ERROR_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(null, "Se han cargado los datos con exito");
        }
    }

    public void readLocationInfo(JLabel label, JComboBox fromSelector, JComboBox toSelector) {
        grafo = new Grafo<>(); //reiniciar el grafo
        dataCollector.setGrafo(grafo);
        restartComboBox(fromSelector);
        restartComboBox(toSelector);
        File file = new File(filesUtil.getPath());
        try {
            dataCollector.readNodesData(file);
            setAction(grafo.getNodes().getRaiz(), fromSelector);
            setAction(grafo.getNodes().getRaiz(), toSelector);
            grafoGrapher.graph(FilesUtil.RESOURCES_PATH, "globalGraph" + globalGraphNum, grafo);
            initGraphImage(label, FilesUtil.RESOURCES_PATH + "globalGraph" + globalGraphNum + ".png");
            globalGraphNum++;
            verificateWarningsFiles();
        } catch (IOException ex) {
            showInesperatedError();
        }
    }

    private void initGraphImage(JLabel label, String path) {
        label.setIcon(null);
        label.revalidate();
        label.repaint();
        ImageIcon imageIcon = new ImageIcon(path);
        label.setIcon(imageIcon);
        label.revalidate();
        label.repaint();
    }

    public void restartComboBox(JComboBox comboBox) {
        DefaultComboBoxModel model = (DefaultComboBoxModel) comboBox.getModel();
        model.removeAllElements();
        comboBox.updateUI();
    }

    public void setAction(BTreePage<NodeGraph<LocationInfo>> page, JComboBox comboBox) {
        if (page != null && !page.isEmpty()) {

            if (page.getPunteros().isEmpty()) {
                for (NodeGraph<LocationInfo> node : page.getNodes()) { //agrega sus nodos
                    comboBox.addItem(node.getKey().getKeyLocation());
                }
            } else {
                //puntero nodo puntero
                int indexNode = 0, indexPuntero = 0, index = 0;
                while (indexNode < page.getNodes().size() || indexPuntero < page.getPunteros().size()) {
                    if (index % 2 == 0) { //agrega sus punteros
                        BTreePage subPage = (BTreePage) page.getPunteros().get(indexPuntero);
                        setAction(subPage, comboBox);
                        indexPuntero++;
                    } else {
                        comboBox.addItem(page.getNodes().get(indexNode).getKey().getKeyLocation());
                        indexNode++;
                    }
                    index++;
                }
            }
        }
    }

    public void readTraficInfo() {
        File file = new File(filesUtil.getPath());
        try {
            dataCollector.readTraficData(file);
            verificateWarningsFiles();
        } catch (IOException ex) {
            showInesperatedError();
        }
    }

    //----------------------------BUSQUEDA DE NODOS----------------------------
    public void findPaths(JComboBox[] specifications) {
        if (specifications[MainMenu.FROM_NODE].getSelectedItem().toString()
                .equals((String) specifications[MainMenu.TO_NODE].getSelectedItem())) {
            JOptionPane.showMessageDialog(null, "El nodo seleccionado es el mismo");
        } else {
            try {
                boolean extendedPath = specifications[MainMenu.TYPE_TRANS].getSelectedIndex() != MainMenu.VEHICLE_TYPE;
                BTree<Recorrido> recorridos = searchearPath.findAllPaths(
                        grafo,
                        new LocationInfo((String) specifications[MainMenu.FROM_NODE].getSelectedItem()),
                        new LocationInfo((String) specifications[MainMenu.TO_NODE].getSelectedItem()),
                        extendedPath
                );
                bTreeGrapher.graph(FilesUtil.RESOURCES_PATH, "arbol_recorridos", recorridos);
                JOptionPane.showMessageDialog(null, "Se ha generado el archivo " + FilesUtil.RESOURCES_PATH
                        + "arbol_recorridos.png como referencia del arbol B de todos los recorridos posibles");
                //inicializar el recorrido
                fronted.setVisible(false);
                GuidedTravel travel;
                try {
                    travel = new GuidedTravel(this, specifications, recorridos);
                    travel.initClock(clock);
                    travel.setVisible(true);
                } catch (NoPathException ex) {
                    JOptionPane.showMessageDialog(null, "No se encontro un camino que une los dos nodos");
                }
            } catch (NoDataFoundException | IOException ex) {
                showInesperatedError();
            }
        }
    }

    /**
     * Inicializa el mejor camino referente a un conjunto de condiciones,
     * utiliza el grafo actual que se encuentra en el backend
     *
     * @param recorridos, son todos los recorridos que fueron previamente
     * encontrados
     * @param displayGraph, una label para mostrar la imagen generada del camino
     * @param specifications, especificaciones de filtros
     * @return el recorrido mas optimo encontrado
     * @throws compi1.travelmapgt.exceptions.NoPathException cuando no hay
     * caminos disponibles
     * @throws java.io.IOException cuando no se pueda generar la imagen
     */
    public Recorrido initDefinePath(BTree<Recorrido> recorridos, JLabel displayGraph, JComboBox[] specifications)
            throws NoPathException, IOException {
        FilterSpecifications filters = new FilterSpecifications(
                specifications[MainMenu.TYPE_TRANS].getSelectedIndex() != MainMenu.VEHICLE_TYPE,
                specifications[MainMenu.FILTER].getSelectedIndex(),
                specifications[MainMenu.BEST_SPECIFICATION].getSelectedIndex() == MainMenu.BEST_ROUTE
        );
        Recorrido recorrido = searchearPath.findPath(grafo, recorridos, filters, clock);
        recorrido.showInGraph();
        filesUtil.deleteFile(FilesUtil.RESOURCES_PATH + "recorrido" + recorridoNum + ".png");
        recorridoNum++;
        if (specifications[MainMenu.DISPLAY_SPECIFICATION].getSelectedIndex() == MainMenu.SHOW_WEIGHT_OP) {
            grafoGrapher.graphWithWeight(FilesUtil.RESOURCES_PATH, "recorrido" + recorridoNum, grafo, getTypeWeight(filters));
        } else {
            grafoGrapher.graph(FilesUtil.RESOURCES_PATH, "recorrido" + recorridoNum, grafo);
        }
        initGraphImage(displayGraph, FilesUtil.RESOURCES_PATH + "recorrido" + recorridoNum + ".png");
        return recorrido;
    }

    private int getTypeWeight(FilterSpecifications filter) {
        if (filter.isExtendedPath()) { //va caminando
            return switch (filter.getTypeFilter()) {
                case MainMenu.RESOURCES_FILTER ->
                    GraphGrapher.WALKING_WEIGHT;
                case MainMenu.DISTANCE_FILTER ->
                    GraphGrapher.DISTANCE_WEIGHT;
                case MainMenu.RESOURCES_DISTANCE_FILTER ->
                    GraphGrapher.WALKING_WEIGHT;
                case MainMenu.ALL_FILTER ->
                    GraphGrapher.DISTANCE_TIME_W;
                default ->
                    GraphGrapher.NOTHING;
            };
        } else { // si en carro
            return switch (filter.getTypeFilter()) {
                case MainMenu.RESOURCES_FILTER ->
                    GraphGrapher.GAS_WEIGHT;
                case MainMenu.DISTANCE_FILTER ->
                    GraphGrapher.DISTANCE_WEIGHT;
                case MainMenu.RESOURCES_DISTANCE_FILTER ->
                    GraphGrapher.GAS_DISTACE_WEIGHT;
                case MainMenu.ALL_FILTER ->
                    GraphGrapher.DISTANCE_TIME_TRAFIC_W;
                default ->
                    GraphGrapher.NOTHING;
            };
        }
    }

    //----------------------------CERRAR/REINICIAR EL PROGRAMA----------------------------
    public void restartIde(JComboBox from, JComboBox to, JLabel label) {
        restartComboBox(from);
        restartComboBox(to);
        label.setIcon(null);
        label.revalidate();
        label.repaint();
    }

    public void endPath(Recorrido currentRecorrido, JFrame frame) {
        if (currentRecorrido != null) {
            currentRecorrido.hiddeInGraph();
        }
        frame.dispose();
        fronted.setVisible(true);
    }

    public void showFronted() {
        fronted.setVisible(true);
    }

    //----------------------------INICIALIZAR RECORRIDOS----------------------------
    public void initOptionsRecorrido(JComboBox moveToCM, BTree<Recorrido> recorridos, int nodeNum) {
        restartComboBox(moveToCM);
        setViableNode(recorridos.getRaiz(), moveToCM, nodeNum, new ArrayList<>());
    }

    private void setViableNode(BTreePage<Recorrido> page, JComboBox comboBox, int nodeNum, 
            List<Integer> nodesKeyList) {
        if (page != null && !page.isEmpty()) {
            if (page.getPunteros().isEmpty()) {
                for (Recorrido node : page.getNodes()) { //agrega sus nodos
                    try {
                        addNode(node, nodeNum, comboBox, nodesKeyList);
                    } catch (IndexOutOfBoundsException | NoDataFoundException e) {
                        //no se pudo agregar, no deberia pasar
                    }
                }
            } else {
                //puntero nodo puntero
                int indexNode = 0, indexPuntero = 0, index = 0;
                while (indexNode < page.getNodes().size() || indexPuntero < page.getPunteros().size()) {
                    if (index % 2 == 0) { //agrega sus punteros
                        BTreePage subPage = (BTreePage) page.getPunteros().get(indexPuntero);
                        setViableNode(subPage, comboBox, nodeNum, nodesKeyList);
                        indexPuntero++;
                    } else {
                        Recorrido node = page.getNodes().get(indexNode);
                        try {
                            addNode(node, nodeNum, comboBox, nodesKeyList);
                        } catch (IndexOutOfBoundsException | NoDataFoundException e) {
                            //no se pudo agregar, no deberia pasar
                        }
                        indexNode++;
                    }
                    index++;
                }
            }
        }
    }

    private void addNode(Recorrido node, int nodeNum, JComboBox comboBox, List<Integer> nodesKeyList) throws NoDataFoundException {
        Collections.sort(nodesKeyList);
        int codeNode = node.getGraph()
                            .getNodeNum(node.getRecorrido().get(nodeNum))
                            .getNumber();
        if (Collections.binarySearch(nodesKeyList, codeNode) < 0) { //no existe
            comboBox.addItem(
                    new KeyMove(
                            nodeNum, 
                            node.getGraph().getNode(node.getRecorrido().get(nodeNum)).getKeyLocation()
                    )
            );
            nodesKeyList.add(codeNode);
        }
    }

}
