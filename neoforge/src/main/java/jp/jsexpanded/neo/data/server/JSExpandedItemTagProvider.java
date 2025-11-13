package jp.jsexpanded.neo.data.server;

import jp.jsexpanded.JSExpanded;
import jp.jsexpanded.server.animals.AbstractAddonAnimal;
import jp.jurassicsaga.server.base.animal.JSAnimals;
import jp.jurassicsaga.server.base.animal.entity.obj.bases.JSAnimalBase;
import jp.jurassicsaga.server.base.animal.obj.JSAnimal;
import jp.jurassicsaga.server.base.tag.JSItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import travelers.server.animal.obj.misc.AnimalType;

import java.util.concurrent.CompletableFuture;

import static net.neoforged.neoforge.common.Tags.Items.*;

public class JSExpandedItemTagProvider extends ItemTagsProvider {
    public JSExpandedItemTagProvider(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, JSExpanded.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        IntrinsicTagAppender<Item> DNA = tag(JSItemTags.DNA);
        for (JSAnimal<? extends JSAnimalBase> animal : JSAnimals.getAnimals()) {
            if(animal instanceof AbstractAddonAnimal<? extends JSAnimalBase>) {
                var animalType = animal.getAnimalAttributes().getEntityBaseProperties().getAnimalType();
                if (animal.getItems().getCoin() != null) {
                    DNA.add(animal.getItems().getCoin().get());
                }

                if (animal.getItems().getFossil_remains() != null) {
                    tag(JSItemTags.FOSSIL).add(animal.getItems().getFossil_remains().get().asItem());
                }

                if (animal.getItems().getCookedMeat() != null) {
                    if (animalType == AnimalType.AQUATIC_MAMMAL || animalType == AnimalType.AQUATIC_REPTILE || animalType == AnimalType.FISH) {
                        tag(JSItemTags.PISCIVORE_EDIBLE).add(animal.getItems().getCookedMeat().get().asItem());
                    } else {
                        tag(JSItemTags.CARNIVORE_EDIBLE).add(animal.getItems().getCookedMeat().get().asItem());
                    }
                }

                if (animal.getItems().getRawMeat() != null) {
                    if (animalType == AnimalType.AQUATIC_MAMMAL || animalType == AnimalType.AQUATIC_REPTILE || animalType == AnimalType.FISH) {
                        tag(JSItemTags.PISCIVORE_EDIBLE).add(animal.getItems().getRawMeat().get().asItem());
                    } else {
                        tag(JSItemTags.CARNIVORE_EDIBLE).add(animal.getItems().getRawMeat().get().asItem());
                    }
                }

                if(animal.getAnimalAttributes().getItemProperties().isHasMeat()) {
                    var type = animal.getAnimalAttributes().getEntityBaseProperties().getAnimalType();
                    if(type == AnimalType.AQUATIC_MAMMAL || type == AnimalType.AQUATIC_REPTILE || type == AnimalType.FISH) {
                        tag(FOODS_RAW_FISH).add(animal.getItems().getRawMeat().get());
                        tag(FOODS_COOKED_FISH).add(animal.getItems().getCookedMeat().get());
                    } else {
                        tag(FOODS_RAW_MEAT).add(animal.getItems().getRawMeat().get());
                        tag(FOODS_COOKED_MEAT).add(animal.getItems().getCookedMeat().get());
                    }
                }

                if(animal.getItems().getHatched_egg() != null) {
                    tag(JSItemTags.FERTILIZED_EGGS).add(animal.getItems().getHatched_egg().get());
                }

                if(animal.getAnimalAttributes().getMiscProperties().isExtinct()) {
                    if(animal.getAnimalAttributes().getMiscProperties().getVersion() == 1.0) {
                        if(animal.getItems().getHatched_egg() != null) {
                            tag(JSItemTags.V1_ANIMALS).add(animal.getItems().getHatched_egg().get());
                        }
                    }
                }
            }
        }
    }
}
