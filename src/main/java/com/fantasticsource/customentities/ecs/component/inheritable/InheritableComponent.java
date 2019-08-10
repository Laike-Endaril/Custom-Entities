package com.fantasticsource.customentities.ecs.component.inheritable;

import com.fantasticsource.customentities.ecs.component.base.CStringUTF8;
import com.fantasticsource.customentities.ecs.component.base.Component;
import com.fantasticsource.customentities.ecs.entity.ECSEntity;

public abstract class InheritableComponent<T extends Component> extends CStringUTF8
{
    protected Class<T> calculatedComponentClass;
    protected boolean valid = false;

    public InheritableComponent(ECSEntity entity)
    {
        super(entity);
    }

    public final Class<T> getCalculatedComponentClass()
    {
        return calculatedComponentClass;
    }

    /**
     * Overrides should check the underlying string and set the 'valid' field to true or false
     */
    public abstract void updateValidity();

    /**
     * @return A Component calculated from the internal String, or null if the internal String is invalid
     */
    public abstract T getCalculatedComponent();

    public final T getParentCalculatedComponent()
    {
        return ((InheritableComponent<T>) entity.parent.get(getClass())).getCalculatedComponent();
    }

    /**
     * @return Cached validity boolean; make sure to call updateValidity() when internal String is changed
     */
    public boolean isValid()
    {
        return valid;
    }
}
