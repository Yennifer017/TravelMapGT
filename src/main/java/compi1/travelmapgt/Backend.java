package compi1.travelmapgt;

import compi1.travelmapgt.files.DataCollector;
import compi1.travelmapgt.files.FilesUtil;
import compi1.travelmapgt.graphviz.GraphGrapher;
import compi1.travelmapgt.models.LocationInfo;
import compi1.travelmapgt.models.PathInfo;
import compi1.travelmapgt.structures.btree.BTreePage;
import compi1.travelmapgt.structures.graph.Grafo;
import compi1.travelmapgt.structures.graph.NodeGraph;
import compi1.travelmapgt.util.Clock;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
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

    private int globalGraphNum;

    public Backend() {
        grafo = new Grafo<>();
        dataCollector = new DataCollector();
        filesUtil = new FilesUtil();
        grafoGrapher = new GraphGrapher();
    }

    //----------------------------DATOS DE HORA----------------------------
    public void setHour(Clock clock) {
        String hour = JOptionPane.showInputDialog(null, "Ingrese la hora (HH:MM):");
        if (hour.matches("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")) {
            String[] hourData = hour.split(":");
            LocalTime newTime = LocalTime.of(Integer.parseInt(hourData[0]), Integer.parseInt(hourData[1]));
            clock.adjust(newTime);
        } else {
            JOptionPane.showMessageDialog(null, "Formato de hora incorrecto.");
        }
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

    public void readLocationInfo(JComboBox fromSelector, JComboBox toSelector) {
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
            //TODO actualizar la imagen
            verificateWarningsFiles();
        } catch (IOException ex) {
            showInesperatedError();
        }
    }

    private void restartComboBox(JComboBox comboBox) {
        DefaultComboBoxModel model = (DefaultComboBoxModel) comboBox.getModel();
        model.removeAllElements();
        comboBox.updateUI();
    }

    private void setAction(BTreePage<NodeGraph<LocationInfo>> page, JComboBox comboBox) {
        if (page != null && !page.isEmpty()) {
            for (int i = 0; i < page.getPunteros().size(); i++) { //agrega sus punteros
                BTreePage subPage = (BTreePage) page.getPunteros().get(i);
                setAction(subPage, comboBox);
            }
            for (NodeGraph<LocationInfo> node : page.getNodes()) { //agrega sus nodos
                comboBox.addItem(node.getKey().getKeyLocation());
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

}
