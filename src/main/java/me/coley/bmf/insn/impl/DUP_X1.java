package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class DUP_X1 extends Instruction {
    public DUP_X1() {
        super(OpType.STACK, Instruction.DUP_X1, 1);
    }
}
