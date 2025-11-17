package jp.jsexpanded.neo.data;

import com.google.common.base.Supplier;
import jp.jsexpanded.JSExpanded;
import jp.jsexpanded.server.animals.AbstractAddonAnimal;
import jp.jsexpanded.server.block.JSExpandedBlocks;
import jp.jurassicsaga.neo.data.obj.*;
import jp.jurassicsaga.server.base.animal.JSAnimals;
import jp.jurassicsaga.server.base.animal.obj.JSAnimal;
import jp.jurassicsaga.server.base.animal.obj.JSTravelersAttributes;
import jp.jurassicsaga.server.base.animal.obj.JSTravelersItems;
import jp.jurassicsaga.server.base.block.obj.group.BasicBlockSetRegistries;
import jp.jurassicsaga.server.base.block.obj.group.ColoredRegistries;
import jp.jurassicsaga.server.base.block.obj.group.StoneRegistries;
import jp.jurassicsaga.server.base.block.obj.group.WoodRegistries;
import lombok.Getter;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.apache.commons.lang3.text.WordUtils;
import org.codehaus.plexus.util.StringUtils;
import travelers.server.animal.obj.misc.AnimalType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;

public class JSExpandedData {
    @Getter
    protected static final LinkedHashMap<Supplier<?>, Data> DATA = new LinkedHashMap<>();
    @Getter
    protected static final ArrayList<Data> DATA_ARRAY = new ArrayList<>();
    @Getter
    protected static final LinkedHashMap<Supplier<Block>, BlockDataObject> BLOCKS = new LinkedHashMap<>();
    @Getter
    protected static final LinkedHashMap<Supplier<Item>, ItemModelDataObject> ITEMS = new LinkedHashMap<>();
    @Getter
    protected static final LinkedHashMap<Supplier<Block>, BlockDataObject> PLANTS = new LinkedHashMap<>();

    /*
    ItemGroups
     */
    static {
        DATA_ARRAY.add(new SimpleDataObject("itemGroup.jsexpanded.items", "JSExpanded : Items"));
        for (JSAnimal<?> animal : JSAnimals.getAnimals()) {
            if (animal instanceof AbstractAddonAnimal<?>) {
                var miscAttributes = animal.getAnimalAttributes().getMiscProperties();
                var animalName = animal.getAnimalAttributes().getAnimalName().toLowerCase(Locale.ROOT);
                DATA_ARRAY.add(new SimpleDataObject("guidebook."+ JSExpanded.MOD_ID +"." + animalName + ".scientific_name", miscAttributes.getGuideBookScientificName()));
                DATA_ARRAY.add(new SimpleDataObject("guidebook."+ JSExpanded.MOD_ID +"." + animalName + ".source", miscAttributes.getGuideBookSource()));
                DATA_ARRAY.add(new SimpleDataObject("guidebook."+ JSExpanded.MOD_ID +"." + animalName + ".description", miscAttributes.getGuideBookDescription()));

                DATA_ARRAY.add(new SimpleDataObject("advancements.js." + animalName + ".title", miscAttributes.getAdvancementTitle()));
                DATA_ARRAY.add(new SimpleDataObject("advancements.js." + animalName + ".desc", "Incubate an " + animalName + " egg!"));
            }
        }
    }

    /*
    Items
     */
    static {
        for (JSAnimal<?> animal : JSAnimals.getAnimals()) {
            if(animal instanceof AbstractAddonAnimal<?>) {
                registerAnimal(animal);
                var items = animal.getItems();
                if (items instanceof JSTravelersItems<?> items1) {
                    registerAnimalSpawnEgg(items1.getBucket(), items1.getSpawnEgg(), items1.getHatched_egg(), animal.getAnimalAttributes());
                }
            }
        }
    }

