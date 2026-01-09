package jp.jsexpanded.server.world.biome.obj;


import jp.jsexpanded.JSExpanded;
import jp.jurassicsaga.JSCommon;
import lombok.Getter;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;

@Getter
public class JSExpandedBiome {
    private final String biomeName;
    private final ResourceKey<Biome> biomeKey;

    protected boolean hasRain = true;
    protected float temperature = 0.0F;
    protected float downfall = 0.9F;
    protected int waterColor = 0x00C3FF;
    protected int waterFogColor = 0x006D8E;
    protected Integer grassColor = null;
    protected Integer foliageColor = null;
    protected Holder.Reference<SoundEvent> music = SoundEvents.MUSIC_BIOME_SNOWY_SLOPES;


    public JSExpandedBiome(String biomeName) {
        this.biomeName = biomeName;
        this.biomeKey = ResourceKey.create(Registries.BIOME, JSExpanded.createId(biomeName));
    }

    protected SurfaceRules.ConditionSource surfaceNoiseAbove(double value) {
        return SurfaceRules.noiseCondition(Noises.SURFACE, value / (double)8.25F, Double.MAX_VALUE);
    }

    public final Biome create(BootstrapContext<Biome> ctx) {
        MobSpawnSettings.Builder spawner = new MobSpawnSettings.Builder();
        addSpawns(ctx, spawner);
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(ctx.lookup(Registries.PLACED_FEATURE), ctx.lookup(Registries.CONFIGURED_CARVER));
        addSettings(ctx, builder);
        var biomeBuilder = biome(hasRain, temperature, downfall, waterColor, waterFogColor, grassColor, foliageColor, spawner, builder, Musics.createGameMusic(music));
        applyExtraSettings(biomeBuilder);
        return biomeBuilder.build();
    }

    protected void applyExtraSettings(Biome.BiomeBuilder biomeBuilder) {
    }

    protected void addSettings(BootstrapContext<Biome> ctx, BiomeGenerationSettings.Builder builder) {
    }

    protected void addSpawns(BootstrapContext<Biome> ctx, MobSpawnSettings.Builder spawner) {
    }

    private Biome.BiomeBuilder biome(
            boolean pHasPrecipitation,
            float pTemperature,
            float pDownfall,
            int pWaterColor,
            int pWaterFogColor,
            Integer pGrassColorOverride,
            Integer pFoliageColorOverride,
            MobSpawnSettings.Builder pMobSpawnSettings,
            BiomeGenerationSettings.Builder pGenerationSettings,
            Music pBackgroundMusic
    ) {
        BiomeSpecialEffects.Builder biomespecialeffects$builder = new BiomeSpecialEffects.Builder()
                .waterColor(pWaterColor)
                .waterFogColor(pWaterFogColor)
                .fogColor(12638463)
                .skyColor(calculateSkyColor(pTemperature))
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .backgroundMusic(pBackgroundMusic);
        if (pGrassColorOverride != null) {
            biomespecialeffects$builder.grassColorOverride(pGrassColorOverride);
        }

        if (pFoliageColorOverride != null) {
            biomespecialeffects$builder.foliageColorOverride(pFoliageColorOverride);
        }

        return new Biome.BiomeBuilder()
                .hasPrecipitation(pHasPrecipitation)
                .temperature(pTemperature)
                .downfall(pDownfall)
                .specialEffects(biomespecialeffects$builder.build())
                .mobSpawnSettings(pMobSpawnSettings.build())
                .generationSettings(pGenerationSettings.build());
    }

    protected int calculateSkyColor(float pTemperature) {
        float $$1 = pTemperature / 3.0F;
        $$1 = Mth.clamp($$1, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - $$1 * 0.05F, 0.5F + $$1 * 0.1F, 1.0F);
    }

    public final SurfaceRules.RuleSource sequence() {
        return SurfaceRules.ifTrue(
                SurfaceRules.isBiome(biomeKey),
                SurfaceRules.sequence(innerSequence())
        );
    }

    protected SurfaceRules.RuleSource innerSequence() {
        return SurfaceRules.state(Blocks.STONE.defaultBlockState());
    }
}
