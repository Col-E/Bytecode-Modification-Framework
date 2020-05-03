package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class DCMPG extends Instruction {
    public DCMPG() {
        super(OpType.STACK, Instruction.DCMPG, 1);
    }
}
