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
}
