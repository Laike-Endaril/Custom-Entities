package com.fantasticsource.customentities;

import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;

@Mod(modid = CustomEntities.MODID, name = CustomEntities.NAME, version = CustomEntities.VERSION, dependencies = "required-after:fantasticlib@[1.12.2.021,)")
public class CustomEntities
{
    public static final String MODID = "customentities";
    public static final String NAME = "Custom Entities";
    public static final String VERSION = "1.12.2.000";

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(CustomEntities.class);
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
        EntityEntry entry = new EntityEntry(CustomLivingEntity.class, name);
        entry.setRegistryName(name);
        entry.setEgg(new EntityList.EntityEggInfo(new ResourceLocation(name), 0, 0xFFFFFFFF));
        event.getRegistry().register(entry);
    }
}
