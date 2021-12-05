package mcjty.ariente.entities.levitator;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;



public class FluxLevitatorModel extends EntityModel {
    public ModelRenderer sideModelsS1;
    public ModelRenderer sideModelsB;
    public ModelRenderer sideModels2;
    public ModelRenderer sideModels3;
    public ModelRenderer sideModels4;
    public ModelRenderer sideModelsT;
    public ModelRenderer sideModels5;
    public ModelRenderer sideModelsS4;
    public ModelRenderer sideModelsS3;
    public ModelRenderer sideModelsS2;

    public FluxLevitatorModel() {
        this.texWidth = 64;
        this.texHeight = 32;
        this.sideModels3 = new ModelRenderer(this, 0, 0);
        this.sideModels3.setPos(15.0F, 4.0F, 0.0F);
        this.sideModels3.addBox(-8.0F, -10.0F, 4.0F, 16, 9, 2, 0.0F);
        this.setRotateAngle(sideModels3, 0.0F, 1.5707963267948966F, 0.0F);
        this.sideModelsS1 = new ModelRenderer(this, 0, 0);
        this.sideModelsS1.setPos(-15.0F, 4.0F, 0.0F);
        this.sideModelsS1.addBox(6.5F, -19.5F, 5.0F, 2, 11, 2, 0.0F);
        this.setRotateAngle(sideModelsS1, 0.0F, 4.71238898038469F, 0.17453292519943295F);
        this.sideModels5 = new ModelRenderer(this, 0, 0);
        this.sideModels5.setPos(0.0F, 4.0F, 7.0F);
        this.sideModels5.addBox(-14.0F, -10.0F, 0.5F, 28, 9, 2, 0.0F);
        this.sideModelsS2 = new ModelRenderer(this, 0, 0);
        this.sideModelsS2.setPos(-15.0F, 4.0F, 0.0F);
        this.sideModelsS2.addBox(-8.5F, -19.5F, 5.0F, 2, 11, 2, 0.0F);
        this.setRotateAngle(sideModelsS2, 0.0F, 4.71238898038469F, 0.17453292519943295F);
        this.sideModelsS4 = new ModelRenderer(this, 0, 0);
        this.sideModelsS4.setPos(15.0F, 4.0F, 0.0F);
        this.sideModelsS4.addBox(-8.5F, -19.5F, -6.5F, 2, 11, 2, 0.0F);
        this.setRotateAngle(sideModelsS4, 0.0F, 4.71238898038469F, -0.17453292519943295F);
        this.sideModels2 = new ModelRenderer(this, 0, 0);
        this.sideModels2.setPos(-15.0F, 4.0F, 0.0F);
        this.sideModels2.addBox(-8.0F, -10.0F, 4.0F, 16, 9, 2, 0.0F);
        this.setRotateAngle(sideModels2, 0.0F, 4.71238898038469F, 0.0F);
        this.sideModels4 = new ModelRenderer(this, 0, 0);
        this.sideModels4.setPos(0.0F, 4.0F, -7.0F);
        this.sideModels4.addBox(-14.0F, -10.0F, 0.5F, 28, 9, 2, 0.0F);
        this.setRotateAngle(sideModels4, 0.0F, 3.141592653589793F, 0.0F);
        this.sideModelsS3 = new ModelRenderer(this, 0, 0);
        this.sideModelsS3.setPos(15.0F, 4.0F, 0.0F);
        this.sideModelsS3.addBox(6.5F, -19.5F, -6.5F, 2, 11, 2, 0.0F);
        this.setRotateAngle(sideModelsS3, 0.0F, 4.71238898038469F, -0.17453292519943295F);
        this.sideModelsB = new ModelRenderer(this, 0, 10);
        this.sideModelsB.setPos(0.0F, 4.0F, 0.0F);
        this.sideModelsB.addBox(-14.0F, -8.0F, -1.0F, 28, 16, 2, 0.0F);
        this.setRotateAngle(sideModelsB, 1.5707963267948966F, 0.0F, 0.0F);
        this.sideModelsT = new ModelRenderer(this, 0, 10);
        this.sideModelsT.setPos(0.0F, 0.0F, 0.0F);
        this.sideModelsT.addBox(-14.0F, -8.0F, 15.0F, 28, 16, 2, 0.0F);
        this.setRotateAngle(sideModelsT, 1.5707963267948966F, 0.0F, 0.0F);
    }

