package com.fantasticsource.customentities.ecs;


import com.fantasticsource.mctools.ecs.ECSEntity;

import java.util.UUID;

public class CParentID extends CEntityID
{
    public CParentID(ECSEntity entity)
    {
        super(entity);
    }

    @Override
    public CParentID set(UUID value)
    {
        return (CParentID) super.set(value);
    }

    @Override
    public CParentID copy()
    {
        return new CParentID(entity).set(value);
    }

    @Override
    public String label()
    {
        return "Parent ID";
    }
}
