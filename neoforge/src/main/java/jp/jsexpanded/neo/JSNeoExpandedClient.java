package jp.jsexpanded.neo;


import jp.jsexpanded.JSExpanded;
import jp.jsexpanded.client.JSExpandedClient;
import jp.jsexpanded.neo.data.GatherData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(value = JSExpanded.MOD_ID, dist = Dist.CLIENT)
public class JSNeoExpandedClient {

    public JSNeoExpandedClient(IEventBus eventBus) {
        JSExpandedClient.init();
    }
}