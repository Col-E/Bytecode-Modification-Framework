package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.AbstractJump;
import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class IFGE extends AbstractJump {
    public IFGE(int jumpIndex) {
        super(OpcodeType.ARRAY, Opcode.IFGE, jumpIndex);
    }
}
