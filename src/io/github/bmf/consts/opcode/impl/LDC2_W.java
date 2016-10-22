package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;

public class LDC2_W extends AbstractLDC {
    public LDC2_W(int index) {
        // TODO: Check is size 3 is correct
        super(Opcode.LDC_W, 3, index);
    }
}
