# Bytecode Modification Framework

BMF is an alternative JVM bytecode modification framework that *(if desired)* will hide nothing about the class structure behind abstraction *(But in the future abstraction will certainly be an option)*.

### Notable Features

* Simplified renaming of classes, fields, and methods
* Direct access to the constant pool. 


### Example: Renaming classes and members

Renaming classes and their members is designed currently to function given a jar file.

Here is an example for renaming a single class and one of its methods.
```java
JarReader read = new JarReader(new File(IN_FILE), true, true);
read.getMapping().getMapping("com/example/game/Edible").name.setValue("com/example/game/Consumable");
read.getMapping().getMapping("com/example/game/Edible").getMemberMapping("isRotten", "()Z").name.setValue("hasDecayed");
read.saveTo(new File(OUT_FILE));
```
To rename each item takes only a single line of code. The affects of this change are instantaneous across the program since all class names link to a single instance. 

### What needs finishing

This library is still a heavy WIP and the main focus is remapping classes, so some basic functionality not-pertaining to remapping still needs to be completed. For example there is no way to edit opcodes in a method.

### Credits

**[Matthew Dupraz](https://github.com/MatthewDupraz)** 
* Wrote the original Readme
* Box class + beginnings of the remapping system
* Large portion of the `Type` classes
* Squashing several bugs	