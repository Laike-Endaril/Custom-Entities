package com.fantasticsource.customentities;

import com.fantasticsource.customentities.blocksanditems.BlocksAndItems;
import com.fantasticsource.customentities.blocksanditems.items.ItemLivingEntityEditor;
import com.fantasticsource.customentities.client.gui.LivingEntityGUI;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;

@Mod(modid = CustomEntities.MODID, name = CustomEntities.NAME, version = CustomEntities.VERSION, dependencies = "required-after:fantasticlib@[1.12.2.021a,)")
public class CustomEntities
{
    public static final String MODID = "customentities";
    public static final String NAME = "Custom Entities";
    public static final String VERSION = "1.12.2.000";

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(CustomEntities.class);
        MinecraftForge.EVENT_BUS.register(BlocksAndItems.class);
        MinecraftForge.EVENT_BUS.register(ItemLivingEntityEditor.class);
        MinecraftForge.EVENT_BUS.register(LivingEntityGUI.class);

        Network.init();
    }

    @SubscribeEvent
    public static void saveConfig(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(MODID)) ConfigManager.sync(MODID, Config.Type.INSTANCE);
    }

    @SubscribeEvent
    public static void registerEntity(RegistryEvent.Register<EntityEntry> event)
    {
        String name = MODID + ":living";
        event.getRegistry().register(new EntityEntry(CustomLivingEntity.class, name).setRegistryName(name));
    }

//    @SubscribeEvent
//    public static void test(EntityJoinWorldEvent event)
//    {
//        if (event.getEntity() instanceof CustomLivingEntity)
//        {
//            CustomLivingEntity custom = (CustomLivingEntity) event.getEntity();
//            System.out.println(custom.eyeHeight);
//        }
//    }
}