    /*
    Blocks
     */
    static {
        BLOCKS.put(JSExpandedBlocks.SHALE_FACE, new BlockDataObject("Shale Face", "natural/stone/shale/", BlockModelType.SIMPLE_WITH_TOP_BOTTOM, ItemModelType.PARENT));
        BLOCKS.put(JSExpandedBlocks.TRAP_SHALE_FACE, new BlockDataObject("Shale Face Trap", "natural/stone/shale/", BlockModelType.ALL_SIDES, ItemModelType.PARENT));
        BLOCKS.put(JSExpandedBlocks.BURNING_SHALE_FACE, new BlockDataObject("Burning Shale Face", "natural/stone/shale/", BlockModelType.SIMPLE_WITH_TOP_BOTTOM, ItemModelType.PARENT));

        BLOCKS.put(JSExpandedBlocks.RHYOLITE_FACE, new BlockDataObject("Rhyolite Face", "natural/stone/rhyolite/", BlockModelType.SIMPLE_WITH_TOP_BOTTOM, ItemModelType.PARENT));
        BLOCKS.put(JSExpandedBlocks.TRAP_RHYOLITE_FACE, new BlockDataObject("Rhyolite Face Trap", "natural/stone/rhyolite/", BlockModelType.ALL_SIDES, ItemModelType.PARENT));
        BLOCKS.put(JSExpandedBlocks.BURNING_RHYOLITE_FACE, new BlockDataObject("Burning Rhyolite Face", "natural/stone/rhyolite/", BlockModelType.SIMPLE_WITH_TOP_BOTTOM, ItemModelType.PARENT));

        BLOCKS.put(JSExpandedBlocks.CARPET_MOSS, new BlockDataObject("Carpet Moss", "plant/land/", BlockModelType.BASIC_PLANT, ItemModelType.BLOCK_TEXTURE_AS_TEXTURE));
        BLOCKS.put(JSExpandedBlocks.HORSETAILS, new BlockDataObject("Horsetails", "plant/land/", BlockModelType.BASIC_PLANT, ItemModelType.BLOCK_TEXTURE_AS_TEXTURE));
        BLOCKS.put(JSExpandedBlocks.INDIGOFERA, new BlockDataObject("Indigofera", "plant/land/", BlockModelType.BASIC_PLANT, ItemModelType.BLOCK_TEXTURE_AS_TEXTURE));
        BLOCKS.put(JSExpandedBlocks.LASIANDRA, new BlockDataObject("Lasiandra", "plant/land/", BlockModelType.BASIC_PLANT, ItemModelType.BLOCK_TEXTURE_AS_TEXTURE));
        BLOCKS.put(JSExpandedBlocks.MELTED_CROWN, new BlockDataObject("Melted Crown", "plant/land/", BlockModelType.BASIC_PLANT, ItemModelType.BLOCK_TEXTURE_AS_TEXTURE));
        BLOCKS.put(JSExpandedBlocks.NEEDLEBRUSH, new BlockDataObject("Needlebrush", "plant/land/", BlockModelType.BASIC_PLANT, ItemModelType.BLOCK_TEXTURE_AS_TEXTURE));
        BLOCKS.put(JSExpandedBlocks.PEARL_MUSHROOM, new BlockDataObject("Pearl Mushroom", "plant/land/", BlockModelType.BASIC_PLANT, ItemModelType.BLOCK_TEXTURE_AS_TEXTURE));
        BLOCKS.put(JSExpandedBlocks.PINNATONO_BERRY, new BlockDataObject("Pinnatono Berry", "plant/land/", BlockModelType.BASIC_PLANT, ItemModelType.BLOCK_TEXTURE_AS_TEXTURE));
        BLOCKS.put(JSExpandedBlocks.RIBBON_FERN, new BlockDataObject("Ribbon Fern", "plant/land/", BlockModelType.BASIC_PLANT, ItemModelType.BLOCK_TEXTURE_AS_TEXTURE));
        BLOCKS.put(JSExpandedBlocks.STOUT_DRAGON, new BlockDataObject("Stout Dragon", "plant/land/", BlockModelType.BASIC_PLANT, ItemModelType.BLOCK_TEXTURE_AS_TEXTURE));
        BLOCKS.put(JSExpandedBlocks.THORNY_STEAMER, new BlockDataObject("Thorny Streamer", "plant/land/", BlockModelType.BASIC_PLANT, ItemModelType.BLOCK_TEXTURE_AS_TEXTURE));
        BLOCKS.put(JSExpandedBlocks.WETA_FERN, new BlockDataObject("Weta Fern", "plant/land/", BlockModelType.BASIC_PLANT, ItemModelType.BLOCK_TEXTURE_AS_TEXTURE));
        BLOCKS.put(JSExpandedBlocks.WILD_PEANUT, new BlockDataObject("Wild Peanut", "plant/land/", BlockModelType.BASIC_PLANT, ItemModelType.BLOCK_TEXTURE_AS_TEXTURE));

        BLOCKS.put(JSExpandedBlocks.HERMIT_GYMPIE, new BlockDataObject("Hermit Gympie", "plant/land/", BlockModelType.BASIC_PLANT_DOUBLE, ItemModelType.DOUBLE_PLANT));
        BLOCKS.put(JSExpandedBlocks.KONGWOBISA, new BlockDataObject("Kongwobisa", "plant/land/", BlockModelType.BASIC_PLANT_DOUBLE, ItemModelType.DOUBLE_PLANT));
        BLOCKS.put(JSExpandedBlocks.TREE_FERN, new BlockDataObject("Tree Fern", "plant/land/", BlockModelType.BASIC_PLANT_DOUBLE, ItemModelType.DOUBLE_PLANT));
        BLOCKS.put(JSExpandedBlocks.REEDS, new BlockDataObject("Reeds", "plant/land/", BlockModelType.BASIC_PLANT_DOUBLE, ItemModelType.DOUBLE_PLANT));

        BLOCKS.put(JSExpandedBlocks.BULLWEED, new BlockDataObject("Bullweed", "plant/aquatic/", BlockModelType.BASIC_AQUATIC_PLANT, ItemModelType.BLOCK_TEXTURE_AS_TEXTURE));
        BLOCKS.put(JSExpandedBlocks.DEAD_MANS_HAIR, new BlockDataObject("Dead Mans Hair", "plant/aquatic/", BlockModelType.BASIC_AQUATIC_PLANT, ItemModelType.BLOCK_TEXTURE_AS_TEXTURE));
        BLOCKS.put(JSExpandedBlocks.DROWNING_LILY, new BlockDataObject("Drowning Lily", "plant/aquatic/", BlockModelType.BASIC_AQUATIC_PLANT, ItemModelType.BLOCK_TEXTURE_AS_TEXTURE));

        BLOCKS.put(JSExpandedBlocks.BUSHY_BAMBOO, new BlockDataObject("Bushy Bamboo", "plant/sugarcane/", BlockModelType.BASIC_PLANT, ItemModelType.BLOCK_TEXTURE_AS_TEXTURE));
        BLOCKS.put(JSExpandedBlocks.FODDER_BAMBOO, new BlockDataObject("Fodder Bamboo", "plant/sugarcane/", BlockModelType.BASIC_PLANT, ItemModelType.BLOCK_TEXTURE_AS_TEXTURE));

        BLOCKS.put(JSExpandedBlocks.SWAMP_SLIME, new BlockDataObject("Swamp Slime", "plant/aquatic/", BlockModelType.LILY_PAD, ItemModelType.BLOCK_TEXTURE_AS_TEXTURE));
        BLOCKS.put(JSExpandedBlocks.WATER_LILY, new BlockDataObject("Water Lily", "plant/aquatic/", BlockModelType.LILY_PAD, ItemModelType.BLOCK_TEXTURE_AS_TEXTURE));

        //Potted is used as a vine here (can't change variables lol)
        BLOCKS.put(JSExpandedBlocks.EPIPHYTE_VERN, new BlockDataObject("Epiphyte Vern", "plant/vine/", BlockModelType.POTTED, ItemModelType.BLOCK_TEXTURE_AS_TEXTURE));
        BLOCKS.put(JSExpandedBlocks.RANCID_WALL_GROWTH, new BlockDataObject("Rancid Wall Growth", "plant/vine/", BlockModelType.POTTED, ItemModelType.BLOCK_TEXTURE_AS_TEXTURE));
        BLOCKS.put(JSExpandedBlocks.ROPE, new BlockDataObject("Rope", "plant/vine/", BlockModelType.POTTED, ItemModelType.BLOCK_TEXTURE_AS_TEXTURE));
        BLOCKS.put(JSExpandedBlocks.SHROUD_MOSS, new BlockDataObject("Shroud Moss", "plant/vine/", BlockModelType.POTTED, ItemModelType.BLOCK_TEXTURE_AS_TEXTURE));
    }

