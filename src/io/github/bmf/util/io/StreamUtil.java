package io.github.bmf.util.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtil {
    private final static int BUFF_SIZE = (int) Math.pow(128, 2);

    /**
     * Creates a DataInputStream from byte[].
     *
     * @param data
     *            byte[] to convert to DataInputStream.
     * @return
     */
    public static DataInputStream fromBytes(byte[] data) {
        return new DataInputStream(new ByteArrayInputStream(data));
    }

    /**
     * Reads the bytes from the inputstream into a byte array.
     * 
     * @param is
     *            InputStream to read from.
     * @return
     * @throws IOException
     *             Thrown if the given input stream cannot be read from.
     */
    public static byte[] fromStream(InputStream is) throws IOException {
        try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            int r;
            byte[] data = new byte[BUFF_SIZE];
            while ((r = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, r);
            }
            buffer.flush();
            return buffer.toByteArray();
        }
    }
}
