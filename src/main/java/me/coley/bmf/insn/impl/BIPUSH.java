package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;
import me.coley.bmf.insn.SingleValueInstruction;

public class BIPUSH extends SingleValueInstruction<Integer> {
    public BIPUSH(int value) {
        super(OpType.STACK, Instruction.BIPUSH, 2, value);
    }
}
