package jp.jsexpanded.neo.data.server;

import jp.jsexpanded.JSExpanded;
import jp.jsexpanded.server.world.biome.JSExpandedBiomes;
import jp.jsexpanded.server.world.level.JSExpandedDimensions;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class JSExpandedDataProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER =
            new RegistrySetBuilder()
                    .add(Registries.BIOME, JSExpandedBiomes::bootstrap)
                    .add(Registries.DIMENSION_TYPE, JSExpandedDimensions::bootstrap)
                    .add(Registries.NOISE_SETTINGS, JSExpandedDimensions::bootstrapNoise)
                    .add(Registries.LEVEL_STEM, JSExpandedDimensions::bootstrapStem);

    public JSExpandedDataProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(JSExpanded.MOD_ID));
    }
}
