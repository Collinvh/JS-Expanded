package jp.jsexpanded.neo.data.server;

import com.google.common.base.Supplier;
import jp.jsexpanded.server.animals.AbstractAddonAnimal;
import jp.jurassicsaga.server.base.animal.JSAnimals;
import jp.jurassicsaga.server.base.block.obj.group.BasicBlockSetRegistries;
import jp.jurassicsaga.server.base.block.obj.group.ColoredRegistries;
import jp.jurassicsaga.server.base.block.obj.group.StoneRegistries;
import jp.jurassicsaga.server.base.tag.JSItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.data.recipes.RecipeBuilder.getDefaultRecipeId;

@SuppressWarnings("SameParameterValue")
public class JSExpandedCraftingProvider extends RecipeProvider implements IConditionBuilder {

    public JSExpandedCraftingProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput out) {
        // Animals
        buildAnimalRecipes(out);
    }

    // ---------- Helpers ----------

    private void buildAnimalRecipes(@NotNull RecipeOutput out) {
        for (var a : JSAnimals.getAnimals()) {
            if(a instanceof AbstractAddonAnimal<?>) {
                var props = a.getAnimalAttributes().getItemProperties();
                if (props.isHasDrops() && props.isHasMeat()) {
                    addCooking(out,
                            a.getItems().getRawMeat().get().getDefaultInstance(),
                            a.getItems().getCookedMeat().get().getDefaultInstance(),
                            600);
                }
            }
        }
    }

    private void addCooking(RecipeOutput out, ItemStack in, ItemStack cooked, int time) {
        cooking(out, SimpleCookingRecipeBuilder.smelting(Ingredient.of(in), RecipeCategory.FOOD, cooked, 0.5f, time / 2), in, cooked, "_smelt");
        cooking(out, SimpleCookingRecipeBuilder.smoking(Ingredient.of(in), RecipeCategory.FOOD, cooked, 0.5f, (time / 2) - ((time / 2) / 5)), in, cooked, "_smoke");
        cooking(out, SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(in), RecipeCategory.FOOD, cooked, 0.5f, time), in, cooked, "_campfire");
    }

    private void cooking(RecipeOutput out, SimpleCookingRecipeBuilder b, ItemStack in, ItemStack cooked, String suffix) {
        b.unlockedBy(getHasName(in.getItem()), has(in.getItem()))
                .unlockedBy(getHasName(cooked.getItem()), has(cooked.getItem()))
                .save(out, getDefaultRecipeId(cooked.getItem()) + suffix);
    }

    private void shaped(RecipeOutput out, RecipeCategory cat, Supplier<? extends ItemLike> result, int count,
                        String[] pattern, Map<Character, Object> keys, ItemLike unlock) {
        shaped(out, cat, result.get(), count, pattern, keys, unlock);
    }

    private void shaped(RecipeOutput out, RecipeCategory cat, ItemLike result, int count,
                        String[] pattern, Map<Character, Object> keys, ItemLike unlock) {
        var b = ShapedRecipeBuilder.shaped(cat, result, count);
        for (String line : pattern) b.pattern(line);
        keys.forEach((ch, obj) -> {
            if (obj instanceof ItemLike il) b.define(ch, il);
            else if (obj instanceof Block bl) b.define(ch, bl);
            else if (obj instanceof net.minecraft.tags.TagKey<?> tag) b.define(ch, (net.minecraft.tags.TagKey<Item>) tag);
            else throw new IllegalArgumentException("Unsupported key type: " + obj);
        });
        b.unlockedBy(getHasName(unlock), has(unlock)).save(out);
    }

    private void shapeless(RecipeOutput out, RecipeCategory cat, ItemLike result, int count,
                           List<?> inputs, ItemLike unlock) {
        var b = ShapelessRecipeBuilder.shapeless(cat, result, count);
        for (Object in : inputs) {
            if (in instanceof ItemLike il) b.requires(il);
            else if (in instanceof Ingredient ing) b.requires(ing);
            else if (in instanceof net.minecraft.tags.TagKey<?> tag)
                b.requires(Ingredient.of((net.minecraft.tags.TagKey<Item>) tag));
            else throw new IllegalArgumentException("Unsupported shapeless input: " + in);
        }
        b.unlockedBy(getHasName(unlock), has(unlock)).save(out);
    }

    private void shapelessCounts(RecipeOutput out, RecipeCategory cat, ItemLike result, int count,
                                 Map<ItemLike, Integer> withCounts, ItemLike unlock) {
        shapelessCounts(out, cat, result, count, withCounts, unlock, List.of());
    }

    private void shapelessCounts(RecipeOutput out, RecipeCategory cat, ItemLike result, int count,
                                 Map<ItemLike, Integer> withCounts, ItemLike unlock, List<Ingredient> extraIngredients) {
        var b = ShapelessRecipeBuilder.shapeless(cat, result, count);
        withCounts.forEach((il, c) -> b.requires(il, c));
        extraIngredients.forEach(b::requires);
        b.unlockedBy(getHasName(unlock), has(unlock)).save(out);
    }

    private void colorGroup(@NotNull RecipeOutput out, ColoredRegistries color, Block base) {
        // dye mapping: registry field -> dye
        Map<Supplier<? extends Block>, Item> map = Map.ofEntries(
                Map.entry(color.BLACK, Items.BLACK_DYE),
                Map.entry(color.BLUE, Items.BLUE_DYE),
                Map.entry(color.BROWN, Items.BROWN_DYE),
                Map.entry(color.CYAN, Items.CYAN_DYE),
                Map.entry(color.GREEN, Items.GREEN_DYE),
                Map.entry(color.GREY, Items.GRAY_DYE),
                Map.entry(color.LIGHTBLUE, Items.LIGHT_BLUE_DYE),
                Map.entry(color.LIGHTGREY, Items.LIGHT_GRAY_DYE),
                Map.entry(color.LIME, Items.LIME_DYE),
                Map.entry(color.MAGENTA, Items.MAGENTA_DYE),
                Map.entry(color.ORANGE, Items.ORANGE_DYE),
                Map.entry(color.PINK, Items.PINK_DYE),
                Map.entry(color.PURPLE, Items.PURPLE_DYE),
                Map.entry(color.RED, Items.RED_DYE),
                Map.entry(color.WHITE, Items.WHITE_DYE),
                Map.entry(color.YELLOW, Items.YELLOW_DYE)
        );
        map.forEach((supplier, dye) -> colorBlock(out, supplier.get(), base, dye));
    }

    private void colorBlock(@NotNull RecipeOutput out, Block colored, Block base, Item dye) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, colored, 1)
                .requires(base, 1).requires(dye, 1)
                .unlockedBy(getHasName(base), has(base))
                .save(out, getDefaultRecipeId(colored) + "_colored");
    }

    private void stairSlabColoredGroup(@NotNull RecipeOutput out, ColoredRegistries stairs, ColoredRegistries slabs, ColoredRegistries bases) {
        List.of(
                stairs.BLACK.get(), stairs.BLUE.get(), stairs.BROWN.get(), stairs.CYAN.get(), stairs.GREEN.get(),
                stairs.GREY.get(), stairs.LIGHTBLUE.get(), stairs.LIGHTGREY.get(), stairs.LIME.get(),
                stairs.MAGENTA.get(), stairs.ORANGE.get(), stairs.PINK.get(), stairs.PURPLE.get(),
                stairs.RED.get(), stairs.WHITE.get(), stairs.YELLOW.get()
        ).forEach(block -> {}); // just to keep IDE from warning about unused getters

        // Pairwise iterate by accessor
        stairSlabColored(out, stairs.BLACK.get(), slabs.BLACK.get(), bases.BLACK.get());
        stairSlabColored(out, stairs.BLUE.get(), slabs.BLUE.get(), bases.BLUE.get());
        stairSlabColored(out, stairs.BROWN.get(), slabs.BROWN.get(), bases.BROWN.get());
        stairSlabColored(out, stairs.CYAN.get(), slabs.CYAN.get(), bases.CYAN.get());
        stairSlabColored(out, stairs.GREEN.get(), slabs.GREEN.get(), bases.GREEN.get());
        stairSlabColored(out, stairs.GREY.get(), slabs.GREY.get(), bases.GREY.get());
        stairSlabColored(out, stairs.LIGHTBLUE.get(), slabs.LIGHTBLUE.get(), bases.LIGHTBLUE.get());
        stairSlabColored(out, stairs.LIGHTGREY.get(), slabs.LIGHTGREY.get(), bases.LIGHTGREY.get());
        stairSlabColored(out, stairs.LIME.get(), slabs.LIME.get(), bases.LIME.get());
        stairSlabColored(out, stairs.MAGENTA.get(), slabs.MAGENTA.get(), bases.MAGENTA.get());
        stairSlabColored(out, stairs.ORANGE.get(), slabs.ORANGE.get(), bases.ORANGE.get());
        stairSlabColored(out, stairs.PINK.get(), slabs.PINK.get(), bases.PINK.get());
        stairSlabColored(out, stairs.PURPLE.get(), slabs.PURPLE.get(), bases.PURPLE.get());
        stairSlabColored(out, stairs.RED.get(), slabs.RED.get(), bases.RED.get());
        stairSlabColored(out, stairs.WHITE.get(), slabs.WHITE.get(), bases.WHITE.get());
        stairSlabColored(out, stairs.YELLOW.get(), slabs.YELLOW.get(), bases.YELLOW.get());
    }

    private void stairSlabColored(@NotNull RecipeOutput out, Block stair, Block slab, Block base) {
        stairBuilder(stair, Ingredient.of(base))
                .unlockedBy(getHasName(base), has(base))
                .save(out, getDefaultRecipeId(stair) + "stair");
        slab(out, RecipeCategory.BUILDING_BLOCKS, slab, base);
    }

    private void stoneFamily(@NotNull RecipeOutput out, StoneRegistries family) {
        stairSlabWallStonecutter(out, family.STAIRS.get(), family.WALL.get(), family.SLAB.get(), family.BLOCK.get());
    }

    private void basicFamily(@NotNull RecipeOutput out, BasicBlockSetRegistries family) {
        stairSlabWallStonecutter(out, family.STAIRS.get(), family.WALL.get(), family.SLAB.get(), family.BLOCK.get());
    }

    private void mossify(@NotNull RecipeOutput out, StoneRegistries base, StoneRegistries mossy) {
        shapelessCounts(out, RecipeCategory.DECORATIONS, mossy.BLOCK.get(), 8,
                Map.of(base.BLOCK.get().asItem(), 8), base.BLOCK.get().asItem(),
                List.of(Ingredient.of(JSItemTags.MOSS)));
        stoneFamily(out, mossy);
    }

    private void mossifyBasic(@NotNull RecipeOutput out, BasicBlockSetRegistries base, BasicBlockSetRegistries mossy) {
        shapelessCounts(out, RecipeCategory.DECORATIONS, mossy.BLOCK.get(), 8,
                Map.of(base.BLOCK.get().asItem(), 8), base.BLOCK.get().asItem(),
                List.of(Ingredient.of(JSItemTags.MOSS)));
        basicFamily(out, mossy);
    }

    private void mossySwap(@NotNull RecipeOutput out, StoneRegistries base, StoneRegistries mossy) {
        shapelessCounts(out, RecipeCategory.DECORATIONS, mossy.BLOCK.get(), 8,
                Map.of(base.BLOCK.get().asItem(), 8), base.BLOCK.get().asItem(),
                List.of(Ingredient.of(JSItemTags.MOSS)));
    }

    private void completeStoneGroup(@NotNull RecipeOutput out, StoneRegistries s) {
        pressurePlate(out, s.getPRESSURE_PLATE().get(), s.getBLOCK().get());
        buttonBuilder(s.BUTTON.get(), Ingredient.of(s.BLOCK.get()))
                .unlockedBy(getHasName(s.BLOCK.get()), has(s.BLOCK.get()))
                .save(out);
        stairSlabWallStonecutter(out, s.STAIRS.get(), s.WALL.get(), s.SLAB.get(), s.BLOCK.get());
    }

    // Reuse of original provided helper
    private void stairSlabWallStonecutter(@NotNull RecipeOutput out, Block stair, Block wall, Block slab, Block base) {
        stairBuilder(stair, Ingredient.of(base))
                .unlockedBy(getHasName(base), has(base))
                .save(out, getDefaultRecipeId(stair) + "stair");
        wall(out, RecipeCategory.BUILDING_BLOCKS, wall, base);
        slab(out, RecipeCategory.BUILDING_BLOCKS, slab, base);

        stonecutterResultFromBase(out, RecipeCategory.BUILDING_BLOCKS, stair, base);
        stonecutterResultFromBase(out, RecipeCategory.BUILDING_BLOCKS, wall, base);
        stonecutterResultFromBase(out, RecipeCategory.BUILDING_BLOCKS, slab, base, 2);
    }
}