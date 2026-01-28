package jp.jsexpanded;

import com.google.common.base.Supplier;
import jp.jsexpanded.server.animals.JSExpandedAnimals;
import jp.jsexpanded.server.block.JSExpandedBlocks;
import jp.jsexpanded.server.sound.JSExpandedSounds;
import jp.jsexpanded.server.world.level.JSLevel;
import jp.jurassicsaga.JSConstants;
import jp.jurassicsaga.server.base.generic.Versions;
import jp.jurassicsaga.server.base.item.JSItemGroups;
import jp.jurassicsaga.server.util.JSHelper;
import jp.jurassicsaga.server.v1.block.JSV1Blocks;
import jp.jurassicsaga.server.v2.block.JSV2Blocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.ItemLike;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import travelers.TravelersMain;
import travelers.util.TravelersUtil;
import travelers.util.helper.TravelersRegistry;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class JSExpanded {
	public static final String MOD_ID = "jsexpanded";
	public static final String MOD_NAME = "JSExpanded";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
    public static final TravelersRegistry<CreativeModeTab> TABS = new TravelersRegistry<>(BuiltInRegistries.CREATIVE_MODE_TAB, MOD_ID);
    public static final Versions.Version EXPANDED_VERSION = Versions.register(
            createId("9000.0"), 9000.0f
    );

    public static void init() {
        TravelersMain.registerMod(MOD_ID);

        TravelersUtil.disableModForGecko(MOD_ID);
//        TravelersMain.enableDebugMode();
        JSExpandedAnimals.init();
        JSExpandedBlocks.init();
        JSExpandedSounds.init();
        JSLevel.init();

        BLOCKS = TABS.register("jsexpanded.items", () -> JSHelper.jsPlatform.registerMenu(
                Component.translatable("itemGroup.jsexpanded.items"),
                () -> JSExpandedBlocks.BULLWEED.get().asItem(),
                (version,output) -> {
                    Set<ItemLike> added = new HashSet<>();
                    Consumer<ItemLike> safeOutput = item -> {
                        if (added.add(item)) output.add(item.asItem().getDefaultInstance());
                    };
                    JSExpandedBlocks.BLOCKS.getValues().forEach((s, blockSupplier) -> safeOutput.accept(blockSupplier.get()));

                    return output;
                }
        ));
    }

    public static Supplier<CreativeModeTab> BLOCKS;


    public static ResourceLocation createId(String s) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, s);
    }
}