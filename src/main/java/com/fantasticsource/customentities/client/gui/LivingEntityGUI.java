package com.fantasticsource.customentities.client.gui;

import com.fantasticsource.customentities.Network;
import com.fantasticsource.mctools.gui.GUILeftClickEvent;
import com.fantasticsource.mctools.gui.GUIScreen;
import com.fantasticsource.mctools.gui.guielements.GUIElement;
import com.fantasticsource.mctools.gui.guielements.rect.GUIGradientBorder;
import com.fantasticsource.mctools.gui.guielements.rect.GUIGradientRect;
import com.fantasticsource.mctools.gui.guielements.rect.GUIRectElement;
import com.fantasticsource.mctools.gui.guielements.rect.GUITextRect;
import com.fantasticsource.mctools.gui.guielements.rect.view.GUIRectTabView;
import com.fantasticsource.mctools.gui.guielements.rect.view.GUIRectView;
import com.fantasticsource.tools.datastructures.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LivingEntityGUI extends GUIScreen
{
    private static final LivingEntityGUI GUI = new LivingEntityGUI();
    private static final Color
            GRAY_2 = new Color(0x777777FF),
            GRAY_1 = new Color(0xAAAAAAFF),
            WHITE = new Color(0xFFFFFFFF),
            BLANK = new Color(0),
            T_BLACK = new Color(0xAA),
            T_GREEN = new Color(0x002200AA);

    public static int homeDimension;
    public static Vec3d homePos, homeLookPos;
    public static float eyeHeight;

    public static float maxHP;


    private static GUIElement createElement;

    public static void show(Network.OpenLivingEntityGUIPacket packet)
    {
        homeDimension = packet.homeDimension;
        homePos = packet.homePos;
        homeLookPos = packet.homeLookPos;
        eyeHeight = packet.eyeHeight;

        maxHP = packet.maxHP;

        Minecraft.getMinecraft().displayGuiScreen(GUI);
    }

    @SubscribeEvent
    public static void mouseClick(GUILeftClickEvent event)
    {
        if (event.getElement() == createElement)
        {
            Minecraft.getMinecraft().player.closeScreen();
            Network.WRAPPER.sendToServer(new Network.CreateLivingEntityPacket(true));
        }
    }

    @Override
    public void initGui()
    {
        guiElements.clear();

        guiElements.add(new GUIGradientRect(this, 0, 0, 1, 1, T_BLACK, T_BLACK, T_GREEN, T_GREEN));

        //Tab buttons
        GUIRectElement[] tabs = new GUIRectElement[]
                {
                        new GUIGradientBorder(this, 0, 0, 0, 0, 1d / 120, WHITE, BLANK),
                        new GUIGradientBorder(this, 0, 0, 0, 0, 1d / 120, WHITE, BLANK),
                        new GUIGradientBorder(this, 0, 0, 0, 0, 1d / 120, WHITE, BLANK),
                        new GUIGradientBorder(this, 0, 0, 0, 0, 1d / 120, WHITE, BLANK),
                        new GUIGradientBorder(this, 0, 0, 0, 0, 1d / 120, WHITE, BLANK),
                        new GUIGradientBorder(this, 0, 0, 0, 0, 1d / 120, WHITE, BLANK),
                        new GUIGradientBorder(this, 0, 0, 0, 0, 1d / 120, WHITE, BLANK),
                };
        tabs[0].add(new GUITextRect(this, 0, 0, "File", GRAY_2, GRAY_1, WHITE));
        tabs[1].add(new GUITextRect(this, 0, 0, "Main", GRAY_2, GRAY_1, WHITE));
        tabs[2].add(new GUITextRect(this, 0, 0, "Inventory", GRAY_2, GRAY_1, WHITE));
        tabs[3].add(new GUITextRect(this, 0, 0, "Spawning", GRAY_2, GRAY_1, WHITE));
        tabs[4].add(new GUITextRect(this, 0, 0, "AI", GRAY_2, GRAY_1, WHITE));
        tabs[5].add(new GUITextRect(this, 0, 0, "Physics & Rendering", GRAY_2, GRAY_1, WHITE));
        tabs[6].add(new GUITextRect(this, 0, 0, "Attributes & Potions", GRAY_2, GRAY_1, WHITE));
        double d = 0;
        for (int i = 0; i < tabs.length; i++)
        {
            GUIElement element = tabs[i];
            element.x = d;
            GUIElement subElement = element.get(0);
            element.linkMouseActivity(subElement);
            element.width = subElement.width;
            element.height = subElement.height;
            d += element.width;
        }
        d = (1 - d) / tabs.length;
        for (int i = 0; i < tabs.length; i++)
        {
            GUIElement element = tabs[i];
            element.x += d * i;
            element.width += d;
            element.height += 0.01;
            element.get(0).x += d / 2;
            element.get(0).y += 0.005 + 1d / width;
        }
        d = tabs[0].height;

        //Tab views
        GUIRectView[] tabViews = new GUIRectView[]
                {
                        new GUIRectView(this, 0, d, 1, 1 - d),
                        new GUIRectView(this, 0, d, 1, 1 - d),
                        new GUIRectView(this, 0, d, 1, 1 - d),
                        new GUIRectView(this, 0, d, 1, 1 - d),
                        new GUIRectView(this, 0, d, 1, 1 - d),
                        new GUIRectView(this, 0, d, 1, 1 - d),
                        new GUIRectView(this, 0, d, 1, 1 - d),
                };

        //Main tabview element
        GUIRectTabView tabView = new GUIRectTabView(this, 0, 0, 1, 1, tabs, tabViews);
        guiElements.add(tabView);

        createElement = new GUITextRect(this, 0, 0, "Create", GRAY_2, GRAY_1, WHITE);
        tabViews[0].add(createElement);
    }
}
