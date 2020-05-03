package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class POP extends Instruction {
    public POP() {
        super(OpType.STACK, Instruction.POP, 1);
    }
}
