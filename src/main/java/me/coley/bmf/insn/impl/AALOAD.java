package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class AALOAD extends Instruction {
    public AALOAD() {
        super(OpType.ARRAY, Instruction.AALOAD, 1);
    }
}
