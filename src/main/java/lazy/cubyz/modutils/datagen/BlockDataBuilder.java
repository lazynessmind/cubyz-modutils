package lazy.cubyz.modutils.datagen;

import cubyz.api.Resource;
import cubyz.utils.json.JsonArray;
import cubyz.utils.json.JsonObject;
import cubyz.world.blocks.Blocks;

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
    public String model = "cubzy:block.obj";
    public String texture = "";

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
        this.texture = texture;
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

    public void build(Consumer<DataObject> obj) {
        obj.accept(this);
    }

    @Override
    public void serialize(JsonObject object) {
        object.put("texture", this.texture);
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
    }

    @Override
    public Resource getLocation() {
        return this.id;
    }
}
