package com.fantasticsource.customentities.client.gui;

import com.fantasticsource.customentities.Network;
import com.fantasticsource.mctools.gui.GUIScreen;
import com.fantasticsource.mctools.gui.element.view.GUIView;

public class LivingEntityInventoryView extends GUIView
{
    public LivingEntityInventoryView(GUIScreen screen, double x, double y, double width, double height, Network.OpenLivingEntityGUIPacket packet)
    {
        super(screen, x, y, width, height);
    }
}
