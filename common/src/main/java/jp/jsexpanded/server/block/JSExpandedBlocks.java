package jp.jsexpanded.server.block;

import com.google.common.base.Supplier;
import jp.jsexpanded.JSExpanded;
import jp.jsexpanded.server.block.obj.JSSugarCaneBlock;
import jp.jsexpanded.server.block.obj.JSTrapBlock;
import jp.jsexpanded.server.block.obj.ReedPlant;
import jp.jsexpanded.server.item.JSExpandedItems;
import jp.jurassicsaga.server.base.block.obj.plant.JSAquaticPlantBlock;
import jp.jurassicsaga.server.base.block.obj.plant.JSDoublePlantBlock;
import jp.jurassicsaga.server.base.block.obj.plant.JSPlantBlock;
import jp.jurassicsaga.server.base.block.obj.vanilla_overrides.JSWaterLily;
import jp.jurassicsaga.server.v2.block.JSV2Blocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import travelers.util.helper.TravelersRegistry;

public class JSExpandedBlocks {
    public static final TravelersRegistry<Block> BLOCKS = new TravelersRegistry<>(BuiltInRegistries.BLOCK, JSExpanded.MOD_ID).storeValues();
    public static Supplier<Block> SHALE_FACE;
    public static Supplier<Block> TRAP_SHALE_FACE;
    public static Supplier<Block> BURNING_SHALE_FACE;
    public static Supplier<Block> RHYOLITE_FACE;
    public static Supplier<Block> TRAP_RHYOLITE_FACE;
    public static Supplier<Block> BURNING_RHYOLITE_FACE;

    public static Supplier<Block> CARPET_MOSS;
    public static Supplier<Block> HORSETAILS;
    public static Supplier<Block> INDIGOFERA;
    public static Supplier<Block> LASIANDRA;
    public static Supplier<Block> MELTED_CROWN;
    public static Supplier<Block> NEEDLEBRUSH;
    public static Supplier<Block> PEARL_MUSHROOM;
    public static Supplier<Block> PINNATONO_BERRY;
    public static Supplier<Block> RIBBON_FERN;
    public static Supplier<Block> STOUT_DRAGON;
    public static Supplier<Block> THORNY_STEAMER;
    public static Supplier<Block> WETA_FERN;
    public static Supplier<Block> WILD_PEANUT;

    public static Supplier<Block> SWAMP_SLIME;
    public static Supplier<Block> WATER_LILY;

    public static Supplier<Block> HERMIT_GYMPIE;
    public static Supplier<Block> KONGWOBISA;
    public static Supplier<Block> TREE_FERN;
    public static Supplier<Block> REEDS;

    public static Supplier<Block> BULLWEED;
    public static Supplier<Block> DEAD_MANS_HAIR;
    public static Supplier<Block> DROWNING_LILY;

    public static Supplier<Block> BUSHY_BAMBOO;
    public static Supplier<Block> FODDER_BAMBOO;

    public static Supplier<Block> EPIPHYTE_VERN;
    public static Supplier<Block> RANCID_WALL_GROWTH;
    public static Supplier<Block> ROPE;
    public static Supplier<Block> SHROUD_MOSS;

