package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class DUP extends Instruction {
    public DUP() {
        super(OpType.STACK, Instruction.DUP, 1);
    }
}
