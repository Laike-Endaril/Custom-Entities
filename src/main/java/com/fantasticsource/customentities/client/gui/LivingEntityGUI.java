package com.fantasticsource.customentities.client.gui;

import com.fantasticsource.customentities.Network;
import com.fantasticsource.mctools.gui.GUILeftClickEvent;
import com.fantasticsource.mctools.gui.GUIScreen;
import com.fantasticsource.mctools.gui.guielements.GUIElement;
import com.fantasticsource.mctools.gui.guielements.rect.GUITextRect;
import com.fantasticsource.mctools.gui.guielements.rect.GradientRect;
import com.fantasticsource.tools.datastructures.Color;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LivingEntityGUI extends GUIScreen
{
    private static final LivingEntityGUI GUI = new LivingEntityGUI();
    private static final Color
            BACKGROUND = new Color(0x77),
            FOREGROUND = new Color(0x777777FF),
            MOUSEOVER = new Color(0xAAAAAAFF),
            ACTIVE = new Color(0xFFFFFFFF);

    private static GUIElement createElement;


    public static void show(Network.OpenLivingEntityGUIPacket packet)
    {
        Minecraft.getMinecraft().displayGuiScreen(GUI);
    }

    @SubscribeEvent
    public static void mouseClick(GUILeftClickEvent event)
    {
        if (event.getElement() == createElement)
        {
            Minecraft.getMinecraft().player.closeScreen();
            Network.WRAPPER.sendToServer(new Network.CreateLivingEntityPacket(GUI));
        }
    }

    @Override
    public void initGui()
    {
        super.initGui();

        guiElements.add(new GradientRect(this, 0, 0, 1, 1, BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND));

        createElement = new GUITextRect(this, 0, 0, 1, "Create", FOREGROUND, MOUSEOVER, ACTIVE);
        guiElements.add(createElement);
    }
}
