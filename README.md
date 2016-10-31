# Bytecode Modification Framework (BMF)

BMF is an alternative to ASM, BCEL and other JVM bytecode modification frameworks.
Its aim is to provide better means of accessing and modifying the class file with little hidden away behind abstraction. The only abstraction is what powers some of the more special features of BMF.

### So what's so special about it?

BMF provides direct access to the constant pool. Some of the special features hinted at previously take advantage of how impactful even small changes become in the constant pool.

For example with BMF renaming classes and methods is as easy as updating a single string and can be done in 4 lines at the time of this commit. 

Here is an example for renaming a single class *(+1 line for renaming a method as well)*:
```java
JarReader read = new JarReader(new File(file), true);
read.genMappings();
read.getMapping().getMapping("com/example/test/Edible").name.setValue("com/example/test/Consumable");
read.getMapping().getMapping("com/example/test/Edible").getMemberMapping("isRotten", "()Z").name.setValue("renamedRotten");
read.saveTo(new File(OUT_FILE));
```
In the example program the class `Edible` is updated everwhere with `Consumable` and the method `isRotten` is also updated everwhere regardless of class hierarchy.

### Sounds cool! Any idea when it might be ready?

We are still finishing up the basic functionalities and adding a few neat features that will make this framework even more special.
We honestly have no idea when we'll manage to finish the first version. 
