package me.coley.cmod.consts;

public class ConstInterfaceMethod extends Constant<Integer> {

	public ConstInterfaceMethod(int clazz, int nameType) {
		super(ConstantType.METHOD, (clazz << 16) | nameType);
	}

	public int getClassIndex() {
		return value.intValue() & 0xffff;
	}

	public int getNameTypeIndex() {
		return (value.intValue() >> 16) & 0xffff;
	}
}
