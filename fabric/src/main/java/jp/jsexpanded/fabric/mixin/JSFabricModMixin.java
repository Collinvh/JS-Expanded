package jp.jsexpanded.fabric.mixin;

import jp.jsexpanded.JSExpanded;
import jp.jurassicsaga.fabric.JSMod;
import jp.jurassicsaga.server.base.animal.JSAnimals;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = JSMod.class, remap = false)
public class JSFabricModMixin {
    @Inject(at = @At("TAIL"), method = "onInitialize")
    private void onInitialize(CallbackInfo ci) {
        JSExpanded.init();
        JSAnimals.getAnimals().clear();
    }
}
