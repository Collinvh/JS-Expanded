package jp.jsexpanded.server.world.level;


import com.mojang.datafixers.util.Pair;
import jp.jsexpanded.server.world.biome.JSExpandedBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;

import java.util.*;
import java.util.function.Consumer;

public final class JSExpandedBiomeBuilder {
    // ---- Core climate axes
    private static final Climate.Parameter FULL_RANGE = Climate.Parameter.span(-1.0F, 1.0F);

    private static final Climate.Parameter[] TEMPS = new Climate.Parameter[]{
            Climate.Parameter.span(-1.0F, -0.45F),
            Climate.Parameter.span(-0.45F, -0.15F),
            Climate.Parameter.span(-0.15F, 0.2F),
            Climate.Parameter.span(0.2F, 0.55F),
            Climate.Parameter.span(0.55F, 1.0F)
    };

    private static final Climate.Parameter[] HUMS = new Climate.Parameter[]{
            Climate.Parameter.span(-1.0F, -0.35F),
            Climate.Parameter.span(-0.35F, -0.1F),
            Climate.Parameter.span(-0.1F, 0.1F),
            Climate.Parameter.span(0.1F, 0.3F),
            Climate.Parameter.span(0.3F, 1.0F)
    };

    private static final Climate.Parameter[] EROS = new Climate.Parameter[]{
            Climate.Parameter.span(-1.0F, -0.78F),
            Climate.Parameter.span(-0.78F, -0.375F),
            Climate.Parameter.span(-0.375F, -0.2225F),
            Climate.Parameter.span(-0.2225F, 0.05F),
            Climate.Parameter.span(0.05F, 0.45F),
            Climate.Parameter.span(0.45F, 0.55F),
            Climate.Parameter.span(0.55F, 1.0F)
    };

    // Cached spans
    private static final Climate.Parameter FROZEN_RANGE = TEMPS[0];
    private static final Climate.Parameter UNFROZEN_RANGE = Climate.Parameter.span(TEMPS[1], TEMPS[4]);

    private static final Climate.Parameter MUSHROOM_C = Climate.Parameter.span(-1.2F, -1.05F);
    private static final Climate.Parameter DEEP_OCEAN_C = Climate.Parameter.span(-1.05F, -0.455F);
    private static final Climate.Parameter OCEAN_C = Climate.Parameter.span(-0.455F, -0.19F);
    private static final Climate.Parameter COAST_C = Climate.Parameter.span(-0.19F, -0.11F);
    private static final Climate.Parameter INLAND_C = Climate.Parameter.span(-0.11F, 0.55F);
    private static final Climate.Parameter NEAR_INLAND_C = Climate.Parameter.span(-0.11F, 0.03F);
    private static final Climate.Parameter MID_INLAND_C = Climate.Parameter.span(0.03F, 0.3F);
    private static final Climate.Parameter FAR_INLAND_C = Climate.Parameter.span(0.3F, 1.0F);

    static final Climate.Parameter NEAR_INLAND  = Climate.Parameter.span(-0.19F, 0.03F);
    static final Climate.Parameter LOW_EROSION  = Climate.Parameter.span(0.6F, 1.0F);   // cliffs
    static final Climate.Parameter MID_EROSION  = Climate.Parameter.span(-0.2F, 0.6F);  // beaches
    static final Climate.Parameter HIGH_EROSION = Climate.Parameter.span(-1.0F, -0.2F); // flats

    // Tables
    private static final ResourceKey<Biome>[][] OCEANS = new ResourceKey[][]{
            { JSExpandedBiomes.SEA.getBiomeKey(), JSExpandedBiomes.SEA.getBiomeKey(), JSExpandedBiomes.FOGGY_SEA.getBiomeKey(), JSExpandedBiomes.SEA.getBiomeKey(), JSExpandedBiomes.SEA.getBiomeKey() },
            { JSExpandedBiomes.SEA.getBiomeKey(), JSExpandedBiomes.SEA.getBiomeKey(), JSExpandedBiomes.SEA.getBiomeKey(), JSExpandedBiomes.FOGGY_SEA.getBiomeKey(), JSExpandedBiomes.SEA.getBiomeKey() }
    };

