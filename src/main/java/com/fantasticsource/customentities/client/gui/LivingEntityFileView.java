package com.fantasticsource.customentities.client.gui;

import com.fantasticsource.customentities.Network;
import com.fantasticsource.mctools.gui.GUILeftClickEvent;
import com.fantasticsource.mctools.gui.GUIScreen;
import com.fantasticsource.mctools.gui.guielements.GUIElement;
import com.fantasticsource.mctools.gui.guielements.rect.text.GUITextButton;
import com.fantasticsource.mctools.gui.guielements.rect.view.GUIRectView;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.fantasticsource.customentities.client.gui.LivingEntityGUI.GREEN;
import static com.fantasticsource.customentities.client.gui.LivingEntityGUI.packet;

public class LivingEntityFileView extends GUIRectView
{
    private static GUIElement createElement;

    static
    {
        MinecraftForge.EVENT_BUS.register(LivingEntityFileView.class);
    }

    public LivingEntityFileView(GUIScreen screen, double x, double y, double width, double height, Network.OpenLivingEntityGUIPacket packet)
    {
        super(screen, x, y, width, height);

        createElement = new GUITextButton(screen, 0, 0, "Create", GREEN);
        add(createElement);
    }

    @SubscribeEvent
    public static void mouseClick(GUILeftClickEvent event)
    {
        if (event.getElement() == createElement)
        {
            Minecraft.getMinecraft().player.closeScreen();

            packet.name = LivingEntityMainView.nameElement.input.text;

            Network.WRAPPER.sendToServer(new Network.ApplyToLivingEntityPacket(packet));
        }
    }
}
