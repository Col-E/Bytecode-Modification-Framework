package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class MONITORENTER extends Instruction {
    public MONITORENTER() {
        super(OpType.OTHER, Instruction.MONITORENTER, 1);
    }
}
