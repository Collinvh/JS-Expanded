package jp.jsexpanded.server.world.level;

import com.mojang.datafixers.util.Pair;
import jp.jsexpanded.JSExpanded;
import jp.jsexpanded.server.world.biome.ExpandedSurfaceRuleData;
import jp.jsexpanded.server.world.biome.JSExpandedBiomes;
import jp.jurassicsaga.server.base.world.biome.JSBiomeBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalLong;
import java.util.stream.Stream;

public class JSExpandedDimensions {
    private static final ResourceKey<DensityFunction> ZERO = createKey("zero");
    private static final ResourceKey<DensityFunction> Y = createKey("y");
    private static final ResourceKey<DensityFunction> SHIFT_X = createKey("shift_x");
    private static final ResourceKey<DensityFunction> SHIFT_Z = createKey("shift_z");
    private static final ResourceKey<DensityFunction> BASE_3D_NOISE_OVERWORLD = createKey("overworld/base_3d_noise");
    private static final ResourceKey<DensityFunction> BASE_3D_NOISE_NETHER = createKey("nether/base_3d_noise");
    private static final ResourceKey<DensityFunction> BASE_3D_NOISE_END = createKey("end/base_3d_noise");
    public static final ResourceKey<DensityFunction> CONTINENTS = createKey("overworld/continents");
    public static final ResourceKey<DensityFunction> EROSION = createKey("overworld/erosion");
    public static final ResourceKey<DensityFunction> RIDGES = createKey("overworld/ridges");
    public static final ResourceKey<DensityFunction> RIDGES_FOLDED = createKey("overworld/ridges_folded");
    public static final ResourceKey<DensityFunction> OFFSET = createKey("overworld/offset");
    public static final ResourceKey<DensityFunction> FACTOR = createKey("overworld/factor");
    public static final ResourceKey<DensityFunction> JAGGEDNESS = createKey("overworld/jaggedness");
    public static final ResourceKey<DensityFunction> DEPTH = createKey("overworld/depth");
    private static final ResourceKey<DensityFunction> SLOPED_CHEESE = createKey("overworld/sloped_cheese");
    public static final ResourceKey<DensityFunction> CONTINENTS_LARGE = createKey("overworld_large_biomes/continents");
    public static final ResourceKey<DensityFunction> EROSION_LARGE = createKey("overworld_large_biomes/erosion");
    private static final ResourceKey<DensityFunction> OFFSET_LARGE = createKey("overworld_large_biomes/offset");
    private static final ResourceKey<DensityFunction> FACTOR_LARGE = createKey("overworld_large_biomes/factor");
    private static final ResourceKey<DensityFunction> JAGGEDNESS_LARGE = createKey("overworld_large_biomes/jaggedness");
    private static final ResourceKey<DensityFunction> DEPTH_LARGE = createKey("overworld_large_biomes/depth");
    private static final ResourceKey<DensityFunction> SLOPED_CHEESE_LARGE = createKey("overworld_large_biomes/sloped_cheese");
    private static final ResourceKey<DensityFunction> OFFSET_AMPLIFIED = createKey("overworld_amplified/offset");
    private static final ResourceKey<DensityFunction> FACTOR_AMPLIFIED = createKey("overworld_amplified/factor");
    private static final ResourceKey<DensityFunction> JAGGEDNESS_AMPLIFIED = createKey("overworld_amplified/jaggedness");
    private static final ResourceKey<DensityFunction> DEPTH_AMPLIFIED = createKey("overworld_amplified/depth");
    private static final ResourceKey<DensityFunction> SLOPED_CHEESE_AMPLIFIED = createKey("overworld_amplified/sloped_cheese");
    private static final ResourceKey<DensityFunction> SLOPED_CHEESE_END = createKey("end/sloped_cheese");
    private static final ResourceKey<DensityFunction> SPAGHETTI_ROUGHNESS_FUNCTION = createKey("overworld/caves/spaghetti_roughness_function");
    private static final ResourceKey<DensityFunction> ENTRANCES = createKey("overworld/caves/entrances");
    private static final ResourceKey<DensityFunction> NOODLE = createKey("overworld/caves/noodle");
    private static final ResourceKey<DensityFunction> PILLARS = createKey("overworld/caves/pillars");
    private static final ResourceKey<DensityFunction> SPAGHETTI_2D_THICKNESS_MODULATOR = createKey("overworld/caves/spaghetti_2d_thickness_modulator");
    private static final ResourceKey<DensityFunction> SPAGHETTI_2D = createKey("overworld/caves/spaghetti_2d");

