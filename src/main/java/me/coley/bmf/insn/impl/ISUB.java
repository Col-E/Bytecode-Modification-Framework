package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class ISUB extends Instruction {
    public ISUB() {
        super(OpType.MATH, Instruction.ISUB, 1);
    }
}
