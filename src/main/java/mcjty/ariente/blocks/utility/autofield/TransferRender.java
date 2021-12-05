package mcjty.ariente.blocks.utility.autofield;

import com.mojang.blaze3d.matrix.MatrixStack;
import mcjty.lib.client.RenderHelper;
import mcjty.lib.multipart.PartSlot;
import mcjty.lib.spline.CatmullRomSpline;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;

import java.util.Random;

// A single item transfer render in progress
public class TransferRender {
    private final long startTime;
    private final long duration;
    private final ItemStack stack;
    private final CatmullRomSpline<AnimatedPoint> spline;

    private static class AnimatedPoint {
        private final double x;
        private final double y;
        private final double z;
        private final double rotation;
        private final double size;

        public AnimatedPoint() {
            this.x = 0;
            this.y = 0;
            this.z = 0;
            this.rotation = 0;
            this.size = 0;
        }

        public AnimatedPoint(double x, double y, double z, double rotation, double size) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.rotation = rotation;
            this.size = size;
        }

        public AnimatedPoint withSize(double s) {
            return new AnimatedPoint(x, y, z, rotation, s);
        }

        public AnimatedPoint subtract(AnimatedPoint other) {
            return new AnimatedPoint(x - other.x, y - other.y, z - other.z, rotation - other.rotation, size - other.size);
        }

        public AnimatedPoint add(AnimatedPoint other) {
            return new AnimatedPoint(x + other.x, y + other.y, z + other.z, rotation + other.rotation, size + other.size);
        }

        public AnimatedPoint scale(double scale) {
            return new AnimatedPoint(x * scale, y * scale, z * scale, rotation * scale, this.size * scale);
        }
    }

    public TransferRender(AutoFieldRenderInfo.TransferPath path, AutoFieldRenderInfo.Transfer transfer, BlockPos relative) {
        startTime = System.currentTimeMillis();
        // @todo 1.14 meta transfer.getMeta()
        stack = new ItemStack(transfer.getItem(), 1);

        BlockPos staBlock = path.getSourcePos().getPos().subtract(relative);
        BlockPos endBlock = path.getDestPos().getPos().subtract(relative);
        Vector3d sta = new Vector3d(staBlock.getX(), staBlock.getY(), staBlock.getZ()).add(getPos(path.getSourcePos().getSlot()));
        Vector3d end = new Vector3d(endBlock.getX(), endBlock.getY(), endBlock.getZ()).add(getPos(path.getDestPos().getSlot()));
        double distance = Math.sqrt(sta.distanceToSqr(end));
        duration = (long) (distance * 600) + 100;

        double jitter = distance / 5.0;
        Random random = new Random();
        Vector3d mid = sta.add(end).scale(0.5).add(
                random.nextFloat() * jitter - (jitter/2.0),
                random.nextFloat() * jitter - (jitter/2.0),
                random.nextFloat() * jitter - (jitter/2.0));

        spline = new CatmullRomSpline<>(
                AnimatedPoint::new,
                AnimatedPoint::subtract,
                AnimatedPoint::add,
                AnimatedPoint::scale);
        spline.addPoint(new AnimatedPoint(sta.x, sta.y, sta.z, 0.0, 0.0), 0.0f);
        spline.addPoint(new AnimatedPoint(mid.x, mid.y, mid.z, 150.0, 0.4), 0.5f);
        spline.addPoint(new AnimatedPoint(end.x, end.y, end.z, 0.0, 0.0), 1.0f);

        spline.calculate(0.2f);
        AnimatedPoint point10 = spline.getInterpolated();

        spline.calculate(0.8f);
        AnimatedPoint point90 = spline.getInterpolated();

        spline.insertPoint(point10.withSize(0.4), 0.2f, 1);
        spline.insertPoint(point90.withSize(0.4), 0.8f, 3);
    }

    public boolean render(MatrixStack matrixStack) {
        long time = System.currentTimeMillis();
        if (time > startTime+duration) {
            return false;
        }
        double factor = (time - startTime) / (double) duration;
        spline.calculate((float) factor);
        AnimatedPoint pos = spline.getInterpolated();
        renderStack(matrixStack, stack, new Vector3d(pos.x, pos.y, pos.z), pos.rotation, pos.size);

        return true;
    }

    private void renderStack(MatrixStack matrixStack, ItemStack stack, Vector3d pos, double rotation, double size) {
        matrixStack.pushPose();
        matrixStack.translate(pos.x, pos.y, pos.z);
        matrixStack.mulPose(new Quaternion((float) rotation, 0, 1, 0));
        matrixStack.scale((float) size, (float) size, (float) size);
        RenderHelper.renderStackOnGround(matrixStack, stack, 1.0f);

        matrixStack.popPose();
    }


    private static Vector3d getPos(PartSlot slot) {
        double d = .3;
        double e = .7;
        switch (slot) {
            case NONE:
                return Vector3d.ZERO;
            case DOWN:
                return new Vector3d(.5, d, .5);
            case UP:
                return new Vector3d(.5, e, .5);
            case NORTH:
                return new Vector3d(.5, .5, d);
            case SOUTH:
                return new Vector3d(.5, .5, e);
            case WEST:
                return new Vector3d(d, .5, .5);
            case EAST:
                return new Vector3d(e, .5, .5);
            case DOWN_NW:
                return new Vector3d(.25, d, .25);
            case DOWN_NE:
                return new Vector3d(.75, d, .25);
            case DOWN_SW:
                return new Vector3d(.25, d, .75);
            case DOWN_SE:
                return new Vector3d(.75, d, .75);
            case UP_NW:
                return new Vector3d(.25, e, .25);
            case UP_NE:
                return new Vector3d(.75, e, .25);
            case UP_SW:
                return new Vector3d(.25, e, .75);
            case UP_SE:
                return new Vector3d(.75, e, .75);
            case NORTH_UW:
                return new Vector3d(.25, .75, d);
            case NORTH_UE:
                return new Vector3d(.75, .75, d);
            case NORTH_DW:
                return new Vector3d(.25, .25, d);
            case NORTH_DE:
                return new Vector3d(.75, .25, d);
            case SOUTH_UW:
                return new Vector3d(.25, .75, e);
            case SOUTH_UE:
                return new Vector3d(.75, .75, e);
            case SOUTH_DW:
                return new Vector3d(.25, .25, e);
            case SOUTH_DE:
                return new Vector3d(.75, .25, e);
            case WEST_US:
                return new Vector3d(d, .75, .75);
            case WEST_UN:
                return new Vector3d(d, .75, .25);
            case WEST_DS:
                return new Vector3d(d, .25, .75);
            case WEST_DN:
                return new Vector3d(d, .25, .25);
            case EAST_US:
                return new Vector3d(e, .75, .75);
            case EAST_UN:
                return new Vector3d(e, .75, .25);
            case EAST_DS:
                return new Vector3d(e, .25, .75);
            case EAST_DN:
                return new Vector3d(e, .25, .25);
        }
        return Vector3d.ZERO;
    }


}
