package me.coley.cmod;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;

import me.coley.cmod.attribute.Attribute;
import me.coley.cmod.attribute.AttributeType;
import me.coley.cmod.attribute.annotation.AttributeAnnotationDefault;
import me.coley.cmod.attribute.annotation.AttributeAnnotations;
import me.coley.cmod.attribute.annotation.AttributeParameterAnnotations;
import me.coley.cmod.attribute.clazz.AttributeBootstrapMethods;
import me.coley.cmod.attribute.clazz.BootstrapMethod;
import me.coley.cmod.attribute.field.AttributeConstantValue;
import me.coley.cmod.attribute.method.AttributeCode;
import me.coley.cmod.attribute.method.AttributeLineNumberTable;
import me.coley.cmod.attribute.method.AttributeLocalVariableTable;
import me.coley.cmod.attribute.method.AttributeLocalVariableTypeTable;
import me.coley.cmod.attribute.method.LineNumberTable;
import me.coley.cmod.attribute.method.LocalVariableTable;
import me.coley.cmod.attribute.method.LocalVariableType;
import me.coley.cmod.attribute.method.MethodException;
import me.coley.cmod.attribute.method.OpcodeListData_TEMP;
import me.coley.cmod.consts.*;
import me.coley.cmod.exception.InvalidClassException;
import me.coley.cmod.io.StreamUtil;

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
		int interfaceIndex = 0;
		int interfaceLength = is.readUnsignedShort();
		while (interfaceIndex < interfaceLength) {
			node.addInterfaceIndex(is.readUnsignedShort());
			interfaceIndex++;
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
		// https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.7
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
			break;
		}
		case ENCLOSING_METHOD: {
			break;
		}
		case EXCEPTIONS: {
			break;
		}
		case INNER_CLASSES: {
			break;
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
			// TODO: Actually figure out the structure then work on these.
			// Until then, slapping the data in a byte array should be OK.
		case RUNTIME_VISIBLE_PARAMETER_ANNOTATIONS:
		case RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS: {
			boolean invis = attributeType == AttributeType.RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS;
			byte[] annotationData = new byte[length];
			is.read(annotationData);
			return new AttributeParameterAnnotations(nameIndex, invis, annotationData);
		}
		case RUNTIME_VISIBLE_ANNOTATIONS:
		case RUNTIME_INVISIBLE_ANNOTATIONS: {
			boolean invis = attributeType == AttributeType.RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS;
			byte[] annotationData = new byte[length];
			is.read(annotationData);
			return new AttributeAnnotations(nameIndex, invis, annotationData);
		}
		case SIGNATURE: {
			break;
		}
		case SOURCE_DEBUG_EXTENSION: {
			break;
		}
		case SOURCE_FILE: {
			break;
		}
		case STACK_MAP_TABLE: {
			break;
		}
		case SYNTHETIC: {
			break;
		}
		default:
			break;
		}
		throw new RuntimeException("Unhandled attribute! " + attributeType);
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