    public static final ResourceKey<DimensionType> EXPANDED_TYPE =
            ResourceKey.create(Registries.DIMENSION_TYPE, JSExpanded.createId("expanded_type"));

    public static void bootstrap(BootstrapContext<DimensionType> ctx) {
        ctx.register(EXPANDED_TYPE, new DimensionType(
                OptionalLong.empty(),     // fixed time
                true,                     // skylight
                false,                    // ceiling
                false,                    // ultra warm
                true,                     // natural
                1.0,                      // coordinate scale
                true,                     // beds work
                false,                    // respawn anchors
                -64,                      // min Y
                384,                      // height
                384,                      // logical height
                BlockTags.INFINIBURN_OVERWORLD,
                BuiltinDimensionTypes.OVERWORLD_EFFECTS,
                0.0f,
                new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)
        ));
    }

    public static final ResourceKey<LevelStem> EXPANDED_DIM =
            ResourceKey.create(Registries.LEVEL_STEM, JSExpanded.createId("expanded_dimension"));


    public static void bootstrapStem(BootstrapContext<LevelStem> ctx) {

        HolderGetter<Biome> biomes = ctx.lookup(Registries.BIOME);
        HolderGetter<DimensionType> types = ctx.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noise = ctx.lookup(Registries.NOISE_SETTINGS);

        JSExpandedBiomeBuilder builder = new JSExpandedBiomeBuilder()
                .setRaritySeed(12345L)
                .addVariant(JSExpandedBiomes.SEA.getBiomeKey(), JSExpandedBiomes.SUNKEN_CITY.getBiomeKey(), 4);
        ctx.register(EXPANDED_DIM,
                new LevelStem(
                        types.getOrThrow(EXPANDED_TYPE),
                        new NoiseBasedChunkGenerator(
                                MultiNoiseBiomeSource.createFromList(
                                        buildParameters(biomes, builder)
                                ),
                                noise.getOrThrow(EXPANDED_NOISE)
                        )
                )
        );
    }

    public static Climate.ParameterList<Holder<Biome>> buildParameters(
            HolderGetter<Biome> biomes,
            JSExpandedBiomeBuilder provider
    ) {
        List<Pair<Climate.ParameterPoint, Holder<Biome>>> list = new ArrayList<>();

        provider.addBiomes(pair -> {
            Holder<Biome> holder = biomes.getOrThrow(pair.getSecond());
            list.add(Pair.of(pair.getFirst(), holder));
        });

        return new Climate.ParameterList<>(list);
    }

    public static final ResourceKey<NoiseGeneratorSettings> EXPANDED_NOISE =
            ResourceKey.create(Registries.NOISE_SETTINGS, JSExpanded.createId("expanded_noise"));

    public static void bootstrapNoise(BootstrapContext<NoiseGeneratorSettings> ctx) {
        HolderGetter<NormalNoise.NoiseParameters> noises = ctx.lookup(Registries.NOISE);



        ctx.register(EXPANDED_NOISE,
                new NoiseGeneratorSettings(
                        NoiseSettings.create(-64, 384, 1, 2),
                        Blocks.STONE.defaultBlockState(),
                        Blocks.WATER.defaultBlockState(),
                        expandedRouter(ctx.lookup(Registries.DENSITY_FUNCTION), ctx.lookup(Registries.NOISE)),
                        ExpandedSurfaceRuleData.overworld(),
                        List.of(),
                        63,
                        false,
                        true,
                        true,
                        false
                )
        );
    }


    protected static NoiseRouter expandedRouter(
            HolderGetter<DensityFunction> densityFunctions,
            HolderGetter<NormalNoise.NoiseParameters> noiseParameters
    ) {
        // ===== Aquifers =====
        DensityFunction aquiferBarrier =
                DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_BARRIER), 0.5F);
        DensityFunction aquiferFloodedness =
                DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_FLOODEDNESS), 0.67F);
        DensityFunction aquiferSpread =
                DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_SPREAD), 0.7142857F);
        DensityFunction aquiferLava =
                DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_LAVA));

        // ===== Climate =====
        DensityFunction shiftX = getFunction(densityFunctions, SHIFT_X);
        DensityFunction shiftZ = getFunction(densityFunctions, SHIFT_Z);

        DensityFunction temperature =
                DensityFunctions.shiftedNoise2d(
                        shiftX, shiftZ, 0.25F,
                        noiseParameters.getOrThrow(Noises.TEMPERATURE)
                );

        DensityFunction vegetation =
                DensityFunctions.shiftedNoise2d(
                        shiftX, shiftZ, 0.25F,
                        noiseParameters.getOrThrow(Noises.VEGETATION)
                );

        // ===== Base terrain =====
        DensityFunction continentFactor = getFunction(densityFunctions, FACTOR);
        DensityFunction depth = getFunction(densityFunctions, DEPTH);

        DensityFunction baseDensity =
                noiseGradientDensity(
                        DensityFunctions.cache2d(continentFactor),
                        DensityFunctions.mul(depth, DensityFunctions.constant(1.3F))
                );

        // IMPORTANT: density increases toward surface (solid), decreases underground (air)
        DensityFunction verticalBias =
                DensityFunctions.yClampedGradient(-64, 320, -1.5F, 1.5F);

        DensityFunction terrainDensity =
                DensityFunctions.add(baseDensity, verticalBias);

        // ===== Erosion =====
        DensityFunction erosion =
                DensityFunctions.cache2d(
                        DensityFunctions.mul(
                                getFunction(densityFunctions, EROSION),
                                DensityFunctions.constant(2.0F)
                        )
                );

        // ===== Mountains =====
        DensityFunction rangeNoise =
                DensityFunctions.cache2d(
                        DensityFunctions.noise(noiseParameters.getOrThrow(Noises.RIDGE), 0.7F)
                );

        DensityFunction mountainRangeMask =
                DensityFunctions.add(
                        DensityFunctions.constant(1.0F),
                        DensityFunctions.mul(rangeNoise.abs(), DensityFunctions.constant(-1.0F))
                ).clamp(0.0F, 1.0F);

        mountainRangeMask =
                DensityFunctions.rangeChoice(
                        mountainRangeMask,
                        0.55F, 1.0F,
                        mountainRangeMask,
                        DensityFunctions.constant(0.0F)
                );

        DensityFunction mountainHeight =
                DensityFunctions.mul(
                        DensityFunctions.cache2d(
                                DensityFunctions.noise(noiseParameters.getOrThrow(Noises.EROSION), 0.3F)
                        ),
                        DensityFunctions.constant(3.0F)
                );

        DensityFunction mountainLift =
                DensityFunctions.mul(mountainHeight, mountainRangeMask);

        terrainDensity =
                DensityFunctions.add(
                        terrainDensity,
                        DensityFunctions.mul(mountainLift, DensityFunctions.constant(1.6F))
                );

        DensityFunction rangeErosion =
                DensityFunctions.mul(erosion, mountainRangeMask);

        terrainDensity =
                DensityFunctions.add(
                        terrainDensity,
                        DensityFunctions.mul(rangeErosion, DensityFunctions.constant(-0.9F))
                );

        // ===== Caves =====
        DensityFunction slopedCheese =
                DensityFunctions.mul(
                        getFunction(densityFunctions, SLOPED_CHEESE),
                        DensityFunctions.constant(0.9F)
                );

        DensityFunction caveEntrances =
                DensityFunctions.min(
                        slopedCheese,
                        DensityFunctions.mul(
                                DensityFunctions.constant(2.5F),
                                getFunction(densityFunctions, ENTRANCES)
                        )
                );

        DensityFunction undergroundDensity =
                DensityFunctions.rangeChoice(
                        slopedCheese,
                        -1_000_000.0F, 1.5625F,
                        caveEntrances,
                        underground(densityFunctions, noiseParameters, slopedCheese)
                );

        // hard-disable caves near surface (prevents surface holes)
        DensityFunction caveMask =
                DensityFunctions.rangeChoice(
                        getFunction(densityFunctions, Y),
                        -1_000_000F, 48F,
                        DensityFunctions.constant(1.0F),
                        DensityFunctions.constant(0.0F)
                );

        undergroundDensity = DensityFunctions.mul(undergroundDensity, caveMask);

        // ===== Rivers =====
        DensityFunction riverCarving =
                DensityFunctions.mul(
                        erosion,
                        DensityFunctions.yClampedGradient(-64, 96, 1.0F, 0.0F)
                );

        terrainDensity =
                DensityFunctions.add(
                        terrainDensity,
                        DensityFunctions.mul(riverCarving, DensityFunctions.constant(-0.5F))
                );

        // ===== Final terrain (NO NOODLE) =====
        DensityFunction combined =
                DensityFunctions.add(terrainDensity, undergroundDensity);

        // extra surface reinforcement for aquifers
        DensityFunction surfaceFloor =
                DensityFunctions.yClampedGradient(48, 96, 0.6F, 1.2F);

        combined = DensityFunctions.add(combined, surfaceFloor);

        DensityFunction finalDensity =
                postProcess(slideOverworld(combined)).clamp(-64.0F, 64.0F);

        // ===== Ore veins =====
        DensityFunction y = getFunction(densityFunctions, Y);

        int veinMinY = Stream.of(VeinType.values()).mapToInt(v -> v.minY).min()
                .orElse(-DimensionType.MIN_Y * 2);
        int veinMaxY = Stream.of(VeinType.values()).mapToInt(v -> v.maxY).max()
                .orElse(-DimensionType.MIN_Y * 2);

        DensityFunction oreVeininess =
                yLimitedInterpolatable(
                        y,
                        DensityFunctions.noise(noiseParameters.getOrThrow(Noises.ORE_VEININESS), 1.5F, 1.5F),
                        veinMinY,
                        veinMaxY,
                        0
                );

        DensityFunction oreVeinA =
                yLimitedInterpolatable(
                        y,
                        DensityFunctions.noise(noiseParameters.getOrThrow(Noises.ORE_VEIN_A), 4.0F, 4.0F),
                        veinMinY,
                        veinMaxY,
                        0
                ).abs();

        DensityFunction oreVeinB =
                yLimitedInterpolatable(
                        y,
                        DensityFunctions.noise(noiseParameters.getOrThrow(Noises.ORE_VEIN_B), 4.0F, 4.0F),
                        veinMinY,
                        veinMaxY,
                        0
                ).abs();

        DensityFunction oreVeinRidge =
                DensityFunctions.add(
                        DensityFunctions.constant(-0.08F),
                        DensityFunctions.max(oreVeinA, oreVeinB)
                );

        DensityFunction oreGap =
                DensityFunctions.noise(noiseParameters.getOrThrow(Noises.ORE_GAP));

        return new NoiseRouter(
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                aquiferLava,
                temperature,
                vegetation,
                getFunction(densityFunctions, CONTINENTS),
                erosion,
                depth,
                getFunction(densityFunctions, RIDGES),
                terrainDensity,
                finalDensity,
                oreVeininess,
                oreVeinRidge,
                oreGap
        );
    }


    private static DensityFunction clampDF(DensityFunction df, float min, float max) {
        return DensityFunctions.rangeChoice(
                df,
                min, max,
                df,
                DensityFunctions.rangeChoice(
                        df,
                        max, 1_000_000F,
                        DensityFunctions.constant(max),
                        DensityFunctions.constant(min)
                )
        );
    }


    private static DensityFunction getFunction(HolderGetter<DensityFunction> densityFunctions, ResourceKey<DensityFunction> key) {
        return new DensityFunctions.HolderHolder(densityFunctions.getOrThrow(key));
    }

    private static DensityFunction noiseGradientDensity(DensityFunction minFunction, DensityFunction maxFunction) {
        DensityFunction densityfunction = DensityFunctions.mul(maxFunction, minFunction);
        return DensityFunctions.mul(DensityFunctions.constant(4.0F), densityfunction.quarterNegative());
    }

    private static DensityFunction yLimitedInterpolatable(DensityFunction input, DensityFunction whenInRange, int minY, int maxY, int whenOutOfRange) {
        return DensityFunctions.interpolated(DensityFunctions.rangeChoice(input, minY, maxY + 1, whenInRange, DensityFunctions.constant(whenOutOfRange)));
    }

    private static DensityFunction postProcess(DensityFunction densityFunction) {
        DensityFunction densityfunction = DensityFunctions.blendDensity(densityFunction);
        return DensityFunctions.mul(DensityFunctions.interpolated(densityfunction), DensityFunctions.constant(0.64)).squeeze();
    }

    private static ResourceKey<DensityFunction> createKey(String location) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, ResourceLocation.withDefaultNamespace(location));
    }

    private static DensityFunction slideOverworld(DensityFunction densityFunction) {
        return slide(densityFunction, -64, 384, 80, 64, -0.078125F, 0, 24, 0.1171875F);
    }

    private static DensityFunction underground(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise.NoiseParameters> noiseParameters, DensityFunction p_256658_) {
        DensityFunction densityfunction = getFunction(densityFunctions, SPAGHETTI_2D);
        DensityFunction densityfunction1 = getFunction(densityFunctions, SPAGHETTI_ROUGHNESS_FUNCTION);
        DensityFunction densityfunction2 = DensityFunctions.noise(noiseParameters.getOrThrow(Noises.CAVE_LAYER), 8.0F);
        DensityFunction densityfunction3 = DensityFunctions.mul(DensityFunctions.constant(4.0F), densityfunction2.square());
        DensityFunction densityfunction4 = DensityFunctions.noise(noiseParameters.getOrThrow(Noises.CAVE_CHEESE), 0.6666666666666666);
        DensityFunction densityfunction5 = DensityFunctions.add(DensityFunctions.add(DensityFunctions.constant(0.27), densityfunction4).clamp(-1.0F, 1.0F), DensityFunctions.add(DensityFunctions.constant(1.5F), DensityFunctions.mul(DensityFunctions.constant(-0.64), p_256658_)).clamp(0.0F, 0.5F));
        DensityFunction densityfunction6 = DensityFunctions.add(densityfunction3, densityfunction5);
        DensityFunction densityfunction7 = DensityFunctions.min(DensityFunctions.min(densityfunction6, getFunction(densityFunctions, ENTRANCES)), DensityFunctions.add(densityfunction, densityfunction1));
        DensityFunction densityfunction8 = getFunction(densityFunctions, PILLARS);
        DensityFunction densityfunction9 = DensityFunctions.rangeChoice(densityfunction8, -1000000.0F, 0.03, DensityFunctions.constant(-1000000.0F), densityfunction8);
        return DensityFunctions.max(densityfunction7, densityfunction9);
    }

    private static DensityFunction slide(DensityFunction input, int minY, int maxY, int p_224447_, int p_224448_, double p_224449_, int p_224450_, int p_224451_, double p_224452_) {
        DensityFunction densityfunction1 = DensityFunctions.yClampedGradient(minY + maxY - p_224447_, minY + maxY - p_224448_, 1.0F, 0.0F);
        DensityFunction $$9 = DensityFunctions.lerp(densityfunction1, p_224449_, input);
        DensityFunction densityfunction2 = DensityFunctions.yClampedGradient(minY + p_224450_, minY + p_224451_, 0.0F, 1.0F);
        return DensityFunctions.lerp(densityfunction2, p_224452_, $$9);
    }


    protected enum VeinType {
        COPPER(Blocks.COPPER_ORE.defaultBlockState(), Blocks.RAW_COPPER_BLOCK.defaultBlockState(), Blocks.GRANITE.defaultBlockState(), 0, 50),
        IRON(Blocks.DEEPSLATE_IRON_ORE.defaultBlockState(), Blocks.RAW_IRON_BLOCK.defaultBlockState(), Blocks.TUFF.defaultBlockState(), -60, -8);

        final BlockState ore;
        final BlockState rawOreBlock;
        final BlockState filler;
        protected final int minY;
        protected final int maxY;

        private VeinType(BlockState ore, BlockState rawOreBlock, BlockState filler, int minY, int maxY) {
            this.ore = ore;
            this.rawOreBlock = rawOreBlock;
            this.filler = filler;
            this.minY = minY;
            this.maxY = maxY;
        }
    }
}