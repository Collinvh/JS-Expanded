package jp.jsexpanded.server.animals;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import jp.jsexpanded.JSExpanded;
import jp.jurassicsaga.server.base.animal.entity.obj.bases.JSAnimalBase;
import jp.jurassicsaga.server.base.animal.entity.obj.info.AnimalGrowthStage;
import jp.jurassicsaga.server.base.animal.entity.obj.other.JSVariants;
import net.minecraft.resources.ResourceLocation;
import travelers.server.animal.obj.locator.ResourceLocator;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static jp.jurassicsaga.server.base.animal.entity.obj.bases.JSEntityDataHolder.textureVariant;

public class JSExpandedLocator<T extends JSAnimalBase> extends ResourceLocator<T> {
    private final boolean hasGenders;
    private boolean adultOnly;
    protected Cache<String, ResourceLocation> cache = CacheBuilder.newBuilder()
            .maximumSize(5000)
            .build();

    public JSExpandedLocator() {
        this(true);
    }

    public JSExpandedLocator(boolean gendered) {
        this.hasGenders = gendered;
    }

    public JSExpandedLocator<T> setAdultOnly() {
        this.adultOnly = true;
        return this;
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        String key = makeCacheKey(entity, "texture");

        if (entity.hasCustomName()) {
            key += "|name=" + entity.getCustomName().getString().toLowerCase(Locale.ROOT);
        }

        try {
            return cache.get(key, () -> buildTextureLocation(entity));
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResourceLocation getLuxLocation(T entity) {
        String key = makeCacheKey(entity, "lux");

        try {
            return cache.get(key, () -> {
                String name = getEntityName(entity);
                String growthStage = getGrowthStage(entity);
                return JSExpanded.createId(
                        "textures/geo/animal/" + name + "/" + name + "_" + growthStage + "_lux.png"
                );
            });
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResourceLocation getModelLocation(T entity) {
        String key = makeCacheKey(entity, "model");

        try {
            return cache.get(key, () -> {
                String name = getEntityName(entity);
                String growthStage = getGrowthStage(entity);
                return JSExpanded.createId(
                        "geo/animal/" +  name + "/" + name + "_" + growthStage + ".geo.json"
                );
            });
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResourceLocation getDefaultModelLocation(T entity) {
        String key = makeCacheKey(entity, "default_model");

        try {
            return cache.get(key, () -> {
                String name = getEntityName(entity);
                return JSExpanded.createId(
                        "geo/animal/" +  name + "/" + name + "_adult.geo.json"
                );
            });
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResourceLocation getAnimationLocation(T entity) {
        String key = makeCacheKey(entity, "anim");

        try {
            return cache.get(key, () -> {
                String name = getEntityName(entity);
                boolean baby =
                        !adultOnly
                                && entity.getAnimal().getAnimalAttributes().getMiscProperties().isBabyAnimations()
                                && (entity.getModules().getGrowthStageModule().getGrowthStage() == AnimalGrowthStage.BABY);

                return JSExpanded.createId(
                        "animations/animal/" +  name + "/" + name +
                                (baby ? "_baby.animation.json" : ".animation.json")
                );
            });
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    protected ResourceLocation buildTextureLocation(T entity) {
        String name = getEntityName(entity);
        String growthStage = getGrowthStage(entity);

        if (entity.getModules().getGrowthStageModule().getGrowthStage() != AnimalGrowthStage.ADULT && !adultOnly) {
            return JSExpanded.createId("textures/geo/animal/" +  name + "/" + name + "_" + growthStage + ".png");
        }

        if(adultOnly) growthStage = "adult";

        JSVariants.JSVariant variant = JSVariants.fromLocation(entity.getEntityData().get(textureVariant));
        boolean extinct = entity.getAnimal().getAnimalAttributes().getMiscProperties().isExtinct();

        if (variant == JSVariants.NONE || !extinct) {
            if (!hasGenders) {
                return JSExpanded.createId("textures/geo/animal/" +  name + "/" + name + "_" + growthStage + ".png");
            }
            String suffix = entity.getModules().getGeneticModule().isMale() ? "male" : "female";
            return JSExpanded.createId("textures/geo/animal/" +  name + "/" + name + "_" + growthStage + "_" + suffix + ".png");
        }

        ResourceLocation variantLoc = variant.location();
        String path = variantLoc.getPath();
        boolean genderedVariants = entity.getAnimal().getAnimalAttributes().getMiscProperties().isGenderedVariants();

        if (variant.ignoresGenders()) {
            return ResourceLocation.fromNamespaceAndPath(JSExpanded.MOD_ID,
                    "textures/geo/animal/" +  name + "/variants/" + name + "_adult_" + path + ".png");
        }

        if (genderedVariants) {
            String suffix = entity.getModules().getGeneticModule().isMale() ? "_male" : "_female";
            return ResourceLocation.fromNamespaceAndPath(JSExpanded.MOD_ID,
                    "textures/geo/animal/" +  name + "/variants/" + name + "_adult_" + path + suffix + ".png");
        }

        return ResourceLocation.fromNamespaceAndPath(JSExpanded.MOD_ID,
                "textures/geo/animal/" +  name + "/variants/" + name + "_adult_" + path + ".png");
    }

    protected String getEntityName(T entity) {
        return entity.getAnimal().getAnimalAttributes().getAnimalName().toLowerCase();
    }

    private String getGrowthStage(T entity) {
        if(adultOnly) return "adult";
        return entity.getModules().getGrowthStageModule().getGrowthStage().getGrowthStageName();
    }

    protected String makeCacheKey(T entity, String type) {
        var animal  = entity.getAnimal().getAnimalAttributes();
        var modules = entity.getModules();
        var growth  = modules.getGrowthStageModule().getGrowthStage().name();
        var male    = modules.getGeneticModule().isMale();
        var variant = entity.getEntityData().get(textureVariant);

        // Avoid String.join (creates an array, iterator, etc.)
        return type + ":" + animal.getAnimalName() + ":" + growth + ":" + (male ? "1" : "0") + ":" + variant;
    }
}