    // Middle biomes and variants
    private static final ResourceKey<Biome>[][] MIDDLE = new ResourceKey[][]{
            // cold
            { JSExpandedBiomes.TROPICAL_HIGHLANDS.getBiomeKey(), JSExpandedBiomes.DEAD_FOREST.getBiomeKey(), JSExpandedBiomes.LOWLAND_PLAINS.getBiomeKey(), JSExpandedBiomes.DENSE_SWAMP.getBiomeKey(), JSExpandedBiomes.MOUNTAINOUS_SWAMP.getBiomeKey() },

            // cool
            { JSExpandedBiomes.LOWLAND_PLAINS.getBiomeKey(), JSExpandedBiomes.MARSHY_PLAINS.getBiomeKey(), JSExpandedBiomes.OPEN_BAYOU.getBiomeKey(), JSExpandedBiomes.DENSE_SWAMP.getBiomeKey(), JSExpandedBiomes.MOUNTAINOUS_SWAMP.getBiomeKey() },

            // temperate
            { JSExpandedBiomes.LOWLAND_PLAINS.getBiomeKey(), JSExpandedBiomes.DRY_FOREST.getBiomeKey(), JSExpandedBiomes.TROPICAL_SHRUBLAND.getBiomeKey(), JSExpandedBiomes.LOW_LAND_JUNGLE.getBiomeKey(), JSExpandedBiomes.PRIMORDIAL_JUNGLE.getBiomeKey() },

            // warm
            { JSExpandedBiomes.DEAD_FOREST.getBiomeKey(), JSExpandedBiomes.TROPICAL_SHRUBLAND.getBiomeKey(), JSExpandedBiomes.JUNGLE_CLEARING.getBiomeKey(), JSExpandedBiomes.LOW_LAND_JUNGLE.getBiomeKey(), JSExpandedBiomes.BAMBOO_FOREST.getBiomeKey() },

            // hot
            { JSExpandedBiomes.OIL_FIELDS.getBiomeKey(), JSExpandedBiomes.OIL_FIELDS.getBiomeKey(), JSExpandedBiomes.OIL_FIELDS.getBiomeKey(), JSExpandedBiomes.DRY_FOREST.getBiomeKey(), JSExpandedBiomes.PRIMORDIAL_JUNGLE.getBiomeKey() }
    };

    private static final ResourceKey<Biome>[][] MIDDLE_VAR = new ResourceKey[][]{
            { null, null, null, null, JSExpandedBiomes.SKULL_MOUNTAINS.getBiomeKey() },
            { null, JSExpandedBiomes.MOUNTAINOUS_PLAINS.getBiomeKey(), null, null, null },
            { JSExpandedBiomes.JUNGLE_CLEARING.getBiomeKey(), null, null, null, JSExpandedBiomes.PITFALL_JUNGLE.getBiomeKey() },
            { null, null, JSExpandedBiomes.MEGAFALLS.getBiomeKey(), JSExpandedBiomes.WATERFALL_CLIFFS.getBiomeKey(), null },
            { null, null, null, null, null }
    };

    private static final ResourceKey<Biome>[][] PLATEAU = new ResourceKey[][]{
            { JSExpandedBiomes.TROPICAL_HIGHLANDS.getBiomeKey(), JSExpandedBiomes.TROPICAL_HIGHLANDS.getBiomeKey(), JSExpandedBiomes.MOUNTAINOUS_PLAINS.getBiomeKey(), JSExpandedBiomes.MOUNTAINOUS_SWAMP.getBiomeKey(), JSExpandedBiomes.SKULL_MOUNTAINS.getBiomeKey() },
            { JSExpandedBiomes.MOUNTAINOUS_PLAINS.getBiomeKey(), JSExpandedBiomes.MOUNTAINOUS_PLAINS.getBiomeKey(), JSExpandedBiomes.TROPICAL_HIGHLANDS.getBiomeKey(), JSExpandedBiomes.TROPICAL_HIGHLANDS.getBiomeKey(), JSExpandedBiomes.WATERFALL_CLIFFS.getBiomeKey() },
            { JSExpandedBiomes.TROPICAL_HIGHLANDS.getBiomeKey(), JSExpandedBiomes.TROPICAL_HIGHLANDS.getBiomeKey(), JSExpandedBiomes.MEGAFALLS.getBiomeKey(), JSExpandedBiomes.MEGAFALLS.getBiomeKey(), JSExpandedBiomes.WATERFALL_CLIFFS.getBiomeKey() },
            { JSExpandedBiomes.CRACKING_CLIFFS.getBiomeKey(), JSExpandedBiomes.CRACKING_CLIFFS.getBiomeKey(), JSExpandedBiomes.MEGAFALLS.getBiomeKey(), JSExpandedBiomes.WATERFALL_CLIFFS.getBiomeKey(), JSExpandedBiomes.SKULL_MOUNTAINS.getBiomeKey() },
            { JSExpandedBiomes.BARREN_VOLCANO.getBiomeKey(), JSExpandedBiomes.BARREN_VOLCANO.getBiomeKey(), JSExpandedBiomes.CRACKING_CLIFFS.getBiomeKey(), JSExpandedBiomes.CRACKING_CLIFFS.getBiomeKey(), JSExpandedBiomes.SKULL_MOUNTAINS.getBiomeKey() }
    };

    private static final ResourceKey<Biome>[][] PLATEAU_VAR = new ResourceKey[][]{
            { null, null, null, null, null },
            { null, null, JSExpandedBiomes.WATERFALL_CLIFFS.getBiomeKey(), null, null },
            { null, null, null, null, null },
            { null, null, null, null, null },
            { JSExpandedBiomes.BARREN_VOLCANO.getBiomeKey(), null, null, null, null }
    };

