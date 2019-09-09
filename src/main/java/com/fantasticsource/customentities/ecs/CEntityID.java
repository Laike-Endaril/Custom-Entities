package com.fantasticsource.customentities.ecs;


import com.fantasticsource.mctools.ecs.ECSEntity;
import com.fantasticsource.mctools.ecs.component.CUUID;

import java.util.UUID;

public class CEntityID extends CUUID
{
    public CEntityID(ECSEntity entity)
    {
        super(entity);
    }

    @Override
    public CEntityID set(UUID value)
    {
        return (CEntityID) super.set(value);
    }

    @Override
    public CEntityID copy()
    {
        return new CEntityID(entity).set(new UUID(value.getMostSignificantBits(), value.getLeastSignificantBits()));
    }

    @Override
    public String label()
    {
        return "ECS Entity ID";
    }
}