    static void registerBasicType(BasicBlockSetRegistries set, String name, String prefix, String overrideName) {
        BLOCKS.put(set.BLOCK, new BlockDataObject(name, prefix, BlockModelType.SIMPLE_BLOCK, ItemModelType.PARENT));
        BLOCKS.put(set.SLAB, new BlockDataObject(name + " Slab", prefix, BlockModelType.SLAB, ItemModelType.PARENT, overrideName));
        BLOCKS.put(set.WALL, new BlockDataObject(name + " Wall", prefix, BlockModelType.WALL, ItemModelType.INVENTORY_MODEL, overrideName));
        BLOCKS.put(set.STAIRS, new BlockDataObject(name + " Stairs", prefix, BlockModelType.STAIR, ItemModelType.PARENT, overrideName));
    }

    static {
        BLOCKS.putAll(PLANTS);
        DATA.putAll(BLOCKS);
        DATA.putAll(ITEMS);
    }

    public static void registerAnimal(JSAnimal<?> animal) {
        JSTravelersItems<?> items = animal.getItems();
        boolean extinct = animal.getAnimalAttributes().getMiscProperties().isExtinct();
        String animalName = animal.getAnimalAttributes().getAnimalName().toLowerCase();
        boolean hasMeat = animal.getAnimalAttributes().getItemProperties().isHasMeat();
        boolean hasCoin = animal.getAnimalAttributes().getItemProperties().isHasCoin();

        if (extinct) {
            ItemModelDataObject bone = new ItemModelDataObject(WordUtils.capitalize(animalName.replace("_", " ") + " Remains"), ItemModelType.SIMPLE_ITEM, "natural/entity/animal/" + (extinct ? "extinct/" : "extant/") + animalName + "/");
            ITEMS.put(items.getFossil_remains(), bone);
            DATA.put(items.getFossil_remains(), bone);
        }

        if (!extinct && hasCoin) {
            var type = animal.getAnimalAttributes().getEntityBaseProperties().getAnimalType();
            boolean useAnimalType = type == AnimalType.FISH || type == AnimalType.BIRD || type == AnimalType.AMPHIBIAN || type == AnimalType.INSECT;
            LayerModelDataObject coin = new LayerModelDataObject(WordUtils.capitalize(animalName.replace("_", " ")) + " DNA", ItemModelType.COIN, "natural/entity/default_coins/coin_" + (useAnimalType ? animal.getAnimalAttributes().getEntityBaseProperties().getAnimalType().getName() : animal.getAnimalAttributes().getMetabolismProperties().getDietType().getName()));
            coin.addLayer(new LayerModelDataObject.ModelDataLayer("natural/entity/animal/" + "extant/" + animalName + "/" + animalName + "_coin"));
            ITEMS.put(items.getCoin(), coin);
            DATA.put(items.getCoin(), coin);
        }
        if (extinct) {
            var type = animal.getAnimalAttributes().getEntityBaseProperties().getAnimalType();
            boolean useAnimalType = type == AnimalType.FISH || type == AnimalType.BIRD || type == AnimalType.AMPHIBIAN || type == AnimalType.INSECT;
            LayerModelDataObject coin = new LayerModelDataObject(WordUtils.capitalize(animalName.replace("_", " ")) + " DNA", ItemModelType.COIN, "natural/entity/default_coins/coin_" + (useAnimalType ? animal.getAnimalAttributes().getEntityBaseProperties().getAnimalType().getName() : animal.getAnimalAttributes().getMetabolismProperties().getDietType().getName()));
            coin.addLayer(new LayerModelDataObject.ModelDataLayer("natural/entity/animal/" + "extinct/" + animalName + "/" + animalName + "_coin"));
            ITEMS.put(items.getCoin(), coin);
            DATA.put(items.getCoin(), coin);
        }
        if (hasMeat) {
            ItemModelDataObject raw_meat = new ItemModelDataObject(WordUtils.capitalize(animalName.replace("_", " ")) + " Raw Meat", ItemModelType.SIMPLE_ITEM, "natural/entity/animal/" + (extinct ? "extinct/" : "extant/") + animalName + "/");
            ItemModelDataObject cooked_meat = new ItemModelDataObject(WordUtils.capitalize(animalName.replace("_", " ")) + " Cooked Meat", ItemModelType.SIMPLE_ITEM, "natural/entity/animal/" + (extinct ? "extinct/" : "extant/") + animalName + "/");
            ITEMS.put(items.getRawMeat(), raw_meat);
            DATA.put(items.getRawMeat(), raw_meat);
            ITEMS.put(items.getCookedMeat(), cooked_meat);
            DATA.put(items.getCookedMeat(), cooked_meat);
        }

        items.getCustom_items().forEach(((droppableItem, itemDeferredItem) -> {
            ItemModelDataObject obj = new ItemModelDataObject(WordUtils.capitalize(animalName.replace("_", " ")) + " " + StringUtils.capitalise(droppableItem.name().replace("_", " ")), ItemModelType.SIMPLE_ITEM, "natural/entity/animal/" + (extinct ? "extinct/" : "extant/") + animalName + "/");
            ITEMS.put(itemDeferredItem, obj);
            DATA.put(itemDeferredItem, obj);
        }));

        DATA_ARRAY.add(new SimpleDataObject("entity." + JSExpanded.MOD_ID +"." + animalName, WordUtils.capitalize(animalName.replace("_", " "))));
    }

