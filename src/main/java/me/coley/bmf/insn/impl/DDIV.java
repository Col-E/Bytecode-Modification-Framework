package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class DDIV extends Instruction {
    public DDIV() {
        super(OpType.MATH, Instruction.DDIV, 1);
    }
}
