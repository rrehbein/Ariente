package mcjty.ariente.blocks.generators;

import mcjty.ariente.Ariente;
import mcjty.ariente.api.IAlarmMode;
import mcjty.ariente.api.IGenerator;
import mcjty.ariente.blocks.BlockProperties;
import mcjty.ariente.cables.CableColor;
import mcjty.ariente.gui.HoloGuiTools;
import mcjty.ariente.power.*;
import mcjty.ariente.setup.Registration;
import mcjty.hologui.api.*;
import mcjty.lib.blocks.BaseBlock;
import mcjty.lib.blocks.RotationType;
import mcjty.lib.builder.BlockBuilder;
import mcjty.lib.container.AutomationFilterItemHander;
import mcjty.lib.container.ContainerFactory;
import mcjty.lib.container.NoDirectionItemHander;
import mcjty.lib.tileentity.GenericTileEntity;
import mcjty.lib.varia.RedstoneMode;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

import static mcjty.ariente.compat.ArienteTOPDriver.DRIVER;
import static mcjty.hologui.api.Icons.*;
import static mcjty.lib.builder.TooltipBuilder.header;
import static mcjty.lib.builder.TooltipBuilder.key;

public class NegariteGeneratorTile extends GenericTileEntity implements ITickableTileEntity, IGuiTile, IPowerBlob, IAlarmMode, IPowerSender, IGenerator {

    public static final int POWERGEN = 1000;        // @todo configurable and based on tanks!

    public static final Lazy<ContainerFactory> CONTAINER_FACTORY = Lazy.of(() -> new ContainerFactory(1)); // @todo 1.14new ResourceLocation(Ariente.MODID, "gui/negarite_generator.gui"));
    public static final int SLOT_NEGARITE_INPUT = 0;

    private final NoDirectionItemHander items = createItemHandler();
    private final LazyOptional<NoDirectionItemHander> itemHandler = LazyOptional.of(() -> items);
    private final LazyOptional<AutomationFilterItemHander> automationItemHandler = LazyOptional.of(() -> new AutomationFilterItemHander(items));

    private final PowerSenderSupport powerBlobSupport = new PowerSenderSupport();

    // @todo, temporary: base on tanks later!
    private int dustCounter;        // Number of ticks before the current dust depletes


    public static BaseBlock createBlock() {
        return new BaseBlock(new BlockBuilder()
//                .flags(REDSTONE_CHECK, RENDER_SOLID, RENDER_CUTOUT)
                .info(key("message.ariente.shiftmessage"))
                .infoShift(header())
                .topDriver(DRIVER)
                .tileEntitySupplier(NegariteGeneratorTile::new)
        ) {
            @Override
            public RotationType getRotationType() {
                return RotationType.HORIZROTATION;
            }

            @Override
            protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
                super.createBlockStateDefinition(builder);
                builder.add(BlockProperties.WORKING);
            }
        };
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        Ariente.guiHandler.openHoloGui(level, worldPosition, player);
        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean isHighAlert() {
        return false;
    }

    @Override
    protected boolean needsRedstoneMode() {
        return true;
    }

    public NegariteGeneratorTile() {
        super(Registration.NEGARITE_GENERATOR_TILE.get());
    }

    @Override
    public void feedDust(int amount) {
        if (items.getStackInSlot(NegariteGeneratorTile.SLOT_NEGARITE_INPUT).isEmpty()) {
            items.setStackInSlot(NegariteGeneratorTile.SLOT_NEGARITE_INPUT, new ItemStack(Registration.DUST_NEGARITE.get(), amount));
            markDirtyClient();
        }
    }

    @Override
    public boolean supportsNegarite() {
        return true;
    }

    @Override
    public boolean supportsPosirite() {
        return false;
    }

    @Override
    public void tick() {
        if (!level.isClientSide) {
            if (!isMachineEnabled()) {
                return;
            }
            if (dustCounter > 0) {
                dustCounter--;
                if (dustCounter == 0 && !canProceed()) {
                    markDirtyClient();
                } else {
                    markDirtyQuick();
                }
                sendPower();
            } else {
                if (canProceed()) {
                    items.extractItem(SLOT_NEGARITE_INPUT, 1, false);
                    dustCounter = 600;
                    markDirtyQuick();
                    sendPower();
                }
            }
        }
    }

