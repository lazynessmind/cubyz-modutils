# cubyz-modutils

Modding utils for Cubyz

## Contents: 

### **DataGeneration:**
Helper class to generate Cubyz data oriented objects like Blocks.
   - Contains a simple implementation of MD5 checksum to check if the file already exists to prevent regeneration of the same file.
   - Example: [Here](https://github.com/lazynessmind/cubyz-modutils/blob/main/src/test/java/lazy/cubyz/modutils/test/DataGenTest.java)
   
#### Block Generation TODO:
   - [ ] Block: MultiTextureVariants
   - [ ] Block: AnimatedTextures
   - [ ] Block: Individual Texture (Currently only accepts one texture for all sides)
   - [ ] Block: BlockEntity class reference (Maybe)
### Build:

```shell
> git clone https://github.com/lazynessmind/cubyz-modutils.git
> cd cubyz-modutils
```

- If you want an .jar you run the following command:

```shell
> mvn clean package
```

- If you want to deploy on your local maven:
```shell
> mvn clean install
```
