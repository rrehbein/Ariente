package mcjty.ariente.datagen;

import mcjty.ariente.Ariente;
import mcjty.ariente.api.MarbleColor;
import mcjty.ariente.api.TechType;
import mcjty.ariente.blocks.decorative.PatternType;
import mcjty.ariente.setup.Registration;
import mcjty.lib.datagen.BaseRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

import java.util.function.Consumer;

public class Recipes extends BaseRecipeProvider {

    public Recipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        build(consumer, ShapedRecipeBuilder.shaped(Registration.ARIENTE_PEARL.get())
                        .define('m', Items.QUARTZ)
                        .unlockedBy("enderpearl", has(Items.ENDER_PEARL)),
                "mmm", "mom", "mmm");
        build(consumer, ShapedRecipeBuilder.shaped(Registration.CONSTRUCTOR.get())
                        .define('l', Registration.TAG_INGOT_SILVER)
                        .define('s', Registration.TAG_INGOT_PLATINUM)
                        .unlockedBy("silver", has(Registration.INGOT_SILVER.get())),
                "iii", "isi", "lll");
        build(consumer, ShapedRecipeBuilder.shaped(Registration.ENHANCED_ENERGY_SABRE.get())
                        .define('x', Registration.TAG_INGOT_LITHIUM)
                        .define('M', Registration.ENERGY_SABRE.get())
                        .unlockedBy("sabre", has(Registration.ENERGY_SABRE.get())),
                "xxx", "xMx", "xxx");

        for (MarbleColor color : MarbleColor.VALUES) {
            build(consumer, new ResourceLocation(Ariente.MODID, "marble_" + color.getSerializedName() + "_dye"), ShapelessRecipeBuilder.shapeless(Registration.MARBLE_ITEMS.get(color).get())
                    .unlockedBy("marble", has(Registration.MARBLE.get()))
                    .requires(Registration.TAG_MARBLE)
                    .requires(color.getColor().getTag()));
            build(consumer, ShapelessRecipeBuilder.shapeless(Registration.MARBLE_SMOOTH_ITEMS.get(color).get())
                    .unlockedBy("marble", has(Registration.MARBLE.get()))
                    .requires(Registration.MARBLE_ITEMS.get(color).get()));
            build(consumer, ShapedRecipeBuilder.shaped(Registration.MARBLE_BRICKS_ITEMS.get(color).get(), 4)
                            .unlockedBy("marble", has(Registration.MARBLE.get()))
                            .define('m', Registration.MARBLE_ITEMS.get(color).get()),
                    "mm", "mm");
            build(consumer, ShapedRecipeBuilder.shaped(Registration.MARBLE_PILAR_ITEMS.get(color).get(), 2)
                            .unlockedBy("marble", has(Registration.MARBLE.get()))
                            .define('m', Registration.MARBLE_ITEMS.get(color).get()),
                    "m", "m");
            build(consumer, ShapedRecipeBuilder.shaped(Registration.MARBLE_SLAB_ITEMS.get(color).get(), 6)
                            .unlockedBy("marble", has(Registration.MARBLE.get()))
                            .define('m', Registration.MARBLE_ITEMS.get(color).get()),
                    "mmm");
        }

