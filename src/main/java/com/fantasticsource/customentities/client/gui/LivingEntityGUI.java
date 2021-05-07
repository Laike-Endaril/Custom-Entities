package com.fantasticsource.customentities.client.gui;

import com.fantasticsource.customentities.Network;
import com.fantasticsource.mctools.gui.GUIScreen;
import com.fantasticsource.mctools.gui.element.other.GUIGradient;
import com.fantasticsource.mctools.gui.element.view.GUITabView;
import com.fantasticsource.tools.datastructures.Color;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

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
        Keyboard.enableRepeatEvents(true);
    }

    @Override
    public String title()
    {
        return packet.name;
    }

    @Override
    public void init()
    {
        //Background
        root.add(new GUIGradient(this, 0, 0, 1, 1, T_BLACK, T_BLACK, T_BLACK, T_BLACK));

        //Main tabview element
        GUITabView tabView = new GUITabView(this, 0, 0, 1, 1, "File", "Main", "Inventory", "Spawn", "AI", "Physics & Render", "Potions & Attributes", "Events", "NBT");
        root.add(tabView);

        //Add custom views inside tab views
        tabView.tabViews.get(0).add(new LivingEntityFileView(this, 0, 0, 1, 1, packet));
        tabView.tabViews.get(1).add(new LivingEntityMainView(this, 0, 0, 1, 1, packet));
        tabView.tabViews.get(2).add(new LivingEntityInventoryView(this, 0, 0, 1, 1, packet));
        tabView.tabViews.get(3).add(new LivingEntitySpawnView(this, 0, 0, 1, 1, packet));
        tabView.tabViews.get(4).add(new LivingEntityAIView(this, 0, 0, 1, 1, packet));
        tabView.tabViews.get(5).add(new LivingEntityPhysicsRenderView(this, 0, 0, 1, 1, packet));
        tabView.tabViews.get(6).add(new LivingEntityPotionsAttributesView(this, 0, 0, 1, 1, packet));
        tabView.tabViews.get(7).add(new LivingEntityEventsView(this, 0, 0, 1, 1, packet));
        tabView.tabViews.get(8).add(new LivingEntityNBTView(this, 0, 0, 1, 1, packet));
    }
}