    @Override
    public void setupAnim(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {

    }

    // @todo 1.15
//    @Override
//    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
//        this.sideModels3.render(f5);
//        this.sideModelsS1.render(f5);
//        GlStateManager.pushMatrix();
//        GlStateManager.translatef(this.sideModels5.offsetX, this.sideModels5.offsetY, this.sideModels5.offsetZ);
//        GlStateManager.translatef(this.sideModels5.rotationPointX * f5, this.sideModels5.rotationPointY * f5, this.sideModels5.rotationPointZ * f5);
//        GlStateManager.scaled(1.5D, 1.0D, 1.0D);
//        GlStateManager.translatef(-this.sideModels5.offsetX, -this.sideModels5.offsetY, -this.sideModels5.offsetZ);
//        GlStateManager.translatef(-this.sideModels5.rotationPointX * f5, -this.sideModels5.rotationPointY * f5, -this.sideModels5.rotationPointZ * f5);
//        this.sideModels5.render(f5);
//        GlStateManager.popMatrix();
//        this.sideModelsS2.render(f5);
//        this.sideModelsS4.render(f5);
//        this.sideModels2.render(f5);
//        GlStateManager.pushMatrix();
//        GlStateManager.translatef(this.sideModels4.offsetX, this.sideModels4.offsetY, this.sideModels4.offsetZ);
//        GlStateManager.translatef(this.sideModels4.rotationPointX * f5, this.sideModels4.rotationPointY * f5, this.sideModels4.rotationPointZ * f5);
//        GlStateManager.scaled(1.5D, 1.0D, 1.0D);
//        GlStateManager.translatef(-this.sideModels4.offsetX, -this.sideModels4.offsetY, -this.sideModels4.offsetZ);
//        GlStateManager.translatef(-this.sideModels4.rotationPointX * f5, -this.sideModels4.rotationPointY * f5, -this.sideModels4.rotationPointZ * f5);
//        this.sideModels4.render(f5);
//        GlStateManager.popMatrix();
//        this.sideModelsS3.render(f5);
//        GlStateManager.pushMatrix();
//        GlStateManager.translatef(this.sideModelsB.offsetX, this.sideModelsB.offsetY, this.sideModelsB.offsetZ);
//        GlStateManager.translatef(this.sideModelsB.rotationPointX * f5, this.sideModelsB.rotationPointY * f5, this.sideModelsB.rotationPointZ * f5);
//        GlStateManager.scaled(1.5D, 1.0D, 1.2D);
//        GlStateManager.translatef(-this.sideModelsB.offsetX, -this.sideModelsB.offsetY, -this.sideModelsB.offsetZ);
//        GlStateManager.translatef(-this.sideModelsB.rotationPointX * f5, -this.sideModelsB.rotationPointY * f5, -this.sideModelsB.rotationPointZ * f5);
//        this.sideModelsB.render(f5);
//        GlStateManager.popMatrix();
//        GlStateManager.pushMatrix();
//        GlStateManager.translatef(this.sideModelsT.offsetX, this.sideModelsT.offsetY, this.sideModelsT.offsetZ);
//        GlStateManager.translatef(this.sideModelsT.rotationPointX * f5, this.sideModelsT.rotationPointY * f5, this.sideModelsT.rotationPointZ * f5);
//        GlStateManager.scaled(1.35D, 1.0D, 1.1D);
//        GlStateManager.translatef(-this.sideModelsT.offsetX, -this.sideModelsT.offsetY, -this.sideModelsT.offsetZ);
//        GlStateManager.translatef(-this.sideModelsT.rotationPointX * f5, -this.sideModelsT.rotationPointY * f5, -this.sideModelsT.rotationPointZ * f5);
//        this.sideModelsT.render(f5);
//        GlStateManager.popMatrix();
//    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
