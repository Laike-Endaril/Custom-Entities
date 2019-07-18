package com.fantasticsource.customentities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

public class CustomLivingEntity extends EntityLiving
{
    private boolean hasHealth = false;

    public CustomLivingEntity(World worldIn)
    {
        super(worldIn);
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (!world.isRemote && isEntityAlive())
        {
            for (Entity felt : world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox()))
            {
                System.out.println(getName() + " collided with " + felt.getName());
            }
        }

        System.out.println("Entity box: " + getEntityBoundingBox());
        System.out.println("Collision box: : " + getEntityBoundingBox());
        System.out.println("Render box: " + getRenderBoundingBox());
    }

    @Override
    public boolean isEntityAlive()
    {
        if (hasHealth) return super.isEntityAlive();
        return !isDead;
    }
}
