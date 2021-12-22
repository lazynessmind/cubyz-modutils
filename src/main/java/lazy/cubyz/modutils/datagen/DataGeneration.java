package lazy.cubyz.modutils.datagen;

import cubyz.utils.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * Class that runs the generators and creates the data files.
 *
 * General use:
 *
 * DataGeneration gen = new DataGeneration("mymod");
 * gen.addGenerator(myGenerator);
 * gen.runDataGenerators();
 */
public class DataGeneration {

    private static final String ASSETS_PATH = "./assets/";
    private final LinkedList<DataGenerator> dataGenerators = new LinkedList<>();
    private final List<DataObject> dataObjects = new ArrayList<>();

    private final String modId;
    private final Consumer<DataObject> dataConsumer;
    private final CacheSystem cacheSystem;

    public DataGeneration(String modId) {
        this.modId = modId;
        this.dataConsumer = this.dataObjects::add;
        this.cacheSystem = new CacheSystem(modId);
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
        this.cacheSystem.saveCache(this.modId);
    }

    private void generateFiles() {
        for (DataObject dataObject : dataObjects) {
            String filePath = ASSETS_PATH.concat(dataObject.getLocation().toString().replace(":", "/").concat(".json"));
            if (!this.cacheSystem.isDataObjectCached(dataObject)) {
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
}
