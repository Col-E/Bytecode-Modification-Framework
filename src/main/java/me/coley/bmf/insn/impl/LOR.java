package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class LOR extends Instruction {
    public LOR() {
        super(OpType.MATH, Instruction.LOR, 1);
    }
}
