package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class IUSHR extends Instruction {
    public IUSHR() {
        super(OpType.MATH, Instruction.IUSHR, 1);
    }
}