    static void registerColoredRegistries(ColoredRegistries registries, String baseTranslation, String prefix, BlockModelType blockModelType, ItemModelType modelType) {
        BLOCKS.put(registries.BLACK, new BlockDataObject(baseTranslation + " Black", prefix, blockModelType, modelType));
        BLOCKS.put(registries.BLUE, new BlockDataObject(baseTranslation + " Blue", prefix, blockModelType, modelType));
        BLOCKS.put(registries.BROWN, new BlockDataObject(baseTranslation + " Brown", prefix, blockModelType, modelType));
        BLOCKS.put(registries.CYAN, new BlockDataObject(baseTranslation + " Cyan", prefix, blockModelType, modelType));
        BLOCKS.put(registries.GREEN, new BlockDataObject(baseTranslation + " Green", prefix, blockModelType, modelType));
        BLOCKS.put(registries.GREY, new BlockDataObject(baseTranslation + " Grey", prefix, blockModelType, modelType));
        BLOCKS.put(registries.LIGHTBLUE, new BlockDataObject(baseTranslation + " Light Blue", prefix, blockModelType, modelType));
        BLOCKS.put(registries.LIGHTGREY, new BlockDataObject(baseTranslation + " Light Grey", prefix, blockModelType, modelType));
        BLOCKS.put(registries.LIME, new BlockDataObject(baseTranslation + " Lime", prefix, blockModelType, modelType));
        BLOCKS.put(registries.MAGENTA, new BlockDataObject(baseTranslation + " Magenta", prefix, blockModelType, modelType));
        BLOCKS.put(registries.ORANGE, new BlockDataObject(baseTranslation + " Orange", prefix, blockModelType, modelType));
        BLOCKS.put(registries.PINK, new BlockDataObject(baseTranslation + " Pink", prefix, blockModelType, modelType));
        BLOCKS.put(registries.PURPLE, new BlockDataObject(baseTranslation + " Purple", prefix, blockModelType, modelType));
        BLOCKS.put(registries.RED, new BlockDataObject(baseTranslation + " Red", prefix, blockModelType, modelType));
        BLOCKS.put(registries.WHITE, new BlockDataObject(baseTranslation + " White", prefix, blockModelType, modelType));
        BLOCKS.put(registries.YELLOW, new BlockDataObject(baseTranslation + " Yellow", prefix, blockModelType, modelType));
    }

