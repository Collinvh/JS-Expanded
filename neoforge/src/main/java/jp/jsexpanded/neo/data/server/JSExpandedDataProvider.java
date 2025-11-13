package jp.jsexpanded.neo.data.server;

import jp.jsexpanded.JSExpanded;
import jp.jurassicsaga.neo.server.world.JSBiomeModifiers;
import jp.jurassicsaga.server.base.world.biome.JSBiomes;
import jp.jurassicsaga.server.base.world.feature.JSConfiguredFeatures;
import jp.jurassicsaga.server.base.world.feature.JSPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class JSExpandedDataProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER =
            new RegistrySetBuilder();

    public JSExpandedDataProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(JSExpanded.MOD_ID));
    }
}
