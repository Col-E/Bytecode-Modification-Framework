package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.AbstractJump;
import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class IFLE extends AbstractJump {
    public IFLE(int jumpIndex) {
        super(OpcodeType.ARRAY, Opcode.IFLE, jumpIndex);
    }
}
