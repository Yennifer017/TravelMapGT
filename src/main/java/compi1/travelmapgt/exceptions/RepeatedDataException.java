
package compi1.travelmapgt.exceptions;

/**
 *
 * @author yennifer
 */
public class RepeatedDataException extends Exception{

    /**
     * Creates a new instance of <code>RepeatedDataException</code> without
     * detail message.
     */
    public RepeatedDataException() {
    }

    /**
     * Constructs an instance of <code>RepeatedDataException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public RepeatedDataException(String msg) {
        super(msg);
    }
}
