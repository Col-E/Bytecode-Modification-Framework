package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class DNEG extends Instruction {
    public DNEG() {
        super(OpType.MATH, Instruction.DNEG, 1);
    }
}
