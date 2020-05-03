package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractFieldInstruction;
import me.coley.bmf.insn.Instruction;

public class GETFIELD extends AbstractFieldInstruction {
    public GETFIELD( int fieldIndex) {
        super(Instruction.GETFIELD, fieldIndex);
    }
}