    static void registerColoredRegistries(ColoredRegistries registries, String baseTranslation, String prefix, BlockModelType blockModelType, ItemModelType modelType, String overrideName) {
        BLOCKS.put(registries.BLACK, new BlockDataObject(baseTranslation + " Black", prefix, blockModelType, modelType, overrideName + "_black"));
        BLOCKS.put(registries.BLUE, new BlockDataObject(baseTranslation + " Blue", prefix, blockModelType, modelType, overrideName + "_blue"));
        BLOCKS.put(registries.BROWN, new BlockDataObject(baseTranslation + " Brown", prefix, blockModelType, modelType, overrideName + "_brown"));
        BLOCKS.put(registries.CYAN, new BlockDataObject(baseTranslation + " Cyan", prefix, blockModelType, modelType, overrideName + "_cyan"));
        BLOCKS.put(registries.GREEN, new BlockDataObject(baseTranslation + " Green", prefix, blockModelType, modelType, overrideName + "_green"));
        BLOCKS.put(registries.GREY, new BlockDataObject(baseTranslation + " Grey", prefix, blockModelType, modelType, overrideName + "_grey"));
        BLOCKS.put(registries.LIGHTBLUE, new BlockDataObject(baseTranslation + " Light Blue", prefix, blockModelType, modelType, overrideName + "_lightblue"));
        BLOCKS.put(registries.LIGHTGREY, new BlockDataObject(baseTranslation + " Light Grey", prefix, blockModelType, modelType, overrideName + "_lightgrey"));
        BLOCKS.put(registries.LIME, new BlockDataObject(baseTranslation + " Lime", prefix, blockModelType, modelType, overrideName + "_lime"));
        BLOCKS.put(registries.MAGENTA, new BlockDataObject(baseTranslation + " Magenta", prefix, blockModelType, modelType, overrideName + "_magenta"));
        BLOCKS.put(registries.ORANGE, new BlockDataObject(baseTranslation + " Orange", prefix, blockModelType, modelType, overrideName + "_orange"));
        BLOCKS.put(registries.PINK, new BlockDataObject(baseTranslation + " Pink", prefix, blockModelType, modelType, overrideName + "_pink"));
        BLOCKS.put(registries.PURPLE, new BlockDataObject(baseTranslation + " Purple", prefix, blockModelType, modelType, overrideName + "_purple"));
        BLOCKS.put(registries.RED, new BlockDataObject(baseTranslation + " Red", prefix, blockModelType, modelType, overrideName + "_red"));
        BLOCKS.put(registries.WHITE, new BlockDataObject(baseTranslation + " White", prefix, blockModelType, modelType, overrideName + "_white"));
        BLOCKS.put(registries.YELLOW, new BlockDataObject(baseTranslation + " Yellow", prefix, blockModelType, modelType, overrideName + "_yellow"));
    }

