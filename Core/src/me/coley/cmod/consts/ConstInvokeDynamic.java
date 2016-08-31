package me.coley.cmod.consts;

public class ConstInvokeDynamic extends Constant<Integer> {

	public ConstInvokeDynamic(int attribute, int nameType) {
		super(ConstantType.INVOKEDYNAMIC, (attribute << 16) | nameType);
	}

	public int getBootstrapAttribute() {
		return value.intValue() & 0xffff;
	}

	public int getNameTypeIndex() {
		return (value.intValue() >> 16) & 0xffff;
	}
}
