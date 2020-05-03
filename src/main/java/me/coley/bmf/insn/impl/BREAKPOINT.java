package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class BREAKPOINT extends Instruction {
    public BREAKPOINT() {
        super(OpType.OTHER, Instruction.BREAKPOINT, 1);
    }
}
