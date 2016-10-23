package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;

public class LDC_W extends AbstractLDC {
    public LDC_W(int index) {
        super(Opcode.LDC_W, 3, index);
    }
}
