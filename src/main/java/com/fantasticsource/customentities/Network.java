package com.fantasticsource.customentities;

import com.fantasticsource.customentities.client.gui.LivingEntityGUI;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.FMLCommonHandler;
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
        WRAPPER.registerMessage(CreateLivingEntityPacketHandler.class, CreateLivingEntityPacket.class, discriminator++, Side.SERVER);
    }


    public static class OpenLivingEntityGUIPacket implements IMessage
    {
        public boolean hasID;
        public int entityID;

        public int homeDimension;
        public Vec3d homePos, homeLookPos;
        public float eyeHeight;

        public float maxHP;


        public OpenLivingEntityGUIPacket()
        {
            //Required
        }

        public OpenLivingEntityGUIPacket(EntityLiving living)
        {
            hasID = true;
            entityID = living.getEntityId();

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
            eyeHeight = living.getEyeHeight();


            maxHP = living.getMaxHealth();
        }

        public OpenLivingEntityGUIPacket(EntityPlayerMP player)
        {
            hasID = false;

            homeDimension = player.dimension;
            eyeHeight = CustomLivingEntity.DEFAULT_EYE_HEIGHT;

            maxHP = 20;

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
        }

        public OpenLivingEntityGUIPacket(Vec3d homePos, EntityPlayerMP player)
        {
            hasID = false;

            homeDimension = player.dimension;
            this.homePos = homePos;
            eyeHeight = CustomLivingEntity.DEFAULT_EYE_HEIGHT;

            maxHP = 20;

            //Create at the clicked position...
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
        }


        @Override
        public void toBytes(ByteBuf buf)
        {
            buf.writeBoolean(hasID);
            if (hasID) buf.writeInt(entityID);

            buf.writeInt(homeDimension);
            buf.writeDouble(homePos.x);
            buf.writeDouble(homePos.y);
            buf.writeDouble(homePos.z);
            buf.writeDouble(homeLookPos.x);
            buf.writeDouble(homeLookPos.y);
            buf.writeDouble(homeLookPos.z);
            buf.writeFloat(eyeHeight);

            buf.writeFloat(maxHP);
        }

        @Override
        public void fromBytes(ByteBuf buf)
        {
            hasID = buf.readBoolean();
            if (hasID) entityID = buf.readInt();

            homeDimension = buf.readInt();
            homePos = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
            homeLookPos = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
            eyeHeight = buf.readFloat();

            maxHP = buf.readFloat();
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


    public static class CreateLivingEntityPacket implements IMessage
    {
        public boolean hasID;
        public int entityID;

        public int homeDimension;
        public Vec3d homePos, homeLookPos;
        public float eyeHeight;

        public float maxHP;


        public CreateLivingEntityPacket()
        {
            //Required
        }

        public CreateLivingEntityPacket(boolean dummyBool)
        {
            homeDimension = LivingEntityGUI.homeDimension;
            homePos = LivingEntityGUI.homePos;
            homeLookPos = LivingEntityGUI.homeLookPos;
            eyeHeight = LivingEntityGUI.eyeHeight;

            maxHP = LivingEntityGUI.maxHP;
        }

        @Override
        public void toBytes(ByteBuf buf)
        {
            buf.writeBoolean(hasID);
            if (hasID) buf.writeInt(entityID);

            buf.writeInt(homeDimension);
            buf.writeDouble(homePos.x);
            buf.writeDouble(homePos.y);
            buf.writeDouble(homePos.z);
            buf.writeDouble(homeLookPos.x);
            buf.writeDouble(homeLookPos.y);
            buf.writeDouble(homeLookPos.z);
            buf.writeFloat(eyeHeight);

            buf.writeFloat(maxHP);
        }

        @Override
        public void fromBytes(ByteBuf buf)
        {
            hasID = buf.readBoolean();
            if (hasID) entityID = buf.readInt();

            homeDimension = buf.readInt();
            homePos = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
            homeLookPos = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
            eyeHeight = buf.readFloat();

            maxHP = buf.readFloat();
        }
    }

    public static class CreateLivingEntityPacketHandler implements IMessageHandler<CreateLivingEntityPacket, IMessage>
    {
        @Override
        public IMessage onMessage(CreateLivingEntityPacket packet, MessageContext ctx)
        {
            if (ctx.getServerHandler().player.capabilities.isCreativeMode) FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> CustomLivingEntity.handlePacket(packet));
            return null;
        }
    }
}
