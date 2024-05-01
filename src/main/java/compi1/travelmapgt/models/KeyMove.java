
package compi1.travelmapgt.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author yennifer
 */
@Getter @Setter @AllArgsConstructor
public class KeyMove {
    private int keyNumber;
    private String keyString;
    
    @Override
    public String toString(){
        return this.keyString;
    }
}
