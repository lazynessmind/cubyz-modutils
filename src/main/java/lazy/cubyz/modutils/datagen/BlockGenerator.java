package lazy.cubyz.modutils.datagen;

import cubyz.api.Resource;

import java.util.function.Consumer;

public abstract class BlockGenerator extends DataGenerator {

    private static final String BLOCK_MODEL = "cubyz:block.obj";

    public BlockGenerator(String modId, Consumer<DataObject> dataConsumer) {
        super(modId, dataConsumer);
    }

    public Resource blockLoc(String path) {
        return new Resource(this.modId(), "blocks/".concat(path));
    }

    public void simpleCubeBlock(Resource resource, String allSidesTexture){
        BlockDataBuilder.create(resource).texture(allSidesTexture).model(BLOCK_MODEL).build(this.dataConsumer());
    }
}