        build(consumer, ShapedRecipeBuilder.shaped(Registration.BLACK_TECH_ITEMS.get(TechType.DARKBLUE_BLUE).get())
                        .unlockedBy("marble", has(Registration.MARBLE.get()))
                        .define('M', Registration.MARBLE_ITEMS.get(MarbleColor.BLACK).get())
                        .define('m', Registration.MARBLE_ITEMS.get(MarbleColor.DARKBLUE).get()),
                "MMM", "MmM", "MMM");
        build(consumer, ShapedRecipeBuilder.shaped(Registration.BLACK_TECH_ITEMS.get(TechType.DARKBLUE_RED).get())
                        .unlockedBy("marble", has(Registration.MARBLE.get()))
                        .define('M', Registration.MARBLE_ITEMS.get(MarbleColor.BLACK).get())
                        .define('m', Registration.MARBLE_ITEMS.get(MarbleColor.RED).get()),
                "MMM", "MmM", "MMM");
        build(consumer, ShapedRecipeBuilder.shaped(Registration.BLACK_TECH_ITEMS.get(TechType.DOTS).get())
                        .unlockedBy("marble", has(Registration.MARBLE.get()))
                        .define('M', Registration.MARBLE_ITEMS.get(MarbleColor.BLACK).get())
                        .define('m', Registration.MARBLE_ITEMS.get(MarbleColor.DARKBLUE).get()),
                "mMm", "MmM", "mMm");
        build(consumer, ShapedRecipeBuilder.shaped(Registration.BLACK_TECH_ITEMS.get(TechType.DOTS2).get())
                        .unlockedBy("marble", has(Registration.MARBLE.get()))
                        .define('M', Registration.MARBLE_ITEMS.get(MarbleColor.BLACK).get())
                        .define('m', Registration.MARBLE_ITEMS.get(MarbleColor.RED).get()),
                "mMm", "MmM", "mMm");
        build(consumer, ShapedRecipeBuilder.shaped(Registration.BLACK_TECH_ITEMS.get(TechType.LINES).get())
                        .unlockedBy("marble", has(Registration.MARBLE.get()))
                        .define('M', Registration.MARBLE_ITEMS.get(MarbleColor.BLACK).get())
                        .define('m', Registration.MARBLE_ITEMS.get(MarbleColor.DARKBLUE).get()),
                "MMM", "mmm", "MMM");
        build(consumer, ShapedRecipeBuilder.shaped(Registration.BLACK_TECH_ITEMS.get(TechType.RED_LINES).get())
                        .unlockedBy("marble", has(Registration.MARBLE.get()))
                        .define('M', Registration.MARBLE_ITEMS.get(MarbleColor.BLACK).get())
                        .define('m', Registration.MARBLE_ITEMS.get(MarbleColor.RED).get()),
                "MMM", "mmm", "MMM");
        build(consumer, ShapedRecipeBuilder.shaped(Registration.BLACK_TECH_ITEMS.get(TechType.LINES_GLOW).get())
                        .unlockedBy("marble", has(Registration.MARBLE.get()))
                        .define('g', Items.GLOWSTONE_DUST)
                        .define('M', Registration.MARBLE_ITEMS.get(MarbleColor.BLACK).get())
                        .define('m', Registration.MARBLE_ITEMS.get(MarbleColor.DARKBLUE).get()),
                "MgM", "mmm", "MMM");
        build(consumer, ShapedRecipeBuilder.shaped(Registration.BLACK_TECH_ITEMS.get(TechType.RED_LINES_GLOW).get())
                        .unlockedBy("marble", has(Registration.MARBLE.get()))
                        .define('g', Items.GLOWSTONE_DUST)
                        .define('M', Registration.MARBLE_ITEMS.get(MarbleColor.BLACK).get())
                        .define('m', Registration.MARBLE_ITEMS.get(MarbleColor.RED).get()),
                "MgM", "mmm", "MMM");
        build(consumer, ShapedRecipeBuilder.shaped(Registration.BLACK_TECH_ITEMS.get(TechType.PANEL).get())
                        .unlockedBy("marble", has(Registration.MARBLE.get()))
                        .define('M', Registration.MARBLE_ITEMS.get(MarbleColor.BLACK).get())
                        .define('S', Registration.MARBLE_ITEMS.get(MarbleColor.DARKBLUE).get())
                        .define('m', Registration.MARBLE_ITEMS.get(MarbleColor.RED).get()),
                "MMM", "Smr", "MMM");
        build(consumer, ShapedRecipeBuilder.shaped(Registration.BLACK_TECH_ITEMS.get(TechType.RED_VAR1).get())
                        .unlockedBy("marble", has(Registration.MARBLE.get()))
                        .define('M', Registration.MARBLE_ITEMS.get(MarbleColor.BLACK).get())
                        .define('m', Registration.MARBLE_ITEMS.get(MarbleColor.RED).get()),
                "MmM", "mmm", "MmM");
        build(consumer, ShapedRecipeBuilder.shaped(Registration.BLACK_TECH_ITEMS.get(TechType.RED_VAR1_ANIM).get())
                        .unlockedBy("marble", has(Registration.MARBLE.get()))
                        .define('M', Registration.MARBLE_ITEMS.get(MarbleColor.BLACK).get())
                        .define('m', Registration.MARBLE_ITEMS.get(MarbleColor.RED).get()),
                "MmM", "mmm", "MMM");
        build(consumer, ShapedRecipeBuilder.shaped(Registration.BLACK_TECH_ITEMS.get(TechType.VAR1).get())
                        .unlockedBy("marble", has(Registration.MARBLE.get()))
                        .define('M', Registration.MARBLE_ITEMS.get(MarbleColor.BLACK).get())
                        .define('m', Registration.MARBLE_ITEMS.get(MarbleColor.DARKBLUE).get()),
                "MmM", "mmm", "MmM");
        build(consumer, ShapedRecipeBuilder.shaped(Registration.BLACK_TECH_ITEMS.get(TechType.VAR1_ANIM).get())
                        .unlockedBy("marble", has(Registration.MARBLE.get()))
                        .define('M', Registration.MARBLE_ITEMS.get(MarbleColor.BLACK).get())
                        .define('m', Registration.MARBLE_ITEMS.get(MarbleColor.DARKBLUE).get()),
                "MmM", "mmm", "MMM");
        build(consumer, ShapedRecipeBuilder.shaped(Registration.PATTERN_ITEMS.get(PatternType.DOTS).get())
                        .unlockedBy("marble", has(Registration.MARBLE.get()))
                        .define('M', Registration.MARBLE_ITEMS.get(MarbleColor.BLACK).get())
                        .define('m', Registration.MARBLE_ITEMS.get(MarbleColor.GRAY).get()),
                "MmM", "mMm", "MmM");
        build(consumer, ShapedRecipeBuilder.shaped(Registration.PATTERN_ITEMS.get(PatternType.LINES).get())
                        .unlockedBy("marble", has(Registration.MARBLE.get()))
                        .define('M', Registration.MARBLE_ITEMS.get(MarbleColor.BLACK).get())
                        .define('m', Registration.MARBLE_ITEMS.get(MarbleColor.GRAY).get()),
                "mMm", "MmM", "mMm");
    }
}
