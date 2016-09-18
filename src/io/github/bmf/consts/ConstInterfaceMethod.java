package io.github.bmf.consts;

public class ConstInterfaceMethod extends Constant<Integer> {

<<<<<<< HEAD
	public ConstInterfaceMethod(int clazz, int nameType) {
		super(ConstantType.INTERFACE_METHOD, (clazz << 16) | nameType);
	}
=======
    public ConstInterfaceMethod(int clazz, int nameType) {
        super(ConstantType.METHOD, (clazz << 16) | nameType);
    }
>>>>>>> 6def9cbeb723d41ff92add2ee84ed035501ee963

    public int getClassIndex() {
        return value.intValue() & 0xffff;
    }

    public int getNameTypeIndex() {
        return (value.intValue() >> 16) & 0xffff;
    }
}
