
package compi1.travelmapgt.models;

import java.time.LocalTime;
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
    private LocalTime hourInitTrafic;
    private LocalTime hourFinishTrafic;
    private int probabilityTrafic;
}
