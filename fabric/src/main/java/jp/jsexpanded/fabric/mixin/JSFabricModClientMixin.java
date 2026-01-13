package jp.jsexpanded.fabric.mixin;

import jp.jsexpanded.JSExpanded;
import jp.jsexpanded.client.JSExpandedClient;
import jp.jurassicsaga.fabric.JSMod;
import jp.jurassicsaga.fabric.JSModClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = JSModClient.class, remap = false)
public class JSFabricModClientMixin {
    @Inject(at = @At("TAIL"), method = "onInitializeClient")
    private void onInitialize(CallbackInfo ci) {
        JSExpandedClient.init();
    }
}
