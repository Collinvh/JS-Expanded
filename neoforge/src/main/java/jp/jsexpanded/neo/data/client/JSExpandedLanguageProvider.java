package jp.jsexpanded.neo.data.client;

import jp.jsexpanded.JSExpanded;
import jp.jsexpanded.neo.data.JSExpandedData;
import jp.jurassicsaga.neo.data.obj.Data;
import jp.jurassicsaga.neo.data.obj.SignDataObject;
import jp.jurassicsaga.neo.data.obj.SimpleDataObject;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.ArrayList;
import java.util.function.Supplier;

public class JSExpandedLanguageProvider extends LanguageProvider {
    private final ArrayList<String> translations = new ArrayList<>();
    boolean firstRun;

    public JSExpandedLanguageProvider(PackOutput output) {
        super(output, JSExpanded.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        if (!firstRun) {
            JSExpandedData.getDATA().forEach((supplier, data) -> {
                accept(supplier, data);
            });
            for (Data data : JSExpandedData.getDATA_ARRAY().stream().filter(data -> data instanceof SimpleDataObject).toList()) {
                if (data instanceof SimpleDataObject(String translationKey, String translation)) {
                    add(translationKey, translation);
                }
            }
        }
        firstRun = true;
    }

    private void accept(Supplier<?> deferredHolder, Data translationObject) {
        if(translationObject instanceof SignDataObject) return;
        if(translationObject.translation() == null) return;
        if (deferredHolder == null) {
            return;
        }
        var result = deferredHolder.get();
        if(result instanceof Block block) {
            if(translations.contains(block.getDescriptionId())) {
                return;
            }
            add(block, translationObject.translation());
            translations.add(block.getDescriptionId());
        } else if(result instanceof Item item) {
            if(translations.contains(item.getDescriptionId())) {
                return;
            }
            add(item, translationObject.translation());
            translations.add(item.getDescriptionId());
        } else if (result instanceof EntityType<?> entity) {
            if(translations.contains(entity.getDescriptionId())) {
                return;
            }
            addEntityType((Supplier<? extends EntityType<?>>) deferredHolder, translationObject.translation());
            translations.add(entity.getDescriptionId());
        } else if (deferredHolder.get() instanceof TagKey<?>(
                net.minecraft.resources.ResourceKey<? extends net.minecraft.core.Registry<?>> registry,
                ResourceLocation location
        )) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("tag.");

            ResourceLocation registryIdentifier = registry.location();

            stringBuilder.append(registryIdentifier.toShortLanguageKey().replace("/", "."))
                    .append(".")
                    .append(location.getNamespace())
                    .append(".")
                    .append(location.getPath().replace("/", ".").replace(":", "."));
            if(translations.contains(stringBuilder.toString())) {
                return;
            }
            addTag((Supplier<? extends TagKey<?>>) deferredHolder, translationObject.translation());
            translations.add(stringBuilder.toString());
        }
    }
}
