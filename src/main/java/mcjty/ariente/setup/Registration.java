package mcjty.ariente.setup;

import mcjty.ariente.Ariente;
import mcjty.ariente.ModCrafting;
import mcjty.ariente.api.ArmorUpgradeType;
import mcjty.ariente.api.MarbleColor;
import mcjty.ariente.api.MarbleType;
import mcjty.ariente.api.TechType;
import mcjty.ariente.blocks.BaseOreBlock;
import mcjty.ariente.blocks.aicore.AICoreTile;
import mcjty.ariente.blocks.decorative.*;
import mcjty.ariente.blocks.defense.ForceFieldTile;
import mcjty.ariente.blocks.generators.*;
import mcjty.ariente.blocks.utility.*;
import mcjty.ariente.blocks.utility.autofield.*;
import mcjty.ariente.blocks.utility.door.DoorMarkerTile;
import mcjty.ariente.blocks.utility.door.InvisibleDoorTile;
import mcjty.ariente.blocks.utility.wireless.SignalReceiverTile;
import mcjty.ariente.blocks.utility.wireless.SignalTransmitterTile;
import mcjty.ariente.blocks.utility.wireless.WirelessButtonTile;
import mcjty.ariente.blocks.utility.wireless.WirelessLockTile;
import mcjty.ariente.cables.ConnectorBlock;
import mcjty.ariente.cables.ConnectorTileEntity;
import mcjty.ariente.cables.NetCableBlock;
import mcjty.ariente.cables.NetCableTileEntity;
import mcjty.ariente.entities.EntityArientePearl;
import mcjty.ariente.entities.LaserEntity;
import mcjty.ariente.entities.drone.DroneEntity;
import mcjty.ariente.entities.drone.SentinelDroneEntity;
import mcjty.ariente.entities.fluxelevator.FluxElevatorEntity;
import mcjty.ariente.entities.fluxship.FluxShipEntity;
import mcjty.ariente.entities.levitator.FluxLevitatorEntity;
import mcjty.ariente.entities.soldier.MasterSoldierEntity;
import mcjty.ariente.entities.soldier.SoldierEntity;
import mcjty.ariente.facade.FacadeBlock;
import mcjty.ariente.facade.FacadeItemBlock;
import mcjty.ariente.items.*;
import mcjty.ariente.items.armor.PowerSuit;
import mcjty.ariente.items.modules.ArmorModuleItem;
import mcjty.lib.blocks.BaseBlock;
import mcjty.lib.blocks.BlockStateItem;
import mcjty.lib.blocks.RotationType;
import mcjty.lib.builder.BlockBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static mcjty.ariente.Ariente.MODID;
import static mcjty.ariente.compat.ArienteTOPDriver.DRIVER;

