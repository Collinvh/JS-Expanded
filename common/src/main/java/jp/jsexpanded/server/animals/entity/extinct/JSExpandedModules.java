package jp.jsexpanded.server.animals.entity.extinct;

import jp.jsexpanded.mixin.JSModuleHolderMixin;
import jp.jsexpanded.server.animals.entity.extinct.terrestial.JSExpandedGeneticModule;
import jp.jurassicsaga.server.base.animal.entity.obj.bases.JSAnimalBase;
import jp.jurassicsaga.server.base.animal.entity.obj.modules.JSModuleHolder;

public class JSExpandedModules extends JSModuleHolder {
    private final JSAnimalBase owner;

    public JSExpandedModules(JSAnimalBase owner) {
        super(owner);
        this.owner = owner;
    }

    @Override
    public void init() {
        Object thisObj = this;
        if(thisObj instanceof JSModuleHolderMixin mixin) {
            mixin.setGeneticModule(new JSExpandedGeneticModule(owner));
        }
        super.init();
    }
}
