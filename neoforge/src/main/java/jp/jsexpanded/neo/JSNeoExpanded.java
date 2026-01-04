package jp.jsexpanded.neo;


import jp.jsexpanded.JSExpanded;
import jp.jsexpanded.neo.data.GatherData;
import jp.jsexpanded.server.animals.JSExpandedAnimals;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(JSExpanded.MOD_ID)
public class JSNeoExpanded {

    public JSNeoExpanded(IEventBus eventBus) {
        JSExpanded.init();

        eventBus.addListener(GatherData::gatherData);
        eventBus.addListener(GatherData::gatherData);
    }


    @EventBusSubscriber(modid = JSExpanded.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    public static class ModEvents {
        @SubscribeEvent
        public static void fmlCommonSetup(FMLCommonSetupEvent event) {
            event.enqueueWork(JSExpandedAnimals::finalizeAnimals);
        }
    }
}