    private boolean canProceed() {
        ItemStack stack = items.getStackInSlot(SLOT_NEGARITE_INPUT);
        return !stack.isEmpty() && stack.getItem() == Registration.DUST_NEGARITE.get();
    }

    private void sendPower() {
        int cnt = 0;
        BlockPos p = worldPosition.above();
        while (level.getBlockEntity(p) instanceof NegariteTankTile) {
            cnt++;
            p = p.above();
        }
        if (cnt > 0) {
            PowerSystem powerSystem = PowerSystem.getPowerSystem(level);
            powerSystem.addPower(powerBlobSupport.getCableId(), POWERGEN * cnt, PowerType.NEGARITE);
        }
    }

    @Override
    public boolean canSendPower() {
        return true;
    }

    @Override
    public CableColor getSupportedColor() {
        return CableColor.NEGARITE;
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet) {
        boolean working = isWorking();

        super.onDataPacket(net, packet);

        if (level.isClientSide) {
            // If needed send a render update.
            boolean newWorking = isWorking();
            if (newWorking != working) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);
                BlockPos p = worldPosition.above();
                BlockState state = level.getBlockState(p);
                while (state.getBlock() == Registration.NEGARITE_TANK.get()) {
                    level.sendBlockUpdated(p, state, state, Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);
                    p = p.above();
                    state = level.getBlockState(p);
                }
            }
        }
    }

    @Override
    public void setPowerInput(int powered) {
        boolean changed = powerLevel != powered;
        super.setPowerInput(powered);
        if (changed) {
            markDirtyClient();
        }
    }


    // @todo 1.14
