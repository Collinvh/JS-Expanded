package jp.jsexpanded.mixin;

import jp.jurassicsaga.server.base.animal.entity.obj.modules.JSAnimalModuleBase;
import jp.jurassicsaga.server.base.animal.entity.obj.modules.JSModuleHolder;
import jp.jurassicsaga.server.base.animal.entity.obj.modules.obj.JSGeneticModule;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.ArrayList;

@Mixin(JSModuleHolder.class)
public interface JSModuleHolderMixin {
    @Accessor("geneticModule")
    JSGeneticModule getGeneticModule();

    @Accessor("geneticModule")
    @Mutable
    void setGeneticModule(JSGeneticModule geneticModule);
}
