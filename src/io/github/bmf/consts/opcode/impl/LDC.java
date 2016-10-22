package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;

public class LDC extends AbstractLDC {
    public LDC(int index) {
        super(Opcode.LDC, 2, index);
    }
}
