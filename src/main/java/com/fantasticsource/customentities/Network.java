package com.fantasticsource.customentities;

import com.fantasticsource.customentities.client.gui.LivingEntityGUI;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.DimensionManager;
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
        public int homeDimension;
        public Vec3d homePos, homeLookPos;

        public float maxHP;


        public OpenLivingEntityGUIPacket()
        {
            //Required
        }

        public OpenLivingEntityGUIPacket(EntityLiving living)
        {
            maxHP = living.getMaxHealth();

            if (living instanceof CustomLivingEntity)
            {
                CustomLivingEntity custom = (CustomLivingEntity) living;
                homePos = custom.homePos;
                homeLookPos = custom.homeLookPos;
            }
            else if (living instanceof EntityCreature)
            {
                EntityCreature creature = (EntityCreature) living;
                homePos = new Vec3d(creature.getHomePosition());
                homeLookPos = homePos.add(creature.getLookVec());
            }
            else
            {
                homePos = new Vec3d(living.getPosition());
                homeLookPos = homePos.add(living.getLookVec());
            }
        }

        public OpenLivingEntityGUIPacket(EntityPlayerMP player)
        {
            maxHP = 20;

            if (player.isSneaking())
            {
                //Create at the player's position, looking the same direction as the player
                homePos = player.getPositionVector().addVector(0, player.eyeHeight - CustomLivingEntity.DEFAULT_EYE_HEIGHT, 0);
                homeLookPos = homePos.add(player.getLookVec());
            }
            else
            {
                //Create at a position a little in front of the player, looking into the player's eyes
                homeLookPos = player.getPositionVector().addVector(0, player.eyeHeight, 0);
                homePos = homeLookPos.add(player.getLookVec().addVector(0, player.eyeHeight - CustomLivingEntity.DEFAULT_EYE_HEIGHT, 0));
            }
        }

        public OpenLivingEntityGUIPacket(Vec3d homePos, EntityPlayerMP player)
        {
            maxHP = 20;

            //Create at the clicked position...
            this.homePos = homePos;
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
            buf.writeInt(homeDimension);
            buf.writeDouble(homePos.x);
            buf.writeDouble(homePos.y);
            buf.writeDouble(homePos.z);
            buf.writeDouble(homeLookPos.x);
            buf.writeDouble(homeLookPos.y);
            buf.writeDouble(homeLookPos.z);

            buf.writeFloat(maxHP);
        }

        @Override
        public void fromBytes(ByteBuf buf)
        {
            homeDimension = buf.readInt();
            homePos = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
            homeLookPos = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());

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
        int homeDimension;
        Vec3d homePos, homeLookPos;

        float maxHP;

        public CreateLivingEntityPacket()
        {
            //Required
        }

        public CreateLivingEntityPacket(boolean dummyBool)
        {
            homeDimension = LivingEntityGUI.homeDimension;
            homePos = LivingEntityGUI.homePos;
            homeLookPos = LivingEntityGUI.homeLookPos;

            maxHP = LivingEntityGUI.maxHP;
        }

        @Override
        public void toBytes(ByteBuf buf)
        {
            buf.writeInt(homeDimension);
            buf.writeDouble(homePos.x);
            buf.writeDouble(homePos.y);
            buf.writeDouble(homePos.z);
            buf.writeDouble(homeLookPos.x);
            buf.writeDouble(homeLookPos.y);
            buf.writeDouble(homeLookPos.z);

            buf.writeFloat(maxHP);
        }

        @Override
        public void fromBytes(ByteBuf buf)
        {
            homeDimension = buf.readInt();
            homePos = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
            homeLookPos = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());

            maxHP = buf.readFloat();
        }
    }

    public static class CreateLivingEntityPacketHandler implements IMessageHandler<CreateLivingEntityPacket, IMessage>
    {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(CreateLivingEntityPacket packet, MessageContext ctx)
        {
            FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> DimensionManager.getWorld(packet.homeDimension).spawnEntity(new CustomLivingEntity(packet)));
            return null;
        }
    }
}
