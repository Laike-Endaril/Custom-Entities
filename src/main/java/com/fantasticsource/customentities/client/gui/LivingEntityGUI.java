package com.fantasticsource.customentities.client.gui;

import com.fantasticsource.customentities.Network;
import com.fantasticsource.mctools.gui.GUIScreen;
import com.fantasticsource.mctools.gui.guielements.rect.GUIGradientRect;
import com.fantasticsource.mctools.gui.guielements.rect.view.GUIRectTabView;
import com.fantasticsource.tools.datastructures.Color;
import net.minecraft.client.Minecraft;

public class LivingEntityGUI extends GUIScreen
{
    public static final Color
            T_BLACK = new Color(0x99),
            GREEN = new Color(0x00FF00FF);


    public static final LivingEntityGUI GUI = new LivingEntityGUI();

    public static Network.OpenLivingEntityGUIPacket packet;

    public static void show(Network.OpenLivingEntityGUIPacket packet)
    {
        LivingEntityGUI.packet = packet;
        Minecraft.getMinecraft().displayGuiScreen(GUI);
    }

    @Override
    public void init()
    {
        //Background
        guiElements.add(new GUIGradientRect(this, 0, 0, 1, 1, T_BLACK, T_BLACK, T_BLACK, T_BLACK));

        //Main tabview element
        GUIRectTabView tabView = new GUIRectTabView(this, 0, 0, 1, 1, "File", "Main", "Inventory", "Spawn", "AI", "Physics & Render", "Potions & Attributes", "Events", "NBT");
        guiElements.add(tabView);

        //Add custom views inside tab views
        tabView.tabViews[0].add(new LivingEntityFileView(this, 0, 0, 1, 1, packet));
        tabView.tabViews[1].add(new LivingEntityMainView(this, 0, 0, 1, 1, packet));
        tabView.tabViews[2].add(new LivingEntityInventoryView(this, 0, 0, 1, 1, packet));
        tabView.tabViews[3].add(new LivingEntitySpawnView(this, 0, 0, 1, 1, packet));
        tabView.tabViews[4].add(new LivingEntityAIView(this, 0, 0, 1, 1, packet));
        tabView.tabViews[5].add(new LivingEntityPhysicsRenderView(this, 0, 0, 1, 1, packet));
        tabView.tabViews[6].add(new LivingEntityPotionsAttributesView(this, 0, 0, 1, 1, packet));
        tabView.tabViews[7].add(new LivingEntityEventsView(this, 0, 0, 1, 1, packet));
        tabView.tabViews[8].add(new LivingEntityNBTView(this, 0, 0, 1, 1, packet));
    }
}
