package io.github.bmf.attribute.method;

import com.google.common.collect.Lists;
import io.github.bmf.util.IMeasurable;

import java.util.List;

/**
 * A temporary garbage class containing the raw data of what should be a list of
 * method opcodes. <br>
 * Delete this and replace it with something better. Or refactor it to be not
 * shit.
 *
 * @author Matt
 */
public class OpcodeListData_TEMP implements IMeasurable {
    public byte[] data;
    public List<MethodException> exceptions = Lists.newArrayList();

    public void addException(MethodException mexeption) {
        exceptions.add(mexeption);
    }

    @Override
    public int getLength() {
        // TODO Fill out length method
        return 0;
    }
}
