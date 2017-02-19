# Bytecode Modification Framework

BMF is an alternative to ASM, BCEL and other JVM bytecode modification frameworks.
Its aim is to provide better means of accessing and modifying the class file with little hidden away behind abstraction.

### So what's so special about it?

1. BMF provides direct access to the constant pool. 
2. BMF makes renaming classes, fields, and methods incredibly simple.

For example with BMF renaming classes and methods is as easy as updating a single string and can each be done in 3 lines at the time of this commit. 

Here is an example for renaming a single class *(+1 line for renaming a method as well)*:
```java
JarReader read = new JarReader(new File(IN_FILE), true, true);
read.getMapping().getMapping("com/example/game/Edible").name.setValue("com/example/game/Consumable");
read.getMapping().getMapping("com/example/game/Edible").getMemberMapping("isRotten", "()Z").name.setValue("hasDecayed");
read.saveTo(new File(OUT_FILE));
```
In the example program all the referenced to the class `Edible` are updated `Consumable` and the method `isRotten` is renamed through the entire class hierarchy and in all of its references.

### Sounds cool! Any idea when it might be ready?

No clue. 

We are finishing up basic functionality and adding a few neat features that will make this framework even more special.
