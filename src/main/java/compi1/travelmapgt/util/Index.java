
package compi1.travelmapgt.util;

/**
 *
 * @author yennifer
 */
public class Index {
    private int index;
    
    public Index(int number){
        index = number;
    }
    
    public int get(){
        return index;
    }
    
    public void increment(){
        index++;
    }
    
    public void increment(int number){
        index += number;
    }
    
    public void set(int number){
        this.index = number;
    }
    
    public void restart(){
        this.index = 0;
    }
}
