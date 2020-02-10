package mcjty.ariente.blocks.utility.door;

import mcjty.ariente.blocks.ModBlocks;
import mcjty.ariente.blocks.utility.ILockable;
import mcjty.ariente.config.UtilityConfiguration;
import mcjty.lib.blocks.BaseBlock;
import mcjty.lib.blocks.RotationType;
import mcjty.lib.builder.BlockBuilder;
import mcjty.lib.tileentity.GenericTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class InvisibleDoorTile extends GenericTileEntity implements ILockable {

    public InvisibleDoorTile() {
        super(ModBlocks.INVISIBLE_DOOR_TILE.get());
    }

    public static BaseBlock createBlock() {
        return new BaseBlock(new BlockBuilder()
//                .addCollisionBoxToList(InvisibleDoorTile::addCollisionBoxToList)
//                .boundingBox(InvisibleDoorTile::getCollisionBoundingBox)
//                .getAIPathNodeType(InvisibleDoorTile::getAiPathNodeType)
                .tileEntitySupplier(InvisibleDoorTile::new)
        ) {
            @Override
            public RotationType getRotationType() {
                return RotationType.HORIZROTATION;
            }
        };
    }

    @Nonnull
    public static PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof InvisibleDoorTile) {
            DoorMarkerTile door = ((InvisibleDoorTile) te).findDoorMarker();
            if (door.isOpen()) {
                return PathNodeType.OPEN;
            }
        }
        return PathNodeType.BLOCKED;
    }

    public static AxisAlignedBB getCollisionBoundingBox(BlockState blockState, IBlockReader world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof InvisibleDoorTile) {
            DoorMarkerTile door = ((InvisibleDoorTile) te).findDoorMarker();
            if (door != null && door.isOpen()) {
                return DoorMarkerTile.OPEN_BLOCK_AABB;
            }
        }
//        return Block.FULL_BLOCK_AABB;
            // @todo 1.14
        return null;
    }

    public static boolean addCollisionBoxToList(BlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof InvisibleDoorTile) {
            DoorMarkerTile door = ((InvisibleDoorTile) te).findDoorMarker();
            if (door != null && !door.isOpen()) {
// @todo 1.14
                //                AxisAlignedBB box = Block.FULL_BLOCK_AABB.offset(pos);
//                if (entityBox.intersects(box)) {
//                    collidingBoxes.add(box);
//                }
            }
        }
        return true;
    }

    @Override
    public boolean isLocked() {
        // An invisible door block can never be broken
        return true;
//        DoorMarkerTile tile = findDoorMarker();
//        if (tile.isLocked()) {
//            return true;
//        }
//        return false;
    }

    public DoorMarkerTile findDoorMarker() {
        DoorMarkerTile doorMarkerTile = null;
        // Find the parent door marker
        BlockPos p = pos.down();
        for (int i = 0; i < UtilityConfiguration.MAX_DOOR_HEIGHT.get() ; i++) {
            TileEntity marker = getWorld().getTileEntity(p);
            if (marker instanceof DoorMarkerTile) {
                doorMarkerTile = (DoorMarkerTile) marker;
                break;
            }
            p = p.down();
        }
        return doorMarkerTile;
    }

// @todo 1.14
//    @Override
//    public boolean shouldRenderInPass(int pass) {
//        // @todo Should be pass 0 but it flickers then if an entity comes into view
//        return pass == 0;
//    }
}
