package io.github.bmf.consts.opcode;

import io.github.bmf.consts.opcode.impl.ACONST_NULL;
import io.github.bmf.consts.opcode.impl.ICONST;
import io.github.bmf.consts.opcode.impl.NOP;

public class OpcodeInst {

    public static final Opcode NOP = new NOP();
    public static final Opcode ACONST_NULL = new ACONST_NULL();
    public static final Opcode ICONST_M1 = new ICONST(Opcode.ICONST_M1, -1);
    public static final Opcode ICONST_0 = new ICONST(Opcode.ICONST_0, 0);
    public static final Opcode ICONST_1 = new ICONST(Opcode.ICONST_1, 1);
    public static final Opcode ICONST_2 = new ICONST(Opcode.ICONST_2, 2);
    public static final Opcode ICONST_3 = new ICONST(Opcode.ICONST_3, 3);
    public static final Opcode ICONST_4 = new ICONST(Opcode.ICONST_4, 4);
    public static final Opcode ICONST_5 = new ICONST(Opcode.ICONST_5, 5);

}
