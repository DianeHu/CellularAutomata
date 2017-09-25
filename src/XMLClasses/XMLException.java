package XMLClasses;

/**
 * This class enables us to throw exceptions when XML cause errors
 * 
 * @author Tyler Yam
 * Source Code from Robert C. Duvall
 */

public class XMLException extends RuntimeException {
    // for serialization
    private static final long serialVersionUID = 1L;


    /** 
     * @author Tyler Yam
     * Source Code from Robert C. Duvall
     * For when our code has an issue
     */
    public XMLException (String message, Object ... values) {
        super(String.format(message, values));
    }

    /**
     *  @author Tyler Yam
     * Source Code from Robert C. Duvall
     * For caught exceptions and gives additional messages
     */
    public XMLException (Throwable cause, String message, Object ... values) {
        super(String.format(message, values), cause);
    }

    /**
     *  @author Tyler Yam
     * Source Code from Robert C. Duvall
     * For caught exceptions but gives no additional messages
     */
    public XMLException (Throwable cause) {
        super(cause);
    }
}
