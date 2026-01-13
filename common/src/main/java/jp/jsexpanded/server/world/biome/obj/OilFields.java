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

public class OilFields extends JSExpandedBiome {
    public OilFields() {
        super("oil_fields");
        temperature = 5;
        grassColor = 0x59211D;
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
        BiomeDefaultFeatures.addDefaultSoftDisks(builder);


        BiomeDefaultFeatures.addDesertVegetation(builder);
    }

    @Override
    protected SurfaceRules.RuleSource innerSequence() {
        return SurfaceRules.ifTrue(
                SurfaceRules.abovePreliminarySurface(),
                SurfaceRules.ifTrue(
                        SurfaceRules.ON_FLOOR,
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(
                                        SurfaceRules.noiseCondition(Noises.SWAMP, 0.7D, 1.0D),
                                        SurfaceRules.state(Blocks.ROOTED_DIRT.defaultBlockState())
                                ),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.noiseCondition(Noises.SWAMP, 0.45D, 0.7D),
                                        SurfaceRules.state(Blocks.MUD.defaultBlockState())
                                ),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.noiseCondition(Noises.SWAMP, -0.01D, 0.25D),
                                        SurfaceRules.state(Blocks.COARSE_DIRT.defaultBlockState())
                                ),
                                SurfaceRules.state(Blocks.SAND.defaultBlockState())
                        )
                )
        );
    }

}
