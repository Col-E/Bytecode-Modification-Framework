package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractLDC;
import me.coley.bmf.insn.Instruction;

public class LDC extends AbstractLDC {
    public LDC(int index) {
        super(Instruction.LDC, 2, index);
    }
}
