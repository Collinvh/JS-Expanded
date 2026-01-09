package jp.jsexpanded.server.world.biome;

import jp.jsexpanded.server.world.biome.obj.*;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class JSExpandedBiomes {
    /*
    Semi-arid/dry tropical jungle
     */
    public static final JSExpandedBiome DRY_FOREST = new DryForest();

    /*
    Tropical/Jungle
     */
    public static final JSExpandedBiome TROPICAL_SHRUBLAND = new TropicalShrubland();

    /*
    Desert
     */
    public static final JSExpandedBiome OIL_FIELDS = new OilFields();

    /*
    Coastal Cliffs, at the edge of the water
     */
    public static final JSExpandedBiome GRASSY_SEA_CLIFFS = new GrassySeaCliffs();

    /*
    Biome between coastline and sea
     */
    public static final JSExpandedBiome SANDY_SHALLOWS = new SandyShallows();

    /*
    Rare alternative for Sandy Shallows
     */
    public static final JSExpandedBiome DARK_SANDY_SHALLOWS = new DarkSandyShallows();

    /*
    Savannah
     */
    public static final JSExpandedBiome DEAD_FOREST = new DeadForest();

    /*
    Hilly Savannah
     */
    public static final JSExpandedBiome DEAD_WOOD_HILLS = new DeadWoodHills();

    /*
    Hilly Jungle
     */
    public static final JSExpandedBiome MEGAFALLS = new Megafalls();

    /*
    Swamp (basically the mangrove swamp)
     */
    public static final JSExpandedBiome DENSE_SWAMP = new DenseSwamp();

    /*
    Swamp
     */
    public static final JSExpandedBiome OPEN_BAYOU = new OpenBayou();

    /*
    Mountainous Swamp
     */
    public static final JSExpandedBiome MOUNTAINOUS_SWAMP = new MountainousSwamp();

    /*
    Big and wide rivers
     */
    public static final JSExpandedBiome MIGHTY_RIVER = new MightyRiver();

    /*
    The highest mountains will be this biome
     */
    public static final JSExpandedBiome SKULL_MOUNTAINS = new SkullMountains();

    /*
    Mountainous Biome, it can also be a hills biome
     */
    public static final JSExpandedBiome TROPICAL_HIGHLANDS = new TropicalHighlands();

    /*
    Barren mountainous biome, like an desert/savannah mountain
     */
    public static final JSExpandedBiome CRACKING_CLIFFS = new CrackingCliffs();

    /*
    Cliffs that have waterfalls embedded in them, usually tropical
     */
    public static final JSExpandedBiome WATERFALL_CLIFFS = new WaterfallCliffs();

    /*
    Really drylands, think like mesa
     */
    public static final JSExpandedBiome BARREN_VOLCANO = new BarrenVolcano();

    /*
    Jungle variant
     */
    public static final JSExpandedBiome LOW_LAND_JUNGLE = new LowlandJungle();

    /*
    Jungle variant
     */
    public static final JSExpandedBiome BAMBOO_FOREST = new BambooForest();

    /*
    Plains variant
     */
    public static final JSExpandedBiome LOWLAND_PLAINS = new LowlandPlains();

    /*
    Plains variant
     */
    public static final JSExpandedBiome MARSHY_PLAINS = new MarshyPlains();

    /*
    Plains variant
     */
    public static final JSExpandedBiome MOUNTAINOUS_PLAINS = new MountainousPlains();

    /*
    Jungle variant
     */
    public static final JSExpandedBiome PRIMORDIAL_JUNGLE = new PrimordialJungle();

    /*
    Jungle variant
     */
    public static final JSExpandedBiome JUNGLE_CLEARING = new JungleClearing();

    /*
    Jungle variant
     */
    public static final JSExpandedBiome PITFALL_JUNGLE = new PitfallJungle();

    /*
    Coast variant -> Warm
     */
    public static final JSExpandedBiome LUSH_COAST = new LushCoast();

    /*
    Coast variant -> Cold
     */
    public static final JSExpandedBiome BARREN_COAST = new BarrenCoast();

    /*
    Coast variant -> Default
     */
    public static final JSExpandedBiome FLAT_SANDY_COAST = new FlatSandyCoast();

    /*
    Ocean variant -> Rare
     */
    public static final JSExpandedBiome FOGGY_SEA = new FoggySea();

    /*
    Ocean variant -> Normal
     */
    public static final JSExpandedBiome SEA = new Sea();

    /*
    Ocean variant -> Rare
     */
    public static final JSExpandedBiome SUNKEN_CITY = new SunkenCity();

    public static void bootstrap(BootstrapContext<Biome> ctx) {
        ctx.register(DRY_FOREST.getBiomeKey(), DRY_FOREST.create(ctx));
        ctx.register(TROPICAL_SHRUBLAND.getBiomeKey(), TROPICAL_SHRUBLAND.create(ctx));
        ctx.register(OIL_FIELDS.getBiomeKey(), OIL_FIELDS.create(ctx));
        ctx.register(GRASSY_SEA_CLIFFS.getBiomeKey(), GRASSY_SEA_CLIFFS.create(ctx));
        ctx.register(SANDY_SHALLOWS.getBiomeKey(), SANDY_SHALLOWS.create(ctx));
        ctx.register(DARK_SANDY_SHALLOWS.getBiomeKey(), DARK_SANDY_SHALLOWS.create(ctx));
        ctx.register(DEAD_FOREST.getBiomeKey(), DEAD_FOREST.create(ctx));
        ctx.register(DEAD_WOOD_HILLS.getBiomeKey(), DEAD_WOOD_HILLS.create(ctx));
        ctx.register(MEGAFALLS.getBiomeKey(), MEGAFALLS.create(ctx));
        ctx.register(DENSE_SWAMP.getBiomeKey(), DENSE_SWAMP.create(ctx));
        ctx.register(OPEN_BAYOU.getBiomeKey(), OPEN_BAYOU.create(ctx));
        ctx.register(MOUNTAINOUS_SWAMP.getBiomeKey(), MOUNTAINOUS_SWAMP.create(ctx));
        ctx.register(MIGHTY_RIVER.getBiomeKey(), MIGHTY_RIVER.create(ctx));
        ctx.register(SKULL_MOUNTAINS.getBiomeKey(), SKULL_MOUNTAINS.create(ctx));
        ctx.register(TROPICAL_HIGHLANDS.getBiomeKey(), TROPICAL_HIGHLANDS.create(ctx));
        ctx.register(CRACKING_CLIFFS.getBiomeKey(), CRACKING_CLIFFS.create(ctx));
        ctx.register(WATERFALL_CLIFFS.getBiomeKey(), WATERFALL_CLIFFS.create(ctx));
        ctx.register(BARREN_VOLCANO.getBiomeKey(), BARREN_VOLCANO.create(ctx));
        ctx.register(LOW_LAND_JUNGLE.getBiomeKey(), LOW_LAND_JUNGLE.create(ctx));
        ctx.register(BAMBOO_FOREST.getBiomeKey(), BAMBOO_FOREST.create(ctx));
        ctx.register(LOWLAND_PLAINS.getBiomeKey(), LOWLAND_PLAINS.create(ctx));
        ctx.register(MARSHY_PLAINS.getBiomeKey(), MARSHY_PLAINS.create(ctx));
        ctx.register(MOUNTAINOUS_PLAINS.getBiomeKey(), MOUNTAINOUS_PLAINS.create(ctx));
        ctx.register(PRIMORDIAL_JUNGLE.getBiomeKey(), PRIMORDIAL_JUNGLE.create(ctx));
        ctx.register(JUNGLE_CLEARING.getBiomeKey(), JUNGLE_CLEARING.create(ctx));
        ctx.register(PITFALL_JUNGLE.getBiomeKey(), PITFALL_JUNGLE.create(ctx));
        ctx.register(LUSH_COAST.getBiomeKey(), LUSH_COAST.create(ctx));
        ctx.register(BARREN_COAST.getBiomeKey(), BARREN_COAST.create(ctx));
        ctx.register(FLAT_SANDY_COAST.getBiomeKey(), FLAT_SANDY_COAST.create(ctx));
        ctx.register(FOGGY_SEA.getBiomeKey(), FOGGY_SEA.create(ctx));
        ctx.register(SEA.getBiomeKey(), SEA.create(ctx));
        ctx.register(SUNKEN_CITY.getBiomeKey(), SUNKEN_CITY.create(ctx));
    }

    public static SurfaceRules.RuleSource sequence() {
        return SurfaceRules.sequence(
                DRY_FOREST.sequence(),
                TROPICAL_SHRUBLAND.sequence(),
                OIL_FIELDS.sequence(),
                GRASSY_SEA_CLIFFS.sequence(),
                SANDY_SHALLOWS.sequence(),
                DARK_SANDY_SHALLOWS.sequence(),
                DEAD_FOREST.sequence(),
                DEAD_WOOD_HILLS.sequence(),
                MEGAFALLS.sequence(),
                DENSE_SWAMP.sequence(),
                OPEN_BAYOU.sequence(),
                MOUNTAINOUS_SWAMP.sequence(),
                MIGHTY_RIVER.sequence(),
                SKULL_MOUNTAINS.sequence(),
                TROPICAL_HIGHLANDS.sequence(),
                CRACKING_CLIFFS.sequence(),
                WATERFALL_CLIFFS.sequence(),
                BARREN_VOLCANO.sequence(),
                LOW_LAND_JUNGLE.sequence(),
                BAMBOO_FOREST.sequence(),
                LOWLAND_PLAINS.sequence(),
                MARSHY_PLAINS.sequence(),
                MOUNTAINOUS_PLAINS.sequence(),
                PRIMORDIAL_JUNGLE.sequence(),
                JUNGLE_CLEARING.sequence(),
                PITFALL_JUNGLE.sequence(),
                LUSH_COAST.sequence(),
                BARREN_COAST.sequence(),
                FLAT_SANDY_COAST.sequence(),
                FOGGY_SEA.sequence(),
                SEA.sequence(),
                SUNKEN_CITY.sequence()
        );
    }
}
