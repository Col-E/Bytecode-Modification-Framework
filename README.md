# Bytecode Modification Framework (BMF)

BMF is an alternative to ASM, BCEL and other JVM bytecode modification frameworks.
Its aim is to provide better means of accessing and modifying the class file with little hidden away behind abstraction. The only abstraction is what powers some of the more special features of BMF.

### So what's so special about it?

BMF provides direct access to the constant pool. Some of the special features hinted at previously take advantage of how impactful even small changes become in the constant pool.

For example with BMF renaming classes is as easy as updating a single string and can be done in 6 lines at the time of this commit.

Here is an example for renaming a single class:
```java
    JarReader read = new JarReader(new File("input.jar"), true);
    read.genMappings(JarReader.PASS_MAKE_CLASS);
    read.genMappings(JarReader.PASS_MAKE_MEMBER_DATA);
    read.genMappings(JarReader.PASS_UPDATE_CONSTANTS);
    read.getMapping().getMapping("com/example/OldClassName").name.value = "com/example/NewClassName";
    read.saveTo(new File("output.jar"));
```

### Sounds cool! Any idea when it might be ready?

We are still finishing up the basic functionalities and adding a few neat features that will make this framework even more special.
We honestly have no idea when we'll manage to finish the first version. 
