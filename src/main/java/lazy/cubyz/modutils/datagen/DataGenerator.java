package lazy.cubyz.modutils.datagen;

import cubyz.api.Resource;

import java.util.function.Consumer;

public abstract class DataGenerator {

    private final String modId;
    private final Consumer<DataObject> dataConsumer;

    public DataGenerator(String modId, Consumer<DataObject> dataConsumer) {
        this.modId = modId;
        this.dataConsumer = dataConsumer;
    }

    public abstract void addDataBuilders(Consumer<DataObject> dataObjectConsumer);

    public abstract Resource getGeneratorID();

    public Consumer<DataObject> dataConsumer() {
        return dataConsumer;
    }

    public String modId() {
        return modId;
    }
}
