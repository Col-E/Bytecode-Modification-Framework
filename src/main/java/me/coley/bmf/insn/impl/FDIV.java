package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class FDIV extends Instruction {
    public FDIV() {
        super(OpType.MATH, Instruction.FDIV, 1);
    }
}
