package com.fantasticsource.customentities.client.gui;

import com.fantasticsource.customentities.Network;
import com.fantasticsource.mctools.gui.GUILeftClickEvent;
import com.fantasticsource.mctools.gui.GUIScreen;
import com.fantasticsource.mctools.gui.guielements.GUIElement;
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
            BACKGROUND = new Color(0x77),
            FOREGROUND = new Color(0x777777FF),
            MOUSEOVER = new Color(0xAAAAAAFF),
            ACTIVE = new Color(0xFFFFFFFF);

    public static int homeDimension;
    public static Vec3d homePos, homeLookPos;
    public static float eyeHeight;

    public static float maxHP;

    private static boolean ready = false;


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
        if (!ready)
        {
            ready = true;

            GUIRectElement[] tabs = new GUIRectElement[]
                    {
                            new GUITextRect(this, 0, 0, "File", FOREGROUND, MOUSEOVER, ACTIVE),
                            new GUITextRect(this, 0, 0, "Main", FOREGROUND, MOUSEOVER, ACTIVE),
                            new GUITextRect(this, 0, 0, "Inventory", FOREGROUND, MOUSEOVER, ACTIVE),
                            new GUITextRect(this, 0, 0, "Spawning", FOREGROUND, MOUSEOVER, ACTIVE),
                            new GUITextRect(this, 0, 0, "AI", FOREGROUND, MOUSEOVER, ACTIVE),
                            new GUITextRect(this, 0, 0, "Physics & Rendering", FOREGROUND, MOUSEOVER, ACTIVE),
                            new GUITextRect(this, 0, 0, "Attributes & Potions", FOREGROUND, MOUSEOVER, ACTIVE),
                    };
            double xx = 0;
            for (GUIRectElement element : tabs)
            {
                element.x = xx;
                xx += element.width;
            }
            GUIRectView[] tabViews = new GUIRectView[]
                    {
                            new GUIRectView(this, 0, 0, 1, 1),
                            new GUIRectView(this, 0, 0, 1, 1),
                            new GUIRectView(this, 0, 0, 1, 1),
                            new GUIRectView(this, 0, 0, 1, 1),
                            new GUIRectView(this, 0, 0, 1, 1),
                            new GUIRectView(this, 0, 0, 1, 1),
                            new GUIRectView(this, 0, 0, 1, 1),
                    };
            tabViews[0].children.add(new GUIGradientRect(this, 0, 0, 1, 1, new Color(0xFF), new Color(0xFF), new Color(0xFF), new Color(0xFF)));
            tabViews[1].children.add(new GUIGradientRect(this, 0, 0, 1, 1, new Color(0xFF000055), new Color(0xFF000055), new Color(0xFF000055), new Color(0xFF000055)));
            tabViews[2].children.add(new GUIGradientRect(this, 0, 0, 1, 1, new Color(0x00FF0055), new Color(0x00FF0055), new Color(0x00FF0055), new Color(0x00FF0055)));
            tabViews[3].children.add(new GUIGradientRect(this, 0, 0, 1, 1, new Color(0x666666FF), new Color(0x666666FF), new Color(0x666666FF), new Color(0x666666FF)));
            tabViews[4].children.add(new GUIGradientRect(this, 0, 0, 1, 1, new Color(0x888888FF), new Color(0x888888FF), new Color(0x888888FF), new Color(0x888888FF)));
            tabViews[5].children.add(new GUIGradientRect(this, 0, 0, 1, 1, new Color(0xAAAAAAFF), new Color(0xAAAAAAFF), new Color(0xAAAAAAFF), new Color(0xAAAAAAFF)));
            tabViews[6].children.add(new GUIGradientRect(this, 0, 0, 1, 1, new Color(0xCCCCCCFF), new Color(0xCCCCCCFF), new Color(0xCCCCCCFF), new Color(0xCCCCCCFF)));

            guiElements.add(new GUIRectTabView(this, 0, 0, 1, 1, tabs, tabViews));

            createElement = new GUITextRect(this, 0, 0.5, "Create", FOREGROUND, MOUSEOVER, ACTIVE);
            guiElements.add(createElement);
        }
    }
}
