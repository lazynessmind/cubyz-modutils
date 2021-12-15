package lazy.cubyz.modutils.test;

import cubyz.api.Resource;
import lazy.cubyz.modutils.datagen.BlockGenerator;
import lazy.cubyz.modutils.datagen.DataGeneration;
import lazy.cubyz.modutils.datagen.DataObject;

import java.util.function.Consumer;

public class DataGenTest {

    /**
     * This test only works in my end currently.
     * If you want to test this, you can copy the code from main method and
     * put it on your mod preInit and copy the class BlockGen to your workspace.
     */
    public static void main(String[] args) {
        DataGeneration dataGeneration = new DataGeneration("example");
        dataGeneration.addGenerator(BlockGenTest::new);
        dataGeneration.runDataGenerators();
    }

    public static class BlockGenTest extends BlockGenerator {

        public BlockGenTest(String modId, Consumer<DataObject> dataConsumer) {
            super(modId, dataConsumer);
        }

        @Override
        public void addDataBuilders(Consumer<DataObject> dataObjectConsumer) {
            this.simpleCubeBlock(this.blockLoc("example"), "example:example");
        }

        @Override
        public Resource getGeneratorID() {
            return new Resource(modId(), "blockgen");
        }
    }
}
