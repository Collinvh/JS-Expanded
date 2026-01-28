package jp.jsexpanded.server.world.level;

import com.mojang.serialization.MapCodec;
import jp.jsexpanded.JSExpanded;
import jp.jsexpanded.server.world.level.gen.JSBasedChunkGenerator;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.chunk.ChunkGenerator;
import travelers.util.helper.TravelersRegistry;

public class JSLevel {
    public static final TravelersRegistry<MapCodec<? extends ChunkGenerator>> REGISTRY = new TravelersRegistry<>(BuiltInRegistries.CHUNK_GENERATOR, JSExpanded.MOD_ID);

    public static void init() {
        REGISTRY.register("js_generator", () -> JSBasedChunkGenerator.CODEC);
    }
}
