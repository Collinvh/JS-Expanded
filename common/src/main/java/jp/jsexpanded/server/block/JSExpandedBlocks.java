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

    public static final Supplier<Block> SHALE_FACE = registerBlockWItem("shale_face", () -> new Block(BlockBehaviour.Properties.ofFullCopy(JSV2Blocks.SHALE_BRICKS.getBLOCK().get())), new Item.Properties());
    public static final Supplier<Block> TRAP_SHALE_FACE = registerBlockWItem("trap_shale_face", () -> new JSTrapBlock(BlockBehaviour.Properties.ofFullCopy(JSV2Blocks.SHALE_BRICKS.getBLOCK().get())), new Item.Properties());
    public static final Supplier<Block> BURNING_SHALE_FACE = registerBlockWItem("burning_shale_face", () -> new Block(BlockBehaviour.Properties.ofFullCopy(JSV2Blocks.SHALE_BRICKS.getBLOCK().get()).lightLevel((a) -> 9)), new Item.Properties());
    public static final Supplier<Block> RHYOLITE_FACE = registerBlockWItem("rhyolite_face", () -> new Block(BlockBehaviour.Properties.ofFullCopy(JSV2Blocks.RHYOLITE_BRICKS.getBLOCK().get())), new Item.Properties());
    public static final Supplier<Block> TRAP_RHYOLITE_FACE = registerBlockWItem("trap_rhyolite_face", () -> new JSTrapBlock(BlockBehaviour.Properties.ofFullCopy(JSV2Blocks.RHYOLITE_BRICKS.getBLOCK().get())), new Item.Properties());
    public static final Supplier<Block> BURNING_RHYOLITE_FACE = registerBlockWItem("burning_rhyolite_face", () -> new Block(BlockBehaviour.Properties.ofFullCopy(JSV2Blocks.RHYOLITE_BRICKS.getBLOCK().get()).lightLevel((a) -> 9)), new Item.Properties());

    public static final Supplier<Block> CARPET_MOSS = registerBlockWItem("carpet_moss", () -> new JSPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());
    public static final Supplier<Block> HORSETAILS = registerBlockWItem("horsetails", () -> new JSPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());
    public static final Supplier<Block> INDIGOFERA = registerBlockWItem("indigofera", () -> new JSPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());
    public static final Supplier<Block> LASIANDRA = registerBlockWItem("lasiandra", () -> new JSPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());
    public static final Supplier<Block> MELTED_CROWN = registerBlockWItem("melted_crown", () -> new JSPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());
    public static final Supplier<Block> NEEDLEBRUSH = registerBlockWItem("needlebrush", () -> new JSPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());
    public static final Supplier<Block> PEARL_MUSHROOM = registerBlockWItem("pearl_mushroom", () -> new JSPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());
    public static final Supplier<Block> PINNATONO_BERRY = registerBlockWItem("pinnatono_berry", () -> new JSPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());
    public static final Supplier<Block> RIBBON_FERN = registerBlockWItem("ribbon_fern", () -> new JSPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());
    public static final Supplier<Block> STOUT_DRAGON = registerBlockWItem("stout_dragon", () -> new JSPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());
    public static final Supplier<Block> THORNY_STEAMER = registerBlockWItem("thorny_streamer", () -> new JSPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());
    public static final Supplier<Block> WETA_FERN = registerBlockWItem("weta_fern", () -> new JSPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());
    public static final Supplier<Block> WILD_PEANUT = registerBlockWItem("wild_peanut", () -> new JSPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());

    public static final Supplier<Block> SWAMP_SLIME = registerLilypad("swamp_slime", () -> new JSWaterLily(BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD)), new Item.Properties());
    public static final Supplier<Block> WATER_LILY = registerLilypad("water_lily", () -> new JSWaterLily(BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD)), new Item.Properties());

    public static final Supplier<Block> HERMIT_GYMPIE = registerBlockWItem("hermit_gympie", () -> new JSDoublePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());
    public static final Supplier<Block> KONGWOBISA = registerBlockWItem("kongwobisa", () -> new JSDoublePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());
    public static final Supplier<Block> TREE_FERN = registerBlockWItem("tree_fern", () -> new JSDoublePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());
    public static final Supplier<Block> REEDS = registerBlockWItem("reeds", () -> new ReedPlant(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)), new Item.Properties());

    public static final Supplier<Block> BULLWEED = registerBlockWItem("bullweed", () -> new JSAquaticPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SEAGRASS)), new Item.Properties());
    public static final Supplier<Block> DEAD_MANS_HAIR = registerBlockWItem("dead_mans_hair", () -> new JSAquaticPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SEAGRASS)), new Item.Properties());
    public static final Supplier<Block> DROWNING_LILY = registerBlockWItem("drowning_lily", () -> new JSAquaticPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SEAGRASS)), new Item.Properties());

    public static final Supplier<Block> BUSHY_BAMBOO = registerBlockWItem("bushy_bamboo", () -> new JSSugarCaneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SUGAR_CANE)), new Item.Properties());
    public static final Supplier<Block> FODDER_BAMBOO = registerBlockWItem("fodder_bamboo", () -> new JSSugarCaneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SUGAR_CANE)), new Item.Properties());

    public static final Supplier<Block> EPIPHYTE_VERN = registerBlockWItem("epiphyte_vern", () -> new VineBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.VINE)), new Item.Properties());
    public static final Supplier<Block> RANCID_WALL_GROWTH = registerBlockWItem("rancid_wall_growth", () -> new VineBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.VINE)), new Item.Properties());
    public static final Supplier<Block> ROPE = registerBlockWItem("rope", () -> new VineBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.VINE)), new Item.Properties());
    public static final Supplier<Block> SHROUD_MOSS = registerBlockWItem("shroud_moss", () -> new VineBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.VINE)), new Item.Properties());


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

    public static void init() {
    }
}
