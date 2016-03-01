/**
 * Classes to model a word ladder solver
 * Solves EE422C Programming Assignment #4
 * @author Jett Anderson and Aria Pahlavan
 * eid: jra2995, ap44342
 * @version 1.00 2016-03-01
 */

package Assignment4;

public class NoSuchLadderException extends Exception
{
    private static final long serialVersionUID = 1L;

    public NoSuchLadderException(String message)
    {
        super(message);
    }

    public NoSuchLadderException(String message, Throwable throwable)
    {
        super(message, throwable);
    }
}
