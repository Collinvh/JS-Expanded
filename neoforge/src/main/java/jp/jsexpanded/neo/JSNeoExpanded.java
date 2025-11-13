package jp.jsexpanded.neo;


import jp.jsexpanded.JSExpanded;
import jp.jsexpanded.neo.data.GatherData;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(JSExpanded.MOD_ID)
public class JSNeoExpanded {

    public JSNeoExpanded(IEventBus eventBus) {
        JSExpanded.init();

        eventBus.addListener(GatherData::gatherData);
    }
}