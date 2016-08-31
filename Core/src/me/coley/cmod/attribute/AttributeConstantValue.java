package me.coley.cmod.attribute;

public class AttributeConstantValue extends Attribute {
	private int constantIndex;

	public AttributeConstantValue(int name, int constantIndex) {
		super(name, AttributeType.CONSTANT_VALUE);
		this.constantIndex = constantIndex;
	}
	
	public void setConstant(int constantIndex){
		this.constantIndex = constantIndex;
	}

	public int getConstant() {
		return constantIndex;
	}
}
