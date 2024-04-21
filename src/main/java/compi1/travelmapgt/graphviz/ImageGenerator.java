package compi1.travelmapgt.graphviz;

import compi1.travelmapgt.files.FilesUtil;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author yennifer
 */
public class ImageGenerator {

    public static final String DOT_EXTENSION = ".dot";

    private final FilesUtil filesUtil;

    public ImageGenerator() {
        filesUtil = new FilesUtil();
    }

    public void generateImg(String finalPath, String nameFile, String code) throws IOException {
        String finalPathDotFile = finalPath + nameFile + DOT_EXTENSION;
        File file = new File(finalPathDotFile);
        filesUtil.saveFile(code, file);
        String[] cmd = {getExecutComand(), "-Tpng", finalPathDotFile, "-o", finalPath + nameFile + ".png"};

        Runtime rt = Runtime.getRuntime();
        rt.exec(cmd);
    }

    public static String getExecutComand() {
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().contains("windows")) {
            return "dot.exe";
        } else {
            return "dot";
        }
    }
}
