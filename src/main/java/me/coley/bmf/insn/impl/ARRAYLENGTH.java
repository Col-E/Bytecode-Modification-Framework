package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class ARRAYLENGTH extends Instruction {
    public ARRAYLENGTH() {
        super(OpType.ARRAY, Instruction.ARRAYLENGTH, 1);
    }
}