public class Registration {

    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<TileEntityType<?>> TILES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, MODID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = new DeferredRegister<>(ForgeRegistries.CONTAINERS, MODID);
    public static final DeferredRegister<SoundEvent> SOUNDS = new DeferredRegister<>(ForgeRegistries.SOUND_EVENTS, MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = new DeferredRegister<>(ForgeRegistries.ENTITIES, MODID);

    public static void register() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final Tag<Item> TAG_INGOT_SILVER = new ItemTags.Wrapper(new ResourceLocation("forge", "ingots/silver"));
    public static final Tag<Item> TAG_INGOT_PLATINUM = new ItemTags.Wrapper(new ResourceLocation("forge", "ingots/platinum"));
    public static final Tag<Item> TAG_INGOT_LITHIUM = new ItemTags.Wrapper(new ResourceLocation("forge", "ingots/lithium"));
    public static final Tag<Item> TAG_INGOT_MANGANESE = new ItemTags.Wrapper(new ResourceLocation("forge", "ingots/manganese"));
    public static final Tag<Item> TAG_DUSTS_SILICON = new ItemTags.Wrapper(new ResourceLocation("forge", "dusts/silicon"));
    public static final Tag<Item> TAG_DUSTS_NEGARITE = new ItemTags.Wrapper(new ResourceLocation("forge", "dusts/negarite"));
    public static final Tag<Item> TAG_DUSTS_POSIRITE = new ItemTags.Wrapper(new ResourceLocation("forge", "dusts/posirite"));
    public static final Tag<Block> TAG_ORE_SILVER = new BlockTags.Wrapper(new ResourceLocation("forge", "ores/silver"));
    public static final Tag<Block> TAG_ORE_PLATINUM = new BlockTags.Wrapper(new ResourceLocation("forge", "ores/platinum"));
    public static final Tag<Block> TAG_ORE_LITHIUM = new BlockTags.Wrapper(new ResourceLocation("forge", "ores/lithium"));
    public static final Tag<Block> TAG_ORE_MANGANESE = new BlockTags.Wrapper(new ResourceLocation("forge", "ores/manganese"));
    public static final Tag<Block> TAG_ORE_SILICON = new BlockTags.Wrapper(new ResourceLocation("forge", "ores/silicon"));
    public static final Tag<Block> TAG_ORE_NEGARITE = new BlockTags.Wrapper(new ResourceLocation("forge", "ores/negarite"));
    public static final Tag<Block> TAG_ORE_POSIRITE = new BlockTags.Wrapper(new ResourceLocation("forge", "ores/posirite"));
    public static final Tag<Item> TAG_MARBLE = new ItemTags.Wrapper(new ResourceLocation("forge", "marble"));

    public static final AxisAlignedBB FLAT_BLOCK_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.1D, 1.0D);
    public static final AxisAlignedBB BEAM_BLOCK_NS_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.3D, 1.0D, 0.3D, 0.7D);
    public static final AxisAlignedBB BEAM_BLOCK_EW_AABB = new AxisAlignedBB(0.3D, 0.0D, 0.0D, 0.7D, 0.3D, 1.0D);

    public static final AxisAlignedBB LIGHT_BLOCK_DOWN = new AxisAlignedBB(0.125F, 0.0F, 0.125F, 0.875F, 0.125F, 0.875F);
    public static final AxisAlignedBB LIGHT_BLOCK_UP = new AxisAlignedBB(0.125F, 0.875F, 0.125F, 0.875F, 1.0F, 0.875F);
    public static final AxisAlignedBB LIGHT_BLOCK_NORTH = new AxisAlignedBB(0.125F, 0.125F, 0.0F, 0.875F, 0.875F, 0.125F);
    public static final AxisAlignedBB LIGHT_BLOCK_SOUTH = new AxisAlignedBB(0.125F, 0.125F, 0.875F, 0.875F, 0.8750F, 1.0F);
    public static final AxisAlignedBB LIGHT_BLOCK_WEST = new AxisAlignedBB(0.0F, 0.125F, 0.125F, 0.125F, 0.875F, 0.8750F);
    public static final AxisAlignedBB LIGHT_BLOCK_EAST = new AxisAlignedBB(0.875F, 0.125F, 0.125F, 1.0F, 0.875F, 0.8750F);

    public static final RegistryObject<BlackTechBlock> BLACK_TECH = BLOCKS.register("blacktech", BlackTechBlock::new);
    public static final Map<TechType, RegistryObject<BlockStateItem>> BLACK_TECH_ITEMS = Arrays.stream(TechType.values())
            .map(type -> Pair.of(type, ITEMS.register("blacktech_" + type.getName(), () -> new BlockStateItem(BLACK_TECH.get().getDefaultState().with(TechType.TYPE, type), createStandardProperties()))))
            .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

    public static final RegistryObject<MarbleTechBlock> MARBLE_TECH = BLOCKS.register("marbletech", MarbleTechBlock::new);
    public static final Map<MarbleType, RegistryObject<BlockStateItem>> MARBLE_TECH_ITEMS = Arrays.stream(MarbleType.values())
            .map(type -> Pair.of(type, ITEMS.register("marbletech_" + type.getName(), () -> new BlockStateItem(MARBLE_TECH.get().getDefaultState().with(MarbleType.TYPE, type), createStandardProperties()))))
            .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

    public static final RegistryObject<PatternBlock> PATTERN = BLOCKS.register("pattern", PatternBlock::new);
    public static final Map<PatternType, RegistryObject<BlockStateItem>> PATTERN_ITEMS = Arrays.stream(PatternType.values())
            .map(type -> Pair.of(type, ITEMS.register("pattern_" + type.getName(), () -> new BlockStateItem(PATTERN.get().getDefaultState().with(PatternBlock.TYPE, type), createStandardProperties()))))
            .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

    public static final RegistryObject<MarbleBlock> MARBLE = BLOCKS.register("marble", MarbleBlock::new);
    public static final Map<MarbleColor, RegistryObject<BlockStateItem>> MARBLE_ITEMS = Arrays.stream(MarbleColor.values())
            .map(type -> Pair.of(type, ITEMS.register("marble_" + type.getName(), () -> new BlockStateItem(MARBLE.get().getDefaultState().with(MarbleColor.COLOR, type), createStandardProperties()))))
            .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

    public static final RegistryObject<MarbleBlock> MARBLE_SMOOTH = BLOCKS.register("marble_smooth", MarbleBlock::new);
    public static final Map<MarbleColor, RegistryObject<BlockStateItem>> MARBLE_SMOOTH_ITEMS = Arrays.stream(MarbleColor.values())
            .map(type -> Pair.of(type, ITEMS.register("marble_smooth_" + type.getName(), () -> new BlockStateItem(MARBLE_SMOOTH.get().getDefaultState().with(MarbleColor.COLOR, type), createStandardProperties()))))
            .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

    public static final RegistryObject<MarbleBlock> MARBLE_PILAR = BLOCKS.register("marble_pilar", MarbleBlock::new);
    public static final Map<MarbleColor, RegistryObject<BlockStateItem>> MARBLE_PILAR_ITEMS = Arrays.stream(MarbleColor.values())
            .map(type -> Pair.of(type, ITEMS.register("marble_pilar_" + type.getName(), () -> new BlockStateItem(MARBLE_PILAR.get().getDefaultState().with(MarbleColor.COLOR, type), createStandardProperties()))))
            .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

    public static final RegistryObject<MarbleBlock> MARBLE_BRICKS = BLOCKS.register("marble_bricks", MarbleBlock::new);
    public static final Map<MarbleColor, RegistryObject<BlockStateItem>> MARBLE_BRICKS_ITEMS = Arrays.stream(MarbleColor.values())
            .map(type -> Pair.of(type, ITEMS.register("marble_bricks_" + type.getName(), () -> new BlockStateItem(MARBLE_BRICKS.get().getDefaultState().with(MarbleColor.COLOR, type), createStandardProperties()))))
            .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

    public static final RegistryObject<MarbleSlabBlock> MARBLE_SLAB = BLOCKS.register("marble_slab", MarbleSlabBlock::new);
    public static final Map<MarbleColor, RegistryObject<BlockStateItem>> MARBLE_SLAB_ITEMS = Arrays.stream(MarbleColor.values())
            .map(type -> Pair.of(type, ITEMS.register("marble_slab_" + type.getName(), () -> new BlockStateItem(MARBLE_SLAB.get().getDefaultState().with(MarbleColor.COLOR, type), createStandardProperties()))))
            .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

    public static final RegistryObject<BaseBlock> ORE_LITHIUM = BLOCKS.register("lithiumore", BaseOreBlock::new);
    public static final RegistryObject<Item> ORE_LITHIUM_ITEM = ITEMS.register("lithiumore", () -> new BlockItem(ORE_LITHIUM.get(), createStandardProperties()));

    public static final RegistryObject<BaseBlock> ORE_MANGANESE = BLOCKS.register("manganeseore", BaseOreBlock::new);
    public static final RegistryObject<Item> ORE_MANGANESE_ITEM = ITEMS.register("manganeseore", () -> new BlockItem(ORE_MANGANESE.get(), createStandardProperties()));

    public static final RegistryObject<BaseBlock> ORE_SILICON = BLOCKS.register("siliconore", BaseOreBlock::new);
    public static final RegistryObject<Item> ORE_SILICON_ITEM = ITEMS.register("siliconore", () -> new BlockItem(ORE_SILICON.get(), createStandardProperties()));

    public static final RegistryObject<BaseBlock> ORE_SILVER = BLOCKS.register("silverore", BaseOreBlock::new);
    public static final RegistryObject<Item> ORE_SILVER_ITEM = ITEMS.register("silverore", () -> new BlockItem(ORE_SILVER.get(), createStandardProperties()));

    public static final RegistryObject<BaseBlock> ORE_PLATINUM = BLOCKS.register("platinumore", BaseOreBlock::new);
    public static final RegistryObject<Item> ORE_PLATINUM_ITEM = ITEMS.register("platinumore", () -> new BlockItem(ORE_PLATINUM.get(), createStandardProperties()));

    public static final RegistryObject<BaseBlock> ORE_POSIRITE = BLOCKS.register("posirite", BaseOreBlock::new);
    public static final RegistryObject<Item> ORE_POSIRITE_ITEM = ITEMS.register("posirite", () -> new BlockItem(ORE_POSIRITE.get(), createStandardProperties()));

    public static final RegistryObject<BaseBlock> ORE_NEGARITE = BLOCKS.register("negarite", BaseOreBlock::new);
    public static final RegistryObject<Item> ORE_NEGARITE_ITEM = ITEMS.register("negarite", () -> new BlockItem(ORE_NEGARITE.get(), createStandardProperties()));

    public static final RegistryObject<BaseBlock> FLUX_BEAM = BLOCKS.register("flux_beam", () -> new BaseBeamBlock(new BlockBuilder()));
    public static final RegistryObject<Item> FLUX_BEAM_ITEM = ITEMS.register("flux_beam", () -> new BlockItem(FLUX_BEAM.get(), createStandardProperties()));

    public static final RegistryObject<BaseBlock> FLUX_BEND_BEAM = BLOCKS.register("flux_bend_beam", () -> new BaseBeamBlock(new BlockBuilder()));
    public static final RegistryObject<Item> FLUX_BEND_BEAM_ITEM = ITEMS.register("flux_bend_beam", () -> new BlockItem(FLUX_BEND_BEAM.get(), createStandardProperties()));

    public static final RegistryObject<BaseBlock> SENSOR_ITEM_NODE = BLOCKS.register("sensor_item_node", SensorItemNodeTile::createBlock);
    public static final RegistryObject<Item> SENSOR_ITEM_NODE_ITEM = ITEMS.register("sensor_item_node", () -> new BlockItem(SENSOR_ITEM_NODE.get(), createStandardProperties()));
    public static final RegistryObject<TileEntityType<SensorItemNodeTile>> SENSOR_ITEM_TILE = TILES.register("sensor_item_node", () -> TileEntityType.Builder.create(SensorItemNodeTile::new, SENSOR_ITEM_NODE.get()).build(null));

    public static final RegistryObject<BaseBlock> INPUT_ITEM_NODE = BLOCKS.register("input_item_node", InputItemNodeTile::createBlock);
    public static final RegistryObject<Item> INPUT_ITEM_NODE_ITEM = ITEMS.register("input_item_node", () -> new BlockItem(INPUT_ITEM_NODE.get(), createStandardProperties()));
    public static final RegistryObject<TileEntityType<InputItemNodeTile>> INPUT_ITEM_TILE = TILES.register("input_item_node", () -> TileEntityType.Builder.create(InputItemNodeTile::new, INPUT_ITEM_NODE.get()).build(null));

    public static final RegistryObject<BaseBlock> OUTPUT_ITEM_NODE = BLOCKS.register("output_item_node", () -> new BaseNodeBlock(new BlockBuilder()
            .tileEntitySupplier(OutputItemNodeTile::new)));
    public static final RegistryObject<Item> OUTPUT_ITEM_NODE_ITEM = ITEMS.register("output_item_node", () -> new BlockItem(OUTPUT_ITEM_NODE.get(), createStandardProperties()));
    public static final RegistryObject<TileEntityType<OutputItemNodeTile>> OUTPUT_ITEM_TILE = TILES.register("output_item_node", () -> TileEntityType.Builder.create(OutputItemNodeTile::new, OUTPUT_ITEM_NODE.get()).build(null));

    public static final RegistryObject<BaseBlock> ROUND_ROBIN_NODE = BLOCKS.register("round_robin_node", () -> new BaseNodeBlock(new BlockBuilder()
            .topDriver(DRIVER)
            .tileEntitySupplier(RoundRobinNodeTile::new)));
    public static final RegistryObject<Item> ROUND_ROBIN_NODE_ITEM = ITEMS.register("round_robin_node", () -> new BlockItem(ROUND_ROBIN_NODE.get(), createStandardProperties()));
    public static final RegistryObject<TileEntityType<RoundRobinNodeTile>> ROUND_ROBIN_TILE = TILES.register("round_robin_node", () -> TileEntityType.Builder.create(RoundRobinNodeTile::new, ROUND_ROBIN_NODE.get()).build(null));

    public static final RegistryObject<BaseBlock> FIELD_MARKER = BLOCKS.register("field_marker", FieldMarkerTile::createBlock);
    public static final RegistryObject<Item> FIELD_MARKER_ITEM = ITEMS.register("field_marker", () -> new BlockItem(FIELD_MARKER.get(), createStandardProperties()));
    public static final RegistryObject<TileEntityType<FieldMarkerTile>> FIELD_MARKER_TILE = TILES.register("field_marker", () -> TileEntityType.Builder.create(FieldMarkerTile::new, ROUND_ROBIN_NODE.get()).build(null));

    public static final RegistryObject<RampBlock> RAMP = BLOCKS.register("ramp", RampBlock::new);
    public static final RegistryObject<Item> RAMP_ITEM = ITEMS.register("ramp", () -> new BlockItem(RAMP.get(), createStandardProperties()));

    // @todo slope model!
    public static final RegistryObject<SlopeBlock> SLOPE = BLOCKS.register("slope", SlopeBlock::new);
    public static final RegistryObject<Item> SLOPE_ITEM = ITEMS.register("slope", () -> new BlockItem(SLOPE.get(), createStandardProperties()));

    public static final RegistryObject<PaneBlock> GLASS_FENCE = BLOCKS.register("glass_fence", () -> new PaneBlock(Material.GLASS, SoundType.GLASS));
    public static final RegistryObject<Item> GLASS_FENCE_ITEM = ITEMS.register("glass_fence", () -> new BlockItem(GLASS_FENCE.get(), createStandardProperties()));

    public static final RegistryObject<PaneBlock> BLUE_GLASS_FENCE = BLOCKS.register("blue_glass_fence", () -> new PaneBlock(Material.GLASS, SoundType.GLASS));
    public static final RegistryObject<Item> BLUE_GLASS_FENCE_ITEM = ITEMS.register("blue_glass_fence", () -> new BlockItem(BLUE_GLASS_FENCE.get(), createStandardProperties()));

    public static final RegistryObject<PaneBlock> MARBLE_FENCE = BLOCKS.register("marble_fence", () -> new PaneBlock(Material.ROCK, SoundType.STONE));
    public static final RegistryObject<Item> MARBLE_FENCE_ITEM = ITEMS.register("marble_fence", () -> new BlockItem(MARBLE_FENCE.get(), createStandardProperties()));

    public static final RegistryObject<PaneBlock> TECH_FENCE = BLOCKS.register("tech_fence", () -> new PaneBlock(Material.ROCK, SoundType.STONE));
    public static final RegistryObject<Item> TECH_FENCE_ITEM = ITEMS.register("tech_fence", () -> new BlockItem(TECH_FENCE.get(), createStandardProperties()));

    public static final RegistryObject<BaseBlock> REINFORCED_MARBLE = BLOCKS.register("reinforced_marble", () -> new BaseBlock(new BlockBuilder()
            .properties(Block.Properties.create(Material.ROCK).hardnessAndResistance(80.0f, 3000.0f))
    ) {
        @Override
        public RotationType getRotationType() {
            return RotationType.NONE;
        }
    });
    public static final RegistryObject<Item> REINFORCED_MARBLE_ITEM = ITEMS.register("reinforced_marble", () -> new BlockItem(REINFORCED_MARBLE.get(), createStandardProperties()));

    public static final RegistryObject<BaseBlock> FLUX_GLOW = BLOCKS.register("fluxglow", () -> new BaseBlock(new BlockBuilder()
        .properties(Block.Properties.create(Material.GLASS).lightValue(15))) {
        @Override
        public RotationType getRotationType() {
            return RotationType.NONE;
        }
    });
    public static final RegistryObject<Item> FLUX_GLOW_ITEM = ITEMS.register("fluxglow", () -> new BlockItem(FLUX_GLOW.get(), createStandardProperties()));

    public static final RegistryObject<BaseBlock> POWER_COMBINER = BLOCKS.register("power_combiner", PowerCombinerTile::createBlock);
    public static final RegistryObject<Item> POWER_COMBINER_ITEM = ITEMS.register("power_combiner", () -> new BlockItem(POWER_COMBINER.get(), createStandardProperties()));
    public static final RegistryObject<TileEntityType<PowerCombinerTile>> POWER_COMBINER_TILE = TILES.register("power_combiner", () -> TileEntityType.Builder.create(PowerCombinerTile::new, POWER_COMBINER.get()).build(null));

    public static final RegistryObject<BaseBlock> NEGARITE_GENERATOR = BLOCKS.register("negarite_generator", NegariteGeneratorTile::createBlock);
    public static final RegistryObject<Item> NEGARITE_GENERATOR_ITEM = ITEMS.register("negarite_generator", () -> new BlockItem(NEGARITE_GENERATOR.get(), createStandardProperties()));
    public static final RegistryObject<TileEntityType<NegariteGeneratorTile>> NEGARITE_GENERATOR_TILE = TILES.register("negarite_generator", () -> TileEntityType.Builder.create(NegariteGeneratorTile::new, NEGARITE_GENERATOR.get()).build(null));

    public static final RegistryObject<BaseBlock> POSIRITE_GENERATOR = BLOCKS.register("posirite_generator", PosiriteGeneratorTile::createBlock);
    public static final RegistryObject<Item> POSIRITE_GENERATOR_ITEM = ITEMS.register("posirite_generator", () -> new BlockItem(POSIRITE_GENERATOR.get(), createStandardProperties()));
    public static final RegistryObject<TileEntityType<PosiriteGeneratorTile>> POSIRITE_GENERATOR_TILE = TILES.register("posirite_generator", () -> TileEntityType.Builder.create(PosiriteGeneratorTile::new, POSIRITE_GENERATOR.get()).build(null));

    public static final RegistryObject<BaseBlock> NEGARITE_TANK = BLOCKS.register("negarite_tank", NegariteTankTile::createBlock);
    public static final RegistryObject<Item> NEGARITE_TANK_ITEM = ITEMS.register("negarite_tank", () -> new BlockItem(NEGARITE_TANK.get(), createStandardProperties()));
    public static final RegistryObject<TileEntityType<NegariteTankTile>> NEGARITE_TANK_TILE = TILES.register("negarite_tank", () -> TileEntityType.Builder.create(NegariteTankTile::new, NEGARITE_TANK.get()).build(null));

    public static final RegistryObject<BaseBlock> POSIRITE_TANK = BLOCKS.register("posirite_tank", PosiriteTankTile::createBlock);
    public static final RegistryObject<Item> POSIRITE_TANK_ITEM = ITEMS.register("posirite_tank", () -> new BlockItem(POSIRITE_TANK.get(), createStandardProperties()));
    public static final RegistryObject<TileEntityType<PosiriteTankTile>> POSIRITE_TANK_TILE = TILES.register("posirite_tank", () -> TileEntityType.Builder.create(PosiriteTankTile::new, POSIRITE_TANK.get()).build(null));

    public static final RegistryObject<BaseBlock> DOOR_MARKER = BLOCKS.register("door_marker", DoorMarkerTile::createBlock);
    public static final RegistryObject<Item> DOOR_MARKER_ITEM = ITEMS.register("door_marker", () -> new BlockItem(DOOR_MARKER.get(), createStandardProperties()));
    public static final RegistryObject<TileEntityType<DoorMarkerTile>> DOOR_MARKER_TILE = TILES.register("door_marker", () -> TileEntityType.Builder.create(DoorMarkerTile::new, DOOR_MARKER.get()).build(null));

    public static final RegistryObject<BaseBlock> AICORE = BLOCKS.register("aicore", AICoreTile::createBlock);
    public static final RegistryObject<Item> AICORE_ITEM = ITEMS.register("aicore", () -> new BlockItem(AICORE.get(), createStandardProperties()));
    public static final RegistryObject<TileEntityType<AICoreTile>> AICORE_TILE = TILES.register("aicore", () -> TileEntityType.Builder.create(AICoreTile::new, AICORE.get()).build(null));

    public static final RegistryObject<BaseBlock> ALARM = BLOCKS.register("alarm", AlarmTile::createBlock);
    public static final RegistryObject<Item> ALARM_ITEM = ITEMS.register("alarm", () -> new BlockItem(ALARM.get(), createStandardProperties()));
    public static final RegistryObject<TileEntityType<AlarmTile>> ALARM_TILE = TILES.register("alarm", () -> TileEntityType.Builder.create(AlarmTile::new, ALARM.get()).build(null));

    public static final RegistryObject<BaseBlock> CONSTRUCTOR = BLOCKS.register("constructor", ConstructorTile::createBlock);
    public static final RegistryObject<Item> CONSTRUCTOR_ITEM = ITEMS.register("constructor", () -> new BlockItem(CONSTRUCTOR.get(), createStandardProperties()));
    public static final RegistryObject<TileEntityType<ConstructorTile>> CONSTRUCTOR_TILE = TILES.register("constructor", () -> TileEntityType.Builder.create(ConstructorTile::new, CONSTRUCTOR.get()).build(null));

    public static final RegistryObject<BaseBlock> AUTO_CONSTRUCTOR = BLOCKS.register("auto_constructor", AutoConstructorTile::createBlock);
    public static final RegistryObject<Item> AUTO_CONSTRUCTOR_ITEM = ITEMS.register("auto_constructor", () -> new BlockItem(AUTO_CONSTRUCTOR.get(), createStandardProperties()));
    public static final RegistryObject<TileEntityType<AutoConstructorTile>> AUTO_CONSTRUCTOR_TILE = TILES.register("auto_constructor", () -> TileEntityType.Builder.create(AutoConstructorTile::new, AUTO_CONSTRUCTOR.get()).build(null));

    public static final RegistryObject<BaseBlock> BLUEPRINT_STORAGE = BLOCKS.register("blueprint_storage", BlueprintStorageTile::createBlock);
    public static final RegistryObject<Item> BLUEPRINT_STORAGE_ITEM = ITEMS.register("blueprint_storage", () -> new BlockItem(BLUEPRINT_STORAGE.get(), createStandardProperties()));
    public static final RegistryObject<TileEntityType<BlueprintStorageTile>> BLUEPRINT_STORAGE_TILE = TILES.register("blueprint_storage", () -> TileEntityType.Builder.create(BlueprintStorageTile::new, BLUEPRINT_STORAGE.get()).build(null));

    public static final RegistryObject<BaseBlock> AUTOMATION_FIELD = BLOCKS.register("automation_field", AutoFieldTile::createBlock);
    public static final RegistryObject<Item> AUTOMATION_FIELD_ITEM = ITEMS.register("automation_field", () -> new BlockItem(AUTOMATION_FIELD.get(), createStandardProperties()));
    public static final RegistryObject<TileEntityType<AutoFieldTile>> AUTOFIELD_TILE = TILES.register("automation_field", () -> TileEntityType.Builder.create(AutoFieldTile::new, AUTOMATION_FIELD.get()).build(null));

    public static final RegistryObject<BaseBlock> STORAGE = BLOCKS.register("storage", StorageTile::createBlock);
    public static final RegistryObject<Item> STORAGE_ITEM = ITEMS.register("storage", () -> new BlockItem(STORAGE.get(), createStandardProperties()));
    public static final RegistryObject<TileEntityType<StorageTile>> STORAGE_TILE = TILES.register("storage", () -> TileEntityType.Builder.create(StorageTile::new, STORAGE.get()).build(null));

    public static final RegistryObject<BaseBlock> ELEVATOR = BLOCKS.register("elevator", ElevatorTile::createBlock);
    public static final RegistryObject<Item> ELEVATOR_ITEM = ITEMS.register("elevator", () -> new BlockItem(ELEVATOR.get(), createStandardProperties()));
    public static final RegistryObject<TileEntityType<ElevatorTile>> ELEVATOR_TILE = TILES.register("elevator", () -> TileEntityType.Builder.create(ElevatorTile::new, ELEVATOR.get()).build(null));

    public static final RegistryObject<BaseBlock> LEVEL_MARKER = BLOCKS.register("level_marker", LevelMarkerTile::createBlock);
    public static final RegistryObject<Item> LEVEL_MARKER_ITEM = ITEMS.register("level_marker", () -> new BlockItem(LEVEL_MARKER.get(), createStandardProperties()));
    public static final RegistryObject<TileEntityType<LevelMarkerTile>> LEVEL_MARKER_TILE = TILES.register("level_marker", () -> TileEntityType.Builder.create(LevelMarkerTile::new, LEVEL_MARKER.get()).build(null));

    public static final RegistryObject<BaseBlock> INVISIBLE_DOOR = BLOCKS.register("invisible_door", InvisibleDoorTile::createBlock);
    public static final RegistryObject<TileEntityType<InvisibleDoorTile>> INVISIBLE_DOOR_TILE = TILES.register("invisible_door", () -> TileEntityType.Builder.create(InvisibleDoorTile::new, INVISIBLE_DOOR.get()).build(null));

    public static final RegistryObject<BaseBlock> FORCEFIELD = BLOCKS.register("forcefield", ForceFieldTile::createBlock);
    public static final RegistryObject<Item> FORCEFIELD_ITEM = ITEMS.register("forcefield", () -> new BlockItem(FORCEFIELD.get(), createStandardProperties()));
    public static final RegistryObject<TileEntityType<ForceFieldTile>> FORCEFIELD_TILE = TILES.register("forcefield", () -> TileEntityType.Builder.create(ForceFieldTile::new, FORCEFIELD.get()).build(null));

    public static final RegistryObject<BaseBlock> WARPER = BLOCKS.register("warper", WarperTile::createBlock);
    public static final RegistryObject<Item> WARPER_ITEM = ITEMS.register("warper", () -> new BlockItem(WARPER.get(), createStandardProperties()));
    public static final RegistryObject<TileEntityType<WarperTile>> WARPER_TILE = TILES.register("warper", () -> TileEntityType.Builder.create(WarperTile::new, WARPER.get()).build(null));

    public static final RegistryObject<BaseBlock> LOCK = BLOCKS.register("lock", LockTile::createBlock);
    public static final RegistryObject<Item> LOCK_ITEM = ITEMS.register("lock", () -> new BlockItem(LOCK.get(), createStandardProperties()));
    public static final RegistryObject<TileEntityType<LockTile>> LOCK_TILE = TILES.register("lock", () -> TileEntityType.Builder.create(LockTile::new, LOCK.get()).build(null));

    public static final RegistryObject<BaseBlock> SIGNAL_RECEIVER = BLOCKS.register("signal_receiver", SignalReceiverTile::createBlock);
    public static final RegistryObject<Item> SIGNAL_RECEIVER_ITEM = ITEMS.register("signal_receiver", () -> new BlockItem(SIGNAL_RECEIVER.get(), createStandardProperties()));
    public static final RegistryObject<TileEntityType<SignalReceiverTile>> SIGNAL_RECEIVER_TILE = TILES.register("signal_receiver", () -> TileEntityType.Builder.create(SignalReceiverTile::new, SIGNAL_RECEIVER.get()).build(null));

    public static final RegistryObject<BaseBlock> SIGNAL_TRANSMITTER = BLOCKS.register("signal_transmitter", SignalTransmitterTile::createBlock);
    public static final RegistryObject<Item> SIGNAL_TRANSMITTER_ITEM = ITEMS.register("signal_transmitter", () -> new BlockItem(SIGNAL_TRANSMITTER.get(), createStandardProperties()));
    public static final RegistryObject<TileEntityType<SignalTransmitterTile>> SIGNAL_TRANSMITTER_TILE = TILES.register("signal_transmitter", () -> TileEntityType.Builder.create(SignalTransmitterTile::new, SIGNAL_TRANSMITTER.get()).build(null));

    public static final RegistryObject<BaseBlock> WIRELESS_BUTTON = BLOCKS.register("wireless_button", WirelessButtonTile::createBlock);
    public static final RegistryObject<Item> WIRELESS_BUTTON_ITEM = ITEMS.register("wireless_button", () -> new BlockItem(WIRELESS_BUTTON.get(), createStandardProperties()));
    public static final RegistryObject<TileEntityType<WirelessButtonTile>> WIRELESS_BUTTON_TILE = TILES.register("wireless_button", () -> TileEntityType.Builder.create(WirelessButtonTile::new, WIRELESS_BUTTON.get()).build(null));

    public static final RegistryObject<BaseBlock> WIRELESS_LOCK = BLOCKS.register("wireless_lock", WirelessLockTile::createBlock);
    public static final RegistryObject<Item> WIRELESS_LOCK_ITEM = ITEMS.register("wireless_lock", () -> new BlockItem(WIRELESS_LOCK.get(), createStandardProperties()));
    public static final RegistryObject<TileEntityType<WirelessLockTile>> WIRELESS_LOCK_TILE = TILES.register("wireless_lock", () -> TileEntityType.Builder.create(WirelessLockTile::new, WIRELESS_LOCK.get()).build(null));

    public static final RegistryObject<BaseBlock> FLAT_LIGHT = BLOCKS.register("flatlight", Registration::createFlatLightBlock);

    public static final RegistryObject<Item> FLAT_LIGHT_ITEM = ITEMS.register("flatlight", () -> new BlockItem(FLAT_LIGHT.get(), createStandardProperties()));



    public static final RegistryObject<NetCableBlock> NETCABLE = BLOCKS.register("netcable", NetCableBlock::new);
    public static final RegistryObject<Item> NETCABLE_ITEM = ITEMS.register("netcable", () -> new BlockItem(NETCABLE.get(), createStandardProperties()));
    public static final RegistryObject<TileEntityType<NetCableTileEntity>> NETCABLE_TILE = TILES.register("netcable", () -> TileEntityType.Builder.create(NetCableTileEntity::new, NETCABLE.get()).build(null));
    public static final RegistryObject<ConnectorBlock> CONNECTOR = BLOCKS.register("connector", ConnectorBlock::new);
    public static final RegistryObject<Item> CONNECTOR_ITEM = ITEMS.register("connector", () -> new BlockItem(CONNECTOR.get(), createStandardProperties()));
    public static final RegistryObject<TileEntityType<ConnectorTileEntity>> CONNECTOR_TILE = TILES.register("connector", () -> TileEntityType.Builder.create(ConnectorTileEntity::new, CONNECTOR.get()).build(null));
    public static final RegistryObject<FacadeBlock> FACADE = BLOCKS.register("facade", FacadeBlock::new);
    public static final RegistryObject<Item> FACADE_ITEM = ITEMS.register("facade", () -> new FacadeItemBlock(FACADE.get()));


    public static final RegistryObject<Item> INGOT_PLATINUM = ITEMS.register("ingot_platinum", () -> new Item(createStandardProperties()));
    public static final RegistryObject<Item> INGOT_LITHIUM = ITEMS.register("ingot_lithium", () -> new Item(createStandardProperties()));
    public static final RegistryObject<Item> INGOT_MANGANESE = ITEMS.register("ingot_manganese", () -> new Item(createStandardProperties()));
    public static final RegistryObject<Item> INGOT_SILVER = ITEMS.register("ingot_silver", () -> new Item(createStandardProperties()));
    public static final RegistryObject<Item> DUST_SILICON = ITEMS.register("silicon", () -> new Item(createStandardProperties()));
    public static final RegistryObject<Item> DUST_NEGARITE = ITEMS.register("dust_negarite", () -> new Item(createStandardProperties()));
    public static final RegistryObject<Item> DUST_POSIRITE = ITEMS.register("dust_posirite", () -> new Item(createStandardProperties()));
    public static final RegistryObject<EnergySabreItem> ENERGY_SABRE = ITEMS.register("energy_sabre", EnergySabreItem::new);
    public static final RegistryObject<EnhancedEnergySabreItem> ENHANCED_ENERGY_SABRE = ITEMS.register("enhanced_energy_sabre", EnhancedEnergySabreItem::new);
    public static final RegistryObject<KeyCardItem> KEY_CARD = ITEMS.register("key_card", KeyCardItem::new);
    public static final RegistryObject<BlueprintItem> BLUEPRINT = ITEMS.register("blueprint", BlueprintItem::new);
    public static final RegistryObject<ArientePearlItem> ARIENTE_PEARL = ITEMS.register("ariente_pearl", ArientePearlItem::new);
    public static final RegistryObject<FluxLevitatorItem> FLUX_LEVITATOR = ITEMS.register("flux_levitator", FluxLevitatorItem::new);
    public static final RegistryObject<FluxShipItem> FLUX_SHIP = ITEMS.register("flux_ship", FluxShipItem::new);
    public static final RegistryObject<FluxCapacitorItem> FLUX_CAPACITOR = ITEMS.register("flux_capacitor", FluxCapacitorItem::new);
    public static final RegistryObject<CircuitItem> CIRCUIT = ITEMS.register("circuit", () -> new CircuitItem(false));
    public static final RegistryObject<CircuitItem> ADVANCED_CIRCUIT = ITEMS.register("advanced_circuit", () -> new CircuitItem(true));
    public static final RegistryObject<EnergyHolderItem> ENERGY_HOLDER = ITEMS.register("energy_holder", EnergyHolderItem::new);
    public static final RegistryObject<PowerSuit> POWERSUIT_HEAD = ITEMS.register("powersuit_head", () -> new PowerSuit(EquipmentSlotType.HEAD));
    public static final RegistryObject<PowerSuit> POWERSUIT_CHEST = ITEMS.register("powersuit_chest", () -> new PowerSuit(EquipmentSlotType.CHEST));
    public static final RegistryObject<PowerSuit> POWERSUIT_LEGS = ITEMS.register("powersuit_legs", () -> new PowerSuit(EquipmentSlotType.LEGS));
    public static final RegistryObject<PowerSuit> POWERSUIT_FEET = ITEMS.register("powersuit_feet", () -> new PowerSuit(EquipmentSlotType.FEET));
    public static final RegistryObject<ArmorModuleItem> MODULE_ARMOR = ITEMS.register("module_armor", () -> new ArmorModuleItem(ArmorUpgradeType.ARMOR));
    public static final RegistryObject<ArmorModuleItem> MODULE_ENERGY = ITEMS.register("module_energy", () -> new ArmorModuleItem(ArmorUpgradeType.ENERGY));
    public static final RegistryObject<ArmorModuleItem> MODULE_FEATHERFALLING = ITEMS.register("module_featherfalling", () -> new ArmorModuleItem(ArmorUpgradeType.FEATHERFALLING));
    public static final RegistryObject<ArmorModuleItem> MODULE_FLIGHT = ITEMS.register("module_flight", () -> new ArmorModuleItem(ArmorUpgradeType.FLIGHT));
    public static final RegistryObject<ArmorModuleItem> MODULE_HOVER = ITEMS.register("module_hover", () -> new ArmorModuleItem(ArmorUpgradeType.HOVER));
    public static final RegistryObject<ArmorModuleItem> MODULE_FORCEFIELD = ITEMS.register("module_forcefield", () -> new ArmorModuleItem(ArmorUpgradeType.FORCEFIELD));
    public static final RegistryObject<ArmorModuleItem> MODULE_INVISIBILITY = ITEMS.register("module_invisibility", () -> new ArmorModuleItem(ArmorUpgradeType.INVISIBILITY));
    public static final RegistryObject<ArmorModuleItem> MODULE_NIGHTVISION = ITEMS.register("module_nightvision", () -> new ArmorModuleItem(ArmorUpgradeType.NIGHTVISION));
    public static final RegistryObject<ArmorModuleItem> MODULE_REGENERATION = ITEMS.register("module_regeneration", () -> new ArmorModuleItem(ArmorUpgradeType.REGENERATION));
    public static final RegistryObject<ArmorModuleItem> MODULE_SCRAMBLE = ITEMS.register("module_scramble", () -> new ArmorModuleItem(ArmorUpgradeType.SCRAMBLE));
    public static final RegistryObject<ArmorModuleItem> MODULE_AUTOFEED = ITEMS.register("module_autofeed", () -> new ArmorModuleItem(ArmorUpgradeType.AUTOFEED));
    public static final RegistryObject<ArmorModuleItem> MODULE_SPEED = ITEMS.register("module_speed", () -> new ArmorModuleItem(ArmorUpgradeType.SPEED));
    public static final RegistryObject<ArmorModuleItem> MODULE_STEPASSIST = ITEMS.register("module_stepassist", () -> new ArmorModuleItem(ArmorUpgradeType.STEPASSIST));
    public static final RegistryObject<ArmorModuleItem> MODULE_INHIBIT = ITEMS.register("module_inhibit", () -> new ArmorModuleItem(ArmorUpgradeType.INHIBIT));
    public static final RegistryObject<ArmorModuleItem> MODULE_POWER = ITEMS.register("module_power", () -> new ArmorModuleItem(ArmorUpgradeType.POWER));
    public static final RegistryObject<ArmorModuleItem> MODULE_LOOTING = ITEMS.register("module_looting", () -> new ArmorModuleItem(ArmorUpgradeType.LOOTING));
    public static final RegistryObject<ArmorModuleItem> MODULE_FIRE = ITEMS.register("module_fire", () -> new ArmorModuleItem(ArmorUpgradeType.FIRE));

    public static final RegistryObject<EntityType<MasterSoldierEntity>> ENTITY_MASTER_SOLDIER = ENTITIES.register("master_soldier", () -> EntityType.Builder.create(MasterSoldierEntity::new, EntityClassification.MISC)
            .size(0.7F, 2.7F)
            .setShouldReceiveVelocityUpdates(false)
            .build("master_soldier"));
    public static final RegistryObject<EntityType<SoldierEntity>> ENTITY_SOLDIER = ENTITIES.register("soldier", () -> EntityType.Builder.create(SoldierEntity::new, EntityClassification.MISC)
            .size(0.6F, 1.95F)
            .setShouldReceiveVelocityUpdates(false)
            .build("soldier"));
    public static final RegistryObject<EntityType<SentinelDroneEntity>> ENTITY_SENTINEL_DRONE = ENTITIES.register("ariente_sentinel_drone", () -> EntityType.Builder.create(SentinelDroneEntity::new, EntityClassification.MISC)
            .size(1.3F, 1.3F)
            .setShouldReceiveVelocityUpdates(false)
            .build("ariente_sentinel_drone"));
    public static final RegistryObject<EntityType<DroneEntity>> ENTITY_DRONE = ENTITIES.register("ariente_drone", () -> EntityType.Builder.create(DroneEntity::new, EntityClassification.MISC)
            .size(2.0F, 2.0F)
            .setShouldReceiveVelocityUpdates(false)
            .build("ariente_drone"));
    public static final RegistryObject<EntityType<FluxElevatorEntity>> ENTITY_ELEVATOR = ENTITIES.register("ariente_flux_elevator", () -> EntityType.Builder.create(FluxElevatorEntity::new, EntityClassification.MISC)
            .size(1.30F, 0.9F)
            .setShouldReceiveVelocityUpdates(false)
            .build("ariente_flux_elevator"));
    public static final RegistryObject<EntityType<EntityArientePearl>> ENTITY_PEARL = ENTITIES.register("ariente_ariente_pearl", () -> EntityType.Builder.create(EntityArientePearl::new, EntityClassification.MISC)
            .size(0.25F, 0.25F)
            .setShouldReceiveVelocityUpdates(false)
            .build("ariente_ariente_pearl"));
    public static final RegistryObject<EntityType<FluxShipEntity>> ENTITY_FLUX_SHIP = ENTITIES.register("ariente_flux_ship", () -> EntityType.Builder.create(FluxShipEntity::new, EntityClassification.MISC)
            .size(2.50F, 1.5F)
            .setShouldReceiveVelocityUpdates(false)
            .build("ariente_flux_ship"));
    public static final RegistryObject<EntityType<FluxLevitatorEntity>> ENTITY_FLUX_LEVITATOR = ENTITIES.register("ariente_flux_levitator", () -> EntityType.Builder.create(FluxLevitatorEntity::new, EntityClassification.MISC)
            .size(1.30F, 0.9F)
            .setShouldReceiveVelocityUpdates(false)
            .build("ariente_flux_levitator"));
    public static final RegistryObject<EntityType<LaserEntity>> ENTITY_LASER = ENTITIES.register("ariente_laser", () -> EntityType.Builder.create(LaserEntity::new, EntityClassification.MISC)
            .size(1, 1)
            .setShouldReceiveVelocityUpdates(false)
            .build("ariente_laser"));


