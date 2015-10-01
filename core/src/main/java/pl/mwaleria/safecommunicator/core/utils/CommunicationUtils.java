
package pl.mwaleria.safecommunicator.core.utils;

import java.nio.ByteBuffer;

/**
 *
 * @author mwaleria
 */
public class CommunicationUtils {

    private CommunicationUtils(){}
    
    public static byte[] intToBytes(int i) {
        byte[] result = new byte[4];

        result[0] = (byte) (i >> 24);
        result[1] = (byte) (i >> 16);
        result[2] = (byte) (i >> 8);
        result[3] = (byte) (i /*>> 0*/);

        return result;
    }

    public static int bytesToint(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.put(bytes);
        buffer.flip();//need flip 
        return buffer.getInt();
    }    
}
