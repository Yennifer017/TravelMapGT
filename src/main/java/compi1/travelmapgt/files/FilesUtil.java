
package compi1.travelmapgt.files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

/**
 *
 * @author yennifer
 */
public class FilesUtil {
    
    /**
     * crea una carpeta
     *
     * @param rootPath el path donde se creara la carpeta, sin el separador al
     * final
     * @param name nombre de la carpeta
     * @return el path de la carpeta creada
     * @throws java.io.IOException
     */
    public String createDirectory(String rootPath, String name)
            throws IOException {
        String path = rootPath + getSeparator() + name;
        File directory = new File(path);
        if (!directory.exists()) {
            if(directory.mkdir()){
                return path;
            }else{
                throw new IOException();
            }
        } else {
            throw new FileAlreadyExistsException(directory.toString());
        }
    }

    /**
     * @return el separador de carpetas dependiendo del sistema operativo
     */
    public static String getSeparator() {
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().contains("windows")) {
            return "\\";
        } else {
            return "/";
        }
    }

    /**
     * Reescribe un archivo a partir de un texto
     *
     * @param texto
     * @param ruta
     * @throws java.io.IOException
     */
    public void saveFile(String texto, String ruta) throws IOException{
        File archivo = new File(ruta); //obtiene el archivo de la ruta
        if(archivo.exists()){
            saveFile(texto, archivo);
        }else{
            throw new FileNotFoundException();
        }
    }

    /**
     * Reescribe o guarda un archivo
     *
     * @param texto
     * @param file
     */
    public void saveFile(String texto, File file) throws IOException {
        FileWriter escritor = new FileWriter(file, false);
        BufferedWriter buffer = new BufferedWriter(escritor);
        buffer.write(texto);
        buffer.close();
        escritor.close();
    }

    /**
     * Elimina un archivo
     *
     * @param path
     * @return si fue correctamente eliminado
     */
    public boolean deleteFile(String path) {
        File archivo = new File(path);
        return archivo.delete();
    }
    
    public boolean deleteDirectory(String path){
        File folder = new File(path);
        if(folder.exists() && folder.isDirectory()) {
            return deleteFolder(folder);
        } else {
            return false;
        }
    }
    
    private boolean deleteFolder(File folder){
        File[] files = folder.listFiles();
        if(files != null) {
            for(File file : files) {
                if(file.isDirectory()) {
                    deleteFolder(file);
                } else {
                    file.delete();
                }
            }
        }
        return folder.delete();
    }

    /**
     * Guarda un nuevo archivo como, solo si no existe
     * @param text
     * @param extension incluyendo el punto
     * @param nameFile
     * @param path donde se guardara el archivo, sin incluir al final el separador
     * @throws java.io.IOException si no se puede guardar bien
     */
    public void saveAs(String text, String extension, String nameFile, String path) 
            throws IOException {
        String finalPath = path + getSeparator() + nameFile + extension;
        File file = new File(finalPath);
        if (!file.exists()) {
            saveFile(text, file);
        } else {
            throw new FileAlreadyExistsException(file.toString());
        }
    }
   
}
