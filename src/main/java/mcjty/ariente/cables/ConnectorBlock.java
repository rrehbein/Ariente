package mcjty.ariente.cables;

import mcjty.ariente.blocks.generators.PowerCombinerTile;
import mcjty.ariente.facade.FacadeItemBlock;
import mcjty.ariente.power.IPowerReceiver;
import mcjty.ariente.power.IPowerSender;
import mcjty.ariente.setup.Registration;
import mcjty.lib.compat.theoneprobe.TOPDriver;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ConnectorBlock extends GenericCableBlock {

    public static final String CONNECTOR = "connector";

    public ConnectorBlock() {
        super(Material.METAL);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ConnectorTileEntity(pos, state);
    }

    @Override
    public TOPDriver getProbeDriver() {
        // @todo 1.14
        return null;
    }

    @Override
    protected Item getItem(CableColor color) {
        switch (color) {
            case NEGARITE:
                return Registration.CONNECTOR_NEGARITE.get();
            case POSIRITE:
                return Registration.CONNECTOR_POSIRITE.get();
            case COMBINED:
                return Registration.CONNECTOR_COMBINED.get();
            case DATA:
                return Registration.CONNECTOR_DATA.get();
        };
        return Items.AIR;
    }


    // @todo 1.14
//    @Override
//    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
//        for (CableColor value : CableColor.VALUES) {
//            items.add(updateColorInStack(new ItemStack(this, 1, value.ordinal()), value));
//        }
//    }

//    @Override
//    public boolean onBlockActivated(World world, BlockPos pos, BlockState state, PlayerEntity player, Hand hand, Direction side, float hitX, float hitY, float hitZ) {
//        if (!world.isRemote) {
//            player.openGui(XNet.instance, GuiProxy.GUI_CONNECTOR, world, pos.getX(), pos.getY(), pos.getZ());
//        }
//        return true;
//    }
//


    @Override
    public void playerDestroy(Level worldIn, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity te, ItemStack stack) {
        if (te instanceof ConnectorTileEntity) {
            // If we are in mimic mode then the drop will be the facade as the connector will remain there
            ConnectorTileEntity connectorTileEntity = (ConnectorTileEntity) te;
            if (connectorTileEntity.getMimicBlock() != null) {
                ItemStack item = new ItemStack(Registration.FACADE.get());
                FacadeItemBlock.setMimicBlock(item, connectorTileEntity.getMimicBlock());
                connectorTileEntity.setMimicBlock(null);
                popResource(worldIn, pos, item);
                return;
            }
        }
        super.playerDestroy(worldIn, player, pos, state, te, stack);
    }

    // @todo 1.18 @Override
    public boolean removedByPlayer(BlockState state, Level world, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        BlockEntity te = world.getBlockEntity(pos);
        if (te instanceof ConnectorTileEntity connectorTileEntity) {
            if (connectorTileEntity.getMimicBlock() == null) {
                this.playerWillDestroy(world, pos, state, player);
                return world.setBlock(pos, Blocks.AIR.defaultBlockState(), world.isClientSide ? 11 : 3);
            } else {
                // We are in mimic mode. Don't remove the connector
                this.playerWillDestroy(world, pos, state, player);
                if (player.getAbilities().instabuild) {
                    connectorTileEntity.setMimicBlock(null);
                }
            }
        } else {
            // @todo 1.18 return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
        }
        return true;
    }

// @todo 1.14
//    @Override
//    @Optional.Method(modid = "theoneprobe")
//    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, PlayerEntity player, World world, BlockState blockState, IProbeHitData data) {
//        super.addProbeInfo(mode, probeInfo, player, world, blockState, data);
//    }
//
//
//    @Override
//    public BlockState getExtendedState(BlockState state, IBlockAccess world, BlockPos pos) {
//        IExtendedBlockState extendedBlockState = (IExtendedBlockState) super.getExtendedState(state, world, pos);
//        BlockState mimicBlock = getMimicBlock(world, pos);
//        if (mimicBlock != null) {
//            return extendedBlockState.with(FACADEID, new FacadeBlockId(mimicBlock));
//        } else {
//            return extendedBlockState;
//        }
//    }

// @todo 1.14
//    @Override
//    public void initModel() {
//        super.initModel();
//        // To make sure that our ISBM model is chosen for all states we use this custom state mapper:
//        SafeClientTools.initStateMapper(this, GenericCableBakedModel.modelConnector);
//        CableRenderer.register(ConnectorTileEntity.class);
//    }


    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        checkRedstone(world, pos);
    }

