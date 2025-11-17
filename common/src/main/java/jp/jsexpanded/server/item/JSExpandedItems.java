package jp.jsexpanded.server.item;

import com.google.common.base.Supplier;
import jp.jsexpanded.JSExpanded;
import jp.jurassicsaga.JSConstants;
import jp.jurassicsaga.server.base.generic.obj.Era;
import jp.jurassicsaga.server.base.generic.obj.PossibleResult;
import jp.jurassicsaga.server.base.item.obj.FossilItem;
import jp.jurassicsaga.server.base.item.obj.GuidebookItem;
import jp.jurassicsaga.server.v1.item.JSV1Items;
import jp.jurassicsaga.server.v2.item.JSV2Items;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import travelers.util.helper.TravelersRegistry;

public class JSExpandedItems {
    public static final TravelersRegistry<Item> ITEMS = new TravelersRegistry<>(BuiltInRegistries.ITEM, JSExpanded.MOD_ID);

    public static Supplier<Item> registerItem(String name, Supplier<Item> item) {
        return ITEMS.register(name, item);
    }

    public static void init() {
    }
}
