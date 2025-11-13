package jp.jsexpanded.neo.data.server;

import jp.jsexpanded.JSExpanded;
import jp.jsexpanded.server.animals.AbstractAddonAnimal;
import jp.jurassicsaga.JSCommon;
import jp.jurassicsaga.server.base.animal.JSAnimals;
import jp.jurassicsaga.server.base.animal.entity.obj.bases.JSAnimalBase;
import jp.jurassicsaga.server.base.animal.obj.JSAnimal;
import jp.jurassicsaga.server.v1.item.JSV1Items;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Consumer;

public class JSExpandedAchievementProvider implements AdvancementProvider.AdvancementGenerator {
    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
        for (JSAnimal<? extends JSAnimalBase> animal : JSAnimals.getAnimals()) {
            if(animal instanceof AbstractAddonAnimal<? extends JSAnimalBase>) {
                if (animal.getAnimalAttributes().getMiscProperties().isExtinct()) {
                    createAnimal(saver, animal);
                }
            }
        }
    }

    private void createAnimal(Consumer<AdvancementHolder> saver, JSAnimal<? extends JSAnimalBase> animal) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        String name = animal.getAnimalAttributes().getAnimalName();
        var hatchedEgg = animal.getItems().getHatched_egg();
        if(hatchedEgg == null) return;
        var item = animal.getItems().getCoin();
        var actualItem = ItemStack.EMPTY;
        if(item != null) {
            actualItem = item.get().getDefaultInstance();
        } else {
            actualItem = JSV1Items.FAILED_EGG.get().getDefaultInstance();
        }
        builder.display(
                actualItem,
                Component.translatable("advancements.js."+ name +".title"),
                Component.translatable("advancements.js."+ name +".desc"),
                JSCommon.createId(("textures/gui/advancements/backgrounds/v"+ animal.getAnimalAttributes().getMiscProperties().getVersion() +".png")),
                AdvancementType.CHALLENGE, true, true, true);
        builder.parent(AdvancementSubProvider.createPlaceholder(createId("base/v" + animal.getAnimalAttributes().getMiscProperties().getVersion())));
        builder.addCriterion("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(hatchedEgg.get())));
        builder.save(saver, createId("base/animals/v"+ animal.getAnimalAttributes().getMiscProperties().getVersion() +"/" + name));
    }

    private String createId(String s) {
        return JSExpanded.MOD_ID + ":" + s;
    }
}
