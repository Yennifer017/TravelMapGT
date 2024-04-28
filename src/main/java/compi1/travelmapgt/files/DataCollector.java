package compi1.travelmapgt.files;

import compi1.travelmapgt.exceptions.InvalidDataException;
import compi1.travelmapgt.exceptions.NoDataFoundException;
import compi1.travelmapgt.exceptions.RepeatedDataException;
import compi1.travelmapgt.models.LocationInfo;
import compi1.travelmapgt.models.PathInfo;
import compi1.travelmapgt.structures.graph.Grafo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yennifer
 */
public class DataCollector {

    private Grafo<LocationInfo, PathInfo> grafo;
    private List<String> warnings;

    public DataCollector(Grafo<LocationInfo, PathInfo> grafo) {
        this.grafo = grafo;
        warnings = new ArrayList<>();
    }

    public DataCollector() {
        warnings = new ArrayList<>();
    }

    public void setGrafo(Grafo<LocationInfo, PathInfo> grafo) {
        this.grafo = grafo;
    }

    public void readNodesData(File file) throws IOException {
        this.readData(file, false);
    }

    public void readTraficData(File file) throws IOException {
        this.readData(file, true);
    }

    private void readNodeLine(String[] line, int numberLine) {
        LocationInfo from = new LocationInfo(line[0]);
        LocationInfo to = new LocationInfo(line[1]);
        try {
            grafo.addNode(from);
        } catch (RepeatedDataException ex) {
            /* controlado*/
        }
        try {
            grafo.addNode(to);
        } catch (RepeatedDataException ex) {
            /* controlado*/
        }
        PathInfo path = new PathInfo();
        try {
            path.setAverageTimeCar(Integer.parseInt(line[2]));
            path.setAverageTimeWalking(Integer.parseInt(line[3]));
            path.setCostGas(Integer.parseInt(line[4]));
            path.setCostWalking(Integer.parseInt(line[5]));
            path.setDistance(Integer.parseInt(line[6]));
            grafo.addPath(from, to, path);
        } catch (NumberFormatException e) {
            warnings.add("No se pudo convertir un dato numerico en una ruta y no se inserto."
                    + "- linea: " + numberLine);
        } catch (NoDataFoundException e) {
            warnings.add("Ocurrio un error inesperado al ingresar los datos de una ruta."
                    + " - linea: " + numberLine);
        }
    }

    public List<String> getWarnings() {
        return this.warnings;
    }

    public String getWarningsStr() {
        String text = "";
        for (int i = 0; i < warnings.size(); i++) {
            text += warnings.get(i) + "\n";
        }
        return text;
    }

    private void readData(File file, boolean trafic) throws FileNotFoundException, IOException {
        warnings.clear();
        FileReader lector = new FileReader(file);
        BufferedReader buffer = new BufferedReader(lector);
        String linea;
        int counterLine = 1;
        while ((linea = buffer.readLine()) != null) {
            String[] primalData = linea.split("\\|");
            if (!trafic && primalData.length == 7) {
                readNodeLine(primalData, counterLine);
            } else if (trafic && primalData.length == 5) {
                readTraficLine(primalData, counterLine);
            } else {
                warnings.add("Linea "
                        + counterLine
                        + " invalida, no contiene los datos suficinetes, no se leyo");
            }
            counterLine++;
        }
        buffer.close();
        lector.close();
    }

    private void readTraficLine(String[] line, int numberLine) {
        try {
            PathInfo pathInfo = grafo.getPath(new LocationInfo(line[0]), new LocationInfo(line[1]));
            int hourInit = Integer.parseInt(line[2]);
            int hourFinish = Integer.parseInt(line[3]);
            int probabilityTrafic = Integer.parseInt(line[4]);
            if (validHour(hourInit) && validHour(hourFinish) && validProbability(probabilityTrafic)) {
                if( ((hourInit > hourFinish ) && hourFinish != 0) || (hourInit == hourFinish)){
                    throw new InvalidDataException("Horarios incongruentes de trafico - linea:" + numberLine);
                }
                pathInfo.setHourInitTrafic(LocalTime.of(hourInit, 0));
                pathInfo.setHourFinishTrafic(
                        hourFinish == 0 ? 
                        LocalTime.of(23, 59) : 
                        LocalTime.of(hourFinish, 0)
                );
                pathInfo.setProbabilityTrafic(probabilityTrafic);
            } else {
                throw new InvalidDataException("Formato de 24 horas o probabilidad invalida"
                        + " - linea: " + numberLine);
            }
        } catch (NoDataFoundException ex) {
            warnings.add("Camino no encontrado, no se pudieron setear las horas de trafico."
                    + " - linea: " + numberLine);
        } catch (InvalidDataException ex) {
            warnings.add(ex.getMessage());
        }
    }

    private boolean validHour(int hour24) {
        return hour24 >= 0 && hour24 < 24;
    }

    private boolean validProbability(int probability) {
        return probability >= 0 && probability <= 100;
    }
}
