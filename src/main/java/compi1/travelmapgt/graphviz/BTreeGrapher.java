
package compi1.travelmapgt.graphviz;

import compi1.travelmapgt.structures.BTree;
import compi1.travelmapgt.structures.BTreePage;
import compi1.travelmapgt.util.Index;
import java.io.IOException;

/**
 *
 * @author yennifer
 */
public class BTreeGrapher {
    
    private static final String ENTER = "\n";
    private static final String IDENTATION = "    ";
    private final ImageGenerator generator;
    
    public BTreeGrapher(){
        generator = new ImageGenerator();
    }
    
    public void graph(String finalPath, String nameFile, BTree tree) throws IOException{
        generator.generateImg(finalPath, nameFile, getCode(tree));
    }
    
    private String getCode(BTree tree){
        String code = "digraph {" + ENTER;
        code += IDENTATION + "node [shape=plaintext];" +  ENTER;
        code += graph(new Index(0), tree.getRaiz(), null);  
        code += "}";
        return code;
    }
    
    private String graph(Index index, BTreePage page, String fatherRef){
        if(page == null || page.isEmpty()){
            return "";
        }
        int itsOwnNumber = index.get();
        String code = getCodePage(index.get(), page);
        if(page.hasFather()){
            code += IDENTATION + fatherRef + "->" + itsOwnNumber + ENTER;
        }
        
        index.increment();
        for (int i = 0; i < page.getPunteros().size(); i++) {
            BTreePage subPage = (BTreePage) page.getPunteros().get(i);
            code += graph(index, subPage, itsOwnNumber + ":" + i);
            index.increment();
        }
        return code;
    }
    
    private String getCodePage(int numberNode, BTreePage page){
        String code = IDENTATION + numberNode + "[" + ENTER;
        code += IDENTATION.repeat(2) + "label=<" + ENTER;
        code += IDENTATION.repeat(2) + "<TABLE BORDER=\"1\" CELLBORDER=\"1\" CELLSPACING=\"0\">" + ENTER;
        code += IDENTATION.repeat(3) + "<TR>" + ENTER;
        
        int elementsIndex = 0;
        int punterosIndex = 0;
        int index = 0;
        
        int totalPunteros = page.getPunteros().isEmpty() ?  page.getNodes().size() + 1 : page.getPunteros().size();
        while (punterosIndex < totalPunteros || elementsIndex < page.getNodes().size()) {            
            if(index % 2 == 0){ //grafica punteros
                code += IDENTATION.repeat(4) + "<TD PORT=\"" 
                        + String.valueOf(punterosIndex) 
                        + "\" BGCOLOR=\"orange\"></TD>" + ENTER;
                punterosIndex++;
            } else { //grafica elementos
                code += IDENTATION.repeat(4) + "<TD>" + page.getNodes().get(elementsIndex).toString() + "</TD>" + ENTER;
                elementsIndex++;
            }
            index++;
        }
        
        code += IDENTATION.repeat(3) + "</TR>" + ENTER;
        code += IDENTATION.repeat(2) + "</TABLE>" + ENTER;
        code += IDENTATION.repeat(2) + ">" + ENTER;
        code += IDENTATION + "]" + ENTER;
        return code;
    }
}
