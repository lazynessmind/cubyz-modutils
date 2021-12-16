package lazy.cubyz.modutils.datagen.generetors;

import cubyz.api.Resource;
import lazy.cubyz.modutils.datagen.DataGenerator;
import lazy.cubyz.modutils.datagen.DataObject;
import lazy.cubyz.modutils.datagen.builder.BlockDataBuilder;
import lazy.cubyz.modutils.datagen.builder.OreDataBuilder;

import java.util.function.Consumer;

public abstract class BlockGenerator extends DataGenerator {

    protected static final String BLOCK_MODEL = "cubyz:block.obj";
    protected static final String DROP_ITSELF = "auto";
    protected static final String NO_ROTATION = "cubyz:no_rotation";

    public BlockGenerator(String modId, Consumer<DataObject> dataConsumer) {
        super(modId, dataConsumer);
    }

    public Resource blockLoc(String path) {
        return new Resource(this.modId(), "blocks/".concat(path));
    }

    public void simpleCubeBlock(Resource resource, String allSidesTexture) {
        BlockDataBuilder.create(resource).texture(allSidesTexture).model(BLOCK_MODEL).addDrop(DROP_ITSELF).build(this.dataConsumer());
    }

    public void simpleOre(Resource resource, String allSidesTexture, int height, int veins, int size, float density) {
        BlockDataBuilder.create(resource).texture(allSidesTexture).model(BLOCK_MODEL).addDrop(DROP_ITSELF)
                .ore(OreDataBuilder.create().density(density).height(height).size(size).veins(veins)).build(this.dataConsumer());
    }
}
