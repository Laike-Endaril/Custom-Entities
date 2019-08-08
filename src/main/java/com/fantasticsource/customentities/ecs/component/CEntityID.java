package com.fantasticsource.customentities.ecs.component;

import com.fantasticsource.customentities.ecs.component.base.CUUID;

public class CEntityID extends CUUID
{
    @Override
    public String label()
    {
        return "Entity ID";
    }
}
