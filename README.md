# cubyz-modutils

Modding utils for Cubyz

### Contents: 

- **DataGeneration:** Helper class to generate Cubyz data oriented objects like Blocks.
   - Contains an simple implementation of MD5 checksum to check if the file already exists to prevent regeneration of the same file.


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
