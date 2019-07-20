package com.fantasticsource.customentities.client.gui;

import com.fantasticsource.customentities.Network;
import com.fantasticsource.mctools.gui.GUIScreen;
import net.minecraft.client.Minecraft;

public class LivingEntityGUI extends GUIScreen
{
    private static final LivingEntityGUI GUI = new LivingEntityGUI();

    public static void show(Network.OpenLivingEntityGUIPacket packet)
    {
        Minecraft.getMinecraft().displayGuiScreen(GUI);
    }

    @Override
    public void initGui()
    {
        super.initGui();

        //TODO
    }
}