    static void registerStoneType(StoneRegistries group, String baseTranslation, String baseName, String prefix) {
        BLOCKS.put(group.BLOCK, new BlockDataObject(baseTranslation, prefix, BlockModelType.SIMPLE_BLOCK, ItemModelType.PARENT));
        BLOCKS.put(group.BUTTON, new BlockDataObject(baseTranslation + " Button", prefix, BlockModelType.BUTTON, ItemModelType.INVENTORY_MODEL, baseName));
        BLOCKS.put(group.PRESSURE_PLATE, new BlockDataObject(baseTranslation + " Pressure Plate", prefix, BlockModelType.PRESSURE_PLATE, ItemModelType.PARENT, baseName));
        BLOCKS.put(group.WALL, new BlockDataObject(baseTranslation + " Wall", prefix, BlockModelType.WALL, ItemModelType.INVENTORY_MODEL, baseName));
        BLOCKS.put(group.STAIRS, new BlockDataObject(baseTranslation + " Stairs", prefix, BlockModelType.STAIR, ItemModelType.PARENT, baseName));
        BLOCKS.put(group.SLAB, new BlockDataObject(baseTranslation + " Slab", prefix, BlockModelType.SLAB, ItemModelType.PARENT, baseName));
    }

    static void registerWoodType(WoodRegistries group, String baseTranslation, String baseName) {
        BLOCKS.put(group.BLOCK, new BlockDataObject(baseTranslation + " Planks", "plant/tree/", BlockModelType.SIMPLE_BLOCK, ItemModelType.PARENT, baseName + "_planks"));
        BLOCKS.put(group.SLAB, new BlockDataObject(baseTranslation + " Slab", "plant/tree/", BlockModelType.SLAB, ItemModelType.PARENT, baseName + "_planks"));
        BLOCKS.put(group.STAIRS, new BlockDataObject(baseTranslation + " Stairs", "plant/tree/", BlockModelType.STAIR, ItemModelType.PARENT, baseName + "_planks"));
        BLOCKS.put(group.FENCE, new BlockDataObject(baseTranslation + " Fence", "plant/tree/", BlockModelType.FENCE, ItemModelType.INVENTORY_MODEL, baseName + "_planks"));
        BLOCKS.put(group.FENCE_GATE, new BlockDataObject(baseTranslation + " Fence Gate", "plant/tree/", BlockModelType.FENCE_GATE, ItemModelType.PARENT, baseName + "_planks"));
        BLOCKS.put(group.LOG, new BlockDataObject(baseTranslation + " Log", "plant/tree/", BlockModelType.PILLAR_BLOCK, ItemModelType.PARENT));
        BLOCKS.put(group.STRIPPED_LOG, new BlockDataObject(baseTranslation + " Stripped Log", "plant/tree/", BlockModelType.PILLAR_BLOCK, ItemModelType.PARENT));
        BLOCKS.put(group.WOOD, new BlockDataObject(baseTranslation + " Wood", "plant/tree/", BlockModelType.SIMPLE_BLOCK, ItemModelType.PARENT, baseName + "_log"));
        BLOCKS.put(group.STRIPPED_WOOD, new BlockDataObject("Stripped " + baseTranslation + " Wood", "plant/tree/", BlockModelType.SIMPLE_BLOCK, ItemModelType.PARENT, "stripped_" + baseName + "_log"));
        BLOCKS.put(group.DOOR, new BlockDataObject(baseTranslation + " Door", "plant/tree/", BlockModelType.DOOR_TRANSLUCENT, ItemModelType.TRANSLUCENT_ITEM));
        BLOCKS.put(group.BUTTON, new BlockDataObject(baseTranslation + " Button", "plant/tree/", BlockModelType.BUTTON, ItemModelType.INVENTORY_MODEL, baseName + "_planks"));
        BLOCKS.put(group.SIGN, new SignDataObject(group.WALL_SIGN, baseTranslation + " Sign", "plant/tree/", BlockModelType.SIGN, ItemModelType.NONE, baseName + "_planks"));
        BLOCKS.put(group.TRAPDOOR, new BlockDataObject(baseTranslation + " Trapdoor", "plant/tree/", BlockModelType.TRAPDOOR_TRANSLUCENT, ItemModelType.TRAPDOOR));
        BLOCKS.put(group.PRESSURE_PLATE, new BlockDataObject(baseTranslation + " Pressure Plate", "plant/tree/", BlockModelType.PRESSURE_PLATE, ItemModelType.PARENT, baseName + "_planks"));
        ITEMS.put(group.SIGN_ITEM, new ItemModelDataObject(baseTranslation + " Sign", ItemModelType.SIMPLE_ITEM, "plant/tree/"));
        BLOCKS.put(group.HANGING_SIGN, new SignDataObject(group.HANGING_SIGN_WALL, baseTranslation + " Hanging Sign", "plant/tree/", BlockModelType.HANGING_SIGN, ItemModelType.NONE, baseName + "_planks"));
        ITEMS.put(group.HANGING_SIGN_ITEM, new ItemModelDataObject(baseTranslation + " Hanging Sign", ItemModelType.SIMPLE_ITEM, "plant/tree/"));
        BLOCKS.put(group.LEAVES, new BlockDataObject(baseTranslation + " Leaves", "plant/tree/", BlockModelType.SIMPLE_TRANSPARENT_BLOCK, ItemModelType.PARENT));
    }

