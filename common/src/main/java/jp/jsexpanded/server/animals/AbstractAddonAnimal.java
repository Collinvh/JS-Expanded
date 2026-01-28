package jp.jsexpanded.server.animals;

import jp.jsexpanded.JSExpanded;
import jp.jurassicsaga.server.base.animal.JSAnimals;
import jp.jurassicsaga.server.base.animal.animals.JSAnimations;
import jp.jurassicsaga.server.base.animal.entity.obj.bases.JSAnimalBase;
import jp.jurassicsaga.server.base.animal.obj.JSAnimal;
import jp.jurassicsaga.server.base.animal.obj.JSTravelersAttributes;
import jp.jurassicsaga.server.base.animal.obj.attributes.*;
import travelers.server.animal.TravelersAnimalRegistry;
import travelers.server.animal.obj.animation.TravelersAnimationControllers;
import travelers.server.animal.obj.attributes.EntityAttributeProperties;
import travelers.server.animal.obj.attributes.EntityBaseProperties;

public abstract class AbstractAddonAnimal<T extends JSAnimalBase> extends JSAnimal<T> {
    public AbstractAddonAnimal(String name) {
        super(new JSTravelersAttributes<>(name, JSExpanded.MOD_ID));
        TravelersAnimalRegistry.register(this);
    }

    @Override
    protected void init(JSTravelersAttributes<T> animalAttributes) {
        applyTravelersProperties(animalAttributes.getEntityAttributeProperties(), animalAttributes.getEntityBaseProperties());
        applyGeneticProperties(animalAttributes.getGeneticProperties());
        applyMetabolismProperties(animalAttributes.getMetabolismProperties());
        applyMiscProperties(animalAttributes.getMiscProperties());
        applySocialProperties(animalAttributes.getSocialGroupProperties());
        applyItemProperties(animalAttributes.getItemProperties());

        animalAttributes.getEntityBaseProperties()
                .addAnimationController(TravelersAnimationControllers.rootController)
                .addAnimationController(JSAnimations.base_controller)
                .addAnimationController(JSAnimations.swayController)
                .addAnimationController(JSAnimations.damageController);
    }

    protected abstract void applyGeneticProperties(JSGeneticProperties<T> geneticProperties);
    protected abstract void applyMetabolismProperties(JSMetabolismProperties<T> metabolismProperties);
    protected abstract void applyMiscProperties(JSMiscProperties<T> miscProperties);
    protected abstract void applySocialProperties(JSSocialGroupProperties<T> socialGroupProperties);
    protected abstract void applyItemProperties(JSItemProperties<T> itemProperties);
    protected abstract void applyTravelersProperties(EntityAttributeProperties<T> attributes, EntityBaseProperties<T> base);
}
