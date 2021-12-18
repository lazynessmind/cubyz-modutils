package lazy.cubyz.modutils.datagen.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Used on {@link BlockDataBuilder} to add ore properties on the block.
 * {@link BlockDataBuilder#ore(OreDataBuilder)}
 */
@SuppressWarnings("unused")
public class OreDataBuilder {

    protected int veins = 1;
    protected int size = 1;
    protected int height = 1;
    protected float density = 0.5f;
    protected List<String> sources = new ArrayList<>();

    public static OreDataBuilder create() {
        return new OreDataBuilder();
    }

    public OreDataBuilder veins(int veins) {
        this.veins = veins;
        return this;
    }

    public OreDataBuilder size(int size) {
        this.size = size;
        return this;
    }

    public OreDataBuilder height(int height) {
        this.height = height;
        return this;
    }

    public OreDataBuilder density(float density) {
        this.density = density;
        return this;
    }

    public OreDataBuilder addSources(List<String> sources) {
        this.sources.addAll(sources);
        return this;
    }

    public OreDataBuilder addSource(String sources) {
        this.sources.add(sources);
        return this;
    }
}