    private static final ResourceKey<Biome>[][] SHATTERED = new ResourceKey[][]{
            { JSExpandedBiomes.CRACKING_CLIFFS.getBiomeKey(), JSExpandedBiomes.CRACKING_CLIFFS.getBiomeKey(), JSExpandedBiomes.TROPICAL_HIGHLANDS.getBiomeKey(), JSExpandedBiomes.WATERFALL_CLIFFS.getBiomeKey(), JSExpandedBiomes.WATERFALL_CLIFFS.getBiomeKey() },
            { JSExpandedBiomes.CRACKING_CLIFFS.getBiomeKey(), JSExpandedBiomes.CRACKING_CLIFFS.getBiomeKey(), JSExpandedBiomes.TROPICAL_HIGHLANDS.getBiomeKey(), JSExpandedBiomes.MEGAFALLS.getBiomeKey(), JSExpandedBiomes.MEGAFALLS.getBiomeKey() },
            { JSExpandedBiomes.TROPICAL_HIGHLANDS.getBiomeKey(), JSExpandedBiomes.TROPICAL_HIGHLANDS.getBiomeKey(), JSExpandedBiomes.MEGAFALLS.getBiomeKey(), JSExpandedBiomes.WATERFALL_CLIFFS.getBiomeKey(), JSExpandedBiomes.WATERFALL_CLIFFS.getBiomeKey() },
            { null, null, null, null, null },
            { null, null, null, null, null }
    };

    // Rarity system: variants per base biome. Defaults preserve results.
    private final Map<ResourceKey<Biome>, JSExpandedBiomeBuilder.WeightBag<ResourceKey<Biome>>> rarityPools = new HashMap<>();
    private long raritySalt = 0L; // 0 keeps exact vanilla+current behavior

    public JSExpandedBiomeBuilder() {
        // no-op
    }

    /**
     * Stable seed for deterministic selection within a climate cell.
     */
    public JSExpandedBiomeBuilder setRaritySeed(long seed) {
        this.raritySalt = seed;
        return this;
    }

    /**
     * Add a weighted custom variant for a base biome. Default weight for base is 1.
     * Vanilla rarities remain equal.
     */
    public JSExpandedBiomeBuilder addVariant(ResourceKey<Biome> base, ResourceKey<Biome> variant, int weight) {
        if (weight <= 0) return this;
        rarityPools.computeIfAbsent(base, k -> new JSExpandedBiomeBuilder.WeightBag<>()).add(base, 1).add(variant, weight);
        return this;
    }

    // ---- API
    public List<Climate.ParameterPoint> spawnTarget() {
        Climate.Parameter y = Climate.Parameter.point(0.0F);
        float weirdCut = 0.16F;
        return List.of(
                new Climate.ParameterPoint(FULL_RANGE, FULL_RANGE, Climate.Parameter.span(INLAND_C, FULL_RANGE), FULL_RANGE, y, Climate.Parameter.span(-1.0F, -weirdCut), 0L),
                new Climate.ParameterPoint(FULL_RANGE, FULL_RANGE, Climate.Parameter.span(INLAND_C, FULL_RANGE), FULL_RANGE, y, Climate.Parameter.span(weirdCut, 1.0F), 0L)
        );
    }

