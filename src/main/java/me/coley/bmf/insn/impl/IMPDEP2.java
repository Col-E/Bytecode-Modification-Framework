package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class IMPDEP2 extends Instruction {
    public IMPDEP2() {
        super(OpType.OTHER, Instruction.IMPDEP2, 1);
    }
}
