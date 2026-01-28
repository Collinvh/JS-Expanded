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
            Climate.Parameter.span(-1.0F, -0.35F),  // rare cool
            Climate.Parameter.span(-0.35F, -0.05F), // mild
            Climate.Parameter.span(-0.05F, 0.35F),  // warm
            Climate.Parameter.span(0.35F, 0.7F),    // hot
            Climate.Parameter.span(0.7F, 1.0F)      // very hot
    };

    private static final Climate.Parameter[] HUMS = new Climate.Parameter[]{
            Climate.Parameter.span(-1.0F, -0.45F),  // arid
            Climate.Parameter.span(-0.45F, -0.15F), // dry
            Climate.Parameter.span(-0.15F, 0.15F),  // neutral
            Climate.Parameter.span(0.15F, 0.5F),    // humid
            Climate.Parameter.span(0.5F, 1.0F)      // very humid
    };

    private static final Climate.Parameter[] EROS = new Climate.Parameter[]{
            Climate.Parameter.span(0.75F, 1.0F),    // shattered cliffs / ridges
            Climate.Parameter.span(0.5F, 0.75F),    // steep slopes
            Climate.Parameter.span(0.2F, 0.5F),     // hills
            Climate.Parameter.span(-0.2F, 0.2F),    // rolling
            Climate.Parameter.span(-0.45F, -0.2F),  // plains
            Climate.Parameter.span(-0.75F, -0.45F), // wetlands
            Climate.Parameter.span(-1.0F, -0.75F)   // river flats
    };

    private static final Climate.Parameter MUSHROOM_C   = Climate.Parameter.span(-1.2F, -1.05F);
    private static final Climate.Parameter DEEP_OCEAN_C = Climate.Parameter.span(-1.05F, -0.455F);
    private static final Climate.Parameter OCEAN_C      = Climate.Parameter.span(-0.455F, -0.19F);
    private static final Climate.Parameter COAST_C      = Climate.Parameter.span(-0.19F, -0.11F);

    private static final Climate.Parameter INLAND_C      = Climate.Parameter.span(-0.19F, 1.0F);
    private static final Climate.Parameter NEAR_INLAND_C = Climate.Parameter.span(-0.19F, 0.1F);
    private static final Climate.Parameter MID_INLAND_C  = Climate.Parameter.span(0.1F, 0.45F);
    private static final Climate.Parameter FAR_INLAND_C  = Climate.Parameter.span(0.45F, 1.0F);

    static final Climate.Parameter HIGH_EROSION = Climate.Parameter.span(0.5F, 1.0F);   // rugged
    static final Climate.Parameter MID_EROSION  = Climate.Parameter.span(-0.2F, 0.5F);  // normal
    static final Climate.Parameter LOW_EROSION  = Climate.Parameter.span(-1.0F, -0.2F); // flat

    // Tables
    private static final ResourceKey<Biome>[][] OCEANS = new ResourceKey[][]{
            { JSExpandedBiomes.SEA.getBiomeKey(), JSExpandedBiomes.SEA.getBiomeKey(), JSExpandedBiomes.FOGGY_SEA.getBiomeKey(), JSExpandedBiomes.SEA.getBiomeKey(), JSExpandedBiomes.SEA.getBiomeKey() },
            { JSExpandedBiomes.SEA.getBiomeKey(), JSExpandedBiomes.SEA.getBiomeKey(), JSExpandedBiomes.SEA.getBiomeKey(), JSExpandedBiomes.FOGGY_SEA.getBiomeKey(), JSExpandedBiomes.SEA.getBiomeKey() }
    };

    private static final ResourceKey<Biome>[][] PLATEAU = new ResourceKey[][]{
            { JSExpandedBiomes.TROPICAL_HIGHLANDS.getBiomeKey(), JSExpandedBiomes.TROPICAL_HIGHLANDS.getBiomeKey(), JSExpandedBiomes.MOUNTAINOUS_PLAINS.getBiomeKey(), JSExpandedBiomes.MOUNTAINOUS_SWAMP.getBiomeKey(), JSExpandedBiomes.SKULL_MOUNTAINS.getBiomeKey() },
            { JSExpandedBiomes.MOUNTAINOUS_PLAINS.getBiomeKey(), JSExpandedBiomes.MOUNTAINOUS_PLAINS.getBiomeKey(), JSExpandedBiomes.TROPICAL_HIGHLANDS.getBiomeKey(), JSExpandedBiomes.TROPICAL_HIGHLANDS.getBiomeKey(), JSExpandedBiomes.SKULL_MOUNTAINS.getBiomeKey() },
            { JSExpandedBiomes.TROPICAL_HIGHLANDS.getBiomeKey(), JSExpandedBiomes.TROPICAL_HIGHLANDS.getBiomeKey(), JSExpandedBiomes.SKULL_MOUNTAINS.getBiomeKey(), JSExpandedBiomes.SKULL_MOUNTAINS.getBiomeKey(), JSExpandedBiomes.SKULL_MOUNTAINS.getBiomeKey() },
            { JSExpandedBiomes.CRACKING_CLIFFS.getBiomeKey(), JSExpandedBiomes.CRACKING_CLIFFS.getBiomeKey(), JSExpandedBiomes.CRACKING_CLIFFS.getBiomeKey(), JSExpandedBiomes.CRACKING_CLIFFS.getBiomeKey(), JSExpandedBiomes.SKULL_MOUNTAINS.getBiomeKey() },
            { JSExpandedBiomes.BARREN_VOLCANO.getBiomeKey(), JSExpandedBiomes.BARREN_VOLCANO.getBiomeKey(), JSExpandedBiomes.CRACKING_CLIFFS.getBiomeKey(), JSExpandedBiomes.CRACKING_CLIFFS.getBiomeKey(), JSExpandedBiomes.SKULL_MOUNTAINS.getBiomeKey() }
    };

    private static final ResourceKey<Biome>[][] PLATEAU_VAR = new ResourceKey[][]{
            { null, null, null, null, null },
            { null, null, JSExpandedBiomes.SKULL_MOUNTAINS.getBiomeKey(), null, null },
            { null, null, null, null, null },
            { null, null, null, null, null },
            { JSExpandedBiomes.BARREN_VOLCANO.getBiomeKey(), null, null, null, null }
    };

    private static final ResourceKey<Biome>[][] SHATTERED = new ResourceKey[][]{
            { JSExpandedBiomes.CRACKING_CLIFFS.getBiomeKey(), JSExpandedBiomes.CRACKING_CLIFFS.getBiomeKey(), JSExpandedBiomes.TROPICAL_HIGHLANDS.getBiomeKey(), JSExpandedBiomes.CRACKING_CLIFFS.getBiomeKey(), JSExpandedBiomes.CRACKING_CLIFFS.getBiomeKey() },
            { JSExpandedBiomes.CRACKING_CLIFFS.getBiomeKey(), JSExpandedBiomes.CRACKING_CLIFFS.getBiomeKey(), JSExpandedBiomes.TROPICAL_HIGHLANDS.getBiomeKey(), JSExpandedBiomes.SKULL_MOUNTAINS.getBiomeKey(), JSExpandedBiomes.SKULL_MOUNTAINS.getBiomeKey() },
            { JSExpandedBiomes.TROPICAL_HIGHLANDS.getBiomeKey(), JSExpandedBiomes.TROPICAL_HIGHLANDS.getBiomeKey(), JSExpandedBiomes.CRACKING_CLIFFS.getBiomeKey(), JSExpandedBiomes.CRACKING_CLIFFS.getBiomeKey(), JSExpandedBiomes.CRACKING_CLIFFS.getBiomeKey() },
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

        // ---- Primary river channels (valleys only)
        emitSurface(out,
                FULL_RANGE,
                FULL_RANGE,
                Climate.Parameter.span(MID_INLAND_C, FAR_INLAND_C), // never coast
                Climate.Parameter.span(EROS[5], EROS[6]),           // true valley flats only
                Climate.Parameter.span(-0.15F, 0.15F),              // valley weirdness
                0.0F,
                JSExpandedBiomes.MIGHTY_RIVER.getBiomeKey()
        );

        // ---- Secondary river floodplains
        emitSurface(out,
                FULL_RANGE,
                FULL_RANGE,
                Climate.Parameter.span(NEAR_INLAND_C, FAR_INLAND_C), // allow near inland but not coast
                Climate.Parameter.span(EROS[5], EROS[6]),            // very flat only
                Climate.Parameter.span(-0.1F, 0.1F),
                0.0F,
                JSExpandedBiomes.MIGHTY_RIVER.getBiomeKey()
        );
    }


    // ---- Off coast (OCEANS ONLY — never touches land)
    private void addOffCoastBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> out) {

        // ---- Ultra-rare deep ocean features (below all other oceans)
        emitSurface(out,
                FULL_RANGE, FULL_RANGE,
                MUSHROOM_C,               // must be far below coast
                FULL_RANGE, FULL_RANGE, 0.0F,
                JSExpandedBiomes.SUNKEN_CITY.getBiomeKey());

        for (int ti = 0; ti < TEMPS.length; ti++) {
            Climate.Parameter T = TEMPS[ti];

            // ---- Deep ocean
            emitSurface(out,
                    T, FULL_RANGE,
                    DEEP_OCEAN_C,
                    FULL_RANGE, FULL_RANGE, 0.0F,
                    OCEANS[0][ti]);

            // ---- Normal ocean
            emitSurface(out,
                    T, FULL_RANGE,
                    OCEAN_C,
                    FULL_RANGE, FULL_RANGE, 0.0F,
                    OCEANS[1][ti]);

            // ---- Rare ocean variant
            emitSurface(out,
                    T, FULL_RANGE,
                    OCEAN_C,
                    FULL_RANGE, FULL_RANGE, 0.05F,
                    JSExpandedBiomes.FOGGY_SEA.getBiomeKey());
        }
    }

    // ---- Inland
    private void addInlandBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> out) {

        // valleys (near ridges = 0)
        addValleys(out, Climate.Parameter.span(-0.15F, 0.15F));

        // lowlands
        addLowSlice(out, Climate.Parameter.span(-0.35F, -0.15F));
        addLowSlice(out, Climate.Parameter.span(0.15F, 0.35F));

        // midlands / hills
        addMidSlice(out, Climate.Parameter.span(-0.6F, -0.35F));
        addMidSlice(out, Climate.Parameter.span(0.35F, 0.6F));

        // highlands
        addHighSlice(out, Climate.Parameter.span(-0.8F, -0.6F));
        addHighSlice(out, Climate.Parameter.span(0.6F, 0.8F));

        // sharp peaks
        addPeaks(out, Climate.Parameter.span(-1.0F, -0.8F));
        addPeaks(out, Climate.Parameter.span(0.8F, 1.0F));
    }

    private void addPeaks(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> out, Climate.Parameter d) {
        grid((ti, hi) -> {
            Climate.Parameter T = TEMPS[ti];
            Climate.Parameter H = HUMS[hi];

            ResourceKey<Biome> peak = pickPeak(ti, hi, d);          // jagged tops
            ResourceKey<Biome> slope = pickSlope(ti, hi, d);        // steep upper slopes
            ResourceKey<Biome> plat = pickPlateau(ti, hi, d);       // high plateaus
            ResourceKey<Biome> mid = pickMiddle(ti, hi, d);         // foothills / uplands
            ResourceKey<Biome> shat = pickShattered(ti, hi, d);     // broken ridges

            // ---- Very steep / jagged ridges
            emitSurface(out, T, H, Climate.Parameter.span(COAST_C, FAR_INLAND_C), EROS[0], d, 0.0F, peak);
            emitSurface(out, T, H, Climate.Parameter.span(MID_INLAND_C, FAR_INLAND_C), EROS[1], d, 0.0F, peak);

            // ---- Upper slopes
            emitSurface(out, T, H, Climate.Parameter.span(COAST_C, FAR_INLAND_C), EROS[2], d, 0.0F, slope);

            // ---- High plateaus
            emitSurface(out, T, H, Climate.Parameter.span(MID_INLAND_C, FAR_INLAND_C), EROS[3], d, 0.0F, plat);

            // ---- Foothills creeping into peak bands
            emitSurface(out, T, H, Climate.Parameter.span(COAST_C, FAR_INLAND_C), EROS[4], d, 0.0F, mid);

            // ---- Shattered ridges / cliff bands
            emitSurface(out, T, H, Climate.Parameter.span(MID_INLAND_C, FAR_INLAND_C), EROS[5], d, 0.0F, shat);

            // ---- Soft fallback (never valleys here)
            emitSurface(out, T, H, Climate.Parameter.span(COAST_C, FAR_INLAND_C), EROS[6], d, 0.0F, mid);
        });
    }

    private void addHighSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> out, Climate.Parameter d) {
        grid((ti, hi) -> {
            Climate.Parameter T = TEMPS[ti];
            Climate.Parameter H = HUMS[hi];

            ResourceKey<Biome> slope = pickSlope(ti, hi, d);        // main biome here
            ResourceKey<Biome> plat  = pickPlateau(ti, hi, d);      // broad high areas
            ResourceKey<Biome> mid   = pickMiddle(ti, hi, d);       // foothills creeping up
            ResourceKey<Biome> shat  = pickShattered(ti, hi, d);    // broken cliff zones

            // ---- Steep upper slopes (near mountains)
            emitSurface(out, T, H, Climate.Parameter.span(COAST_C, FAR_INLAND_C), EROS[0], d, 0.0F, slope);
            emitSurface(out, T, H, Climate.Parameter.span(MID_INLAND_C, FAR_INLAND_C), EROS[1], d, 0.0F, slope);

            // ---- High plateaus
            emitSurface(out, T, H, Climate.Parameter.span(MID_INLAND_C, FAR_INLAND_C), EROS[2], d, 0.0F, plat);
            emitSurface(out, T, H, Climate.Parameter.span(MID_INLAND_C, FAR_INLAND_C), EROS[3], d, 0.0F, plat);

            // ---- Foothills and uplands
            emitSurface(out, T, H, Climate.Parameter.span(COAST_C, FAR_INLAND_C), EROS[4], d, 0.0F, mid);

            // ---- Shattered ridges (rare, harsh erosion)
            emitSurface(out, T, H, Climate.Parameter.span(MID_INLAND_C, FAR_INLAND_C), EROS[5], d, 0.0F, shat);

            // ---- Transition to lower terrain
            emitSurface(out, T, H, Climate.Parameter.span(COAST_C, FAR_INLAND_C), EROS[6], d, 0.0F, mid);
        });
    }

    private void addMidSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> out, Climate.Parameter d) {

        // ---- Coastal cliffs (rocky shoreline)
        emitSurface(out, FULL_RANGE, FULL_RANGE, COAST_C,
                Climate.Parameter.span(EROS[2], EROS[0]), d, 0.0F,
                JSExpandedBiomes.GRASSY_SEA_CLIFFS.getBiomeKey());

        // ---- Wetlands in valleys and flat lowlands
        emitSurface(out, Climate.Parameter.span(TEMPS[1], TEMPS[2]), FULL_RANGE,
                Climate.Parameter.span(NEAR_INLAND_C, FAR_INLAND_C), EROS[6], d, 0.0F,
                JSExpandedBiomes.OPEN_BAYOU.getBiomeKey());

        emitSurface(out, Climate.Parameter.span(TEMPS[3], TEMPS[4]), FULL_RANGE,
                Climate.Parameter.span(NEAR_INLAND_C, FAR_INLAND_C), EROS[6], d, 0.0F,
                JSExpandedBiomes.DENSE_SWAMP.getBiomeKey());

        grid((ti, hi) -> {
            Climate.Parameter T = TEMPS[ti];
            Climate.Parameter H = HUMS[hi];

            ResourceKey<Biome> mid   = pickMiddle(ti, hi, d);     // plains / jungle / savanna
            ResourceKey<Biome> slope = pickSlope(ti, hi, d);      // rare hills
            ResourceKey<Biome> shat  = pickShattered(ti, hi, d);  // broken ridges
            ResourceKey<Biome> beach = pickBeach(ti, hi);
            ResourceKey<Biome> shCoast = pickShatteredCoast(ti, hi, d);

            // ---- Gentle hills near mountains
            emitSurface(out, T, H,
                    Climate.Parameter.span(NEAR_INLAND_C, FAR_INLAND_C),
                    EROS[0], d, 0.0F, slope);

            // ---- Rolling uplands
            emitSurface(out, T, H,
                    Climate.Parameter.span(NEAR_INLAND_C, FAR_INLAND_C),
                    EROS[1], d, 0.0F, mid);

            emitSurface(out, T, H,
                    Climate.Parameter.span(NEAR_INLAND_C, FAR_INLAND_C),
                    EROS[2], d, 0.0F, mid);

            // ---- Plains and jungle lowlands
            emitSurface(out, T, H,
                    Climate.Parameter.span(COAST_C, FAR_INLAND_C),
                    EROS[3], d, 0.0F, mid);

            emitSurface(out, T, H,
                    Climate.Parameter.span(COAST_C, FAR_INLAND_C),
                    EROS[4], d, 0.0F, mid);

            // ---- Broken terrain bands (rare)
            emitSurface(out, T, H,
                    Climate.Parameter.span(MID_INLAND_C, FAR_INLAND_C),
                    EROS[5], d, 0.0F, shat);

            // ---- Valleys and wetlands transition
            emitSurface(out, T, H,
                    Climate.Parameter.span(COAST_C, FAR_INLAND_C),
                    EROS[6], d, 0.0F, mid);

            // ---- Beaches only at negative weirdness (true lowlands)
            if (d.max() < 0L) {
                emitSurface(out, T, H, COAST_C, EROS[4], d, 0.0F, beach);
                emitSurface(out, T, H, COAST_C, EROS[6], d, 0.0F, beach);
            } else {
                emitSurface(out, T, H, COAST_C, EROS[6], d, 0.0F, mid);
            }

            // ---- Extra wetlands in cold zones
            if (ti == 0) {
                emitSurface(out, T, H,
                        Climate.Parameter.span(NEAR_INLAND_C, FAR_INLAND_C),
                        EROS[6], d, 0.0F, mid);
            }

            // ---- Shattered coasts (stormy shores)
            emitSurface(out, T, H, COAST_C, EROS[5], d, 0.0F, shCoast);
        });
    }

    private void addLowSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> out, Climate.Parameter d) {

        // ---- Coastal cliffs (where terrain still rises sharply from sea)
        emitSurface(out, FULL_RANGE, FULL_RANGE, COAST_C,
                Climate.Parameter.span(EROS[0], EROS[1]), d, 0.0F,
                JSExpandedBiomes.GRASSY_SEA_CLIFFS.getBiomeKey());

        // ---- Wetlands in flat lowlands
        emitSurface(out, Climate.Parameter.span(TEMPS[1], TEMPS[2]), FULL_RANGE,
                Climate.Parameter.span(NEAR_INLAND_C, FAR_INLAND_C), EROS[6], d, 0.0F,
                JSExpandedBiomes.OPEN_BAYOU.getBiomeKey());

        emitSurface(out, Climate.Parameter.span(TEMPS[3], TEMPS[4]), FULL_RANGE,
                Climate.Parameter.span(NEAR_INLAND_C, FAR_INLAND_C), EROS[6], d, 0.0F,
                JSExpandedBiomes.DENSE_SWAMP.getBiomeKey());

        grid((ti, hi) -> {
            Climate.Parameter T = TEMPS[ti];
            Climate.Parameter H = HUMS[hi];

            ResourceKey<Biome> mid   = pickMiddle(ti, hi, d);   // plains / jungle / savanna
            ResourceKey<Biome> beach = pickBeach(ti, hi);
            ResourceKey<Biome> shCoast = pickShatteredCoast(ti, hi, d);

            // ---- Gentle terrain everywhere inland
            emitSurface(out, T, H,
                    Climate.Parameter.span(NEAR_INLAND_C, FAR_INLAND_C),
                    EROS[0], d, 0.0F, mid);

            emitSurface(out, T, H,
                    Climate.Parameter.span(NEAR_INLAND_C, FAR_INLAND_C),
                    EROS[1], d, 0.0F, mid);

            emitSurface(out, T, H,
                    Climate.Parameter.span(NEAR_INLAND_C, FAR_INLAND_C),
                    EROS[2], d, 0.0F, mid);

            emitSurface(out, T, H,
                    Climate.Parameter.span(COAST_C, FAR_INLAND_C),
                    EROS[3], d, 0.0F, mid);

            emitSurface(out, T, H,
                    Climate.Parameter.span(COAST_C, FAR_INLAND_C),
                    EROS[4], d, 0.0F, mid);

            // ---- Stormy / rocky coast only
            emitSurface(out, T, H, COAST_C, EROS[5], d, 0.0F, shCoast);

            // ---- Beaches at coast
            emitSurface(out, T, H, COAST_C, EROS[6], d, 0.0F, beach);

            // ---- Extra flat plains in cold zones
            if (ti == 0) {
                emitSurface(out, T, H,
                        Climate.Parameter.span(NEAR_INLAND_C, FAR_INLAND_C),
                        EROS[6], d, 0.0F, mid);
            }
        });
    }

    private void addValleys(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> out, Climate.Parameter d) {

        // ---- Main river corridors (all erosion levels)
        emitSurface(out, FULL_RANGE, FULL_RANGE,
                Climate.Parameter.span(COAST_C, FAR_INLAND_C),
                Climate.Parameter.span(EROS[6], EROS[0]), d, 0.0F,
                JSExpandedBiomes.MIGHTY_RIVER.getBiomeKey());

        // ---- Bayous in cooler flat valleys
        emitSurface(out, Climate.Parameter.span(TEMPS[1], TEMPS[2]), FULL_RANGE,
                Climate.Parameter.span(NEAR_INLAND_C, FAR_INLAND_C),
                EROS[5], d, 0.0F,
                JSExpandedBiomes.OPEN_BAYOU.getBiomeKey());

        emitSurface(out, Climate.Parameter.span(TEMPS[1], TEMPS[2]), FULL_RANGE,
                Climate.Parameter.span(NEAR_INLAND_C, FAR_INLAND_C),
                EROS[6], d, 0.0F,
                JSExpandedBiomes.OPEN_BAYOU.getBiomeKey());

        // ---- Swamps in hot flat valleys
        emitSurface(out, Climate.Parameter.span(TEMPS[3], TEMPS[4]), FULL_RANGE,
                Climate.Parameter.span(NEAR_INLAND_C, FAR_INLAND_C),
                EROS[5], d, 0.0F,
                JSExpandedBiomes.DENSE_SWAMP.getBiomeKey());

        emitSurface(out, Climate.Parameter.span(TEMPS[3], TEMPS[4]), FULL_RANGE,
                Climate.Parameter.span(NEAR_INLAND_C, FAR_INLAND_C),
                EROS[6], d, 0.0F,
                JSExpandedBiomes.DENSE_SWAMP.getBiomeKey());

        // ---- Valley sides: plains and jungle
        grid((ti, hi) -> {
            Climate.Parameter T = TEMPS[ti];
            Climate.Parameter H = HUMS[hi];

            ResourceKey<Biome> mid = pickMiddleOrBadlandsIfHot(ti, hi, d);

            emitSurface(out, T, H,
                    Climate.Parameter.span(MID_INLAND_C, FAR_INLAND_C),
                    EROS[2], d, 0.0F, mid);

            emitSurface(out, T, H,
                    Climate.Parameter.span(MID_INLAND_C, FAR_INLAND_C),
                    EROS[3], d, 0.0F, mid);

            emitSurface(out, T, H,
                    Climate.Parameter.span(MID_INLAND_C, FAR_INLAND_C),
                    EROS[4], d, 0.0F, mid);
        });
    }

    // ---- Underground
    private void addUndergroundBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> out) {
    }

    // ---- Pickers (now pass through rarity)
    private ResourceKey<Biome> pickMiddle(int t, int h, Climate.Parameter d) {

        // ---- Extremely dry + hot → desert / volcanic lowlands
        if (t >= 4 && h <= 1) {
            return JSExpandedBiomes.OIL_FIELDS.getBiomeKey();
        }

        // ---- Dry + warm → savanna
        if (h <= 1 && t >= 2) {
            return JSExpandedBiomes.DEAD_FOREST.getBiomeKey();
        }

        // ---- Very humid → swamps
        if (h >= 4) {
            return (t >= 3)
                    ? JSExpandedBiomes.DENSE_SWAMP.getBiomeKey()
                    : JSExpandedBiomes.OPEN_BAYOU.getBiomeKey();
        }

        // ---- Humid → jungle variants
        if (h >= 3) {
            if (t >= 4) return JSExpandedBiomes.PRIMORDIAL_JUNGLE.getBiomeKey();
            if (t >= 3) return JSExpandedBiomes.BAMBOO_FOREST.getBiomeKey();
            return JSExpandedBiomes.LOW_LAND_JUNGLE.getBiomeKey();
        }

        // ---- Neutral → plains variants
        if (h == 2) {
            return (t >= 3)
                    ? JSExpandedBiomes.LOWLAND_PLAINS.getBiomeKey()
                    : JSExpandedBiomes.MARSHY_PLAINS.getBiomeKey();
        }

        // ---- Fallback
        return JSExpandedBiomes.LOWLAND_PLAINS.getBiomeKey();
    }

    private ResourceKey<Biome> pickMiddleOrBadlandsIfHot(int t, int h, Climate.Parameter d) {
        if (t >= 4 && h <= 2) {
            return JSExpandedBiomes.BARREN_VOLCANO.getBiomeKey();
        }
        return pickMiddle(t, h, d);
    }

    private ResourceKey<Biome> maybeWindsweptSavanna(int t, int h, Climate.Parameter d, ResourceKey<Biome> fallback) {
        if (t >= 3 && h <= 1 && d.max() >= 0L) {
            return JSExpandedBiomes.DEAD_WOOD_HILLS.getBiomeKey();
        }
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
        // extremely dry highlands → volcanic mesas
        if (t >= 4 && h <= 1) {
            return JSExpandedBiomes.BARREN_VOLCANO.getBiomeKey();
        }

        // very humid → megafalls plateaus
        if (h >= 4) {
            return JSExpandedBiomes.MEGAFALLS.getBiomeKey();
        }

        ResourceKey<Biome> base;
        if (d.max() >= 0L && PLATEAU_VAR[t][h] != null) base = PLATEAU_VAR[t][h];
        else base = PLATEAU[t][h];

        return withRarity(base, t, h, d);
    }

    private ResourceKey<Biome> pickPeak(int t, int h, Climate.Parameter d) {
        // very dry + hot → volcanic / cracked peaks
        if (t >= 3 && h <= 1 || h >= 3) {
            return JSExpandedBiomes.BARREN_VOLCANO.getBiomeKey();
        }

        // default dramatic peaks
        return JSExpandedBiomes.SKULL_MOUNTAINS.getBiomeKey();
    }

    private ResourceKey<Biome> pickSlope(int t, int h, Climate.Parameter d) {
        // very humid slopes → mountainous swamp
        if (h >= 4) {
            return JSExpandedBiomes.MOUNTAINOUS_SWAMP.getBiomeKey();
        }

        // humid → jungle hills
        if (h >= 3) {
            return JSExpandedBiomes.MEGAFALLS.getBiomeKey();
        }

        // dry → cracked cliffs
        if (h <= 1 && t >= 3) {
            return JSExpandedBiomes.CRACKING_CLIFFS.getBiomeKey();
        }

        // default mountain slopes
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
