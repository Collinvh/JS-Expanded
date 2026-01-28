package jp.jsexpanded.mixin;

import jp.jsexpanded.JSExpanded;
import jp.jsexpanded.server.animals.JSExpandedAnimals;
import jp.jurassicsaga.JSCommon;
import jp.jurassicsaga.server.base.animal.JSAnimals;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = JSCommon.class, remap = false)
public class JSModMixin {
    @Inject(at = @At("TAIL"), method = "init")
    private static void init(CallbackInfo ci) {
        JSExpanded.init();

        // Reset the cache, so the new animals are added
        JSAnimals.getAnimals().clear();
    }
}
