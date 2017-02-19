package io.github.bmf.opcode;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class AbstractMethodOpcode extends Opcode {
    public int methodIndex;

    public AbstractMethodOpcode(int opcode, int size, int methodIndex) {
        super(OpcodeType.STACK, opcode, size);
        this.methodIndex = methodIndex;
    }

}
