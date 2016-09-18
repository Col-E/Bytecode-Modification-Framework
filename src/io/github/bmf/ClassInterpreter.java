package io.github.bmf;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;

import io.github.bmf.attribute.Attribute;
import io.github.bmf.attribute.AttributeDeprecated;
import io.github.bmf.attribute.AttributeSignature;
import io.github.bmf.attribute.AttributeSynthetic;
import io.github.bmf.attribute.AttributeType;
import io.github.bmf.attribute.annotation.Annotation;
import io.github.bmf.attribute.annotation.AttributeAnnotationDefault;
import io.github.bmf.attribute.annotation.AttributeAnnotations;
import io.github.bmf.attribute.annotation.AttributeParameterAnnotations;
import io.github.bmf.attribute.annotation.ParameterAnnotations;
import io.github.bmf.attribute.annotation.element.ElementValue;
import io.github.bmf.attribute.annotation.element.ElementValuePair;
import io.github.bmf.attribute.clazz.AttributeBootstrapMethods;
import io.github.bmf.attribute.clazz.AttributeEnclosingMethod;
import io.github.bmf.attribute.clazz.AttributeInnerClasses;
import io.github.bmf.attribute.clazz.AttributeSourceDebugExtension;
import io.github.bmf.attribute.clazz.AttributeSourceFile;
import io.github.bmf.attribute.clazz.BootstrapMethod;
import io.github.bmf.attribute.clazz.InnerClass;
import io.github.bmf.attribute.field.AttributeConstantValue;
import io.github.bmf.attribute.method.AttributeCode;
import io.github.bmf.attribute.method.AttributeExceptions;
import io.github.bmf.attribute.method.AttributeLineNumberTable;
import io.github.bmf.attribute.method.AttributeLocalVariableTable;
import io.github.bmf.attribute.method.AttributeLocalVariableTypeTable;
import io.github.bmf.attribute.method.AttributeStackMapTable;
import io.github.bmf.attribute.method.LineNumberTable;
import io.github.bmf.attribute.method.LocalVariableTable;
import io.github.bmf.attribute.method.LocalVariableType;
import io.github.bmf.attribute.method.MethodException;
import io.github.bmf.attribute.method.OpcodeListData_TEMP;
import io.github.bmf.consts.*;
import io.github.bmf.exception.InvalidClassException;
import io.github.bmf.io.StreamUtil;

/**
 * 
 * @author Matt
 *
 */
@SuppressWarnings("rawtypes")
class ClassInterpreter {
	public static ClassNode getNode(byte[] data) throws InvalidClassException, IOException {
		DataInputStream is = StreamUtil.fromBytes(data);
		if (is.readInt() != 0xCAFEBABE) {
			throw new InvalidClassException();
		}
		// Create the node
		ClassNode node = new ClassNode();
		// Read version information
		node.minor = is.readUnsignedShort();
		node.major = is.readUnsignedShort();
		// Read the constant pool
		int poolIndex = 1;
		int poolLength = is.readUnsignedShort();
		while (poolIndex < poolLength) {
			ConstantType type = ConstantType.fromTag(is.readUnsignedByte());
			Constant constant = readConst(type, is);
			node.addConst(constant);
			if (type == ConstantType.DOUBLE || type == ConstantType.LONG) {
				// They take up 2 constant places in the pool.
				node.addConst(null);
				poolIndex++;
			}
			poolIndex++;
		}
		// Read access and index information
		node.access = is.readUnsignedShort();
		node.classIndex = is.readUnsignedShort();
		node.superIndex = is.readUnsignedShort();
		// Read interfaces
		int interfaceLength = is.readUnsignedShort();
		for (int i = 0; i < interfaceLength; i++) {
			node.addInterfaceIndex(is.readUnsignedShort());
		}
		// Read fields
		int fieldLength = is.readUnsignedShort();
		for (int i = 0; i < fieldLength; i++) {
			FieldNode field = readField(node, is);
			node.addField(field);
		}
		// Read methods
		int methodLength = is.readUnsignedShort();
		for (int i = 0; i < methodLength; i++) {
			MethodNode method = readMethod(node, is);
			node.addMethod(method);
		}
		// Read class attributes
		int attribsLength = is.readUnsignedShort();
		for (int i = 0; i < attribsLength; i++) {
			Attribute attribute = readAttribute(node, is);
			node.addAttribute(attribute);
		}
		return node;
	}

