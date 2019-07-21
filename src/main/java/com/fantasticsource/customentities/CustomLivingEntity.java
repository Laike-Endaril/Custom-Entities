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

    public CustomLivingEntity(Network.CreateLivingEntityPacket packet)
    {
        super(DimensionManager.getWorld(packet.homeDimension));


        homeDimension = world.provider.getDimension();
        homePos = packet.homePos;
        homeLookPos = packet.homeLookPos;
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

    @Override
    public float getEyeHeight()
    {
        return height * eyeHeight;
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (!world.isRemote && isEntityAlive())
        {
            for (Entity felt : world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox()))
            {
//                System.out.println(getName() + " collided with " + felt.getName());
            }
        }
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
