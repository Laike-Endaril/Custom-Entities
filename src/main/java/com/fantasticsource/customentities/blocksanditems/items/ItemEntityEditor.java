package com.fantasticsource.customentities.blocksanditems.items;

import com.fantasticsource.customentities.CustomEntities;
import com.fantasticsource.customentities.blocksanditems.BlocksAndItems;
import net.minecraft.item.Item;

public class ItemEntityEditor extends Item
{
    public ItemEntityEditor()
    {
        setCreativeTab(BlocksAndItems.creativeTab);

        setUnlocalizedName(CustomEntities.MODID + ":entityeditor");
        setRegistryName("entityeditor");
    }
}
