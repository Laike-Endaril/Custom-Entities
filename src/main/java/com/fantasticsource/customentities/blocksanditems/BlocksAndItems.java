package com.fantasticsource.customentities.blocksanditems;

import com.fantasticsource.customentities.CustomEntities;
import com.fantasticsource.customentities.blocksanditems.items.ItemEntityEditor;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class BlocksAndItems
{
    @GameRegistry.ObjectHolder(CustomEntities.MODID + ":entityeditor")
    public static ItemEntityEditor entityeditor;

    public static CreativeTabs creativeTab = new CreativeTabs(CustomEntities.MODID)
    {
        @Override
        public ItemStack getTabIconItem()
        {
            return new ItemStack(entityeditor);
        }

        @Override
        public void displayAllRelevantItems(NonNullList<ItemStack> itemStacks)
        {
            super.displayAllRelevantItems(itemStacks);
        }
    };

    @SubscribeEvent
    public static void itemRegistry(RegistryEvent.Register<Item> event)
    {
        IForgeRegistry<Item> registry = event.getRegistry();
        registry.register(new ItemEntityEditor());
    }

    @SubscribeEvent
    public static void modelRegistry(ModelRegistryEvent event)
    {
        ModelLoader.setCustomModelResourceLocation(entityeditor, 0, new ModelResourceLocation(CustomEntities.MODID + ":entityeditor", "inventory"));
    }
}
