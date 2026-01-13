package jp.jsexpanded.server.world.biome.obj;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class MightyRiver extends JSExpandedBiome {
    public MightyRiver() {
        super("mighty_river");
        temperature = 5;
        grassColor = 0x52FF00;
        waterColor = 0x3F76E4;
    }

    @Override
    protected void addSpawns(BootstrapContext<Biome> ctx, MobSpawnSettings.Builder spawner) {
        BiomeDefaultFeatures.monsters(spawner, 95, 5, 4, false);
    }

    @Override
    protected void applyExtraSettings(Biome.BiomeBuilder biomeBuilder) {
    }

    @Override
    protected void addSettings(BootstrapContext<Biome> ctx, BiomeGenerationSettings.Builder builder) {
        var lookup = ctx.lookup(Registries.PLACED_FEATURE);

        BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
        BiomeDefaultFeatures.addDefaultOres(builder);
        BiomeDefaultFeatures.addGiantTaigaVegetation(builder);
        BiomeDefaultFeatures.addTaigaGrass(builder);
        BiomeDefaultFeatures.addDefaultSoftDisks(builder);
        BiomeDefaultFeatures.addDefaultMushrooms(builder);
    }

    @Override
    protected SurfaceRules.RuleSource innerSequence() {
        return SurfaceRules.ifTrue(
                SurfaceRules.abovePreliminarySurface(),
                SurfaceRules.sequence(

                        // Underwater river / lake beds
                        SurfaceRules.ifTrue(
                                SurfaceRules.waterBlockCheck(0, 0),
                                SurfaceRules.sequence(
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.noiseCondition(Noises.GRAVEL, 0.4D, 1.0D),
                                                SurfaceRules.state(Blocks.GRAVEL.defaultBlockState())
                                        ),
                                        SurfaceRules.state(Blocks.STONE.defaultBlockState())
                                )
                        ),

                        // Land surface
                        SurfaceRules.ifTrue(
                                SurfaceRules.ON_FLOOR,
                                SurfaceRules.sequence(
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.noiseCondition(Noises.GRAVEL, 0.55D, 1.0D),
                                                SurfaceRules.state(Blocks.COARSE_DIRT.defaultBlockState())
                                        ),
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.noiseCondition(Noises.SWAMP, 0.7D, 1.0D),
                                                SurfaceRules.state(Blocks.PODZOL.defaultBlockState())
                                        ),
                                        SurfaceRules.state(Blocks.GRASS_BLOCK.defaultBlockState())
                                )
                        )
                )
        );
    }
}
