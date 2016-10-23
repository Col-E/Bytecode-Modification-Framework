package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.SingleValueOpcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class AbstractSTORE extends SingleValueOpcode<Integer> {
    public AbstractSTORE(int opcode, int index) {
        super(OpcodeType.VARIABLE, opcode, 2, index);
    }
}
