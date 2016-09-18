package io.github.bmf.attribute.method;

import com.google.common.collect.Lists;
import io.github.bmf.util.IMeasurable;
import io.github.bmf.util.MeasurableUtils;

import java.util.List;

/**
 * Local variable table attribute belonging to a {@link io.github.bmf.MethodNode
 * method's} "{@link io.github.bmf.attribute.method.AttributeCode Code}"
 * attribute.
 *
 * @author Matt
 */
public class LocalVariableTable implements IMeasurable {
    public List<Local> locals = Lists.newArrayList();

    /**
     * Creates and adds a local variable to the table. See the documentation of
     * {@link io.github.bmf.attribute.method.Local Local} for more information.
     *
     * @param start
     *            Start index in the opcodes
     * @param len
     *            Length from start local lasts in the opcodes
     * @param name
     *            Constant pool pointers
     * @param desc
     *            Constant pool pointers
     * @param index
     *            Stack frame index.
     */
    public void add(int start, int len, int name, int desc, int index) {
        Local local = new Local(start, len, name, desc, index);
        locals.add(local);
    }

    @Override
    public int getLength() {
        // u2: num_locals
        // ??: locals
        return 2 + MeasurableUtils.getLength(locals);
    }

}
