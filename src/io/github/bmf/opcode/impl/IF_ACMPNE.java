package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.AbstractJump;
import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class IF_ACMPNE extends AbstractJump {
    public IF_ACMPNE(int jumpIndex) {
        super(OpcodeType.ARRAY, Opcode.IF_ACMPNE, jumpIndex);
    }
}