	private static FieldNode readField(ClassNode owner, DataInputStream is) throws IOException {
		FieldNode field = new FieldNode(owner);
		field.access = is.readUnsignedShort();
		field.name = is.readUnsignedShort();
		field.desc = is.readUnsignedShort();
		int attribsLength = is.readUnsignedShort();
		for (int i = 0; i < attribsLength; i++) {
			Attribute attribute = readAttribute(owner, is);
			field.addAttribute(attribute);
		}
		return field;
	}

	private static MethodNode readMethod(ClassNode owner, DataInputStream is) throws IOException {
		MethodNode method = new MethodNode(owner);
		method.access = is.readUnsignedShort();
		method.name = is.readUnsignedShort();
		method.desc = is.readUnsignedShort();
		int attribsLength = is.readUnsignedShort();
		for (int i = 0; i < attribsLength; i++) {
			Attribute attribute = readAttribute(owner, is);
			method.addAttribute(attribute);
		}
		return method;
	}

	private static Attribute readAttribute(ClassNode owner, DataInputStream is) throws IOException {
		int nameIndex = is.readUnsignedShort();
		int length = is.readInt();
		String name = owner.getConst(nameIndex).value.toString();
		AttributeType attributeType = AttributeType.fromName(name);
		switch (attributeType) {
		case ANNOTATION_DEFAULT: {
			byte[] annotationData = new byte[length];
			is.read(annotationData);
			return new AttributeAnnotationDefault(nameIndex, annotationData);
		}
		case BOOTSTRAP_METHODS: {
			int methods = is.readUnsignedShort();
			List<BootstrapMethod> bsMethods = Lists.newArrayList();
			for (int i = 0; i < methods; i++) {
				int methodRef = is.readUnsignedShort();
				int args = is.readUnsignedShort();
				BootstrapMethod bsm = new BootstrapMethod(methodRef);
				for (int a = 0; a < args; a++) {
					bsm.addArgument(is.readUnsignedShort());
				}
				bsMethods.add(bsm);
			}
			return new AttributeBootstrapMethods(nameIndex, bsMethods);
		}
		case CODE: {
			int maxStack = is.readUnsignedShort();
			int maxLocals = is.readUnsignedShort();
			int codeLength = is.readInt();
			byte[] code = new byte[codeLength];
			is.read(code);
			OpcodeListData_TEMP codeData = new OpcodeListData_TEMP();
			codeData.data = code;
			int exceptionLength = is.readUnsignedShort();
			for (int i = 0; i < exceptionLength; i++) {
				MethodException mexeption = new MethodException();
				int rangeStart = is.readUnsignedShort();
				int rangeEnd = is.readUnsignedShort();
				int rangeHandler = is.readUnsignedShort();
				int catchType = is.readUnsignedShort();
				mexeption.start = rangeStart;
				mexeption.end = rangeEnd;
				mexeption.handler = rangeHandler;
				mexeption.type = catchType;
				codeData.addException(mexeption);
			}
			List<Attribute> attributes = Lists.newArrayList();
			int attributeLength = is.readUnsignedShort();
			for (int i = 0; i < attributeLength; i++) {
				Attribute attribute = readAttribute(owner, is);
				attributes.add(attribute);
			}
			return new AttributeCode(nameIndex, maxStack, maxLocals, codeData, attributes);
		}
		case CONSTANT_VALUE: {
			int value = is.readUnsignedShort();
			return new AttributeConstantValue(nameIndex, value);
		}
		case DEPRECATED: {
			return new AttributeDeprecated(nameIndex);
		}
		case ENCLOSING_METHOD: {
			int classIndex = is.readUnsignedShort();
			int methodIndex = is.readUnsignedShort();
			return new AttributeEnclosingMethod(nameIndex, classIndex, methodIndex);
		}
		case EXCEPTIONS: {
			int exceptionCount = is.readUnsignedShort();
			List<Integer> exceptionIndices = Lists.newArrayList();
			for (int i = 0; i < exceptionCount; i++) {
				exceptionIndices.add(is.readUnsignedShort());
			}
			return new AttributeExceptions(nameIndex, exceptionIndices);
		}
		case INNER_CLASSES: {
			int classCount = is.readUnsignedShort();
			List<InnerClass> classes = Lists.newArrayList();
			for (int i = 0; i < classCount; i++) {
				int innerIndex = is.readUnsignedShort();
				int outerIndex = is.readUnsignedShort();
				int cInnerName = is.readUnsignedShort();
				int innerAccess = is.readUnsignedShort();
				classes.add(new InnerClass(innerIndex, outerIndex, cInnerName, innerAccess));
			}
			return new AttributeInnerClasses(nameIndex, classes);
		}
		case LINE_NUMBER_TABLE: {
			LineNumberTable table = new LineNumberTable();
			int tableLength = is.readUnsignedShort();
			for (int i = 0; i < tableLength; i++) {
				int startPC = is.readUnsignedShort();
				int lineNumber = is.readUnsignedShort();
				table.add(startPC, lineNumber);
			}
			return new AttributeLineNumberTable(nameIndex, table);
		}
		case LOCAL_VARIABLE_TYPE_TABLE: {
			int tableLength = is.readUnsignedShort();
			List<LocalVariableType> variableTypes = Lists.newArrayList();
			for (int i = 0; i < tableLength; i++) {
				int varStart = is.readUnsignedShort();
				int varLen = is.readUnsignedShort();
				int varName = is.readUnsignedShort();
				int varSignature = is.readUnsignedShort();
				int varIndex = is.readUnsignedShort();
				variableTypes.add(new LocalVariableType(varStart, varLen, varName, varSignature, varIndex));
			}
			return new AttributeLocalVariableTypeTable(nameIndex, variableTypes);
		}
		case LOCAL_VARIABLE_TABLE: {
			LocalVariableTable locals = new LocalVariableTable();
			int tableLength = is.readUnsignedShort();
			for (int i = 0; i < tableLength; i++) {
				int lstartPC = is.readUnsignedShort();
				int llength = is.readUnsignedShort();
				int lname = is.readUnsignedShort();
				int ldesc = is.readUnsignedShort();
				int lindex = is.readUnsignedShort();
				locals.add(lstartPC, llength, lname, ldesc, lindex);
			}
			return new AttributeLocalVariableTable(nameIndex, locals);
		}
		case RUNTIME_VISIBLE_PARAMETER_ANNOTATIONS:
		case RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS:
		case RUNTIME_VISIBLE_ANNOTATIONS:
		case RUNTIME_INVISIBLE_ANNOTATIONS: {
			return readAnnotatons(owner, attributeType, is, nameIndex, length);
		}
		case SIGNATURE: {
			int sig = is.readUnsignedShort();
			return new AttributeSignature(nameIndex, sig);
		}
		case SOURCE_DEBUG_EXTENSION: {
			List<Integer> data = Lists.newArrayList();
			for (int i = 0; i < length; i++) {
				data.add(is.readUnsignedByte());
			}
			return new AttributeSourceDebugExtension(nameIndex, data);
		}
		case SOURCE_FILE: {
			int source = is.readUnsignedShort();
			return new AttributeSourceFile(nameIndex, source);
		}
		case STACK_MAP_TABLE: {
			byte[] data = new byte[length];
			is.read(data);
			return new AttributeStackMapTable(nameIndex, data);
		}
		case SYNTHETIC: {
			return new AttributeSynthetic(nameIndex);
		}
		default:
			break;
		}
		throw new RuntimeException("Unhandled attribute! " + attributeType);
	}

