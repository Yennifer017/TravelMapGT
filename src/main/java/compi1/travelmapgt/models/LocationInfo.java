
package compi1.travelmapgt.models;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author yennifer
 */
@Getter @Setter
public class LocationInfo implements Comparable<LocationInfo>{
    String keyLocation;
    boolean active;

    public LocationInfo(String keyLocation){
        this.keyLocation = keyLocation;
    }
    
    @Override
    public int compareTo(LocationInfo t) {
        return this.keyLocation.compareTo(t.keyLocation);
    }

}
