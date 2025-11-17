package jp.jsexpanded.fabric;

import jp.jsexpanded.client.JSExpandedClient;
import net.fabricmc.api.ClientModInitializer;

public class JSFabricExpandedClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        JSExpandedClient.init();
    }
}
