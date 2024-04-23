
package compi1.travelmapgt.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author yennifer
 */
@Getter @Setter @NoArgsConstructor
public class PathInfo {
    private int averageTimeCar;
    private int averageTimeWalking;
    private int costGas;
    private int costWalking;
    private int distance;
    private int hourInitTrafic;
    private int hourFinishTrafic;
    private int probabilityTrafic;
}
