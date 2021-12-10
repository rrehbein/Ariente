package mcjty.ariente.blocks.utility.wireless;

import mcjty.ariente.blocks.BlockProperties;
import mcjty.ariente.setup.Registration;
import mcjty.ariente.sounds.ModSounds;
import mcjty.lib.blocks.BaseBlock;
import mcjty.lib.builder.BlockBuilder;
import mcjty.lib.varia.NBTTools;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import static mcjty.ariente.compat.ArienteTOPDriver.DRIVER;
import static mcjty.lib.builder.TooltipBuilder.*;

public class WirelessButtonTile extends SignalChannelTileEntity {

    private boolean locked = false;
    private int prevIn = -1;

    private static final VoxelShape BLOCK_AABB = VoxelShapes.box(1.0D/16.0, 1.0D/16.0, 15.0D/16.0, 15.0D/16.0, 15.0D/16.0, 1.0D);

    public WirelessButtonTile() {
        super(Registration.WIRELESS_BUTTON_TILE.get());
    }

    public static BaseBlock createBlock() {
        return new BaseBlock(new BlockBuilder()
//                .flags(REDSTONE_CHECK, RENDER_SOLID, RENDER_CUTOUT)
                .info(key("message.ariente.shiftmessage"))
                .infoShift(header(), parameter("channel", NBTTools.intGetter("channel", -1)))
                .topDriver(DRIVER)
                .tileEntitySupplier(WirelessButtonTile::new)
        ) {
            @Override
            protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
                super.createBlockStateDefinition(builder);
                builder.add(BlockProperties.POWER);
            }

            @Override
            public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
                return BLOCK_AABB;
            }
        };
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        onBlockActivatedWithToggle(level, worldPosition, player, hand);
        return ActionResultType.SUCCESS;
    }


    @Override
    public void setChannel(int channel) {
        super.setChannel(channel);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet) {
        boolean locked = isLocked();

        super.onDataPacket(net, packet);

        if (level.isClientSide) {
            // If needed send a render update.
            boolean newLocked = isLocked();
            if (newLocked != locked) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);
            }
        }
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        if (locked == this.locked) {
            return;
        }
        if (channel != -1) {
            this.locked = locked;
            RedstoneChannels channels = RedstoneChannels.getChannels(getLevel());
            RedstoneChannels.RedstoneChannel ch = channels.getOrCreateChannel(channel);
            ch.setValue(locked ? 15 : 0);
        }
        markDirtyClient();
    }

    public static boolean onBlockActivatedWithToggle(World world, BlockPos pos, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (SignalChannelTileEntity.isRedstoneChannelItem(stack.getItem())) {
            setChannel(world, pos, player, stack);
        } else {
            TileEntity te = world.getBlockEntity(pos);
            if (te instanceof WirelessButtonTile) {
                world.playSound(null, pos, ModSounds.buzzOk, SoundCategory.BLOCKS, 1.0f, 1.0f);
                ((WirelessButtonTile) te).toggleLock();
            }
        }
        return true;
    }

    public void toggleLock() {
        if (!level.isClientSide) {
            setLocked(!locked);
        }
    }


    // @todo 1.14
//    @Override
//    public BlockState getActualState(BlockState state) {
//        return state.with(POWER, !locked);
//    }

    @Override
    public void load(CompoundNBT tagCompound) {
        super.load(tagCompound);
        prevIn = tagCompound.getInt("prevIn");
    }

    @Override
    public void saveAdditional(CompoundNBT tagCompound) {
        super.saveAdditional(tagCompound);
        tagCompound.putInt("prevIn", prevIn);
    }

    @Override
    public void readRestorableFromNBT(CompoundNBT tagCompound) {
        super.readRestorableFromNBT(tagCompound);
        CompoundNBT info = tagCompound.getCompound("Info");
        if (info.contains("locked")) {
            locked = info.getBoolean("locked");
        }
    }

    @Override
    public void writeRestorableToNBT(CompoundNBT tagCompound) {
        super.writeRestorableToNBT(tagCompound);
        getOrCreateInfo(tagCompound).putBoolean("locked", locked);
    }

}