//    @Override
//    public void onNeighborChange(IBlockAccess blockAccess, BlockPos pos, BlockPos neighbor) {
//        if (blockAccess instanceof World) {
//            World world = (World) blockAccess;
//            if (!world.isRemote) {
//                TileEntity te = world.getTileEntity(pos);
//                if (te instanceof ConnectorTileEntity) {
//                    ConnectorTileEntity connector = (ConnectorTileEntity) te;
//                    connector.possiblyMarkNetworkDirty(neighbor);
//                }
//            }
//        }
//    }
//


    // @todo 1.18 @Override
    public boolean shouldCheckWeakPower(BlockState state, BlockGetter world, BlockPos pos, Direction side) {
        return false;
    }

    private void checkRedstone(Level world, BlockPos pos) {
        BlockEntity te = world.getBlockEntity(pos);
        if (te instanceof ConnectorTileEntity) {
            int powered = world.getBestNeighborSignal(pos);
            ConnectorTileEntity genericTileEntity = (ConnectorTileEntity) te;
            genericTileEntity.setPowerInput(powered);
        }
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    public int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction side) {
        return getRedstoneOutput(state, world, pos, side);
    }

    @Override
    public int getDirectSignal(BlockState state, BlockGetter world, BlockPos pos, Direction side) {
        return getRedstoneOutput(state, world, pos, side);
    }

    protected int getRedstoneOutput(BlockState state, BlockGetter world, BlockPos pos, Direction side) {
        BlockEntity te = world.getBlockEntity(pos);
        if (state.getBlock() instanceof ConnectorBlock && te instanceof ConnectorTileEntity) {
            ConnectorTileEntity connector = (ConnectorTileEntity) te;
            return connector.getPowerOut(side.getOpposite());
        }
        return 0;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        // When our block is placed down we force a re-render of adjacent blocks to make sure their ISBM model is updated
// @todo 1.14
//        world.markBlockRangeForRenderUpdate(pos.add(-1, -1, -1), pos.add(1, 1, 1));
        return super.getStateForPlacement(context);
    }

    @Override
    protected ConnectorType getConnectorType(@Nonnull CableColor color, BlockGetter world, BlockPos connectorPos, Direction facing) {
        BlockPos pos = connectorPos.relative(facing);
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if ((block instanceof NetCableBlock || block instanceof ConnectorBlock) && state.getValue(COLOR) == color) {
            return ConnectorType.CABLE;
        } else if (isConnectable(world, connectorPos, facing)) {
            return ConnectorType.BLOCK;
        } else {
            return ConnectorType.NONE;
        }
    }

    public static boolean isConnectable(BlockGetter world, BlockPos connectorPos, Direction facing) {

        BlockPos pos = connectorPos.relative(facing);
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (state.isAir()) {
            return false;
        }

        ConnectorTileEntity connectorTE = (ConnectorTileEntity) world.getBlockEntity(connectorPos);
        if (connectorTE == null) {
            return false;
        }

        BlockEntity te = world.getBlockEntity(pos);

        if (block instanceof ConnectorBlock) {
            return false;
        }
        if (te == null) {
            return false;
        }
        if (te instanceof IPowerSender) {
            return connectorTE.supportsCableColor(((IPowerSender) te).getSupportedColor());
        }
        if (te instanceof PowerCombinerTile) {
            return true;        // All connectors connect to the power combiner
        }
        if (te instanceof IPowerReceiver) {
            return true;
        }
        return false;
    }

    // @todo 1.14
//    @Override
//    @SideOnly(Side.CLIENT)
//    public boolean shouldSideBeRendered(BlockState blockState, IBlockAccess blockAccess, BlockPos pos, Direction side) {
//        BlockState mimicBlock = getMimicBlock(blockAccess, pos);
//        if (mimicBlock == null) {
//            return false;
//        } else {
//            return mimicBlock.shouldSideBeRendered(blockAccess, pos, side);
//        }
//    }

    // @todo 1.14
//    @Override
//    @SideOnly(Side.CLIENT)
//    public boolean canRenderInLayer(BlockState state, BlockRenderLayer layer) {
//        return true; // delegated to GenericCableBakedModel#getQuads
//    }

    // @todo 1.14
//    @Override
//    public void getDrops(NonNullList<ItemStack> drops, IBlockReader blockAccess, BlockPos pos, BlockState state, int fortune) {
//        super.getDrops(drops, blockAccess, pos, state, fortune);
//        if (blockAccess instanceof World) {
//            World world = (World) blockAccess;
//            for (ItemStack drop : drops) {
//                if (!drop.hasTagCompound()) {
//                    drop.setTagCompound(new CompoundNBT());
//                }
//            }
//        }
//    }
}
