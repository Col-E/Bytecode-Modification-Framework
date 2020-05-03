package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class LASTORE extends Instruction {
    public LASTORE() {
        super(OpType.ARRAY, Instruction.LASTORE, 1);
    }
}