//    @Override
//    public BlockState getActualState(BlockState state) {
//        return state.with(WORKING, isWorking());
//    }

    public boolean isWorking() {
        return dustCounter > 0 && isMachineEnabled();
    }

    @Override
    public void read(CompoundNBT tagCompound) {
        super.read(tagCompound);
        powerBlobSupport.setCableId(tagCompound.getInt("cableId"));
        //        readBufferFromNBT(tagCompound, inventoryHelper);
        CompoundNBT info = tagCompound.getCompound("Info");
        if (info.contains("dust")) {
            dustCounter = info.getInt("dust");
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT tagCompound) {
        tagCompound.putInt("cableId", powerBlobSupport.getCableId());
        //        writeBufferToNBT(tagCompound, inventoryHelper);
        getOrCreateInfo(tagCompound).putInt("dust", dustCounter);
        return super.save(tagCompound);
    }

    @Override
    public int getCableId() {
        return powerBlobSupport.getCableId();
    }

    @Override
    public void fillCableId(int id) {
        powerBlobSupport.fillCableId(level, worldPosition, id, getCableColor());
    }

    @Override
    public CableColor getCableColor() {
        return CableColor.NEGARITE;
    }

    @Override
    public IGuiComponent<?> createGui(String tag, IGuiComponentRegistry registry) {
        return registry.panel(0, 0, 8, 8)
                .add(registry.text(0, 0, 8, 1).text("Negarite").color(registry.color(StyledColor.LABEL)))
                .add(registry.stackIcon(0, 3, 1, 1).itemStack(new ItemStack(Registration.DUST_NEGARITE.get())))

                .add(registry.icon(1, 3, 1, 1).icon(registry.image(WHITE_PLAYER)))
                .add(registry.number(2, 3, 1, 1).color(registry.color(StyledColor.INFORMATION)).getter((p,h) -> HoloGuiTools.countItem(p, Registration.DUST_NEGARITE.get())))

                .add(registry.iconButton(2, 4, 1, 1).icon(registry.image(GRAY_DOUBLE_ARROW_LEFT)).hover(registry.image(WHITE_DOUBLE_ARROW_LEFT))
                    .hitEvent((component, player, e, x, y) -> toPlayer(player, 64)))
                .add(registry.iconButton(3, 4, 1, 1).icon(registry.image(GRAY_ARROW_LEFT)).hover(registry.image(WHITE_ARROW_LEFT))
                    .hitEvent((component, player, e, x, y) -> toPlayer(player, 1)))
                .add(registry.iconButton(5, 4, 1, 1).icon(registry.image(GRAY_ARROW_RIGHT)).hover(registry.image(WHITE_ARROW_RIGHT))
                    .hitEvent((component, player, e, x, y) -> toMachine(player, 1)))
                .add(registry.iconButton(6, 4, 1, 1).icon(registry.image(GRAY_DOUBLE_ARROW_RIGHT)).hover(registry.image(WHITE_DOUBLE_ARROW_RIGHT))
                    .hitEvent((component, player, e, x, y) -> toMachine(player, 64)))

                .add(registry.stackIcon(5, 3, 1, 1).itemStack(new ItemStack(Registration.NEGARITE_GENERATOR.get())))
                .add(registry.number(6, 3, 1, 1).color(registry.color(StyledColor.INFORMATION)).getter(this::countNegariteGenerator))

                .add(registry.iconChoice(7, 6, 1, 1)
                        .getter((player) -> getRSModeInt())
                        .addImage(registry.image(REDSTONE_DUST))
                        .addImage(registry.image(REDSTONE_OFF))
                        .addImage(registry.image(REDSTONE_ON))
                        .hitEvent((component, player, entity1, x, y) -> changeMode()))
                ;
    }

    private void changeMode() {
        int current = rsMode.ordinal() + 1;
        if (current >= RedstoneMode.values().length) {
            current = 0;
        }
        setRSMode(RedstoneMode.values()[current]);
        markDirtyClient();
    }

    private void toPlayer(PlayerEntity player, int amount) {
        ItemStack stack = items.extractItem(SLOT_NEGARITE_INPUT, amount, false);
        if ((!stack.isEmpty()) && player.inventory.add(stack)) {
            markDirtyClient();
        } else {
            ItemStack stillThere = items.getStackInSlot(SLOT_NEGARITE_INPUT);
            if (stillThere.isEmpty()) {
                stillThere = stack;
            } else {
                stillThere.grow(stack.getCount());
            }
            items.setStackInSlot(SLOT_NEGARITE_INPUT, stillThere);
        }
    }

    private void toMachine(PlayerEntity player, int amount) {
        ItemStack toTransfer = ItemStack.EMPTY;
        ItemStack stackInSlot = items.getStackInSlot(SLOT_NEGARITE_INPUT);
        if (!stackInSlot.isEmpty()) {
            amount = Math.min(amount, 64 - stackInSlot.getCount());    // @todo item specific max stacksize
        }

        for (int i = 0 ; i < player.inventory.getContainerSize() ; i++) {
            ItemStack stack = player.inventory.getItem(i);
            if (stack.getItem() == Registration.DUST_NEGARITE.get()) {
                ItemStack splitted = stack.split(amount);
                if ((!splitted.isEmpty())) {
                    if (toTransfer.isEmpty()) {
                        toTransfer = splitted;
                    } else {
                        toTransfer.grow(amount);
                    }
                    amount -= splitted.getCount();
                    if (amount <= 0) {
                        break;
                    }
                }
            }
        }

        if (!toTransfer.isEmpty()) {
            if (!stackInSlot.isEmpty()) {
                toTransfer.grow(stackInSlot.getCount());
            }
            items.setStackInSlot(SLOT_NEGARITE_INPUT, toTransfer);
            markDirtyClient();
        }

    }

    @Override
    public void syncToClient() {
        // @todo more efficient
        markDirtyClient();
    }

    public Integer countNegariteGenerator(PlayerEntity player, IHoloGuiEntity holo) {
        int size = items.getSlots();
        int cnt = 0;
        for (int i = 0 ; i < size ; i++) {
            ItemStack stack = items.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() == Registration.DUST_NEGARITE.get()) {
                cnt += stack.getCount();
            }
        }
        return cnt;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, placer, stack);
        if (!world.isClientSide) {
            PowerSenderSupport.fixNetworks(world, pos);
        }
    }

    @Override
    public void onReplaced(World world, BlockPos pos, BlockState state, BlockState newstate) {
        super.onReplaced(world, pos, state, newstate);
        if (!this.level.isClientSide) {
            PowerSenderSupport.fixNetworks(this.level, pos);
        }
    }

    private NoDirectionItemHander createItemHandler() {
        return new NoDirectionItemHander(NegariteGeneratorTile.this, CONTAINER_FACTORY.get()) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if (slot == SLOT_NEGARITE_INPUT) {
                    return stack.getItem() == Registration.DUST_NEGARITE.get();
                }
                return true;
            }

        };
    }
}
