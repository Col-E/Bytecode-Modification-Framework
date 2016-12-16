package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.AbstractJump;
import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class IFNE extends AbstractJump {
    public IFNE(int jumpIndex) {
        super(OpcodeType.ARRAY, Opcode.IFNE, jumpIndex);
    }
}
