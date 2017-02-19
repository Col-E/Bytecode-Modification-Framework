package io.github.bmf.opcode;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class AbstractFieldOpcode extends Opcode {
    public int fieldIndex;

    public AbstractFieldOpcode(int opcode, int fieldIndex) {
        super(OpcodeType.STACK, opcode, 3);
        this.fieldIndex = fieldIndex;
    }

}
