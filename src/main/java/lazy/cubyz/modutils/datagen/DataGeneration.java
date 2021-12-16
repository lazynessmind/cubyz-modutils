package lazy.cubyz.modutils.datagen;

import cubyz.api.Resource;
import cubyz.utils.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class DataGeneration {

    private static final String ASSETS_PATH = "./assets/";
    private final LinkedList<DataGenerator> dataGenerators = new LinkedList<>();
    private final List<DataObject> dataObjects = new ArrayList<>();

    private final String modId;
    private final Consumer<DataObject> dataConsumer;
    private final Properties cachedFiles;

    public DataGeneration(String modId) {
        this.modId = modId;
        this.dataConsumer = this.dataObjects::add;
        this.cachedFiles = this.loadCacheFile();
    }

    public void addGenerator(BiFunction<String, Consumer<DataObject>, DataGenerator> generatorFactory) {
        this.dataGenerators.add(generatorFactory.apply(this.modId, this.dataConsumer));
    }

    public void runDataGenerators() {
        while (!this.dataGenerators.isEmpty()) {
            DataGenerator generator = this.dataGenerators.removeLast();
            Logger.info("Running generator " + generator.getGeneratorID() + " from " + this.modId);
            this.dataObjects.clear();
            generator.addDataBuilders(this.dataConsumer);
            this.generateFiles();
        }
        try {
            FileWriter writer = new FileWriter(ASSETS_PATH.concat("cacheFile.txt"));
            this.cachedFiles.store(writer, "");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateFiles() {
        for (DataObject dataObject : dataObjects) {
            String filePath = ASSETS_PATH.concat(dataObject.getLocation().toString().replace(":", "/").concat(".json"));
            if(!this.checkIsCached(dataObject)){
                try {
                    FileWriter writer = new FileWriter(filePath);
                    writer.append(dataObject.buildObject().toString());
                    writer.close();
                    Logger.info("   > Created " + dataObject.getLocation() + ".");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Logger.info("   > Skipped " + dataObject.getLocation() + ". File is already cached.");
            }
        }
    }

    private boolean checkIsCached(DataObject object) {
        Resource resource = object.getLocation();
        String md5Checksum = getMD5Checksum(object.buildObject().toString());
        String fileName = resource.getID().concat(".json");
        boolean hasKey = this.cachedFiles.containsKey(fileName);
        boolean isTheSameKey = hasKey && this.cachedFiles.get(fileName).equals(md5Checksum);
        if (!hasKey || !isTheSameKey) {
            this.cachedFiles.put(fileName, md5Checksum);
            return false;
        } else {
            return true;
        }
    }

    private Properties loadCacheFile() {
        Properties props = new Properties();
        if (Files.exists(Paths.get(ASSETS_PATH.concat("cacheFile.txt")))) {
            try {
                FileReader reader = new FileReader(ASSETS_PATH.concat("cacheFile.txt"));
                props.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Files.createFile(Paths.get(ASSETS_PATH.concat("cacheFile.txt")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return props;
    }

    public static byte[] createChecksum(String fileContent) throws Exception {
        InputStream fis = new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8));
        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("MD5");
        int numRead;
        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);
        fis.close();
        return complete.digest();
    }

    public static String getMD5Checksum(String fileContent) {
        byte[] b = new byte[0];
        try {
            b = createChecksum(fileContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder result = new StringBuilder();

        for (byte value : b) {
            result.append(Integer.toString((value & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }
}
