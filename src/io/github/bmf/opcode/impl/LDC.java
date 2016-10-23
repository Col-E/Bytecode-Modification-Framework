package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;

public class LDC extends AbstractLDC {
    public LDC(int index) {
        super(Opcode.LDC, 2, index);
    }
}
