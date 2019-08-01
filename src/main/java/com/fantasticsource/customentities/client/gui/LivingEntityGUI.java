package com.fantasticsource.customentities.client.gui;

import com.fantasticsource.customentities.Network;
import com.fantasticsource.mctools.gui.GUIScreen;
import com.fantasticsource.mctools.gui.guielements.GUIElement;
import com.fantasticsource.mctools.gui.guielements.rect.GUIGradientRect;
import com.fantasticsource.mctools.gui.guielements.rect.GUIRectElement;
import com.fantasticsource.mctools.gui.guielements.rect.GUITextButton;
import com.fantasticsource.mctools.gui.guielements.rect.view.GUIRectTabView;
import com.fantasticsource.mctools.gui.guielements.rect.view.GUIRectView;
import com.fantasticsource.tools.datastructures.Color;
import net.minecraft.client.Minecraft;

public class LivingEntityGUI extends GUIScreen
{
    public static final Color
            GRAY_2 = new Color(0x777777FF),
            GRAY_1 = new Color(0xAAAAAAFF),
            WHITE = new Color(0xFFFFFFFF),
            T_GRAY_2 = new Color(0x77777733),
            T_GRAY_1 = new Color(0xAAAAAA33),
            T_WHITE = new Color(0xFFFFFF33),
            BLANK = new Color(0),
            T_BLACK = new Color(0xDD),
            RED = new Color(0xFF0000FF),
            T_RED = new Color(0xFF000055);
    private static final LivingEntityGUI GUI = new LivingEntityGUI();
    public static Network.OpenLivingEntityGUIPacket packet;


    public static void show(Network.OpenLivingEntityGUIPacket packet)
    {
        LivingEntityGUI.packet = packet;
        Minecraft.getMinecraft().displayGuiScreen(GUI);
    }

    @Override
    public void initGui()
    {
        guiElements.clear();

        guiElements.add(new GUIGradientRect(this, 0, 0, 1, 1, T_BLACK, T_BLACK, T_BLACK, T_BLACK));


        //Tab buttons
        GUIRectElement[] tabs = new GUIRectElement[]
                {
                        new GUITextButton(this, 0, 0, "File", WHITE),
                        new GUITextButton(this, 0, 0, "Main", WHITE),
                        new GUITextButton(this, 0, 0, "Inventory", WHITE),
                        new GUITextButton(this, 0, 0, "Spawning", WHITE),
                        new GUITextButton(this, 0, 0, "AI", WHITE),
                        new GUITextButton(this, 0, 0, "Physics & Rendering", WHITE),
                        new GUITextButton(this, 0, 0, "Potions & Attributes", WHITE),
                };
        double d = 0;
        for (int i = 0; i < tabs.length; i++)
        {
            GUIElement element = tabs[i];
            element.x = d;
            d += element.width;
        }
        d = tabs[0].height;


        //Tab views
        GUIRectView[] tabViews = new GUIRectView[]
                {
                        new LivingEntityFileView(this, 0, d, 1, 1 - d, packet),
                        new LivingEntityMainView(this, 0, d, 1, 1 - d, packet),
                        new LivingEntityInventoryView(this, 0, d, 1, 1 - d, packet),
                        new LivingEntitySpawningView(this, 0, d, 1, 1 - d, packet),
                        new LivingEntityAIView(this, 0, d, 1, 1 - d, packet),
                        new LivingEntityPhysicsRenderView(this, 0, d, 1, 1 - d, packet),
                        new LivingEntityPotionsAttributesView(this, 0, d, 1, 1 - d, packet),
                };


        //Main tabview element
        GUIRectTabView tabView = new GUIRectTabView(this, 0, 0, 1, 1, tabs, tabViews);
        guiElements.add(tabView);
    }
}
