package me.coley.cmod;

import java.util.List;

import com.google.common.collect.Lists;

import me.coley.cmod.attribute.Attribute;
import me.coley.cmod.attribute.AttributeDeprecated;
import me.coley.cmod.attribute.AttributeSignature;
import me.coley.cmod.attribute.AttributeSynthetic;
import me.coley.cmod.attribute.IAttributeOwner;
import me.coley.cmod.attribute.annotation.AttributeAnnotations;
import me.coley.cmod.attribute.annotation.AttributeParameterAnnotations;
import me.coley.cmod.attribute.clazz.AttributeBootstrapMethods;
import me.coley.cmod.attribute.clazz.AttributeEnclosingMethod;
import me.coley.cmod.attribute.clazz.AttributeSourceDebugExtension;
import me.coley.cmod.attribute.clazz.AttributeSourceFile;
import me.coley.cmod.consts.Constant;

/**
 * A class.
 * 
 * @author Matt
 */
@SuppressWarnings("rawtypes")
class ClassNode implements IAttributeOwner {
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
	 * Attribute: If not-null, the class is deprecated.
	 */
	public AttributeDeprecated deprecated;
	/**
	 * Attribute: If not-null, the class is synthetic.
	 */
	public AttributeSynthetic synthetic;
	/**
	 * Attribute: Signature for generic information.
	 */
	public AttributeSignature signature;
	/**
	 * Attribute: Annotations
	 */
	public AttributeAnnotations runtimeInvisibleAnnotations;
	/**
	 * Attribute: Annotations
	 */
	public AttributeAnnotations runtimeVisibleAnnotations;
	/**
	 * Attribute: Annotations
	 */
	public AttributeParameterAnnotations runtimeInvisibleParamAnnotations;
	/**
	 * Attribute: Annotations
	 */
	public AttributeParameterAnnotations runtimeVisibleParamAnnotations;
	/**
	 * Attribute: Source file information
	 */
	public AttributeSourceFile sourceFile;
	/**
	 * Attribute: The method this <i>(anonymouse)</i> class resides in.
	 */
	public AttributeEnclosingMethod encloseingMethod;
	/**
	 * Attribute: Inner classes
	 */
	public AttributeInnerClasses innerClasses;
	/**
	 * Attribute: InvokeDynamic bootstrap method information.
	 */
	public AttributeBootstrapMethods bootstrapMethods;
	/**
	 * Attribute: Debug information.
	 */
	public AttributeSourceDebugExtension sourceDebug;

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

	@Override
	public void addAttribute(Attribute attribute) {
		switch (attribute.type) {
		case SOURCE_FILE:
			sourceFile = (AttributeSourceFile) attribute;
			break;
		case SOURCE_DEBUG_EXTENSION:
			sourceDebug = (AttributeSourceDebugExtension) attribute;
			break;
		case ENCLOSING_METHOD:
			encloseingMethod = (AttributeEnclosingMethod) attribute;
			break;
		case INNER_CLASSES:
			innerClasses = (AttributeInnerClasses) attribute;
			break;
		case BOOTSTRAP_METHODS:
			bootstrapMethods = (AttributeBootstrapMethods) attribute;
			break;
		case SYNTHETIC:
			synthetic = (AttributeSynthetic) attribute;
			break;
		case SIGNATURE:
			signature = (AttributeSignature) attribute;
			break;
		case DEPRECATED:
			deprecated = (AttributeDeprecated) attribute;
			break;
		case RUNTIME_INVISIBLE_ANNOTATIONS:
			runtimeInvisibleAnnotations = (AttributeAnnotations) attribute;
			break;
		case RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS:
			runtimeVisibleAnnotations = (AttributeAnnotations) attribute;
			break;
		case RUNTIME_VISIBLE_ANNOTATIONS:
			runtimeVisibleParamAnnotations = (AttributeParameterAnnotations) attribute;
			break;
		case RUNTIME_VISIBLE_PARAMETER_ANNOTATIONS:
			runtimeInvisibleParamAnnotations = (AttributeParameterAnnotations) attribute;
			break;
		default:
			break;
		}
	}

	@Override
	public List<Attribute> getAttributes() {
		List<Attribute> attributes = Lists.newArrayList();
		if (signature != null)
			attributes.add(signature);
		if (runtimeInvisibleAnnotations != null)
			attributes.add(runtimeInvisibleAnnotations);
		if (runtimeVisibleAnnotations != null)
			attributes.add(runtimeVisibleAnnotations);
		if (runtimeVisibleParamAnnotations != null)
			attributes.add(runtimeVisibleParamAnnotations);
		if (runtimeInvisibleParamAnnotations != null)
			attributes.add(runtimeInvisibleParamAnnotations);
		if (bootstrapMethods != null)
			attributes.add(bootstrapMethods);
		if (deprecated != null)
			attributes.add(deprecated);
		if (synthetic != null)
			attributes.add(synthetic);
		if (sourceFile != null)
			attributes.add(sourceFile);
		if (encloseingMethod != null)
			attributes.add(encloseingMethod);
		if (innerClasses != null)
			attributes.add(innerClasses);
		if (bootstrapMethods != null)
			attributes.add(bootstrapMethods);
		if (sourceDebug != null)
			attributes.add(sourceDebug);
		return attributes;
	}

	@Override
	public String toString() {
		String out = "  Version: " + major + "." + minor + "\n";
		out += "  Access: " + access + "\n";
		out += "  Class Index: " + classIndex + "\n";
		out += "  Super Index: " + superIndex + "\n";
		// out += " Attributes[" + attributes.size() + "]\n";
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