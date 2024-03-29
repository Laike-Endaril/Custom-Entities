package com.fantasticsource.customentities;

import com.fantasticsource.customentities.blocksanditems.BlocksAndItems;
import com.fantasticsource.customentities.blocksanditems.items.ItemEntityEditor;
import com.fantasticsource.customentities.client.RenderCustomLiving;
import com.fantasticsource.customentities.server.EntityTemplates;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;

@Mod(modid = CustomEntities.MODID, name = CustomEntities.NAME, version = CustomEntities.VERSION, dependencies = "required-after:fantasticlib@[1.12.2.021a,)")
public class CustomEntities
{
    public static final String MODID = "customentities";
    public static final String NAME = "Custom Entities";
    public static final String VERSION = "1.12.2.000";

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) throws IOException
    {
        MinecraftForge.EVENT_BUS.register(CustomEntities.class);
        MinecraftForge.EVENT_BUS.register(BlocksAndItems.class);
        MinecraftForge.EVENT_BUS.register(ItemEntityEditor.class);

        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
        {
            //Physical client
            RenderingRegistry.registerEntityRenderingHandler(CustomLivingEntity.class, RenderCustomLiving::new);
        }

        Network.init();

        EntityTemplates.load();
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
        EntityEntryBuilder<?> builder = EntityEntryBuilder.create()
                .entity(CustomLivingEntity.class)
                .name(name)
                .id(new ResourceLocation(name), 1)
                .tracker(64, 3, true);
        event.getRegistry().register(builder.build());
    }
}
