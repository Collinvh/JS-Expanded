package jp.jsexpanded.neo.data.client;


import com.google.common.base.Supplier;
import jp.jsexpanded.JSExpanded;
import jp.jsexpanded.neo.data.JSExpandedData;
import jp.jsexpanded.server.animals.AbstractAddonAnimal;
import jp.jurassicsaga.neo.data.obj.LayerModelDataObject;
import jp.jurassicsaga.neo.data.obj.SignDataObject;
import jp.jurassicsaga.server.base.animal.JSAnimals;
import jp.jurassicsaga.server.base.animal.obj.JSAnimal;
import jp.jurassicsaga.server.base.block.obj.JSBlockStateProperties;
import jp.jurassicsaga.server.base.block.obj.plant.JSTallPlantBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static net.neoforged.neoforge.client.model.generators.ModelProvider.BLOCK_FOLDER;


public class JSExpandedModelProvider {
    public static class Block extends BlockStateProvider {
        public Block(PackOutput output, ExistingFileHelper existingFileHelper) {
            super(output, JSExpanded.MOD_ID, existingFileHelper);
        }

        @Override
        protected void registerStatesAndModels() {
            JSExpandedData.getBLOCKS().forEach((blockRegistryObject, dataObject) -> {
                net.minecraft.world.level.block.Block block = blockRegistryObject.get();
                ResourceLocation location = ResourceLocation.fromNamespaceAndPath(JSExpanded.MOD_ID, BLOCK_FOLDER + "/" + dataObject.getGetFolderPrefix() + dataObject.getPrefix() + (!dataObject.getOverrideName().equals("none") ? dataObject.getOverrideName() : BuiltInRegistries.BLOCK.getKey(block).getPath() + dataObject.getSuffix()));
                switch (dataObject.getModelType()) {
                    case PARTICLE ->
                            getVariantBuilder(block).forAllStatesExcept(blockState -> ConfiguredModel.builder().modelFile(models().getBuilder(name(block)).texture("particle", location)).build());
                    case POTTED ->
                            simpleBlockWithItem(block, models().singleTexture(name(block), ResourceLocation.withDefaultNamespace("flower_pot_cross"), "plant", location).renderType("cutout_mipped").ao(false));
                    case SIMPLE_BLOCK -> simpleBlock(block, models().cubeAll(name(block), location));
                    case SIMPLE_WITH_TOP ->
                            simpleBlock(block, models().cubeTop(name(block), location, location.withSuffix("_top")));
                    case SIMPLE_WITH_TOP_BOTTOM ->
                            simpleBlock(block, models().cubeBottomTop(name(block), location, location.withSuffix("_bottom"), location.withSuffix("_top")));
                    case SIMPLE_WITH_TOP_BOTTOM_SAME_AS_SIDE ->
                            simpleBlock(block, models().cubeBottomTop(name(block), location, location, location.withSuffix("_top")));
                    case PILLAR_BLOCK -> {
                        if (block instanceof RotatedPillarBlock block1) {
                            ModelFile vertical = models().cubeColumn(name(block), location, location.withSuffix("_top"));
                            ModelFile horizontal = models().cubeColumnHorizontal(name(block), location, location.withSuffix("_top"));
                            getVariantBuilder(block)
                                    .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.Y)
                                    .modelForState().modelFile(vertical).addModel()
                                    .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.Z)
                                    .modelForState().modelFile(horizontal).rotationX(90).addModel()
                                    .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.X)
                                    .modelForState().modelFile(horizontal).rotationX(90).rotationY(90).addModel();
                        } else if (block instanceof InfestedRotatedPillarBlock block1) {
                            ModelFile vertical = models().cubeColumn(name(block), location, location.withSuffix("_top"));
                            ModelFile horizontal = models().cubeColumnHorizontal(name(block), location, location.withSuffix("_top"));
                            getVariantBuilder(block)
                                    .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.Y)
                                    .modelForState().modelFile(vertical).addModel()
                                    .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.Z)
                                    .modelForState().modelFile(horizontal).rotationX(90).addModel()
                                    .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.X)
                                    .modelForState().modelFile(horizontal).rotationX(90).rotationY(90).addModel();
                        }
                    }
                    case TRAPDOOR -> trapdoorBlockWithRenderType((TrapDoorBlock) block, location, true, "cutout");
                    case DOOR ->
                            doorBlockWithRenderType((DoorBlock) block, location.withSuffix("_lower"), location.withSuffix("_upper"), "cutout");
                    case ALL_SIDES ->
                            simpleBlock(block, models().cube(name(block), location.withSuffix("_down"), location.withSuffix("_up"), location.withSuffix("_side_1"), location.withSuffix("_side_2"), location.withSuffix("_side_3"), location.withSuffix("_side_4")).texture("particle", location.withSuffix("_side_1")));
                    case IRONBAR -> paneBlock((IronBarsBlock) block, location, location.withSuffix("_frame"));
                    case SIMPLE_TRANSLUCENT_BLOCK ->
                            simpleBlock(block, models().cubeAll(name(block), location).renderType("translucent"));
                    case SIMPLE_TRANSPARENT_BLOCK ->
                            simpleBlock(block, models().cubeAll(name(block), location).renderType("transparent"));
                    case DOOR_TRANSLUCENT ->
                            doorBlockWithRenderType((DoorBlock) block, location.withSuffix("_lower"), location.withSuffix("_upper"), "translucent");
                    case DOOR_TRANSLUCENT_SIDE -> {
                        ModelFile bottomLeft = models()
                                .withExistingParent(name(block) + "_bottom_left", JSExpanded.createId("block/door_bottom_left"))
                                .texture("1", location.withSuffix("_lower")).texture("2", location.withSuffix("_side"))
                                .texture("particle", location.withSuffix("_upper")).renderType("translucent");
                        ModelFile bottomLeftOpen = models()
                                .withExistingParent(name(block) + "_bottom_left_open", JSExpanded.createId("block/door_bottom_left_open"))
                                .texture("1", location.withSuffix("_lower")).texture("2", location.withSuffix("_side"))
                                .texture("particle", location.withSuffix("_upper")).renderType("translucent");
                        ModelFile bottomRight = models()
                                .withExistingParent(name(block) + "_bottom_right", JSExpanded.createId("block/door_bottom_right"))
                                .texture("1", location.withSuffix("_lower")).texture("2", location.withSuffix("_side"))
                                .texture("particle", location.withSuffix("_upper")).renderType("translucent");
                        ModelFile bottomRightOpen = models()
                                .withExistingParent(name(block) + "_bottom_right_open", JSExpanded.createId("block/door_bottom_right_open"))
                                .texture("1", location.withSuffix("_lower")).texture("2", location.withSuffix("_side"))
                                .texture("particle", location.withSuffix("_upper")).renderType("translucent");
                        ModelFile topLeft = models()
                                .withExistingParent(name(block) + "_top_left", JSExpanded.createId("block/door_top_left"))
                                .texture("1", location.withSuffix("_upper")).texture("2", location.withSuffix("_side"))
                                .texture("particle", location.withSuffix("_upper")).renderType("translucent");
                        ModelFile topLeftOpen = models()
                                .withExistingParent(name(block) + "_top_left_open", JSExpanded.createId("block/door_top_left_open"))
                                .texture("1", location.withSuffix("_upper")).texture("2", location.withSuffix("_side"))
                                .texture("particle", location.withSuffix("_upper")).renderType("translucent");
                        ModelFile topRight = models()
                                .withExistingParent(name(block) + "_top_right", JSExpanded.createId("block/door_top_right"))
                                .texture("1", location.withSuffix("_upper")).texture("2", location.withSuffix("_side"))
                                .texture("particle", location.withSuffix("_upper")).renderType("translucent");
                        ModelFile topRightOpen = models()
                                .withExistingParent(name(block) + "_top_right_open", JSExpanded.createId("block/door_top_right_open"))
                                .texture("1", location.withSuffix("_upper")).texture("2", location.withSuffix("_side"))
                                .texture("particle", location.withSuffix("_upper")).renderType("translucent");
                        doorBlock((DoorBlock) block, bottomLeft, bottomLeftOpen, bottomRight, bottomRightOpen, topLeft, topLeftOpen, topRight, topRightOpen);
                    }
                    case TRAPDOOR_TRANSLUCENT ->
                            trapdoorBlockWithRenderType((TrapDoorBlock) block, location, true, "translucent");
                    case IRONBAR_TRANSLUCENT ->
                            paneBlockWithRenderType((IronBarsBlock) block, location, location.withSuffix("_frame"), "translucent");
                    case WALL -> {
                        wallBlock((WallBlock) block, models().wallPost(name(block) + "_post", location), models().wallSide(name(block) + "_side", location), models().wallSideTall(name(block) + "_side_tall", location));
                        models().wallInventory(name(block) + "_inventory", location);
                    }
                    case VANILLA_BENCH -> {
                        var base = models().withExistingParent(name(block), "jurassicsaga:block/bench").texture("plank_texture", ResourceLocation.withDefaultNamespace(location.withSuffix("_planks").getPath()));
                        getVariantBuilder(block).forAllStatesExcept(state -> {
                            return ConfiguredModel.builder()
                                    .modelFile(base)
                                    .rotationY((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot())
                                    .uvLock(false)
                                    .build();
                        });
                    }
                    case BENCH -> {
                        var base = models().withExistingParent(name(block), "jurassicsaga:block/bench").texture("plank_texture", location.withSuffix("_planks"));
                        getVariantBuilder(block).forAllStatesExcept(state -> {
                            return ConfiguredModel.builder()
                                    .modelFile(base)
                                    .rotationY((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot())
                                    .uvLock(false)
                                    .build();
                        });
                    }
                    case CARPET -> {
                        BlockModelBuilder all = models().carpet("block/" + name(block), location).renderType("cutout_mipped");
                        getVariantBuilder(block).forAllStatesExcept(state -> ConfiguredModel.builder()
                                .modelFile(all)
                                .uvLock(false)
                                .build());
                    }
                    case LILY_PAD -> {
                        BlockModelBuilder all = models().singleTexture("plant/" + name(block), ResourceLocation.withDefaultNamespace(BLOCK_FOLDER + "/lily_pad"), location).ao(false).renderType("cutout_mipped");
                        getVariantBuilder(block).forAllStatesExcept(state -> ConfiguredModel.builder()
                                .modelFile(all)
                                .uvLock(true)
                                .build());
                    }
                    case LILY_PAD_VARIANTS_2 -> {
                        BlockModelBuilder all = models().singleTexture("plant/" + name(block), ResourceLocation.withDefaultNamespace(BLOCK_FOLDER + "/lily_pad"), location).ao(false).renderType("cutout_mipped");
                        BlockModelBuilder all_var1 = models().singleTexture("plant/" + name(block) + "_variant", ResourceLocation.withDefaultNamespace(BLOCK_FOLDER + "/lily_pad"), location.withSuffix("_variant")).ao(false).renderType("cutout_mipped");
                        getVariantBuilder(block).forAllStatesExcept(state -> ConfiguredModel.builder()
                                .modelFile(all)
                                .uvLock(true)
                                .nextModel()
                                .modelFile(all_var1)
                                .uvLock(true)
                                .build());
                    }
                    case LILY_PAD_VARIANTS_3 -> {
                        BlockModelBuilder all = models().singleTexture("plant/" + name(block), ResourceLocation.withDefaultNamespace(BLOCK_FOLDER + "/lily_pad"), location).ao(false).renderType("cutout_mipped");
                        BlockModelBuilder all_var1 = models().singleTexture("plant/" + name(block) + "_variant1", ResourceLocation.withDefaultNamespace(BLOCK_FOLDER + "/lily_pad"), location.withSuffix("_variant1")).ao(false).renderType("cutout_mipped");
                        BlockModelBuilder all_var2 = models().singleTexture("plant/" + name(block) + "_variant2", ResourceLocation.withDefaultNamespace(BLOCK_FOLDER + "/lily_pad"), location.withSuffix("_variant2")).ao(false).renderType("cutout_mipped");
                        getVariantBuilder(block).forAllStatesExcept(state -> ConfiguredModel.builder()
                                .modelFile(all)
                                .uvLock(true)
                                .nextModel()
                                .modelFile(all_var1)
                                .uvLock(true)
                                .nextModel()
                                .modelFile(all_var2)
                                .uvLock(true)
                                .build());
                    }
                    case STAIR ->
                            stairsBlock((StairBlock) block, models().stairs(name(block), location, location, location), models().stairsInner(name(block) + "_inner", location, location, location), models().stairsOuter(name(block) + "_outer", location, location, location));
                    case STAIR_TRANSLUCENT ->
                            stairsBlock((StairBlock) block, models().stairs(name(block), location, location, location).renderType("translucent"), models().stairsInner(name(block) + "_inner", location, location, location).renderType("translucent"), models().stairsOuter(name(block) + "_outer", location, location, location).renderType("translucent"));
                    case SLAB ->
                            slabBlock((SlabBlock) block, models().slab(name(block), location, location, location), models().slabTop(name(block) + "_top", location, location, location), models().cubeAll(dataObject.getOverrideName(), location));
                    case SLAB_TRANSLUCENT ->
                            slabBlock((SlabBlock) block, models().slab(name(block), location, location, location).renderType("translucent"), models().slabTop(name(block) + "_top", location, location, location).renderType("translucent"), models().cubeAll(dataObject.getOverrideName(), location).renderType("translucent"));
                    case BUTTON -> {
                        models().buttonInventory(name(block) + "_inventory", location);
                        buttonBlock((ButtonBlock) block, models().button(name(block), location), models().buttonPressed(name(block) + "_pressed", location));
                    }
                    case FENCE -> {
                        fourWayBlock((FenceBlock) block, models().fencePost(name(block) + "_post", location), models().fenceSide(name(block) + "_side", location));
                        models().fenceInventory(name(block) + "_inventory", location);
                    }
                    case LOG_FENCE -> {
                        fourWayBlock((FenceBlock) block, models().withExistingParent(name(block) + "_post", "JSMod:block/log_fence_post").texture("0", location.withSuffix("_log")).texture("1", location.withSuffix("_log_top")), models().withExistingParent(name(block) + "_side", "JSMod:block/log_fence_side").texture("0", location.withSuffix("_log")).texture("1", location.withSuffix("_log_top")));
                        models().withExistingParent(name(block) + "_inventory", "JSMod:block/log_fence_inventory").texture("0", location.withSuffix("_log")).texture("1", location.withSuffix("_log_top"));
                    }
                    case FENCE_GATE ->
                            fenceGateBlock((FenceGateBlock) block, models().fenceGate(name(block), location), models().fenceGateOpen(name(block) + "_open", location), models().fenceGateWall(name(block) + "_wall", location), models().fenceGateWallOpen(name(block) + "_wall_open", location));
                    case LOG_FENCE_GATE -> {
                        var base = models().withExistingParent(name(block), "JSMod:block/log_fence_gate").texture("0", location.withSuffix("_log")).texture("1", location.withSuffix("_log_top")).texture("2", location.withSuffix("_planks"));
                        var baseOpen = models().withExistingParent(name(block) + "_open", "JSMod:block/log_fence_gate_open").texture("0", location.withSuffix("_log")).texture("1", location.withSuffix("_log_top")).texture("2", location.withSuffix("_planks"));
                        getVariantBuilder(block).forAllStatesExcept(state -> {
                            ModelFile model = base;
                            if (state.getValue(FenceGateBlock.OPEN)) {
                                model = baseOpen;
                            }
                            return ConfiguredModel.builder()
                                    .modelFile(model)
                                    .rotationY((int) state.getValue(FenceGateBlock.FACING).toYRot())
                                    .uvLock(false)
                                    .build();
                        }, FenceGateBlock.POWERED);
                    }
                    case SIGN -> {
                        if (dataObject instanceof SignDataObject object) {
                            signBlock((StandingSignBlock) block, (WallSignBlock) object.getBlock().get(), models().sign(name(block), location));
                        }
                    }
                    case HANGING_SIGN -> {
                        if (dataObject instanceof SignDataObject object) {
                            getVariantBuilder(block).forAllStatesExcept(blockState -> ConfiguredModel.builder().modelFile(models().getBuilder(name(block)).texture("particle", location)).build());
                            getVariantBuilder(object.getBlock().get()).forAllStatesExcept(blockState -> ConfiguredModel.builder().modelFile(models().getBuilder(name(block)).texture("particle", location)).build());
                        }
                    }
                    case PRESSURE_PLATE ->
                            pressurePlateBlock((PressurePlateBlock) block, models().pressurePlate(name(block), location), models().pressurePlateDown(name(block) + "_down", location));
                    case PRESSURE_PLATE_TRANSLUCENT ->
                            pressurePlateBlock((PressurePlateBlock) block, models().pressurePlate(name(block), location).renderType("translucent"), models().pressurePlateDown(name(block) + "_down", location).renderType("translucent"));

                    /*
                    Plants
                     */
                    case BASIC_PLANT ->
                            simpleBlock(block, models().cross("plant/" + name(block), location).ao(false).renderType("cutout_mipped"));
                    case COLOR_PLANT ->
                            simpleBlock(block, crossTexture("plant/" + name(block), location).ao(false).renderType("cutout_mipped"));
                    case BASIC_PLANT_DOUBLE -> {
                        BlockModelBuilder top = models().cross("plant/" + name(block) + "_top", location.withSuffix("_top")).ao(false).renderType("cutout_mipped");
                        BlockModelBuilder bottom = models().cross("plant/" + name(block) + "_bottom", location.withSuffix("_bottom")).ao(false).renderType("cutout_mipped");

                        getVariantBuilder(block).forAllStatesExcept(state -> {
                            ModelFile model = bottom;
                            if (state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER) {
                                model = top;
                            }
                            return ConfiguredModel.builder()
                                    .modelFile(model)
                                    .uvLock(true)
                                    .build();
                        });
                    }
                    case COLOR_DOUBLE_PLANT -> {
                        BlockModelBuilder top = crossTexture("plant/" + name(block) + "_top", location.withSuffix("_top")).ao(false).renderType("cutout_mipped");
                        BlockModelBuilder bottom = crossTexture("plant/" + name(block) + "_bottom", location.withSuffix("_bottom")).ao(false).renderType("cutout_mipped");

                        getVariantBuilder(block).forAllStatesExcept(state -> {
                            ModelFile model = bottom;
                            if (state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER) {
                                model = top;
                            }
                            return ConfiguredModel.builder()
                                    .modelFile(model)
                                    .uvLock(true)
                                    .build();
                        });
                    }
                    case SEGMENTED -> {
                        if (block instanceof JSTallPlantBlock segmentedPart) {
                            int maxSize = segmentedPart.getWantedSegmentCount();
                            BlockModelBuilder segment_1 = models().cross("plant/" + name(block) + "_segment1", location.withSuffix("_segment1")).ao(false).renderType("cutout_mipped");
                            BlockModelBuilder segment_2 = models().cross("plant/" + name(block) + "_segment2", location.withSuffix("_segment2")).ao(false).renderType("cutout_mipped");
                            BlockModelBuilder segment_3 = models().cross("plant/" + name(block) + "_segment3", location.withSuffix("_segment3")).ao(false).renderType("cutout_mipped");
                            BlockModelBuilder segment_4 = maxSize >= 4 ? models().cross("plant/" + name(block) + "_segment4", location.withSuffix("_segment4")).ao(false).renderType("cutout_mipped") : null;
                            BlockModelBuilder segment_5 = maxSize >= 5 ? models().cross("plant/" + name(block) + "_segment5", location.withSuffix("_segment5")).ao(false).renderType("cutout_mipped") : null;
                            BlockModelBuilder segment_6 = maxSize >= 6 ? models().cross("plant/" + name(block) + "_segment6", location.withSuffix("_segment6")).ao(false).renderType("cutout_mipped") : null;
                            BlockModelBuilder segment_7 = maxSize >= 7 ? models().cross("plant/" + name(block) + "_segment7", location.withSuffix("_segment7")).ao(false).renderType("cutout_mipped") : null;
                            BlockModelBuilder segment_8 = maxSize >= 8 ? models().cross("plant/" + name(block) + "_segment8", location.withSuffix("_segment8")).ao(false).renderType("cutout_mipped") : null;
                            getVariantBuilder(block).forAllStatesExcept(state -> {
                                int curSize = state.getValue(JSBlockStateProperties.SEGMENTED).getOffset() + 1;
                                if (curSize <= maxSize) {
                                    BlockModelBuilder curModel;
                                    switch (state.getValue(JSBlockStateProperties.SEGMENTED)) {
                                        case SEGMENT_1 -> curModel = segment_1;
                                        case SEGMENT_2 -> curModel = segment_2;
                                        case SEGMENT_3 -> curModel = segment_3;
                                        case SEGMENT_4 -> curModel = segment_4;
                                        case SEGMENT_5 -> curModel = segment_5;
                                        case SEGMENT_6 -> curModel = segment_6;
                                        case SEGMENT_7 -> curModel = segment_7;
                                        default -> curModel = segment_8;
                                    }
                                    return ConfiguredModel.builder()
                                            .modelFile(curModel)
                                            .uvLock(true)
                                            .build();
                                } else {
                                    return ConfiguredModel.builder()
                                            .modelFile(segment_1)
                                            .uvLock(true)
                                            .build();
                                }
                            });
                        }
                    }
                    case SEGMENTED_COLOR -> {
                        if (block instanceof JSTallPlantBlock segmentedPart) {
                            int maxSize = segmentedPart.getWantedSegmentCount();
                            BlockModelBuilder segment_1 = crossTextureTall("plant/" + name(block) + "_segment1", location.withSuffix("_segment1")).ao(false).renderType("cutout_mipped");
                            BlockModelBuilder segment_2 = crossTextureTall("plant/" + name(block) + "_segment2", location.withSuffix("_segment2")).ao(false).renderType("cutout_mipped");
                            BlockModelBuilder segment_3 = crossTextureTall("plant/" + name(block) + "_segment3", location.withSuffix("_segment3")).ao(false).renderType("cutout_mipped");
                            BlockModelBuilder segment_4 = maxSize >= 4 ? crossTextureTall("plant/" + name(block) + "_segment4", location.withSuffix("_segment4")).ao(false).renderType("cutout_mipped") : null;
                            BlockModelBuilder segment_5 = maxSize >= 5 ? crossTextureTall("plant/" + name(block) + "_segment5", location.withSuffix("_segment5")).ao(false).renderType("cutout_mipped") : null;
                            BlockModelBuilder segment_6 = maxSize >= 6 ? crossTextureTall("plant/" + name(block) + "_segment6", location.withSuffix("_segment6")).ao(false).renderType("cutout_mipped") : null;
                            BlockModelBuilder segment_7 = maxSize >= 7 ? crossTextureTall("plant/" + name(block) + "_segment7", location.withSuffix("_segment7")).ao(false).renderType("cutout_mipped") : null;
                            BlockModelBuilder segment_8 = maxSize >= 8 ? crossTextureTall("plant/" + name(block) + "_segment8", location.withSuffix("_segment8")).ao(false).renderType("cutout_mipped") : null;
                            getVariantBuilder(block).forAllStatesExcept(state -> {
                                int curSize = state.getValue(JSBlockStateProperties.SEGMENTED).getOffset() + 1;
                                if (curSize <= maxSize) {
                                    BlockModelBuilder curModel;
                                    switch (state.getValue(JSBlockStateProperties.SEGMENTED)) {
                                        case SEGMENT_1 -> curModel = segment_1;
                                        case SEGMENT_2 -> curModel = segment_2;
                                        case SEGMENT_3 -> curModel = segment_3;
                                        case SEGMENT_4 -> curModel = segment_4;
                                        case SEGMENT_5 -> curModel = segment_5;
                                        case SEGMENT_6 -> curModel = segment_6;
                                        case SEGMENT_7 -> curModel = segment_7;
                                        default -> curModel = segment_8;
                                    }
                                    return ConfiguredModel.builder()
                                            .modelFile(curModel)
                                            .uvLock(true)
                                            .build();
                                } else {
                                    return ConfiguredModel.builder()
                                            .modelFile(segment_1)
                                            .uvLock(true)
                                            .build();
                                }
                            });
                        }
                    }
                    case ANCIENT_BUSH ->
                            simpleBlock(block, models().cross("plant/bush/" + name(block), location).ao(false).renderType("cutout_mipped"));
                    case BASIC_AQUATIC_PLANT, CORAL -> {
                        BlockModelBuilder model = models().cross("plant/" + name(block), location).ao(false).renderType("cutout_mipped");
                        getVariantBuilder(block).forAllStatesExcept(state -> ConfiguredModel.builder()
                                .modelFile(model)
                                .uvLock(true)
                                .build());
                    }
                    case CORAL_FAN -> {
                        BlockModelBuilder model = models().withExistingParent("plant/" + name(block), "coral_fan").texture("fan", location).ao(false).renderType("cutout_mipped");
                        getVariantBuilder(block).forAllStatesExcept(state -> ConfiguredModel.builder()
                                .modelFile(model)
                                .uvLock(false)
                                .build());
                    }
                    case CORAL_FAN_WALL -> getVariantBuilder(block).forAllStatesExcept(state -> {
                        BlockModelBuilder model = models().withExistingParent("plant/" + name(block), "coral_wall_fan").texture("fan", location).ao(false).renderType("cutout_mipped");
                        Direction dir = state.getValue(BaseCoralWallFanBlock.FACING);
                        if (dir == Direction.NORTH) {
                            return ConfiguredModel.builder()
                                    .modelFile(model)
                                    .uvLock(true)
                                    .build();
                        } else {
                            int y = 0;
                            switch (dir) {
                                case EAST -> y = 90;
                                case SOUTH -> y = 180;
                                case WEST -> y = 270;
                            }

                            return ConfiguredModel.builder()
                                    .modelFile(model)
                                    .rotationY(y)
                                    .build();
                        }
                    });
                }
            });
        }

        private String name(net.minecraft.world.level.block.Block block) {
            return BuiltInRegistries.BLOCK.getKey(block).getPath();
        }

        public BlockModelBuilder crossTexture(String name, ResourceLocation cross) {
            return models()
                    .withExistingParent(name, JSExpanded.createId("plant/layered_cross"))
                    .texture("flower", cross.withSuffix("_colour"))
                    .texture("stem", cross.withSuffix("_greyscale"));
        }

        public BlockModelBuilder crossTextureTall(String name, ResourceLocation cross) {
            return models()
                    .withExistingParent(name, JSExpanded.createId("plant/layered_cross"))
                    .texture("flower", cross.withSuffix("_colour_layer"))
                    .texture("stem", cross.withSuffix("_greyscale"));
        }
    }

    public static class Item extends ItemModelProvider {
        public Item(PackOutput output, ExistingFileHelper existingFileHelper) {
            super(output, JSExpanded.MOD_ID, existingFileHelper);
        }

        @Override
        protected void registerModels() {
            JSExpandedData.getBLOCKS().forEach((blockRegistryObject, translationObject) -> {
                ResourceLocation location = ResourceLocation.fromNamespaceAndPath(JSExpanded.MOD_ID, translationObject.getGetFolderPrefix() + translationObject.getPrefix() + BuiltInRegistries.BLOCK.getKey(blockRegistryObject.get()).getPath() + translationObject.getSuffix());
                switch (translationObject.getItemModelType()) {
                    case PARENT -> createParent(blockRegistryObject);
                    case SPAWN_EGG ->
                            getBuilder(BuiltInRegistries.ITEM.getKey(blockRegistryObject.get().asItem()).toString())
                                    .parent(new ModelFile.UncheckedModelFile("item/template_spawn_egg"));
                    case INVENTORY_MODEL -> createSuffixedParent(blockRegistryObject, "_inventory");
                    case INVENTORY_MODEL_TRANSPERNT ->
                            createTransparentSuffixedParent(blockRegistryObject, "_inventory");
                    case TRAPDOOR -> createTransparentSuffixedParent(blockRegistryObject, "_bottom");

                    case SIMPLE_ITEM ->
                            getBuilder(BuiltInRegistries.ITEM.getKey(blockRegistryObject.get().asItem()).toString())
                                    .parent(new ModelFile.UncheckedModelFile("item/generated"))
                                    .texture("layer0", ResourceLocation.fromNamespaceAndPath(location.getNamespace(), "item/" + location.getPath()));
                    case TRANSLUCENT_ITEM ->
                            getBuilder(BuiltInRegistries.ITEM.getKey(blockRegistryObject.get().asItem()).toString())
                                    .parent(new ModelFile.UncheckedModelFile("item/generated"))
                                    .texture("layer0", ResourceLocation.fromNamespaceAndPath(location.getNamespace(), "item/" + location.getPath())).renderType("translucent");
                    case BLOCK_TEXTURE_AS_TEXTURE ->
                            getBuilder(BuiltInRegistries.ITEM.getKey(blockRegistryObject.get().asItem()).toString())
                                    .parent(new ModelFile.UncheckedModelFile("item/generated"))
                                    .texture("layer0", ResourceLocation.fromNamespaceAndPath(location.getNamespace(), "block/" + location.getPath())).renderType("translucent");
                    case DOUBLE_PLANT ->
                            getBuilder(BuiltInRegistries.ITEM.getKey(blockRegistryObject.get().asItem()).toString())
                                    .parent(new ModelFile.UncheckedModelFile("item/generated"))
                                    .texture("layer0", ResourceLocation.fromNamespaceAndPath(location.getNamespace(), "block/" + location.getPath() + "_top")).renderType("translucent");
                    case SEGMENTED ->
                            getBuilder(BuiltInRegistries.ITEM.getKey(blockRegistryObject.get().asItem()).toString())
                                    .parent(new ModelFile.UncheckedModelFile("item/generated"))
                                    .texture("layer0", ResourceLocation.fromNamespaceAndPath(location.getNamespace(), "block/" + location.getPath() + "_segment1")).renderType("translucent");
                }
            });
            JSExpandedData.getITEMS().forEach((itemDeferredItem, translationObject) -> {
                if(itemDeferredItem != null) {
                    ResourceLocation location = ResourceLocation.fromNamespaceAndPath(JSExpanded.MOD_ID, translationObject.getGetFolderPrefix() + translationObject.getPrefix() + (!(translationObject.getOverrideName().equals("none")) ? translationObject.getOverrideName() : BuiltInRegistries.ITEM.getKey(itemDeferredItem.get()).getPath()) + translationObject.getSuffix());
                    switch (translationObject.getType()) {
                        case EMPTY -> {
                        }
                        case COIN -> {
                            if (translationObject instanceof LayerModelDataObject layerModelDataObject) {
                                getBuilder(BuiltInRegistries.ITEM.getKey(itemDeferredItem.get()).toString())
                                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                                        .texture("layer0", ResourceLocation.fromNamespaceAndPath(location.getNamespace(), "item/" + translationObject.getGetFolderPrefix() + translationObject.getPrefix() + translationObject.getSuffix()))
                                        .texture("layer1", ResourceLocation.fromNamespaceAndPath(location.getNamespace(), "item/" + layerModelDataObject.getLayerPath(0)));
                            }
                        }
                        case SPAWN_EGG -> getBuilder(BuiltInRegistries.ITEM.getKey(itemDeferredItem.get()).toString())
                                .parent(new ModelFile.UncheckedModelFile("item/template_spawn_egg"));
                        default -> getBuilder(BuiltInRegistries.ITEM.getKey(itemDeferredItem.get()).toString())
                                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                                .texture("layer0", ResourceLocation.fromNamespaceAndPath(location.getNamespace(), "item/" + location.getPath()));
                    }
                }
            });

            for (JSAnimal<?> value : JSAnimals.getAnimals()) {
                if(value instanceof AbstractAddonAnimal<?>) {
                    if (value.getAnimalAttributes().getMiscProperties().isExtinct()) {
                        getBuilder("jurassicsaga:signs/paddock/" + value.getAnimalAttributes().getAnimalName()).parent(new ModelFile.UncheckedModelFile("item/generated"))
                                .texture("layer0", "block/manmade/other/paddock_sign/" + value.getAnimalAttributes().getAnimalName());
                    }
                }
            }
        }

        private void createParent(Supplier<net.minecraft.world.level.block.Block> handler) {
            var path = BuiltInRegistries.BLOCK.getKey(handler.get()).getPath();
            withExistingParent(path, modLoc( "block/" + path));
        }

        private void createSuffixedParent(Supplier<net.minecraft.world.level.block.Block> handler, String suffix) {
            var path = BuiltInRegistries.BLOCK.getKey(handler.get()).getPath();
            withExistingParent(path, modLoc( "block/" + path + suffix));
        }

        private void createTransparentSuffixedParent(Supplier<net.minecraft.world.level.block.Block> handler, String suffix) {
            var path = BuiltInRegistries.BLOCK.getKey(handler.get()).getPath();
            withExistingParent(path, modLoc( "block/" + path + suffix)).renderType("translucent");
        }
    }
}
