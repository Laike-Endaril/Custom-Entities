package com.fantasticsource.customentities.ecs.component;

import com.fantasticsource.customentities.ecs.component.inheritable.CIStringUTF8;
import com.fantasticsource.customentities.ecs.entity.ECSEntity;

public class CIName extends CIStringUTF8
{
    public CIName(ECSEntity entity)
    {
        super(entity);
    }

    @Override
    public String label()
    {
        return "Name";
    }

    @Override
    public CIName set(String value)
    {
        return (CIName) super.set(value);
    }

    @Override
    public CIName copy()
    {
        return new CIName(entity).set(value);
    }
}
