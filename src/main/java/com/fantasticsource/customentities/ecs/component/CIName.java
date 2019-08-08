package com.fantasticsource.customentities.ecs.component;

import com.fantasticsource.customentities.ecs.component.base.CStringUTF8;
import com.fantasticsource.customentities.ecs.component.inheritable.CIStringUTF8;
import com.fantasticsource.customentities.ecs.entity.Entity;

public class CIName extends CIStringUTF8
{
    public CIName(Entity parent, Class<CStringUTF8> componentClass)
    {
        super(parent, componentClass);
    }

    @Override
    public String label()
    {
        return "Name";
    }
}
