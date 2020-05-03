package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractLDC;
import me.coley.bmf.insn.Instruction;

public class LDC_W extends AbstractLDC {
    public LDC_W(int index) {
        super(Instruction.LDC_W, 3, index);
    }
}