//    public static AxisAlignedBB getFlatBox(BlockState state) {
//        Direction facing = state.get(BaseBlock.FACING);
//        switch (facing) {
//            case UP:
//                return LIGHT_BLOCK_DOWN;
//            case DOWN:
//                return LIGHT_BLOCK_UP;
//            case SOUTH:
//                return LIGHT_BLOCK_NORTH;
//            case NORTH:
//                return LIGHT_BLOCK_SOUTH;
//            case EAST:
//                return LIGHT_BLOCK_WEST;
//            case WEST:
//                return LIGHT_BLOCK_EAST;
//        }
//        return Block.FULL_BLOCK_AABB;
//    }

//    public static AxisAlignedBB getBeamBox(BlockState state) {
//        Direction facing = state.getValue(BaseBlock.FACING_HORIZ);
//        switch (facing) {
//            case SOUTH:
//                return BEAM_BLOCK_NS_AABB;
//            case NORTH:
//                return BEAM_BLOCK_NS_AABB;
//            case EAST:
//                return BEAM_BLOCK_EW_AABB;
//            case WEST:
//                return BEAM_BLOCK_EW_AABB;
//        }
//        return Block.FULL_BLOCK_AABB;
//    }


    public static void initOreDict() {
        // @todo 1.14
//        OreDictionary.registerOre("blockMarble", marble);
//        OreDictionary.registerOre("oreSilver", silverore);
//        OreDictionary.registerOre("orePlatinum", platinumore);
//        OreDictionary.registerOre("oreSilicon", siliconore);
//        OreDictionary.registerOre("oreManganese", manganeseore);
//        OreDictionary.registerOre("oreLithium", lithiumore);
//        OreDictionary.registerOre("oreNegarite", negarite);
//        OreDictionary.registerOre("orePosirite", posirite);
    }

//    @SideOnly(Side.CLIENT)
//    public static void initItemModels() {
//        facadeBlock.initItemModel();
//        netCableBlock.initItemModel();
//        connectorBlock.initItemModel();
//    }
//
//    @SideOnly(Side.CLIENT)
//    public static void initColorHandlers(BlockColors blockColors) {
//        facadeBlock.initColorHandler(blockColors);
//        connectorBlock.initColorHandler(blockColors);
//        netCableBlock.initColorHandler(blockColors);
//    }


    public static Item.Properties createStandardProperties() {
        return new Item.Properties().group(Ariente.setup.getTab());
    }

    private static final VoxelShape FLAT_LIGHT_AABB = VoxelShapes.create(1.0D/16.0, 1.0D/16.0, 14.0D/16.0, 15.0D/16.0, 15.0D/16.0, 1.0D);

    private static BaseBlock createFlatLightBlock() {
        return new BaseBlock(new BlockBuilder()
                .properties(Block.Properties.create(Material.GLASS).lightValue(15))) {
            @Override
            public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
                return FLAT_LIGHT_AABB;
            }

        };
    }

    // @todo 1.14
    public static void registerItems() {
        initOreDict();
        ModItems.initOreDict();
        ModCrafting.init();
    }
}