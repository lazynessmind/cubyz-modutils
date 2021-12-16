package lazy.cubyz.modutils.datagen.builder;

import cubyz.api.Resource;
import cubyz.utils.Logger;
import cubyz.utils.json.JsonArray;
import cubyz.utils.json.JsonObject;
import cubyz.world.blocks.Blocks;
import lazy.cubyz.modutils.datagen.DataObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BlockDataBuilder implements DataObject {

    private Resource id;
    private float breakingPower;
    private float hardness;
    private String blockClass = "";
    private int emittedLight;
    private int absorbedLight;
    private boolean degradable;
    private boolean selectable;
    private boolean solid;
    private String gui = "";
    private boolean transparent;
    private boolean viewThrough;
    private String rotation = "";
    private List<String> drops = new ArrayList<>();
    private String model = "cubzy:block.obj";
    private List<String> textures = new ArrayList<>();
    private int time = 0;

    private OreDataBuilder oreDataBuilder = null;

    private BlockDataBuilder(Resource location) {
        this.id = location;
    }

    public static BlockDataBuilder create(Resource location) {
        return new BlockDataBuilder(location);
    }

    public BlockDataBuilder model(String model) {
        this.model = model;
        return this;
    }

    public BlockDataBuilder texture(String texture) {
        this.textures.add(texture);
        return this;
    }

    public BlockDataBuilder textures(List<String> texture) {
        this.textures.addAll(texture);
        return this;
    }


    public BlockDataBuilder breakingPower(float breakingPower) {
        this.breakingPower = breakingPower;
        return this;
    }

    public BlockDataBuilder hardness(float hardness) {
        this.hardness = hardness;
        return this;
    }

    public BlockDataBuilder blockClass(Blocks.BlockClass blockClass) {
        this.blockClass = blockClass.toString();
        return this;
    }

    public BlockDataBuilder emittedLight(int emittedLight) {
        this.emittedLight = emittedLight;
        return this;
    }

    public BlockDataBuilder absorbedLight(int absorbedLight) {
        this.absorbedLight = absorbedLight;
        return this;
    }

    public BlockDataBuilder degradable(boolean degradable) {
        this.degradable = degradable;
        return this;
    }

    public BlockDataBuilder selectable(boolean selectable) {
        this.selectable = selectable;
        return this;
    }

    public BlockDataBuilder solid(boolean solid) {
        this.solid = solid;
        return this;
    }

    public BlockDataBuilder gui(String gui) {
        this.gui = gui;
        return this;
    }

    public BlockDataBuilder transparent(boolean transparent) {
        this.transparent = transparent;
        return this;
    }

    public BlockDataBuilder viewThrough(boolean viewThrough) {
        this.viewThrough = viewThrough;
        return this;
    }

    public BlockDataBuilder rotation(String rotation) {
        this.rotation = rotation;
        return this;
    }

    public BlockDataBuilder addDrop(String drop) {
        this.drops.add(drop);
        return this;
    }

    public BlockDataBuilder addDrops(List<String> drops) {
        this.drops.addAll(drops);
        return this;
    }

    public BlockDataBuilder ore(OreDataBuilder builder) {
        this.oreDataBuilder = builder;
        return this;
    }

    public BlockDataBuilder time(int time) {
        this.time = time;
        return this;
    }

    public void build(Consumer<DataObject> obj) {
        obj.accept(this);
    }

    @Override
    public void serialize(JsonObject object) {
        if (this.textures.size() == 1) {
            object.put("texture", this.textures.get(0));
        } else if (this.textures.size() > 1 && this.time > 0) {
            JsonObject animTextObj = new JsonObject();
            animTextObj.put("time", this.time);
            JsonArray texArr = new JsonArray();
            texArr.addStrings(this.textures.toArray(new String[0]));
            animTextObj.put("textures", texArr);
            object.put("texture", animTextObj);
        } else if (this.textures.size() > 1 && this.time == 0) {
            object.put("texture", this.textures.get(0));
            Logger.warning("Block " + this.id + " has animation textures but the animation time wasn't set. Call time(int) to set the animation time.");
        }
        object.put("model", this.model);
        if (this.breakingPower != 0f) object.put("breakingPower", this.breakingPower);
        if (this.hardness != 0f) object.put("hardness", this.hardness);
        if (!this.blockClass.isEmpty()) object.put("class", this.blockClass);
        if (this.emittedLight != 0) object.put("emittedLight", this.emittedLight);
        if (this.absorbedLight != 0) object.put("absorbedLight", this.emittedLight);
        if (this.degradable) object.put("degradable", true);
        if (!this.selectable) object.put("selectable", false);
        if (!this.solid) object.put("solid", false);
        if (!this.gui.isEmpty()) object.put("GUI", this.gui);
        if (!this.rotation.isEmpty()) object.put("rotation", this.rotation);
        if (this.transparent) object.put("transparent", true);
        if (this.viewThrough) object.put("viewThrough", true);
        if (!this.drops.isEmpty()) {
            JsonArray dropsArr = new JsonArray();
            dropsArr.addStrings(this.drops.toArray(new String[0]));
            object.put("drops", dropsArr);
        }
        if (this.oreDataBuilder != null) {
            JsonObject oreObj = new JsonObject();
            oreObj.put("veins", this.oreDataBuilder.veins);
            oreObj.put("size", this.oreDataBuilder.size);
            oreObj.put("height", this.oreDataBuilder.height);
            oreObj.put("density", this.oreDataBuilder.density);
            JsonArray sourcesArr = new JsonArray();
            sourcesArr.addStrings(this.oreDataBuilder.sources.toArray(new String[0]));
            oreObj.put("source", sourcesArr);
            object.put("ore", oreObj);
        }
    }

    @Override
    public Resource getLocation() {
        return this.id;
    }
}
