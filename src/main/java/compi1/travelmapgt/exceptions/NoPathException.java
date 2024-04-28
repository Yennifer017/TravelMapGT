
package compi1.travelmapgt.exceptions;

/**
 *
 * @author yennifer
 */
public class NoPathException extends Exception{

    /**
     * Creates a new instance of <code>NoPathException</code> without detail
     * message.
     */
    public NoPathException() {
    }

    /**
     * Constructs an instance of <code>NoPathException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public NoPathException(String msg) {
        super(msg);
    }
}
