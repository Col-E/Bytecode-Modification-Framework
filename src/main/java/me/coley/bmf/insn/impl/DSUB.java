package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class DSUB extends Instruction {
    public DSUB() {
        super(OpType.MATH, Instruction.DSUB, 1);
    }
}
