package jp.jsexpanded.neo.data;

import jp.jsexpanded.JSExpanded;
import jp.jsexpanded.neo.data.client.JSExpandedLanguageProvider;
import jp.jsexpanded.neo.data.client.JSExpandedModelProvider;
import jp.jsexpanded.neo.data.client.JSExpandedSoundProvider;
import jp.jsexpanded.neo.data.server.*;
import jp.jsexpanded.neo.data.server.loot.JSExpandedBlockLootTableProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class GatherData {
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        if(!event.getModContainer().getNamespace().equalsIgnoreCase(JSExpanded.MOD_ID)) return;

        JSExpandedBlockTagProvider blockTags = new JSExpandedBlockTagProvider(generator.getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper());
        event.getGenerator().addProvider(event.includeClient(), new JSExpandedModelProvider.Block(packOutput, event.getExistingFileHelper()));
        event.getGenerator().addProvider(event.includeClient(), new JSExpandedModelProvider.Item(packOutput, event.getExistingFileHelper()));
        event.getGenerator().addProvider(event.includeClient(), new JSExpandedLanguageProvider(packOutput));
        event.getGenerator().addProvider(event.includeClient(), new JSExpandedSoundProvider(packOutput, event.getExistingFileHelper()));
        event.getGenerator().addProvider(event.includeServer(), blockTags);
        event.getGenerator().addProvider(event.includeServer(), new JSExpandedItemTagProvider(packOutput, event.getLookupProvider(), blockTags.contentsGetter(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(event.includeServer(), new JSExpandedCraftingProvider(packOutput, event.getLookupProvider()));
        event.getGenerator().addProvider(event.includeServer(), new JSExpandedDataProvider(packOutput, event.getLookupProvider()));
        event.getGenerator().addProvider(event.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(), List.of(new LootTableProvider.SubProviderEntry(JSExpandedBlockLootTableProvider::new, LootContextParamSets.BLOCK)), event.getLookupProvider()));
        event.getGenerator().addProvider(event.includeServer(), new AdvancementProvider(packOutput, event.getLookupProvider(), event.getExistingFileHelper(), List.of(new JSExpandedAchievementProvider())));
        try {
            generator.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
