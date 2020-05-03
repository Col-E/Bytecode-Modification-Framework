package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class ACONST_NULL extends Instruction {
    public ACONST_NULL() {
        super(OpType.CONST_VALUE, Instruction.ACONST_NULL, 1);
    }
}
