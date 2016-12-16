package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.AbstractJump;
import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class IF_ICMPLE extends AbstractJump {
    public IF_ICMPLE(int jumpIndex) {
        super(OpcodeType.ARRAY, Opcode.IF_ICMPLE, jumpIndex);
    }
}
