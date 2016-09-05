package me.coley.cmod.attribute.clazz;

public class InnerClass {
	public int innerClassIndex;
	public int outerClassIndex;
	public int innerName;
	public int innerAccess;

	public InnerClass(int ici, int oci, int in, int ia) {
		this.innerAccess = ici;
		this.outerClassIndex = oci;
		this.innerName = in;
		this.innerAccess = ia;
	}
}
