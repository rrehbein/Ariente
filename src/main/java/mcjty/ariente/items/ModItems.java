package mcjty.ariente.items;

import mcjty.ariente.items.armor.PowerSuit;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class ModItems {

    public static GenericItem platinumIngot;
    public static GenericItem lithiumIngot;
    public static GenericItem manganeseIngot;
    public static GenericItem silverIngot;

    public static GenericItem negariteDust;
    public static GenericItem posiriteDust;

    public static EnergySabreItem energySabre;
    public static EnhancedEnergySabreItem enhancedEnergySabreItem;
    public static KeyCardItem keyCardItem;
    public static DirtyDiamondItem dirtyDiamondItem;

    public static PowerSuit powerSuitHelmet;
    public static PowerSuit powerSuitChest;
    public static PowerSuit powerSuitLegs;
    public static PowerSuit powerSuitBoots;

    public static void init() {
        platinumIngot = new GenericItem("ingot_platinum");
        lithiumIngot = new GenericItem("ingot_lithium");
        manganeseIngot = new GenericItem("ingot_manganese");
        silverIngot = new GenericItem("ingot_silver");

        negariteDust = new GenericItem("dust_negarite");
        posiriteDust = new GenericItem("dust_posirite");

        energySabre = new EnergySabreItem("energy_sabre");
        enhancedEnergySabreItem = new EnhancedEnergySabreItem("enhanced_energy_sabre");
        keyCardItem = new KeyCardItem();

        dirtyDiamondItem = new DirtyDiamondItem();

        powerSuitBoots = new PowerSuit(EntityEquipmentSlot.FEET);
        powerSuitChest = new PowerSuit(EntityEquipmentSlot.CHEST);
        powerSuitHelmet = new PowerSuit(EntityEquipmentSlot.HEAD);
        powerSuitLegs = new PowerSuit(EntityEquipmentSlot.LEGS);
    }

    public static void initOreDict() {
        OreDictionary.registerOre("ingotSilver", silverIngot);
        OreDictionary.registerOre("ingotPlatinum", platinumIngot);
        OreDictionary.registerOre("ingotManganese", manganeseIngot);
        OreDictionary.registerOre("ingotLithium", lithiumIngot);
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        platinumIngot.initModel();
        lithiumIngot.initModel();
        manganeseIngot.initModel();
        silverIngot.initModel();
        negariteDust.initModel();
        posiriteDust.initModel();

        energySabre.initModel();
        enhancedEnergySabreItem.initModel();
        keyCardItem.initModel();

        dirtyDiamondItem.initModel();

        ModelLoader.setCustomModelResourceLocation(powerSuitBoots, 0, new ModelResourceLocation(powerSuitBoots.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(powerSuitChest, 0, new ModelResourceLocation(powerSuitChest.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(powerSuitHelmet, 0, new ModelResourceLocation(powerSuitHelmet.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(powerSuitLegs, 0, new ModelResourceLocation(powerSuitLegs.getRegistryName(), "inventory"));
    }

}
