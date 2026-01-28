package jp.jsexpanded.neo.mixin;

import jp.jurassicsaga.neo.data.GatherData;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GatherData.class)
public class JSDataGatherMixin {
    @Inject(at = @At("HEAD"), method = "gatherData", cancellable = true)
    private static void gatherData(GatherDataEvent event, CallbackInfo ci) {
        //This is done because I sometimes forget to disable datagen on the main mod (sorry)
        ci.cancel();
    }
}
