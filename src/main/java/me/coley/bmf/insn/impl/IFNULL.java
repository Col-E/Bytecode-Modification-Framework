package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractJump;
import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class IFNULL extends AbstractJump {
    public IFNULL(int jumpIndex) {
        super(OpType.ARRAY, Instruction.IFNULL, jumpIndex);
    }
}
