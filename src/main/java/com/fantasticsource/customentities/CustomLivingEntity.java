package com.fantasticsource.customentities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class CustomLivingEntity extends EntityLiving
{
    public static final float DEFAULT_WIDTH = 0.6f, DEFAULT_HEIGHT = 1.8f, DEFAULT_EYE_HEIGHT = 0.85F;

    public int homeDimension;
    public Vec3d homePos, homeLookPos;
    public float eyeHeight;

    public CustomLivingEntity(World world)
    {
        //Required
        super(world);
    }

    public CustomLivingEntity(Network.ApplyToLivingEntityPacket packet)
    {
        //Main
        super(DimensionManager.getWorld(packet.homeDimension));
        setCustomNameTag(packet.name);
        homeDimension = world.provider.getDimension();
        homePos = packet.homePos;
        homeLookPos = packet.homeLookPos;

        //Physics and Render
        eyeHeight = packet.eyeHeight;
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;


        double d0 = homeLookPos.x - homePos.x;
        double d1 = homeLookPos.y - (homePos.y + eyeHeight);
        double d2 = homeLookPos.z - homePos.z;
        double d3 = (double) MathHelper.sqrt(d0 * d0 + d2 * d2);
        float yaw = (float) (MathHelper.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
        float pitch = (float) (-(MathHelper.atan2(d1, d3) * (180D / Math.PI)));
        setLocationAndAngles(homePos.x, homePos.y, homePos.z, yaw, pitch);
    }

    public static void handlePacket(Network.ApplyToLivingEntityPacket packet)
    {
        World world = DimensionManager.getWorld(packet.homeDimension);

        if (!packet.hasEntityID) world.spawnEntity(new CustomLivingEntity(packet));
        else
        {
            //Edit existing entity
            Entity entity = world.getEntityByID(packet.entityID);

            if (entity instanceof CustomLivingEntity)
            {
                //Already a custom entity; just edit the existing one
            }
            else if (entity instanceof EntityLiving)
            {
                //Edit existing non-custom living entity, with the option of converting it to a custom entity
            }
            else world.spawnEntity(new CustomLivingEntity(packet));

            if (entity != null)
            {
                entity.setCustomNameTag(packet.name);
            }
        }
    }

    @Override
    public float getEyeHeight()
    {
        return height * eyeHeight;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound = super.writeToNBT(compound);

        compound.setInteger("homeDimension", homeDimension);
        compound.setDouble("homeX", homePos.x);
        compound.setDouble("homeY", homePos.y);
        compound.setDouble("homeZ", homePos.z);
        compound.setDouble("homeLookX", homeLookPos.x);
        compound.setDouble("homeLookY", homeLookPos.y);
        compound.setDouble("homeLookZ", homeLookPos.z);

        compound.setFloat("eyeHeight", eyeHeight);

        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        homeDimension = compound.getInteger("homeDimension");
        homePos = new Vec3d(compound.getDouble("homeX"), compound.getDouble("homeY"), compound.getDouble("homeZ"));
        homeLookPos = new Vec3d(compound.getDouble("homeLookX"), compound.getDouble("homeLookY"), compound.getDouble("homeLookZ"));

        eyeHeight = compound.getFloat("eyeHeight");
    }
}
