package jp.jsexpanded.fabric;

import jp.jsexpanded.JSExpanded;
import jp.jsexpanded.client.JSExpandedClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class JSFabricExpandedClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        JSExpandedClient.init();
    }
}