	private static Attribute readAnnotatons(ClassNode owner, AttributeType type, DataInputStream is, int nameIndex,
			int length) throws IOException {
		boolean param = type == AttributeType.RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS
				|| type == AttributeType.RUNTIME_VISIBLE_PARAMETER_ANNOTATIONS;
		boolean invisible = type == AttributeType.RUNTIME_INVISIBLE_ANNOTATIONS
				|| type == AttributeType.RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS;
		int num = param ? is.readUnsignedByte() : is.readUnsignedShort();
		switch (type) {
		case RUNTIME_INVISIBLE_ANNOTATIONS:
		case RUNTIME_VISIBLE_ANNOTATIONS:
			List<Annotation> annotations = Lists.newArrayList();
			for (int i = 0; i < num; i++) {
				annotations.add(readAnnotation(owner, is));
			}
			return new AttributeAnnotations(nameIndex, invisible, annotations);
		case RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS:
		case RUNTIME_VISIBLE_PARAMETER_ANNOTATIONS:
			List<ParameterAnnotations> paramAnnotations = Lists.newArrayList();
			for (int i = 0; i < num; i++) {
				paramAnnotations.add(readParameterAnnotations(owner, is));
			}
			return new AttributeParameterAnnotations(nameIndex, invisible, paramAnnotations);

		default:
			throw new RuntimeException("UNKNOWN ANNOTATION: " + type.name());
		}
	}

