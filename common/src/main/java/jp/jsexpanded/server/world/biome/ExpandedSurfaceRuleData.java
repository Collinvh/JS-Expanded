package jp.jsexpanded.server.world.biome;

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
        return overworldLike();
    }

    public static SurfaceRules.RuleSource overworldLike(
            ) {
        SurfaceRules.ConditionSource steep = SurfaceRules.steep();
        SurfaceRules.ConditionSource high =
                SurfaceRules.yBlockCheck(VerticalAnchor.absolute(120), 0);

        SurfaceRules.RuleSource surfaceRule =
                SurfaceRules.ifTrue(
                        SurfaceRules.ON_FLOOR,
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(steep, STONE),
                                SurfaceRules.ifTrue(high, STONE),
                                GRASS_BLOCK
                        )
                );
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.verticalGradient(
                                "bedrock_floor",
                                VerticalAnchor.bottom(),
                                VerticalAnchor.aboveBottom(5)
                        ),
                        BEDROCK
                ),
                SurfaceRules.sequence(
                        JSExpandedBiomes.sequence(),
                        surfaceRule
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.verticalGradient(
                                "deepslate",
                                VerticalAnchor.absolute(0),
                                VerticalAnchor.absolute(8)
                        ),
                        DEEPSLATE
                )
        );
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
