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
    ) {// ===== Climate =====
        DensityFunction shiftX = getFunction(densityFunctions, SHIFT_X);
        DensityFunction shiftZ = getFunction(densityFunctions, SHIFT_Z);

        DensityFunction temperature =
                DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25D,
                        noiseParameters.getOrThrow(Noises.TEMPERATURE));

        DensityFunction vegetation =
                DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25D,
                        noiseParameters.getOrThrow(Noises.VEGETATION));


// ===== Base terrain =====
        DensityFunction continentFactor = getFunction(densityFunctions, FACTOR);
        DensityFunction depth = getFunction(densityFunctions, DEPTH);

        DensityFunction continents =
                DensityFunctions.cache2d(getFunction(densityFunctions, CONTINENTS));

        DensityFunction erosion =
                DensityFunctions.cache2d(getFunction(densityFunctions, EROSION));

        DensityFunction baseDensity =
                noiseGradientDensity(
                        DensityFunctions.cache2d(continentFactor),
                        DensityFunctions.mul(depth, DensityFunctions.constant(0.9D))
                );

        DensityFunction verticalBias =
                DensityFunctions.yClampedGradient(-64, 320, -0.9F, 0.9F);

        DensityFunction terrainDensity =
                DensityFunctions.add(baseDensity, verticalBias);


// =====================================================
// ===== Mountains (vanilla-style jaggedness) =====
// =====================================================

// Use routed RIDGES, not raw noise
        DensityFunction ridges =
                DensityFunctions.cache2d(getFunction(densityFunctions, RIDGES));


// ---- Fold weirdness into Peaks & Valleys (PV) ----
// PV = (-abs(abs(ridges) - 2/3) + 1/3) * 3

        DensityFunction ridgesAbs = ridges.abs();

        DensityFunction folded =
                DensityFunctions.add(
                        DensityFunctions.constant(-0.6666667D),
                        ridgesAbs
                ).abs();

        DensityFunction pv =
                DensityFunctions.mul(
                        DensityFunctions.add(folded, DensityFunctions.constant(-0.3333333D)),
                        DensityFunctions.constant(-3.0D)
                );


// ---- Continentalness gate (coasts = 0, inland = 1) ----
        DensityFunction inlandMask =
                DensityFunctions.add(
                        DensityFunctions.mul(continents, DensityFunctions.constant(1.2D)),
                        DensityFunctions.constant(0.4D)
                ).clamp(0.0D, 1.0D);


// ---- Erosion gate (flat biomes = 0, rugged = 1) ----
        DensityFunction ruggedMask =
                DensityFunctions.add(
                        DensityFunctions.mul(erosion, DensityFunctions.constant(-1.3D)),
                        DensityFunctions.constant(1.1D)
                ).clamp(0.0D, 1.0D);


// ---- Final jaggedness strength ----
        DensityFunction jaggednessStrength =
                DensityFunctions.mul(inlandMask, ruggedMask);


// ---- Apply mountain lift ----
        DensityFunction mountainJaggedness =
                DensityFunctions.mul(
                        pv,
                        DensityFunctions.mul(jaggednessStrength, DensityFunctions.constant(1.6D))
                );

        terrainDensity =
                DensityFunctions.add(terrainDensity, mountainJaggedness);


// =====================================================
// ===== Rivers (erosion based, no beach damage) =====
// =====================================================

// River carving only inland + only where erosion says valleys exist
        DensityFunction riverMask =
                DensityFunctions.mul(inlandMask, ruggedMask);

        DensityFunction riverCarving =
                DensityFunctions.mul(
                        erosion,
                        DensityFunctions.mul(riverMask, DensityFunctions.constant(1.2D))
                );

        terrainDensity =
                DensityFunctions.add(
                        terrainDensity,
                        DensityFunctions.mul(riverCarving, DensityFunctions.constant(-1.1D))
                );


// =====================================================
// ===== Surface shaping =====
// =====================================================
        DensityFunction surfaceFloor =
                DensityFunctions.yClampedGradient(40, 96, 0.3F, 0.8F);

        terrainDensity =
                DensityFunctions.add(terrainDensity, surfaceFloor);


// ===== Final terrain =====
        DensityFunction finalDensity =
                postProcess(slideOverworld(terrainDensity)).clamp(-8.0D, 8.0D);


// ===== Initial density (pre-cave) =====
        DensityFunction initialDensityWithoutJaggedness =
                slideOverworld(
                        DensityFunctions.add(baseDensity, DensityFunctions.constant(-0.703125D))
                                .clamp(-8.0D, 8.0D)
                );


// ===== Ore veins =====
        DensityFunction y = getFunction(densityFunctions, Y);

        int veinMinY = Stream.of(VeinType.values()).mapToInt(v -> v.minY).min()
                .orElse(-DimensionType.MIN_Y * 2);
        int veinMaxY = Stream.of(VeinType.values()).mapToInt(v -> v.maxY).max()
                .orElse(-DimensionType.MIN_Y * 2);

        DensityFunction oreVeininess =
                yLimitedInterpolatable(
                        y,
                        DensityFunctions.noise(noiseParameters.getOrThrow(Noises.ORE_VEININESS), 1.5D, 1.5D),
                        veinMinY, veinMaxY, 0
                );

        DensityFunction oreVeinA =
                yLimitedInterpolatable(
                        y,
                        DensityFunctions.noise(noiseParameters.getOrThrow(Noises.ORE_VEIN_A), 4.0D, 4.0D),
                        veinMinY, veinMaxY, 0
                ).abs();

        DensityFunction oreVeinB =
                yLimitedInterpolatable(
                        y,
                        DensityFunctions.noise(noiseParameters.getOrThrow(Noises.ORE_VEIN_B), 4.0D, 4.0D),
                        veinMinY, veinMaxY, 0
                ).abs();

        DensityFunction oreVeinRidge =
                DensityFunctions.add(
                        DensityFunctions.constant(-0.08D),
                        DensityFunctions.max(oreVeinA, oreVeinB)
                );

        DensityFunction oreGap =
                DensityFunctions.noise(noiseParameters.getOrThrow(Noises.ORE_GAP));

        return new NoiseRouter(
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                temperature,
                vegetation,
                continents,
                erosion,
                depth,
                getFunction(densityFunctions, RIDGES),
                initialDensityWithoutJaggedness,
                finalDensity,
                oreVeininess,
                oreVeinRidge,
                oreGap
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

    private static DensityFunction slide(
            DensityFunction input,
            int minY, int maxY,
            int topStart, int topEnd, double topTarget,
            int bottomStart, int bottomEnd, double bottomTarget
    ) {
        // soften top fade (less crushing of caves near surface)
        DensityFunction topGradient =
                DensityFunctions.yClampedGradient(
                        minY + maxY - topStart,
                        minY + maxY - topEnd,
                        0.9F, 0.0F
                );

        DensityFunction topLerp =
                DensityFunctions.lerp(topGradient, topTarget, input);

        // soften bottom fade (keep caves near bedrock)
        DensityFunction bottomGradient =
                DensityFunctions.yClampedGradient(
                        minY + bottomStart,
                        minY + bottomEnd,
                        0.1F, 1.0F
                );

        return DensityFunctions.lerp(bottomGradient, bottomTarget, topLerp);
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