package com.fantasticsource.customentities.client.gui;

import com.fantasticsource.customentities.Network;
import com.fantasticsource.mctools.gui.GUIScreen;
import com.fantasticsource.mctools.gui.element.text.GUILabeledTextInput;
import com.fantasticsource.mctools.gui.element.text.filter.FilterNotEmpty;
import com.fantasticsource.mctools.gui.element.view.GUIView;

public class LivingEntityMainView extends GUIView
{
    public static GUILabeledTextInput nameElement;

    public LivingEntityMainView(GUIScreen screen, double x, double y, double width, double height, Network.OpenLivingEntityGUIPacket packet)
    {
        super(screen, x, y, width, height);

        nameElement = new GUILabeledTextInput(screen, 0, 0, "Name: ", packet.name, FilterNotEmpty.INSTANCE);
        add(nameElement);
    }
}