	private static Annotation readAnnotation(ClassNode owner, DataInputStream is) throws IOException {
		int typeIndex = is.readUnsignedShort();
		int num = is.readUnsignedShort();
		List<ElementValuePair> valuePairs = Lists.newArrayList();
		for (int i = 0; i < num; i++) {
			valuePairs.add(readElementValuePair(owner, is));
		}
		return new Annotation(typeIndex, valuePairs);
	}

	private static ElementValuePair readElementValuePair(ClassNode owner, DataInputStream is) throws IOException {
		int nameIndex = is.readUnsignedShort();
		ElementValue value = readElementValue(owner, is);
		return new ElementValuePair(nameIndex, value);
	}

	private static ElementValue readElementValue(ClassNode owner, DataInputStream is) throws IOException {
		int tag = is.readUnsignedByte();
		// TODO: If tag isn't an index in the constant pool (At least it never
		// says anything on the jvm specs page) then how the hell is it used to
		// get a char value if char is 2 bytes but tag is 1???
		System.err.println("TAG: " + (char) tag);
		throw new RuntimeException("Found a parameter annotation. Gotta implement that next.");
	}

	private static ParameterAnnotations readParameterAnnotations(ClassNode owner, DataInputStream is)
			throws IOException {
		int num = is.readUnsignedShort();
		List<Annotation> annotations = Lists.newArrayList();
		for (int i = 0; i < num; i++) {
			annotations.add(readAnnotation(owner, is));
		}
		return new ParameterAnnotations(annotations);
	}

	private static Constant readConst(ConstantType constType, DataInputStream is) throws IOException {
		// Braces in switches are ugly, but having variable name pass-through
		// sucks.
		switch (constType) {
		case DOUBLE: {
			double value = is.readDouble();
			return new ConstDouble(value);
		}
		case LONG: {
			long value = is.readLong();
			return new ConstLong(value);
		}
		case FLOAT: {
			float value = is.readFloat();
			return new ConstFloat(value);
		}
		case INT: {
			int value = is.readInt();
			return new ConstInt(value);
		}
		case STRING: {
			int index = is.readUnsignedShort();
			return new ConstString(index);
		}
		case UTF8: {
			int length = is.readUnsignedShort();
			byte[] data = new byte[length];
			is.read(data);
			return new ConstUTF8(new String(data));
		}
		case CLASS: {
			int name = is.readUnsignedShort();
			return new ConstClass(name);
		}
		case FIELD: {
			int clazz = is.readUnsignedShort();
			int nameType = is.readUnsignedShort();
			return new ConstField(clazz, nameType);
		}
		case METHOD: {
			int clazz = is.readUnsignedShort();
			int nameType = is.readUnsignedShort();
			return new ConstMethod(clazz, nameType);
		}
		case INTERFACE_METHOD: {
			int clazz = is.readUnsignedShort();
			int nameType = is.readUnsignedShort();
			return new ConstInterfaceMethod(clazz, nameType);
		}
		case INVOKEDYNAMIC: {
			int attribute = is.readUnsignedShort();
			int nameType = is.readUnsignedShort();
			return new ConstInvokeDynamic(attribute, nameType);
		}
		case METHOD_HANDLE: {
			int kind = is.readUnsignedByte();
			int index = is.readUnsignedShort();
			return new ConstMethodHandle(kind, index);
		}
		case METHOD_TYPE: {
			int type = is.readUnsignedShort();
			return new ConstMethodType(type);
		}
		case NAME_TYPE: {
			int name = is.readUnsignedShort();
			int desc = is.readUnsignedShort();
			return new ConstNameType(name, desc);
		}
		default:
			break;
		}
		return null;
	}
}