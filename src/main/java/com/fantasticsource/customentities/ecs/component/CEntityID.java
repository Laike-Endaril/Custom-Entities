package com.fantasticsource.customentities.ecs.component;

import com.fantasticsource.customentities.ecs.component.base.CUUID;
import com.fantasticsource.customentities.ecs.entity.ECSEntity;

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