    public static void init() {

        SHALE_FACE = registerBlockWItem(
                "shale_face",
                () -> new Block(BlockBehaviour.Properties.ofFullCopy(JSV2Blocks.SHALE_BRICKS.getBLOCK().get())),
                new Item.Properties()
        );

        TRAP_SHALE_FACE = registerBlockWItem(
                "trap_shale_face",
                () -> new JSTrapBlock(BlockBehaviour.Properties.ofFullCopy(JSV2Blocks.SHALE_BRICKS.getBLOCK().get())),
                new Item.Properties()
        );

        BURNING_SHALE_FACE = registerBlockWItem(
                "burning_shale_face",
                () -> new Block(BlockBehaviour.Properties.ofFullCopy(JSV2Blocks.SHALE_BRICKS.getBLOCK().get()).lightLevel(a -> 9)),
                new Item.Properties()
        );

        RHYOLITE_FACE = registerBlockWItem(
                "rhyolite_face",
                () -> new Block(BlockBehaviour.Properties.ofFullCopy(JSV2Blocks.RHYOLITE_BRICKS.getBLOCK().get())),
                new Item.Properties()
        );

        TRAP_RHYOLITE_FACE = registerBlockWItem(
                "trap_rhyolite_face",
                () -> new JSTrapBlock(BlockBehaviour.Properties.ofFullCopy(JSV2Blocks.RHYOLITE_BRICKS.getBLOCK().get())),
                new Item.Properties()
        );

        BURNING_RHYOLITE_FACE = registerBlockWItem(
                "burning_rhyolite_face",
                () -> new Block(BlockBehaviour.Properties.ofFullCopy(JSV2Blocks.RHYOLITE_BRICKS.getBLOCK().get()).lightLevel(a -> 9)),
                new Item.Properties()
        );

        CARPET_MOSS = registerBlockWItem("carpet_moss", () -> new JSPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());
        HORSETAILS = registerBlockWItem("horsetails", () -> new JSPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());
        INDIGOFERA = registerBlockWItem("indigofera", () -> new JSPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());
        LASIANDRA = registerBlockWItem("lasiandra", () -> new JSPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());
        MELTED_CROWN = registerBlockWItem("melted_crown", () -> new JSPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());
        NEEDLEBRUSH = registerBlockWItem("needlebrush", () -> new JSPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());
        PEARL_MUSHROOM = registerBlockWItem("pearl_mushroom", () -> new JSPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());
        PINNATONO_BERRY = registerBlockWItem("pinnatono_berry", () -> new JSPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());
        RIBBON_FERN = registerBlockWItem("ribbon_fern", () -> new JSPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());
        STOUT_DRAGON = registerBlockWItem("stout_dragon", () -> new JSPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());
        THORNY_STEAMER = registerBlockWItem("thorny_streamer", () -> new JSPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());
        WETA_FERN = registerBlockWItem("weta_fern", () -> new JSPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());
        WILD_PEANUT = registerBlockWItem("wild_peanut", () -> new JSPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());

        SWAMP_SLIME = registerLilypad("swamp_slime", () -> new JSWaterLily(BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD)), new Item.Properties());
        WATER_LILY = registerLilypad("water_lily", () -> new JSWaterLily(BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD)), new Item.Properties());

        HERMIT_GYMPIE = registerBlockWItem("hermit_gympie", () -> new JSDoublePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());
        KONGWOBISA = registerBlockWItem("kongwobisa", () -> new JSDoublePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());
        TREE_FERN = registerBlockWItem("tree_fern", () -> new JSDoublePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());
        REEDS = registerBlockWItem("reeds", () -> new ReedPlant(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());

        BULLWEED = registerBlockWItem("bullweed", () -> new JSAquaticPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SEAGRASS)), new Item.Properties());
        DEAD_MANS_HAIR = registerBlockWItem("dead_mans_hair", () -> new JSAquaticPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SEAGRASS)), new Item.Properties());
        DROWNING_LILY = registerBlockWItem("drowning_lily", () -> new JSAquaticPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SEAGRASS)), new Item.Properties());

        BUSHY_BAMBOO = registerBlockWItem("bushy_bamboo", () -> new JSSugarCaneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SUGAR_CANE)), new Item.Properties());
        FODDER_BAMBOO = registerBlockWItem("fodder_bamboo", () -> new JSSugarCaneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SUGAR_CANE)), new Item.Properties());

        EPIPHYTE_VERN = registerBlockWItem("epiphyte_vern", () -> new VineBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.VINE)), new Item.Properties());
        RANCID_WALL_GROWTH = registerBlockWItem("rancid_wall_growth", () -> new VineBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.VINE)), new Item.Properties());
        ROPE = registerBlockWItem("rope", () -> new VineBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.VINE)), new Item.Properties());
        SHROUD_MOSS = registerBlockWItem("shroud_moss", () -> new VineBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.VINE)), new Item.Properties());
    }

    public static Supplier<Block> registerBlock(String name, Supplier<Block> block) {
        return BLOCKS.register(name, block);
    }

    public static Supplier<Block> registerBlockWItem(String name, Supplier<Block> block, Item.Properties properties) {
        Supplier<Block> blockRegistryObject = BLOCKS.register(name, block);
        JSExpandedItems.registerItem(name, () -> new BlockItem(blockRegistryObject.get(), properties));
        return blockRegistryObject;
    }

    public static Supplier<Block> registerLilypad(String name, Supplier<Block> block, Item.Properties properties) {
        Supplier<Block> blockRegistryObject = BLOCKS.register(name, block);
        JSExpandedItems.registerItem(name, () -> new PlaceOnWaterBlockItem(blockRegistryObject.get(), properties));
        return blockRegistryObject;
    }
}
