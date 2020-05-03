package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class ISHL extends Instruction {
    public ISHL() {
        super(OpType.MATH, Instruction.ISHL, 1);
    }
}