    public void addBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> out) {
        addOffCoastBiomes(out);
        addInlandBiomes(out);
        addUndergroundBiomes(out);
        addCoastBiomes(out);
        addRiverBiomes(out);
    }

    private void addCoastBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> out) {

        for (int ti = 0; ti < TEMPS.length; ti++) {
            Climate.Parameter t = TEMPS[ti];

            // Shallows (between ocean and land)
            emitSurface(out, t, FULL_RANGE, COAST_C, MID_EROSION, FULL_RANGE, 0.0F,
                    JSExpandedBiomes.SANDY_SHALLOWS.getBiomeKey());

            emitSurface(out, t, FULL_RANGE, COAST_C, MID_EROSION, FULL_RANGE, 0.05F,
                    JSExpandedBiomes.DARK_SANDY_SHALLOWS.getBiomeKey());

            // Flat coasts
            emitSurface(out, t, FULL_RANGE, COAST_C, HIGH_EROSION, FULL_RANGE, 0.0F,
                    JSExpandedBiomes.FLAT_SANDY_COAST.getBiomeKey());

            // Cliffs
            emitSurface(out, t, FULL_RANGE, COAST_C, LOW_EROSION, FULL_RANGE, 0.0F,
                    JSExpandedBiomes.GRASSY_SEA_CLIFFS.getBiomeKey());

            // Warm / cold coast variants
            if (ti >= 3) { // warm
                emitSurface(out, t, FULL_RANGE, COAST_C, MID_EROSION, FULL_RANGE, 0.0F,
                        JSExpandedBiomes.LUSH_COAST.getBiomeKey());
            } else { // cold
                emitSurface(out, t, FULL_RANGE, COAST_C, MID_EROSION, FULL_RANGE, 0.0F,
                        JSExpandedBiomes.BARREN_COAST.getBiomeKey());
            }
        }
    }

    private void addRiverBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> out) {

        emitSurface(out,
                FULL_RANGE,
                FULL_RANGE,
                NEAR_INLAND_C, // inland
                FULL_RANGE,
                FULL_RANGE,
                0.0F, // weirdness = river channels
                JSExpandedBiomes.MIGHTY_RIVER.getBiomeKey()
        );
    }

    // ---- Off coast
    private void addOffCoastBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> out) {

        // Rare ocean features
        emitSurface(out, FULL_RANGE, FULL_RANGE, MUSHROOM_C, FULL_RANGE, FULL_RANGE, 0.0F,
                JSExpandedBiomes.SUNKEN_CITY.getBiomeKey());

        for (int ti = 0; ti < TEMPS.length; ti++) {
            Climate.Parameter temp = TEMPS[ti];

            // Deep ocean
            emitSurface(out, temp, FULL_RANGE, DEEP_OCEAN_C, FULL_RANGE, FULL_RANGE, 0.0F,
                    OCEANS[0][ti]);

            // Normal ocean
            emitSurface(out, temp, FULL_RANGE, OCEAN_C, FULL_RANGE, FULL_RANGE, 0.0F,
                    OCEANS[1][ti]);

            // Rare ocean variant (foggy)
            emitSurface(out, temp, FULL_RANGE, OCEAN_C, FULL_RANGE, FULL_RANGE, 0.05F,
                    JSExpandedBiomes.FOGGY_SEA.getBiomeKey());
        }
    }

    // ---- Inland
    private void addInlandBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> out) {
        addMidSlice(out, Climate.Parameter.span(-1.0F, -0.93333334F));
        addHighSlice(out, Climate.Parameter.span(-0.93333334F, -0.7666667F));
        addPeaks(out, Climate.Parameter.span(-0.7666667F, -0.56666666F));
        addHighSlice(out, Climate.Parameter.span(-0.56666666F, -0.4F));
        addMidSlice(out, Climate.Parameter.span(-0.4F, -0.26666668F));
        addLowSlice(out, Climate.Parameter.span(-0.26666668F, -0.05F));
        addValleys(out, Climate.Parameter.span(-0.05F, 0.05F));
        addLowSlice(out, Climate.Parameter.span(0.05F, 0.26666668F));
        addMidSlice(out, Climate.Parameter.span(0.26666668F, 0.4F));
        addHighSlice(out, Climate.Parameter.span(0.4F, 0.56666666F));
        addPeaks(out, Climate.Parameter.span(0.56666666F, 0.7666667F));
        addHighSlice(out, Climate.Parameter.span(0.7666667F, 0.93333334F));
        addMidSlice(out, Climate.Parameter.span(0.93333334F, 1.0F));
    }

    private void addPeaks(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> out, Climate.Parameter d) {
        grid((ti, hi) -> {
            Climate.Parameter T = TEMPS[ti];
            Climate.Parameter H = HUMS[hi];
            ResourceKey<Biome> mid = pickMiddle(ti, hi, d);
            ResourceKey<Biome> midHot = pickMiddleOrBadlandsIfHot(ti, hi, d);
            ResourceKey<Biome> midHotOrSlopeCold = pickMiddleOrBadlandsIfHotOrSlopeIfCold(ti, hi, d);
            ResourceKey<Biome> plat = pickPlateau(ti, hi, d);
            ResourceKey<Biome> shat = pickShattered(ti, hi, d);
            ResourceKey<Biome> maybeSav = maybeWindsweptSavanna(ti, hi, d, shat);
            ResourceKey<Biome> peak = pickPeak(ti, hi, d);

            emitSurface(out, T, H, Climate.Parameter.span(COAST_C, FAR_INLAND_C), EROS[0], d, 0.0F, peak);
            emitSurface(out, T, H, Climate.Parameter.span(COAST_C, NEAR_INLAND_C), EROS[1], d, 0.0F, midHotOrSlopeCold);
            emitSurface(out, T, H, Climate.Parameter.span(MID_INLAND_C, FAR_INLAND_C), EROS[1], d, 0.0F, peak);
            emitSurface(out, T, H, Climate.Parameter.span(COAST_C, NEAR_INLAND_C), Climate.Parameter.span(EROS[2], EROS[3]), d, 0.0F, mid);
            emitSurface(out, T, H, Climate.Parameter.span(MID_INLAND_C, FAR_INLAND_C), EROS[2], d, 0.0F, plat);
            emitSurface(out, T, H, MID_INLAND_C, EROS[3], d, 0.0F, midHot);
            emitSurface(out, T, H, FAR_INLAND_C, EROS[3], d, 0.0F, plat);
            emitSurface(out, T, H, Climate.Parameter.span(COAST_C, FAR_INLAND_C), EROS[4], d, 0.0F, mid);
            emitSurface(out, T, H, Climate.Parameter.span(COAST_C, NEAR_INLAND_C), EROS[5], d, 0.0F, maybeSav);
            emitSurface(out, T, H, Climate.Parameter.span(MID_INLAND_C, FAR_INLAND_C), EROS[5], d, 0.0F, shat);
            emitSurface(out, T, H, Climate.Parameter.span(COAST_C, FAR_INLAND_C), EROS[6], d, 0.0F, mid);
        });
    }

    private void addHighSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> out, Climate.Parameter d) {
        grid((ti, hi) -> {
            Climate.Parameter T = TEMPS[ti];
            Climate.Parameter H = HUMS[hi];
            ResourceKey<Biome> mid = pickMiddle(ti, hi, d);
            ResourceKey<Biome> midHot = pickMiddleOrBadlandsIfHot(ti, hi, d);
            ResourceKey<Biome> midHotOrSlopeCold = pickMiddleOrBadlandsIfHotOrSlopeIfCold(ti, hi, d);
            ResourceKey<Biome> plat = pickPlateau(ti, hi, d);
            ResourceKey<Biome> shat = pickShattered(ti, hi, d);
            ResourceKey<Biome> maybeSav = maybeWindsweptSavanna(ti, hi, d, mid);
            ResourceKey<Biome> slope = pickSlope(ti, hi, d);
            ResourceKey<Biome> peak = pickPeak(ti, hi, d);

            emitSurface(out, T, H, COAST_C, Climate.Parameter.span(EROS[0], EROS[1]), d, 0.0F, mid);
            emitSurface(out, T, H, NEAR_INLAND_C, EROS[0], d, 0.0F, slope);
            emitSurface(out, T, H, Climate.Parameter.span(MID_INLAND_C, FAR_INLAND_C), EROS[0], d, 0.0F, peak);
            emitSurface(out, T, H, NEAR_INLAND_C, EROS[1], d, 0.0F, midHotOrSlopeCold);
            emitSurface(out, T, H, Climate.Parameter.span(MID_INLAND_C, FAR_INLAND_C), EROS[1], d, 0.0F, slope);
            emitSurface(out, T, H, Climate.Parameter.span(COAST_C, NEAR_INLAND_C), Climate.Parameter.span(EROS[2], EROS[3]), d, 0.0F, mid);
            emitSurface(out, T, H, Climate.Parameter.span(MID_INLAND_C, FAR_INLAND_C), EROS[2], d, 0.0F, plat);
            emitSurface(out, T, H, MID_INLAND_C, EROS[3], d, 0.0F, midHot);
            emitSurface(out, T, H, FAR_INLAND_C, EROS[3], d, 0.0F, plat);
            emitSurface(out, T, H, Climate.Parameter.span(COAST_C, FAR_INLAND_C), EROS[4], d, 0.0F, mid);
            emitSurface(out, T, H, Climate.Parameter.span(COAST_C, NEAR_INLAND_C), EROS[5], d, 0.0F, maybeSav);
            emitSurface(out, T, H, Climate.Parameter.span(MID_INLAND_C, FAR_INLAND_C), EROS[5], d, 0.0F, shat);
            emitSurface(out, T, H, Climate.Parameter.span(COAST_C, FAR_INLAND_C), EROS[6], d, 0.0F, mid);
        });
    }

    private void addMidSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> out, Climate.Parameter d) {

        // coast rock -> use cliffs
        emitSurface(out, FULL_RANGE, FULL_RANGE, COAST_C, Climate.Parameter.span(EROS[0], EROS[2]), d, 0.0F,
                JSExpandedBiomes.GRASSY_SEA_CLIFFS.getBiomeKey());

        // wetlands
        emitSurface(out, Climate.Parameter.span(TEMPS[1], TEMPS[2]), FULL_RANGE,
                Climate.Parameter.span(NEAR_INLAND_C, FAR_INLAND_C), EROS[6], d, 0.0F,
                JSExpandedBiomes.OPEN_BAYOU.getBiomeKey());

        emitSurface(out, Climate.Parameter.span(TEMPS[3], TEMPS[4]), FULL_RANGE,
                Climate.Parameter.span(NEAR_INLAND_C, FAR_INLAND_C), EROS[6], d, 0.0F,
                JSExpandedBiomes.DENSE_SWAMP.getBiomeKey());

        grid((ti, hi) -> {
            Climate.Parameter T = TEMPS[ti];
            Climate.Parameter H = HUMS[hi];

            ResourceKey<Biome> mid = pickMiddle(ti, hi, d);
            ResourceKey<Biome> midHot = pickMiddleOrBadlandsIfHot(ti, hi, d);
            ResourceKey<Biome> midHotOrSlopeCold = pickMiddleOrBadlandsIfHotOrSlopeIfCold(ti, hi, d);
            ResourceKey<Biome> shat = pickShattered(ti, hi, d);
            ResourceKey<Biome> plat = pickPlateau(ti, hi, d);
            ResourceKey<Biome> beach = pickBeach(ti, hi);
            ResourceKey<Biome> maybeSav = maybeWindsweptSavanna(ti, hi, d, mid);
            ResourceKey<Biome> shCoast = pickShatteredCoast(ti, hi, d);
            ResourceKey<Biome> slope = pickSlope(ti, hi, d);

            emitSurface(out, T, H, Climate.Parameter.span(NEAR_INLAND_C, FAR_INLAND_C), EROS[0], d, 0.0F, slope);
            emitSurface(out, T, H, Climate.Parameter.span(NEAR_INLAND_C, MID_INLAND_C), EROS[1], d, 0.0F, midHotOrSlopeCold);
            emitSurface(out, T, H, FAR_INLAND_C, EROS[1], d, 0.0F, plat);
            emitSurface(out, T, H, NEAR_INLAND_C, EROS[2], d, 0.0F, mid);
            emitSurface(out, T, H, MID_INLAND_C, EROS[2], d, 0.0F, midHot);
            emitSurface(out, T, H, FAR_INLAND_C, EROS[2], d, 0.0F, plat);
            emitSurface(out, T, H, Climate.Parameter.span(COAST_C, NEAR_INLAND_C), EROS[3], d, 0.0F, mid);
            emitSurface(out, T, H, Climate.Parameter.span(MID_INLAND_C, FAR_INLAND_C), EROS[3], d, 0.0F, midHot);

            if (d.max() < 0L) {
                emitSurface(out, T, H, COAST_C, EROS[4], d, 0.0F, beach);
                emitSurface(out, T, H, Climate.Parameter.span(NEAR_INLAND_C, FAR_INLAND_C), EROS[4], d, 0.0F, mid);
            } else {
                emitSurface(out, T, H, Climate.Parameter.span(COAST_C, FAR_INLAND_C), EROS[4], d, 0.0F, mid);
            }

            emitSurface(out, T, H, COAST_C, EROS[5], d, 0.0F, shCoast);
            emitSurface(out, T, H, NEAR_INLAND_C, EROS[5], d, 0.0F, maybeSav);
            emitSurface(out, T, H, Climate.Parameter.span(MID_INLAND_C, FAR_INLAND_C), EROS[5], d, 0.0F, shat);

            if (d.max() < 0L) {
                emitSurface(out, T, H, COAST_C, EROS[6], d, 0.0F, beach);
            } else {
                emitSurface(out, T, H, COAST_C, EROS[6], d, 0.0F, mid);
            }

            if (ti == 0) {
                emitSurface(out, T, H, Climate.Parameter.span(NEAR_INLAND_C, FAR_INLAND_C), EROS[6], d, 0.0F, mid);
            }
        });
    }

    private void addLowSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> out, Climate.Parameter d) {

        emitSurface(out, FULL_RANGE, FULL_RANGE, COAST_C, Climate.Parameter.span(EROS[0], EROS[2]), d, 0.0F,
                JSExpandedBiomes.GRASSY_SEA_CLIFFS.getBiomeKey());

        emitSurface(out, Climate.Parameter.span(TEMPS[1], TEMPS[2]), FULL_RANGE,
                Climate.Parameter.span(NEAR_INLAND_C, FAR_INLAND_C), EROS[6], d, 0.0F,
                JSExpandedBiomes.OPEN_BAYOU.getBiomeKey());

        emitSurface(out, Climate.Parameter.span(TEMPS[3], TEMPS[4]), FULL_RANGE,
                Climate.Parameter.span(NEAR_INLAND_C, FAR_INLAND_C), EROS[6], d, 0.0F,
                JSExpandedBiomes.DENSE_SWAMP.getBiomeKey());

        grid((ti, hi) -> {
            Climate.Parameter T = TEMPS[ti];
            Climate.Parameter H = HUMS[hi];

            ResourceKey<Biome> mid = pickMiddle(ti, hi, d);
            ResourceKey<Biome> midHot = pickMiddleOrBadlandsIfHot(ti, hi, d);
            ResourceKey<Biome> midHotOrSlopeCold = pickMiddleOrBadlandsIfHotOrSlopeIfCold(ti, hi, d);
            ResourceKey<Biome> beach = pickBeach(ti, hi);
            ResourceKey<Biome> maybeSav = maybeWindsweptSavanna(ti, hi, d, mid);
            ResourceKey<Biome> shCoast = pickShatteredCoast(ti, hi, d);

            emitSurface(out, T, H, NEAR_INLAND_C, Climate.Parameter.span(EROS[0], EROS[1]), d, 0.0F, midHot);
            emitSurface(out, T, H, Climate.Parameter.span(MID_INLAND_C, FAR_INLAND_C),
                    Climate.Parameter.span(EROS[0], EROS[1]), d, 0.0F, midHotOrSlopeCold);

            emitSurface(out, T, H, NEAR_INLAND_C, Climate.Parameter.span(EROS[2], EROS[3]), d, 0.0F, mid);
            emitSurface(out, T, H, Climate.Parameter.span(MID_INLAND_C, FAR_INLAND_C),
                    Climate.Parameter.span(EROS[2], EROS[3]), d, 0.0F, midHot);

            emitSurface(out, T, H, COAST_C, Climate.Parameter.span(EROS[3], EROS[4]), d, 0.0F, beach);
            emitSurface(out, T, H, Climate.Parameter.span(NEAR_INLAND_C, FAR_INLAND_C), EROS[4], d, 0.0F, mid);

            emitSurface(out, T, H, COAST_C, EROS[5], d, 0.0F, shCoast);
            emitSurface(out, T, H, NEAR_INLAND_C, EROS[5], d, 0.0F, maybeSav);
            emitSurface(out, T, H, Climate.Parameter.span(MID_INLAND_C, FAR_INLAND_C), EROS[5], d, 0.0F, mid);

            emitSurface(out, T, H, COAST_C, EROS[6], d, 0.0F, beach);

            if (ti == 0) {
                emitSurface(out, T, H, Climate.Parameter.span(NEAR_INLAND_C, FAR_INLAND_C), EROS[6], d, 0.0F, mid);
            }
        });
    }

    private void addValleys(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> out, Climate.Parameter d) {

        emitSurface(out, FULL_RANGE, FULL_RANGE, Climate.Parameter.span(COAST_C, FAR_INLAND_C),
                Climate.Parameter.span(EROS[0], EROS[6]), d, 0.0F,
                JSExpandedBiomes.MIGHTY_RIVER.getBiomeKey());

        emitSurface(out, Climate.Parameter.span(TEMPS[1], TEMPS[2]), FULL_RANGE,
                Climate.Parameter.span(INLAND_C, FAR_INLAND_C), EROS[6], d, 0.0F,
                JSExpandedBiomes.OPEN_BAYOU.getBiomeKey());

        emitSurface(out, Climate.Parameter.span(TEMPS[3], TEMPS[4]), FULL_RANGE,
                Climate.Parameter.span(INLAND_C, FAR_INLAND_C), EROS[6], d, 0.0F,
                JSExpandedBiomes.DENSE_SWAMP.getBiomeKey());

        grid((ti, hi) -> {
            Climate.Parameter T = TEMPS[ti];
            Climate.Parameter H = HUMS[hi];
            ResourceKey<Biome> mid = pickMiddleOrBadlandsIfHot(ti, hi, d);

            emitSurface(out, T, H, Climate.Parameter.span(MID_INLAND_C, FAR_INLAND_C),
                    Climate.Parameter.span(EROS[0], EROS[1]), d, 0.0F, mid);
        });
    }

    // ---- Underground
    private void addUndergroundBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> out) {
    }

    // ---- Pickers (now pass through rarity)
    private ResourceKey<Biome> pickMiddle(int t, int h, Climate.Parameter d) {
        ResourceKey<Biome> base = (d.max() < 0L) ? MIDDLE[t][h] : Optional.ofNullable(MIDDLE_VAR[t][h]).orElse(MIDDLE[t][h]);
        return withRarity(base, t, h, d);
    }

    private ResourceKey<Biome> pickMiddleOrBadlandsIfHot(int t, int h, Climate.Parameter d) {
        ResourceKey<Biome> base = (t == 4) ? pickBadlands(h, d) : pickMiddle(t, h, d);
        return withRarity(base, t, h, d);
    }

    private ResourceKey<Biome> pickMiddleOrBadlandsIfHotOrSlopeIfCold(int t, int h, Climate.Parameter d) {
        ResourceKey<Biome> base = (t == 0) ? pickSlope(t, h, d) : pickMiddleOrBadlandsIfHot(t, h, d);
        return withRarity(base, t, h, d);
    }

    private ResourceKey<Biome> maybeWindsweptSavanna(int t, int h, Climate.Parameter d, ResourceKey<Biome> fallback) {
//        ResourceKey<Biome> base = (t > 1 && h < 4 && d.max() >= 0L) ? Biomes.WINDSWEPT_SAVANNA : fallback;
//        return withRarity(base, t, h, d);
        return fallback;
    }

    private ResourceKey<Biome> pickShatteredCoast(int t, int h, Climate.Parameter d) {
        ResourceKey<Biome> base = (d.max() >= 0L) ? pickMiddle(t, h, d) : pickBeach(t, h);
        base = maybeWindsweptSavanna(t, h, d, base);
        return withRarity(base, t, h, d);
    }

    private ResourceKey<Biome> pickBeach(int t, int h) {
        if (t == 0) return JSExpandedBiomes.BARREN_COAST.getBiomeKey();
        return t == 4 ? JSExpandedBiomes.LUSH_COAST.getBiomeKey() : JSExpandedBiomes.FLAT_SANDY_COAST.getBiomeKey();
    }

    private ResourceKey<Biome> pickBadlands(int h, Climate.Parameter d) {
        return JSExpandedBiomes.BARREN_VOLCANO.getBiomeKey(); // placeholder
    }

    private ResourceKey<Biome> pickPlateau(int t, int h, Climate.Parameter d) {
        ResourceKey<Biome> base;
        if (d.max() >= 0L && PLATEAU_VAR[t][h] != null) base = PLATEAU_VAR[t][h];
        else base = PLATEAU[t][h];
        return withRarity(base, t, h, d);
    }

    private ResourceKey<Biome> pickPeak(int t, int h, Climate.Parameter d) {
//        ResourceKey<Biome> base;
//        if (t <= 2) base = d.max() < 0L ? Biomes.JAGGED_PEAKS : Biomes.FROZEN_PEAKS;
//        else base = (t == 3) ? Biomes.STONY_PEAKS : pickBadlands(h, d);
//        return withRarity(base, t, h, d);
        return JSExpandedBiomes.SKULL_MOUNTAINS.getBiomeKey();
    }

    private ResourceKey<Biome> pickSlope(int t, int h, Climate.Parameter d) {
//        ResourceKey<Biome> base;
//        if (t >= 3) base = pickPlateau(t, h, d);
//        else base = (h <= 1) ? Biomes.SNOWY_SLOPES : Biomes.GROVE;
//        return withRarity(base, t, h, d);
        return JSExpandedBiomes.TROPICAL_HIGHLANDS.getBiomeKey();
    }

    private ResourceKey<Biome> pickShattered(int t, int h, Climate.Parameter d) {
        ResourceKey<Biome> base = Optional.ofNullable(SHATTERED[t][h]).orElse(pickMiddle(t, h, d));
        return withRarity(base, t, h, d);
    }

    // ---- Emit helpers
    private void emitSurface(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> out,
                             Climate.Parameter T, Climate.Parameter H, Climate.Parameter C,
                             Climate.Parameter E, Climate.Parameter D, float W, ResourceKey<Biome> key) {
        ResourceKey<Biome> k = key;
        Climate.ParameterPoint p1 = Climate.parameters(T, H, C, E, Climate.Parameter.point(0.0F), D, W);
        Climate.ParameterPoint p2 = Climate.parameters(T, H, C, E, Climate.Parameter.point(1.0F), D, W);
        out.accept(Pair.of(p1, k));
        out.accept(Pair.of(p2, k));
    }

    private void emitUnderground(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> out,
                                 Climate.Parameter T, Climate.Parameter H, Climate.Parameter C,
                                 Climate.Parameter E, Climate.Parameter D, float W, ResourceKey<Biome> key) {
        Climate.ParameterPoint p = Climate.parameters(T, H, C, E, Climate.Parameter.span(0.2F, 0.9F), D, W);
        out.accept(Pair.of(p, key));
    }

    // ---- Grid iterator
    private interface Cell {
        void accept(int tempIdx, int humIdx);
    }

    private void grid(JSExpandedBiomeBuilder.Cell cell) {
        for (int ti = 0; ti < TEMPS.length; ti++) {
            for (int hi = 0; hi < HUMS.length; hi++) {
                cell.accept(ti, hi);
            }
        }
    }

    // ---- Rarity selection (deterministic). Default preserves results.
    private ResourceKey<Biome> withRarity(ResourceKey<Biome> base, int t, int h, Climate.Parameter d) {
        JSExpandedBiomeBuilder.WeightBag<ResourceKey<Biome>> bag = rarityPools.get(base);
        if (bag == null) return base; // identical result
        long hash = mix(raritySalt, t, h, d.min(), d.max());
        return bag.pick(hash);
    }

    private static long mix(long salt, int t, int h, long dMin, long dMax) {
        long x = salt ^ 0x9E3779B97F4A7C15L;
        x ^= (long) t * 0xBF58476D1CE4E5B9L;
        x ^= (long) h * 0x94D049BB133111EBL;
        x ^= (dMin & 0xFFFF) * 0x2545F4914F6CDD1DL;
        x ^= (dMax & 0xFFFF) * 0x1234567890ABCDEFL;
        return x == 0 ? 1 : x;
    }

    // ---- Simple weighted bag with deterministic pick
    private static final class WeightBag<T> {
        private final List<T> items = new ArrayList<>();
        private final List<Integer> cum = new ArrayList<>();
        private int total = 0;

        JSExpandedBiomeBuilder.WeightBag<T> add(T item, int w) {
            if (w <= 0) return this;
            total += w;
            items.add(item);
            cum.add(total);
            return this;
        }

        T pick(long seed) {
            if (items.isEmpty()) throw new IllegalStateException("Empty weight bag");
            if (items.size() == 1) return items.get(0);
            long r = nextPositive(seed) % total;
            int idx = Collections.binarySearch(cum, (int) r + 1);
            if (idx < 0) idx = -idx - 1;
            return items.get(idx);
        }

        private static long nextPositive(long x) {
            // xorshift64*
            x ^= (x << 13);
            x ^= (x >>> 7);
            x ^= (x << 17);
            return x & Long.MAX_VALUE;
        }
    }
}
