package com.fantasticsource.customentities;

import com.fantasticsource.customentities.client.gui.LivingEntityGUI;
import com.fantasticsource.customentities.data.LivingData;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Network
{
    public static final SimpleNetworkWrapper WRAPPER = new SimpleNetworkWrapper(CustomEntities.MODID);
    private static int discriminator = 0;

    public static void init()
    {
        WRAPPER.registerMessage(OpenLivingEntityGUIPacketHandler.class, OpenLivingEntityGUIPacket.class, discriminator++, Side.CLIENT);
        WRAPPER.registerMessage(CreateLivingEntityPacketHandler.class, ApplyToLivingEntityPacket.class, discriminator++, Side.SERVER);
    }


    public static abstract class IEntityMessage extends LivingData implements IMessage
    {
        //Backend
        boolean hasEntityID;
        int entityID;

        @Override
        public void toBytes(ByteBuf buf)
        {
            //Backend
            buf.writeBoolean(hasEntityID);
            if (hasEntityID) buf.writeInt(entityID);

            //Main
            ByteBufUtils.writeUTF8String(buf, name);
            buf.writeInt(homeDimension);
            buf.writeDouble(homePos.x);
            buf.writeDouble(homePos.y);
            buf.writeDouble(homePos.z);
            buf.writeDouble(homeLookPos.x);
            buf.writeDouble(homeLookPos.y);
            buf.writeDouble(homeLookPos.z);
            buf.writeFloat(maxHP);

            //Physics and Render
            buf.writeFloat(eyeHeight);
        }

        @Override
        public void fromBytes(ByteBuf buf)
        {
            //Backend
            hasEntityID = buf.readBoolean();
            if (hasEntityID) entityID = buf.readInt();

            //Main
            name = ByteBufUtils.readUTF8String(buf);
            homeDimension = buf.readInt();
            homePos = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
            homeLookPos = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
            maxHP = buf.readFloat();

            //Physics and Render
            eyeHeight = buf.readFloat();
        }
    }

    public static class OpenLivingEntityGUIPacket extends IEntityMessage
    {
        public OpenLivingEntityGUIPacket()
        {
            //Required
        }

        public OpenLivingEntityGUIPacket(EntityLiving living)
        {
            //Backend
            hasEntityID = true;
            entityID = living.getEntityId();

            //Main
            name = living.getName();
            homeDimension = living.dimension;
            if (living instanceof CustomLivingEntity)
            {
                CustomLivingEntity custom = (CustomLivingEntity) living;
                homePos = custom.homePos;
                homeLookPos = custom.homeLookPos;
            }
            else
            {
                homePos = new Vec3d(living.getPosition());
                homeLookPos = homePos.add(living.getLookVec());
            }
            maxHP = living.getMaxHealth();

            //Physics and Render
            eyeHeight = living.getEyeHeight();
        }

        public OpenLivingEntityGUIPacket(EntityPlayerMP player)
        {
            //Backend
            hasEntityID = false;

            //Main
            name = "Custom Living Entity";
            homeDimension = player.dimension;
            if (player.isSneaking())
            {
                //Create at the player's position, looking the same direction as the player
                homePos = player.getPositionVector().addVector(0, player.eyeHeight - eyeHeight, 0);
                homeLookPos = homePos.add(player.getLookVec());
            }
            else
            {
                //Create at a position a little in front of the player, looking into the player's eyes
                homeLookPos = player.getPositionVector().addVector(0, player.eyeHeight, 0);
                homePos = homeLookPos.add(player.getLookVec().addVector(0, player.eyeHeight - eyeHeight, 0));
            }
            maxHP = 20;

            //Physics and Render
            eyeHeight = CustomLivingEntity.DEFAULT_EYE_HEIGHT;
        }

        public OpenLivingEntityGUIPacket(Vec3d homePos, EntityPlayerMP player)
        {
            //Backend
            hasEntityID = false;

            //Main
            name = "Custom Living Entity";
            homeDimension = player.dimension;
            this.homePos = homePos; //Create at the clicked position...
            if (player.isSneaking())
            {
                //...looking into the player's eyes
                homeLookPos = player.getPositionVector().addVector(0, player.eyeHeight, 0);
            }
            else
            {
                //...looking in the direction of the player, except for the vertical part (not looking up or down)
                homeLookPos = player.getPositionVector().addVector(0, CustomLivingEntity.DEFAULT_EYE_HEIGHT, 0);
            }
            maxHP = 20;

            //Physics and Render
            eyeHeight = CustomLivingEntity.DEFAULT_EYE_HEIGHT;
        }
    }

    public static class OpenLivingEntityGUIPacketHandler implements IMessageHandler<OpenLivingEntityGUIPacket, IMessage>
    {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(OpenLivingEntityGUIPacket packet, MessageContext ctx)
        {
            Minecraft.getMinecraft().addScheduledTask(() -> LivingEntityGUI.show(packet));
            return null;
        }
    }


    public static class ApplyToLivingEntityPacket extends IEntityMessage
    {
        public ApplyToLivingEntityPacket()
        {
            //Required
        }

        public ApplyToLivingEntityPacket(OpenLivingEntityGUIPacket packet)
        {
            //Backend
            hasEntityID = packet.hasEntityID;
            if (hasEntityID) entityID = packet.entityID;

            //Main
            name = packet.name;
            homeDimension = packet.homeDimension;
            homePos = packet.homePos;
            homeLookPos = packet.homeLookPos;
            maxHP = packet.maxHP;

            //Physics and Render
            eyeHeight = packet.eyeHeight;
        }
    }

    public static class CreateLivingEntityPacketHandler implements IMessageHandler<ApplyToLivingEntityPacket, IMessage>
    {
        @Override
        public IMessage onMessage(ApplyToLivingEntityPacket packet, MessageContext ctx)
        {
            if (ctx.getServerHandler().player.capabilities.isCreativeMode) FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> CustomLivingEntity.handlePacket(packet));
            return null;
        }
    }
}
