package jp.jsexpanded.server.world.biome;

import com.google.common.collect.ImmutableList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class ExpandedSurfaceRuleData {
    private static final SurfaceRules.RuleSource AIR;
    private static final SurfaceRules.RuleSource BEDROCK;
    private static final SurfaceRules.RuleSource WHITE_TERRACOTTA;
    private static final SurfaceRules.RuleSource ORANGE_TERRACOTTA;
    private static final SurfaceRules.RuleSource TERRACOTTA;
    private static final SurfaceRules.RuleSource RED_SAND;
    private static final SurfaceRules.RuleSource RED_SANDSTONE;
    private static final SurfaceRules.RuleSource STONE;
    private static final SurfaceRules.RuleSource DEEPSLATE;
    private static final SurfaceRules.RuleSource DIRT;
    private static final SurfaceRules.RuleSource PODZOL;
    private static final SurfaceRules.RuleSource COARSE_DIRT;
    private static final SurfaceRules.RuleSource MYCELIUM;
    private static final SurfaceRules.RuleSource GRASS_BLOCK;
    private static final SurfaceRules.RuleSource CALCITE;
    private static final SurfaceRules.RuleSource GRAVEL;
    private static final SurfaceRules.RuleSource SAND;
    private static final SurfaceRules.RuleSource SANDSTONE;
    private static final SurfaceRules.RuleSource PACKED_ICE;
    private static final SurfaceRules.RuleSource SNOW_BLOCK;
    private static final SurfaceRules.RuleSource MUD;
    private static final SurfaceRules.RuleSource POWDER_SNOW;
    private static final SurfaceRules.RuleSource ICE;
    private static final SurfaceRules.RuleSource WATER;
    private static final SurfaceRules.RuleSource LAVA;
    private static final SurfaceRules.RuleSource NETHERRACK;
    private static final SurfaceRules.RuleSource SOUL_SAND;
    private static final SurfaceRules.RuleSource SOUL_SOIL;
    private static final SurfaceRules.RuleSource BASALT;
    private static final SurfaceRules.RuleSource BLACKSTONE;
    private static final SurfaceRules.RuleSource WARPED_WART_BLOCK;
    private static final SurfaceRules.RuleSource WARPED_NYLIUM;
    private static final SurfaceRules.RuleSource NETHER_WART_BLOCK;
    private static final SurfaceRules.RuleSource CRIMSON_NYLIUM;
    private static final SurfaceRules.RuleSource ENDSTONE;


    public static SurfaceRules.RuleSource end() {
        return ENDSTONE;
    }

    public static SurfaceRules.RuleSource air() {
        return AIR;
    }

    public static SurfaceRules.RuleSource overworld() {
        return overworldLike(true, false, true);
    }

    public static SurfaceRules.RuleSource overworldLike(
            boolean aboveGround,
            boolean bedrockRoof,
            boolean bedrockFloor
    ) {
        // ===== Water / holes / terrain =====
        SurfaceRules.ConditionSource waterBelow = SurfaceRules.waterBlockCheck(-1, 0);
        SurfaceRules.ConditionSource waterHere  = SurfaceRules.waterBlockCheck(0, 0);
        SurfaceRules.ConditionSource waterStart = SurfaceRules.waterStartCheck(-6, -1);

        SurfaceRules.ConditionSource hole  = SurfaceRules.hole();
        SurfaceRules.ConditionSource steep = SurfaceRules.steep();

        // ===== Biome checks =====
        SurfaceRules.ConditionSource frozenOcean =
                SurfaceRules.isBiome(Biomes.FROZEN_OCEAN, Biomes.DEEP_FROZEN_OCEAN);

        SurfaceRules.ConditionSource beaches =
                SurfaceRules.isBiome(Biomes.WARM_OCEAN, Biomes.BEACH, Biomes.SNOWY_BEACH);

        SurfaceRules.ConditionSource desert =
                SurfaceRules.isBiome(Biomes.DESERT);

        // ===== Basic surface blocks =====
        SurfaceRules.RuleSource grassOrDirt =
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(waterHere, GRASS_BLOCK),
                        DIRT
                );

        SurfaceRules.RuleSource sandOrSandstone =
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, SANDSTONE),
                        SAND
                );

        SurfaceRules.RuleSource gravelOrStone =
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, STONE),
                        GRAVEL
                );

        // ===== Rocky / special biomes =====
        SurfaceRules.RuleSource rockyBiomes =
                SurfaceRules.sequence(
                        // Stony Peaks
                        SurfaceRules.ifTrue(
                                SurfaceRules.isBiome(Biomes.STONY_PEAKS),
                                SurfaceRules.sequence(
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.noiseCondition(Noises.CALCITE, -0.0125, 0.0125),
                                                CALCITE
                                        ),
                                        STONE
                                )
                        ),

                        // Stony Shore
                        SurfaceRules.ifTrue(
                                SurfaceRules.isBiome(Biomes.STONY_SHORE),
                                SurfaceRules.sequence(
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.noiseCondition(Noises.GRAVEL, -0.05, 0.05),
                                                gravelOrStone
                                        ),
                                        STONE
                                )
                        ),

                        // Windswept Hills
                        SurfaceRules.ifTrue(
                                SurfaceRules.isBiome(Biomes.WINDSWEPT_HILLS),
                                SurfaceRules.ifTrue(surfaceNoiseAbove(1.0F), STONE)
                        ),

                        SurfaceRules.ifTrue(beaches, sandOrSandstone),
                        SurfaceRules.ifTrue(desert, sandOrSandstone),

                        SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.DRIPSTONE_CAVES), STONE)
                );

        // ===== Powder snow rules =====
        SurfaceRules.RuleSource powderSnowHigh =
                SurfaceRules.ifTrue(
                        SurfaceRules.noiseCondition(Noises.POWDER_SNOW, 0.45, 0.58),
                        SurfaceRules.ifTrue(waterHere, POWDER_SNOW)
                );

        // ===== Underwater / floor rules =====
        SurfaceRules.RuleSource underwaterRuleA =
                SurfaceRules.sequence(
                        // Frozen Peaks
                        SurfaceRules.ifTrue(
                                SurfaceRules.isBiome(Biomes.FROZEN_PEAKS),
                                SurfaceRules.sequence(
                                        SurfaceRules.ifTrue(steep, PACKED_ICE),
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.noiseCondition(Noises.PACKED_ICE, -0.5F, 0.2),
                                                PACKED_ICE
                                        ),
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.noiseCondition(Noises.ICE, -0.0625F, 0.025),
                                                ICE
                                        ),
                                        SurfaceRules.ifTrue(waterHere, SNOW_BLOCK)
                                )
                        ),

                        // Snowy Slopes
                        SurfaceRules.ifTrue(
                                SurfaceRules.isBiome(Biomes.SNOWY_SLOPES),
                                SurfaceRules.sequence(
                                        SurfaceRules.ifTrue(steep, STONE),
                                        powderSnowHigh,
                                        SurfaceRules.ifTrue(waterHere, SNOW_BLOCK)
                                )
                        ),

                        SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.JAGGED_PEAKS), STONE),

                        SurfaceRules.ifTrue(
                                SurfaceRules.isBiome(Biomes.GROVE),
                                SurfaceRules.sequence(powderSnowHigh, DIRT)
                        ),

                        rockyBiomes,

                        SurfaceRules.ifTrue(
                                SurfaceRules.isBiome(Biomes.WINDSWEPT_SAVANNA),
                                SurfaceRules.ifTrue(surfaceNoiseAbove(1.75F), STONE)
                        ),

                        SurfaceRules.ifTrue(
                                SurfaceRules.isBiome(Biomes.WINDSWEPT_GRAVELLY_HILLS),
                                SurfaceRules.sequence(
                                        SurfaceRules.ifTrue(surfaceNoiseAbove(2.0F), gravelOrStone),
                                        SurfaceRules.ifTrue(surfaceNoiseAbove(1.0F), STONE),
                                        SurfaceRules.ifTrue(surfaceNoiseAbove(-1.0F), DIRT),
                                        gravelOrStone
                                )
                        ),

                        SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.MANGROVE_SWAMP), MUD),
                        DIRT
                );

        // ===== Final surface rules =====
        SurfaceRules.RuleSource surfaceRule =
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(
                                SurfaceRules.ON_FLOOR,
                                SurfaceRules.sequence(
                                        SurfaceRules.ifTrue(
                                                waterBelow,
                                                SurfaceRules.sequence(
                                                        SurfaceRules.ifTrue(
                                                                frozenOcean,
                                                                SurfaceRules.ifTrue(
                                                                        hole,
                                                                        SurfaceRules.sequence(
                                                                                SurfaceRules.ifTrue(waterHere, AIR),
                                                                                SurfaceRules.ifTrue(SurfaceRules.temperature(), ICE),
                                                                                WATER
                                                                        )
                                                                )
                                                        ),
                                                        underwaterRuleA
                                                )
                                        )
                                )
                        ),

                        SurfaceRules.ifTrue(
                                waterStart,
                                SurfaceRules.sequence(
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.ON_FLOOR,
                                                SurfaceRules.ifTrue(
                                                        frozenOcean,
                                                        SurfaceRules.ifTrue(hole, WATER)
                                                )
                                        ),
                                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, underwaterRuleA),
                                        SurfaceRules.ifTrue(beaches, SurfaceRules.ifTrue(SurfaceRules.DEEP_UNDER_FLOOR, SANDSTONE)),
                                        SurfaceRules.ifTrue(desert, SurfaceRules.ifTrue(SurfaceRules.VERY_DEEP_UNDER_FLOOR, SANDSTONE))
                                )
                        ),

                        SurfaceRules.ifTrue(
                                SurfaceRules.ON_FLOOR,
                                SurfaceRules.sequence(
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.isBiome(Biomes.FROZEN_PEAKS, Biomes.JAGGED_PEAKS),
                                                STONE
                                        ),
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.isBiome(
                                                        Biomes.WARM_OCEAN,
                                                        Biomes.LUKEWARM_OCEAN,
                                                        Biomes.DEEP_LUKEWARM_OCEAN
                                                ),
                                                sandOrSandstone
                                        ),
                                        gravelOrStone
                                )
                        )
                );

        // ===== Builder =====
        ImmutableList.Builder<SurfaceRules.RuleSource> rules = ImmutableList.builder();

        if (bedrockRoof) {
            rules.add(
                    SurfaceRules.ifTrue(
                            SurfaceRules.not(
                                    SurfaceRules.verticalGradient(
                                            "bedrock_roof",
                                            VerticalAnchor.belowTop(5),
                                            VerticalAnchor.top()
                                    )
                            ),
                            BEDROCK
                    )
            );
        }

        if (bedrockFloor) {
            rules.add(
                    SurfaceRules.ifTrue(
                            SurfaceRules.verticalGradient(
                                    "bedrock_floor",
                                    VerticalAnchor.bottom(),
                                    VerticalAnchor.aboveBottom(5)
                            ),
                            BEDROCK
                    )
            );
        }

        rules.add(JSExpandedBiomes.sequence());

        SurfaceRules.RuleSource finalSurface =
                SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), surfaceRule);

        rules.add(aboveGround ? finalSurface : surfaceRule);

        rules.add(
                SurfaceRules.ifTrue(
                        SurfaceRules.verticalGradient(
                                "deepslate",
                                VerticalAnchor.absolute(0),
                                VerticalAnchor.absolute(8)
                        ),
                        DEEPSLATE
                )
        );

        return SurfaceRules.sequence(rules.build().toArray(SurfaceRules.RuleSource[]::new));
    }

    private static SurfaceRules.ConditionSource surfaceNoiseAbove(double value) {
        return SurfaceRules.noiseCondition(Noises.SURFACE, value / (double)8.25F, Double.MAX_VALUE);
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }

    static {
        AIR = makeStateRule(Blocks.AIR);
        BEDROCK = makeStateRule(Blocks.BEDROCK);
        WHITE_TERRACOTTA = makeStateRule(Blocks.WHITE_TERRACOTTA);
        ORANGE_TERRACOTTA = makeStateRule(Blocks.ORANGE_TERRACOTTA);
        TERRACOTTA = makeStateRule(Blocks.TERRACOTTA);
        RED_SAND = makeStateRule(Blocks.RED_SAND);
        RED_SANDSTONE = makeStateRule(Blocks.RED_SANDSTONE);
        STONE = makeStateRule(Blocks.STONE);
        DEEPSLATE = makeStateRule(Blocks.DEEPSLATE);
        DIRT = makeStateRule(Blocks.DIRT);
        PODZOL = makeStateRule(Blocks.PODZOL);
        COARSE_DIRT = makeStateRule(Blocks.COARSE_DIRT);
        MYCELIUM = makeStateRule(Blocks.MYCELIUM);
        GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
        CALCITE = makeStateRule(Blocks.CALCITE);
        GRAVEL = makeStateRule(Blocks.GRAVEL);
        SAND = makeStateRule(Blocks.SAND);
        SANDSTONE = makeStateRule(Blocks.SANDSTONE);
        PACKED_ICE = makeStateRule(Blocks.PACKED_ICE);
        SNOW_BLOCK = makeStateRule(Blocks.SNOW_BLOCK);
        MUD = makeStateRule(Blocks.MUD);
        POWDER_SNOW = makeStateRule(Blocks.POWDER_SNOW);
        ICE = makeStateRule(Blocks.ICE);
        WATER = makeStateRule(Blocks.WATER);
        LAVA = makeStateRule(Blocks.LAVA);
        NETHERRACK = makeStateRule(Blocks.NETHERRACK);
        SOUL_SAND = makeStateRule(Blocks.SOUL_SAND);
        SOUL_SOIL = makeStateRule(Blocks.SOUL_SOIL);
        BASALT = makeStateRule(Blocks.BASALT);
        BLACKSTONE = makeStateRule(Blocks.BLACKSTONE);
        WARPED_WART_BLOCK = makeStateRule(Blocks.WARPED_WART_BLOCK);
        WARPED_NYLIUM = makeStateRule(Blocks.WARPED_NYLIUM);
        NETHER_WART_BLOCK = makeStateRule(Blocks.NETHER_WART_BLOCK);
        CRIMSON_NYLIUM = makeStateRule(Blocks.CRIMSON_NYLIUM);
        ENDSTONE = makeStateRule(Blocks.END_STONE);
    }
}