    public static void registerAnimalSpawnEgg(Supplier<Item> bucket, Supplier<Item> spawnEgg, Supplier<Item> hatchedEgg, JSTravelersAttributes<?> attributes) {
        ItemModelDataObject spawn_egg = new ItemModelDataObject(WordUtils.capitalize(attributes.getAnimalName().replace("_", " ")) + " Spawn Egg", ItemModelType.SPAWN_EGG, "");
        ITEMS.put(spawnEgg, spawn_egg);
        DATA.put(spawnEgg, spawn_egg);
        if (hatchedEgg != null) {
            String animalName = attributes.getAnimalName().toLowerCase();
            ItemModelDataObject hatched_egg = new ItemModelDataObject(WordUtils.capitalize(animalName.replace("_", " ") + (attributes.getMiscProperties().isMammal() ? " Gestated Embryo" : " Hatched Egg")), ItemModelType.SIMPLE_ITEM, "natural/entity/eggs/", "egg_" + attributes.getItemProperties().getEggtype().toString().toLowerCase() + "_hatched");
            ITEMS.put(hatchedEgg, hatched_egg);
            DATA.put(hatchedEgg, hatched_egg);
        }

        if (bucket != null) {
            String animalName = attributes.getAnimalName().toLowerCase();
            ItemModelDataObject obj = new ItemModelDataObject(WordUtils.capitalize(animalName.replace("_", " ")) + " Bucket", ItemModelType.SIMPLE_ITEM, "natural/entity/animal/" + (attributes.getMiscProperties().isExtinct() ? "extinct/" : "extant/") + animalName + "/");
            ITEMS.put(bucket, obj);
            DATA.put(bucket, obj);
        }
    }
}