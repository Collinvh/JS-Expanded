package jp.jsexpanded;

import jp.jsexpanded.server.animals.JSExpandedAnimals;
import jp.jsexpanded.server.sound.JSExpandedSounds;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import travelers.TravelersMain;

public class JSExpanded {
	public static final String MOD_ID = "jsexpanded";
	public static final String MOD_NAME = "JSExpanded";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
	
    public static void init() {
        TravelersMain.registerMod(MOD_ID);
        TravelersMain.enableDebugMode();
        JSExpandedAnimals.init();
        JSExpandedSounds.init();
    }

    public static ResourceLocation createId(String s) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, s);
    }
}