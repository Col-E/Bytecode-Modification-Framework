package io.github.bmf.consts;

public class ConstField extends Constant<Integer> {

<<<<<<< HEAD
	public ConstField(int clazz, int nameType) {
		super(ConstantType.FIELD, (clazz << 16) | nameType);
	}
=======
    public ConstField(int clazz, int nameType) {
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
