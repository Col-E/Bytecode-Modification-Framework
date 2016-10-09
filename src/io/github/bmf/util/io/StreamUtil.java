package io.github.bmf.util.io;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class StreamUtil {
    /**
     * Byte[] to DataInputStream
     *
     * @param data
     * @return
     */
    public static DataInputStream fromBytes(byte[] data) {
        return new DataInputStream(new ByteArrayInputStream(data));
    }

    /**
     * Reads an unsigned short stored in a byte[]
     *
     * @param data
     * @return
     * @throws IOException
     */
    public static int readUnsignedShort(byte[] data) throws IOException {
        DataInputStream dis = fromBytes(data);
        int value = dis.readUnsignedShort();
        dis.close();
        return value;
    }
}
