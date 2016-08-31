package me.coley.cmod;

import java.util.List;

import com.google.common.collect.Lists;

import me.coley.cmod.attribute.Attribute;
import me.coley.cmod.consts.Constant;

/**
 * A class.
 * 
 * @author Matt
 */
@SuppressWarnings("rawtypes")
class ClassNode {
	/**
	 * Version info.
	 */
	public int major = -1, minor = -1;
	/**
	 * Access. May be multiple combined.
	 */
	public int access;
	/**
	 * Indices in the constant pool pointing to a class name.
	 */
	public int classIndex, superIndex;
	/**
	 * A list of indices in the constant pool pointing to classes that are
	 * implemented.
	 */
	public List<Integer> interfaceIndices = Lists.newArrayList();
	/**
	 * The constant pool.
	 */
	public List<Constant> constants = Lists.newArrayList();
	/**
	 * Fields.
	 */
	public List<FieldNode> fields = Lists.newArrayList();
	/**
	 * Methods.
	 */
	public List<MethodNode> methods = Lists.newArrayList();
	/**
	 * Attributes.<br>
	 * TODO: Do what was done to AttributeCode and make specific attributes
	 * fields.
	 */
	public List<Attribute> attributes = Lists.newArrayList();

	/**
	 * Adds a constant to the constant pool.
	 * 
	 * @param constant
	 */
	public void addConst(Constant constant) {
		constants.add(constant);
	}

	/**
	 * Retrieves a constant from the constant pool. Index starts at 1.
	 * 
	 * @param index
	 * @return
	 */
	public Constant getConst(int index) {
		return constants.get(index - 1);
	}

	/**
	 * Adds an interface index to the class indices list.
	 * 
	 * @param index
	 */
	public void addInterfaceIndex(int index) {
		interfaceIndices.add(index);
	}

	/**
	 * Adds a field to the class.
	 * 
	 * @param field
	 */
	public void addField(FieldNode field) {
		fields.add(field);
	}

	/**
	 * Adds a method to the class.
	 * 
	 * @param method
	 */
	public void addMethod(MethodNode method) {
		methods.add(method);
	}

	/**
	 * Adds an attribute to the class.
	 * 
	 * @param attribute
	 */
	public void addAttribute(Attribute attribute) {
		attributes.add(attribute);
	}

	@Override
	public String toString() {
		String out = "  Version: " + major + "." + minor + "\n";
		out += "  Access: " + access + "\n";
		out += "  Class Index: " + classIndex + "\n";
		out += "  Super Index: " + superIndex + "\n";
		out += "  Attributes[" + attributes.size() + "]\n";
		out += "  Interfaces { ";
		for (int index : interfaceIndices) {
			out += index + ", ";
		}
		int interfaceCut = interfaceIndices.size() == 0 ? 0 : 2;
		out = out.substring(0, out.length() - interfaceCut) + " }\n";
		out += "  Constant Pool {\n";
		for (Constant constant : constants) {
			out += "    " + constant.getType().toString() + " " + constant.value + "\n";
		}
		out += "  }\n";
		out += "  Fields { \n";
		for (FieldNode field : fields) {
			out += "    " + field.toString() + "\n";
		}
		out += "  }\n";
		out += "  Methods { \n";
		for (MethodNode methods : methods) {
			out += "    " + methods.toString() + "\n";
		}
		out += "  }\n";
		return out;
	}

}