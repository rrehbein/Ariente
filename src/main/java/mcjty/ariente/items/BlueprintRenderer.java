package mcjty.ariente.items;

import com.mojang.blaze3d.matrix.MatrixStack;
import mcjty.ariente.setup.Registration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

import java.util.concurrent.Callable;

public class BlueprintRenderer extends ItemStackTileEntityRenderer {


    @Override
    public void render(ItemStack stack, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLightIn, int combinedOverlayIn) {
        matrixStack.push();

        // Render our item
        matrixStack.translate(.5, .5, 0);

        ItemRenderer itemRender = Minecraft.getInstance().getItemRenderer();
        ItemStack itm = new ItemStack(Registration.BLUEPRINT.get());
        IBakedModel ibakedmodel = itemRender.getItemModelWithOverrides(itm, Minecraft.getInstance().world, (LivingEntity)null);
        int lightmapValue = 140;
        itemRender.renderItem(itm, ItemCameraTransforms.TransformType.GUI, false, matrixStack, buffer, lightmapValue, OverlayTexture.NO_OVERLAY, ibakedmodel);

        ItemStack destination = BlueprintItem.getDestination(stack);
        if (!destination.isEmpty()) {
            renderItem(destination, matrixStack, buffer);
        }

        matrixStack.pop();
    }

    private void renderItem(ItemStack stack, MatrixStack matrixStack, IRenderTypeBuffer buffer) {
        if (!stack.isEmpty()) {
            // Translate to the center of the block and .9 points higher
            matrixStack.translate(0, 0, .5);
            matrixStack.scale(.5f, .5f, .5f);
            matrixStack.rotate(Vector3f.XP.rotationDegrees(30));
            long angle = (System.currentTimeMillis() / 50) % 360;
            matrixStack.rotate(Vector3f.YP.rotationDegrees(angle));

            ItemRenderer itemRender = Minecraft.getInstance().getItemRenderer();
            IBakedModel ibakedmodel = itemRender.getItemModelWithOverrides(stack, Minecraft.getInstance().world, (LivingEntity)null);
            int lightmapValue = 140;
            itemRender.renderItem(stack, ItemCameraTransforms.TransformType.GUI, false, matrixStack, buffer, lightmapValue, OverlayTexture.NO_OVERLAY, ibakedmodel);
        }
    }

    public static Callable createRenderer() {
        return BlueprintRenderer::new;
    }
}
