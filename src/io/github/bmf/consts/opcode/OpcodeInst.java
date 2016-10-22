package io.github.bmf.consts.opcode;

import io.github.bmf.consts.opcode.impl.*;

public class OpcodeInst {

    public static final NOP NOP = new NOP();
    public static final ACONST_NULL ACONST_NULL = new ACONST_NULL();
    public static final ICONST ICONST_M1 = new ICONST(Opcode.ICONST_M1, -1);
    public static final ICONST ICONST_0 = new ICONST(Opcode.ICONST_0, 0);
    public static final ICONST ICONST_1 = new ICONST(Opcode.ICONST_1, 1);
    public static final ICONST ICONST_2 = new ICONST(Opcode.ICONST_2, 2);
    public static final ICONST ICONST_3 = new ICONST(Opcode.ICONST_3, 3);
    public static final ICONST ICONST_4 = new ICONST(Opcode.ICONST_4, 4);
    public static final ICONST ICONST_5 = new ICONST(Opcode.ICONST_5, 5);
    public static final DCONST DCONST_0 = new DCONST(Opcode.DCONST_0, 0);
    public static final DCONST DCONST_1 = new DCONST(Opcode.DCONST_1, 1);
    public static final FCONST FCONST_0 = new FCONST(Opcode.FCONST_0, 0);
    public static final FCONST FCONST_1 = new FCONST(Opcode.FCONST_1, 1);
    public static final FCONST FCONST_2 = new FCONST(Opcode.FCONST_2, 2);
    public static final LCONST LCONST_0 = new LCONST(Opcode.LCONST_0, 0);
    public static final LCONST LCONST_1 = new LCONST(Opcode.LCONST_1, 1);

}
