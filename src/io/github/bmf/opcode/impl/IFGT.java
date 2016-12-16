package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.AbstractJump;
import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class IFGT extends AbstractJump {
    public IFGT(int jumpIndex) {
        super(OpcodeType.ARRAY, Opcode.IFGT, jumpIndex);
    }
}
