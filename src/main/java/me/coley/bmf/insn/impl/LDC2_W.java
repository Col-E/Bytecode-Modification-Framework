package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractLDC;
import me.coley.bmf.insn.Instruction;

public class LDC2_W extends AbstractLDC {
    public LDC2_W(int index) {
        // TODO: Check is size 3 is correct
        super(Instruction.LDC_W, 3, index);
    }
}
