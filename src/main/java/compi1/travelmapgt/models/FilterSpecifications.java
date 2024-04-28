
package compi1.travelmapgt.models;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author yennifer
 */
@Getter @Setter
public class FilterSpecifications {
    private boolean extendedPath;
    private int typeFilter;
    private boolean bestPath;

    public FilterSpecifications(boolean extendedPath, int typeFilter, boolean bestPath) {
        this.extendedPath = extendedPath;
        this.typeFilter = typeFilter;
        this.bestPath = bestPath;
    }
    
}
