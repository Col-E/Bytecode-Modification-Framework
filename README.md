# Bytecode Modification Framework (BMF)

BMF is an alternative to ASM, BCEL and other JVM bytecode modification frameworks.
Its aim is to provide a more efficient, advanced and versatile way to modify bytecode.

### So what's so special about it?

BMF provides full control over the constant pool, without losing on ease-of-use.
This approach provides a way to modify different values, very efficiently.

Think about ASM, how would you rename a class? You would need to search for every single reference and change it.
This is not only non-practical, but also very slow.  
With BMF, you can just modify a few class pool constants and you're done.

### Sounds cool! Any idea when it might be ready?

We are still finishing up the basic functionalities and adding a few neat features that will make this framework even more special.
We honestly have no idea when we'll manage to finish the first version.
