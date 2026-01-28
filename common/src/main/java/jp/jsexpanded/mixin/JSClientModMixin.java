package jp.jsexpanded.mixin;

import jp.jsexpanded.JSExpanded;
import jp.jsexpanded.client.JSExpandedClient;
import jp.jurassicsaga.JSCommon;
import jp.jurassicsaga.client.JSCommonClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = JSCommonClient.class, remap = false)
public class JSClientModMixin {
    @Inject(at = @At("TAIL"), method = "init")
    private static void init(CallbackInfo ci) {
        JSExpandedClient.init();
    }
}
