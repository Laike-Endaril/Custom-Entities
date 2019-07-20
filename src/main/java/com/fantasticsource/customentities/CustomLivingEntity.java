package com.fantasticsource.customentities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CustomLivingEntity extends EntityLiving
{
    public static final float DEFAULT_WIDTH = 0.6f, DEFAULT_HEIGHT = 1.8f, DEFAULT_EYE_HEIGHT = DEFAULT_HEIGHT * 0.85F;

    public boolean hasHealth = false;

    public Vec3d homePosition, homeLookPos;

    public CustomLivingEntity(World worldIn)
    {
        super(worldIn);

        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;
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
    public boolean isEntityAlive()
    {
        if (hasHealth) return super.isEntityAlive();
        return !isDead;
    }
}
