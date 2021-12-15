package lazy.cubyz.modutils.datagen;

import cubyz.api.Resource;
import cubyz.utils.json.JsonObject;

public interface DataObject {

    void serialize(JsonObject object);

    default JsonObject buildObject(){
        JsonObject object = new JsonObject();
        serialize(object);
        return object;
    }

    Resource getLocation();
}
