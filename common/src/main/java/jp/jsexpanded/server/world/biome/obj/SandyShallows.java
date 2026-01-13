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

public class SandyShallows extends JSExpandedBiome {
    public SandyShallows() {
        super("sandy_shallows");
        temperature = 5;
        grassColor = 0xA88C79;
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
                SurfaceRules.ifTrue(
                        SurfaceRules.ON_FLOOR,
                        SurfaceRules.sequence(
                                SurfaceRules.state(Blocks.SAND.defaultBlockState())
                        )
                )
        );
    }
}
