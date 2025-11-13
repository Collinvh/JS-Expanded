package jp.jsexpanded.neo.data.server;

import jp.jsexpanded.JSExpanded;
import jp.jurassicsaga.server.base.block.obj.group.BasicBlockSetRegistries;
import jp.jurassicsaga.server.base.block.obj.group.StoneRegistries;
import jp.jurassicsaga.server.base.block.obj.group.WoodRegistries;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class JSExpandedBlockTagProvider extends BlockTagsProvider {
    public JSExpandedBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, JSExpanded.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
    }

    private void initBasic(BasicBlockSetRegistries stone) {
        var tag = tag(BlockTags.MINEABLE_WITH_PICKAXE);
        tag(BlockTags.STAIRS).add(stone.STAIRS.get());
        tag(BlockTags.SLABS).add(stone.SLAB.get());
        tag(BlockTags.WALLS).add(stone.WALL.get());

        tag.add(stone.WALL.get());
        tag.add(stone.STAIRS.get());
        tag.add(stone.BLOCK.get());
    }

    private void initStone(StoneRegistries stone) {
        var tag = tag(BlockTags.MINEABLE_WITH_PICKAXE);
        tag(BlockTags.STONE_BUTTONS).add(stone.BUTTON.get());
        tag(BlockTags.STAIRS).add(stone.STAIRS.get());
        tag(BlockTags.SLABS).add(stone.SLAB.get());
        tag(BlockTags.STONE_PRESSURE_PLATES).add(stone.PRESSURE_PLATE.get());
        tag(BlockTags.WALLS).add(stone.WALL.get());

        tag.add(stone.BUTTON.get());
        tag.add(stone.WALL.get());
        tag.add(stone.STAIRS.get());
        tag.add(stone.PRESSURE_PLATE.get());
        tag.add(stone.BLOCK.get());
    }

    private void initWood(WoodRegistries woodRegistries) {
        tag(BlockTags.FENCES).add(woodRegistries.FENCE.get());
        tag(BlockTags.FENCE_GATES).add(woodRegistries.FENCE_GATE.get());
        tag(BlockTags.LOGS).add(woodRegistries.LOG.get(), woodRegistries.STRIPPED_LOG.get(), woodRegistries.WOOD.get(), woodRegistries.STRIPPED_WOOD.get());
        tag(BlockTags.LOGS_THAT_BURN).add(woodRegistries.WOOD.get(), woodRegistries.STRIPPED_LOG.get(), woodRegistries.WOOD.get(), woodRegistries.STRIPPED_WOOD.get());
        tag(BlockTags.BUTTONS).add(woodRegistries.BUTTON.get());
        tag(BlockTags.WOODEN_BUTTONS).add(woodRegistries.BUTTON.get());
        tag(BlockTags.PLANKS).add(woodRegistries.BLOCK.get());
        tag(BlockTags.WOODEN_STAIRS).add(woodRegistries.STAIRS.get());
        tag(BlockTags.WOODEN_SLABS).add(woodRegistries.SLAB.get());
        tag(BlockTags.WOODEN_TRAPDOORS).add(woodRegistries.TRAPDOOR.get());
        tag(BlockTags.LOGS).add(woodRegistries.TRAPDOOR.get());
        tag(BlockTags.LEAVES).add(woodRegistries.LEAVES.get());
        tag(BlockTags.WOODEN_DOORS).add(woodRegistries.DOOR.get());
        tag(BlockTags.MOB_INTERACTABLE_DOORS).add(woodRegistries.DOOR.get());
        tag(BlockTags.DOORS).add(woodRegistries.DOOR.get());
        tag(BlockTags.STANDING_SIGNS).add(woodRegistries.SIGN.get());
        tag(BlockTags.WALL_SIGNS).add(woodRegistries.WALL_SIGN.get());
        tag(BlockTags.CEILING_HANGING_SIGNS).add(woodRegistries.HANGING_SIGN.get());
        tag(BlockTags.WALL_HANGING_SIGNS).add(woodRegistries.HANGING_SIGN_WALL.get());
    }
}
