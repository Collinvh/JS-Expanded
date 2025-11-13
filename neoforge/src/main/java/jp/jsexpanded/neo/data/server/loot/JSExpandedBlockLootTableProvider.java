package jp.jsexpanded.neo.data.server.loot;

import jp.jsexpanded.JSExpanded;
import jp.jurassicsaga.server.base.block.obj.group.BasicBlockSetRegistries;
import jp.jurassicsaga.server.base.block.obj.group.ColoredRegistries;
import jp.jurassicsaga.server.base.block.obj.group.StoneRegistries;
import jp.jurassicsaga.server.base.generic.obj.Era;
import jp.jurassicsaga.server.base.generic.obj.PossibleResult;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.stream.Collectors;

public class JSExpandedBlockLootTableProvider extends VanillaBlockLoot {
    HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

    public JSExpandedBlockLootTableProvider(HolderLookup.Provider registries) {
        super(registries);
    }

    @Override
    protected void generate() {

    }

    private void dropStoneSet(StoneRegistries stoneRegistries) {
        dropSelf(stoneRegistries.BLOCK.get());
        dropSelf(stoneRegistries.WALL.get());
        dropSelf(stoneRegistries.STAIRS.get());
        dropSelf(stoneRegistries.SLAB.get());
        dropSelf(stoneRegistries.PRESSURE_PLATE.get());
        dropSelf(stoneRegistries.BUTTON.get());
    }

    private void dropSimpleSelf(BasicBlockSetRegistries blockSetRegistries) {
        dropSelf(blockSetRegistries.getBLOCK().get());
        dropSelf(blockSetRegistries.getSLAB().get());
        dropSelf(blockSetRegistries.getSTAIRS().get());
        dropSelf(blockSetRegistries.getWALL().get());
    }

    protected LootTable.Builder createDoublePlantDrop(Block sheared) {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool().add(LootItem.lootTableItem(sheared).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                );
    }

    private void initFossil() {
        for (Era value : Era.values()) {
            if (value.isHasFossilOre()) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .when(hasSilkTouch().invert())
                        .setRolls(UniformGenerator.between(4.0F, 8.0F))
                        .add(LootItem.lootTableItem(
                                Items.BONE
                        ).setWeight(60))
                        .add(LootItem.lootTableItem(
                                Items.COBBLESTONE
                        ).setWeight(70))
                        .add(LootItem.lootTableItem(
                                Items.FLINT
                        ).setWeight(80));

                value.getRegistryMap().forEach((possibleResult, itemDeferredItem) -> {
                    if(possibleResult != PossibleResult.MARINE_DEPOSIT) {
                        if (!possibleResult.isAmber()) {
                            poolBuilder.add(
                                    LootItem.lootTableItem(itemDeferredItem.get()).setWeight(40)
                                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 2F)))
                                            .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                            );
                        } else {
                            poolBuilder.add(
                                    LootItem.lootTableItem(itemDeferredItem.get()).setWeight(50)
                                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F)))
                                            .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                            );
                        }
                    }
                });

                this.add(
                        value.getFossilOre().get(),
                        LootTable.lootTable().setParamSet(LootContextParamSets.BLOCK)
                                .withPool(
                                        LootPool.lootPool()
                                                .when(hasSilkTouch())
                                                .setRolls(ConstantValue.exactly(1.0F))
                                                .add(LootItem.lootTableItem(value.getFossilOre().get()))
                                )
                                .withPool(
                                        poolBuilder
                                )
                );
            }
            //Todo:
            if (value.isHasMarineOre()) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .when(hasSilkTouch().invert())
                        .setRolls(UniformGenerator.between(4.0F, 8.0F))
                        .add(LootItem.lootTableItem(
                                Items.BONE
                        ).setWeight(60))
                        .add(LootItem.lootTableItem(
                                Items.COBBLESTONE
                        ).setWeight(70))
                        .add(LootItem.lootTableItem(
                                Items.FLINT
                        ).setWeight(80));

                value.getRegistryMap().forEach((possibleResult, itemDeferredItem) -> {
                    if(possibleResult == PossibleResult.MARINE_DEPOSIT) {
                        if (!possibleResult.isAmber()) {
                            poolBuilder.add(
                                    LootItem.lootTableItem(itemDeferredItem.get()).setWeight(40)
                                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 2F)))
                                            .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                            );
                        } else {
                            poolBuilder.add(
                                    LootItem.lootTableItem(itemDeferredItem.get()).setWeight(50)
                                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F)))
                                            .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                            );
                        }
                    }
                });

                this.add(
                        value.getMarineOre().get(),
                        LootTable.lootTable().setParamSet(LootContextParamSets.BLOCK)
                                .withPool(
                                        LootPool.lootPool()
                                                .when(hasSilkTouch())
                                                .setRolls(ConstantValue.exactly(1.0F))
                                                .add(LootItem.lootTableItem(value.getFossilOre().get()))
                                )
                                .withPool(
                                        poolBuilder
                                )
                );
            }
        }
    }

    private void dropColoredSelf(ColoredRegistries c) {
        dropSelf(c.BLACK.get());
        dropSelf(c.BLUE.get());
        dropSelf(c.BROWN.get());
        dropSelf(c.CYAN.get());
        dropSelf(c.GREEN.get());
        dropSelf(c.GREY.get());
        dropSelf(c.LIGHTBLUE.get());
        dropSelf(c.LIGHTGREY.get());
        dropSelf(c.LIME.get());
        dropSelf(c.MAGENTA.get());
        dropSelf(c.ORANGE.get());
        dropSelf(c.PINK.get());
        dropSelf(c.PURPLE.get());
        dropSelf(c.RED.get());
        dropSelf(c.WHITE.get());
        dropSelf(c.YELLOW.get());
    }

    private void dropColoredDoor(ColoredRegistries c) {
        this.add(c.BLACK.get(), createDoorTable(c.BLACK.get()));
        this.add(c.BLUE.get(), createDoorTable(c.BLUE.get()));
        this.add(c.BROWN.get(), createDoorTable(c.BROWN.get()));
        this.add(c.CYAN.get(), createDoorTable(c.CYAN.get()));
        this.add(c.GREEN.get(), createDoorTable(c.GREEN.get()));
        this.add(c.GREY.get(), createDoorTable(c.GREY.get()));
        this.add(c.LIGHTBLUE.get(), createDoorTable(c.LIGHTBLUE.get()));
        this.add(c.LIGHTGREY.get(), createDoorTable(c.LIGHTGREY.get()));
        this.add(c.LIME.get(), createDoorTable(c.LIME.get()));
        this.add(c.MAGENTA.get(), createDoorTable(c.MAGENTA.get()));
        this.add(c.ORANGE.get(), createDoorTable(c.ORANGE.get()));
        this.add(c.PINK.get(), createDoorTable(c.PINK.get()));
        this.add(c.PURPLE.get(), createDoorTable(c.PURPLE.get()));
        this.add(c.RED.get(), createDoorTable(c.RED.get()));
        this.add(c.WHITE.get(), createDoorTable(c.WHITE.get()));
        this.add(c.YELLOW.get(), createDoorTable(c.YELLOW.get()));
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return BuiltInRegistries.BLOCK.entrySet().stream()
                .filter(e -> e.getKey().location().getNamespace().equals(JSExpanded.MOD_ID))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}
