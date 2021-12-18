package lazy.cubyz.modutils.datagen;

import cubyz.api.Resource;
import cubyz.utils.json.JsonObject;
import lazy.cubyz.modutils.datagen.builder.BlockDataBuilder;

/**
 * Interface used to create the JsonObjects.
 * @see BlockDataBuilder for more info.
 * {@link BlockDataBuilder#serialize(JsonObject)}
 */
public interface DataObject {

    void serialize(JsonObject object);

    default JsonObject buildObject(){
        JsonObject object = new JsonObject();
        serialize(object);
        return object;
    }

    Resource getLocation();
}
