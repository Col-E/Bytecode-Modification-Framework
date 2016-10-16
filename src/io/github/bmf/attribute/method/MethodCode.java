package io.github.bmf.attribute.method;

import io.github.bmf.util.IMeasurable;

/**
 * A temporary garbage class containing the raw data of what should be a list of
 * method opcodes. <br>
 * Delete this and replace it with something better. Or refactor it to be not
 * shit.
 *
 * @author Matt
 */
public class MethodCode implements IMeasurable {
    public byte[] original;

    @Override
    public int getLength() {
        return original.length;
    }
}
