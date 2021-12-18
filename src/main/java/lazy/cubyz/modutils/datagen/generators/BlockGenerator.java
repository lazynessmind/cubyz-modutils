package lazy.cubyz.modutils.datagen.generators;

import cubyz.api.Resource;
import lazy.cubyz.modutils.datagen.DataGenerator;
import lazy.cubyz.modutils.datagen.DataObject;
import lazy.cubyz.modutils.datagen.builder.BlockDataBuilder;
import lazy.cubyz.modutils.datagen.builder.OreDataBuilder;

import java.util.List;
import java.util.function.Consumer;

/**
 * Helper class to with Block specific methods for auto DataGeneration
 *
 * Currently, this has only a few block creation methods, but you can use this for more customization:
 * @see BlockDataBuilder
 * @see OreDataBuilder (This one is used in {@link BlockDataBuilder#ore(OreDataBuilder)})
 */
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

    public void simpleCubeBlock(Resource resource, String allSidesTexture, String model) {
        BlockDataBuilder.create(resource).texture(allSidesTexture).model(model).addDrop(DROP_ITSELF).build(this.dataConsumer());
    }

    public void simpleCubeBlock(Resource resource, String allSidesTexture) {
        this.simpleCubeBlock(resource, allSidesTexture, BLOCK_MODEL);
    }

    public void simpleOre(Resource resource, String allSidesTexture, int height, int veins, int size, float density) {
        BlockDataBuilder.create(resource).texture(allSidesTexture).addDrop(DROP_ITSELF)
                .ore(OreDataBuilder.create().density(density).height(height).size(size).veins(veins).addSource("cubyz:stone")).build(this.dataConsumer());
    }

    public void simpleAnimatedBlock(Resource resource, List<String> textures, int time) {
        BlockDataBuilder.create(resource).textures(textures).model(BLOCK_MODEL).addDrop(DROP_ITSELF)
                .time(time).build(this.dataConsumer());
    }

    public void simpleMultiVariantBlock(Resource resource, String allSidesTexture, List<String> variantTextures){
        BlockDataBuilder.create(resource).texture(allSidesTexture).addDrop(DROP_ITSELF).addTextureVariants(variantTextures).build(this.dataConsumer());
    }
}
