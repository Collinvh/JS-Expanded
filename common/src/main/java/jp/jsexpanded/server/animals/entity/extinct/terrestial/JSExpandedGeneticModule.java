package jp.jsexpanded.server.animals.entity.extinct.terrestial;

import jp.jurassicsaga.server.base.animal.entity.obj.bases.JSAnimalBase;
import jp.jurassicsaga.server.base.animal.entity.obj.modules.obj.JSGeneticModule;
import jp.jurassicsaga.server.base.generic.gene.obj.JSGeneData;
import net.minecraft.world.level.ServerLevelAccessor;

public class JSExpandedGeneticModule extends JSGeneticModule {
    public JSExpandedGeneticModule(JSAnimalBase owner) {
        super(owner);
    }

    @Override
    public void hatch() {
        super.hatch();

        JSGeneData data = getGeneData();
        data.setGeneSeed(owner.level().getRandom().nextInt());
        data.setSizeDimorphism((float) (1 + owner.level().getRandom().nextInt(
                (int) owner.getAnimal().getAnimalAttributes().getEntityBaseProperties().getSizeDimorphism()
        )) / 750);
        setGeneData(data);
    }
}
