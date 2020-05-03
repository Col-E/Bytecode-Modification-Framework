package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractFieldInstruction;
import me.coley.bmf.insn.Instruction;

public class PUTSTATIC extends AbstractFieldInstruction {
    public PUTSTATIC( int fieldIndex) {
        super(Instruction.PUTSTATIC, fieldIndex);
    }
}
