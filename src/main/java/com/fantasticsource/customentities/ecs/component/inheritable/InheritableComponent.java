package com.fantasticsource.customentities.ecs.component.inheritable;

import com.fantasticsource.customentities.ecs.component.base.CStringUTF8;
import com.fantasticsource.customentities.ecs.component.base.Component;
import com.fantasticsource.customentities.ecs.entity.Entity;

public abstract class InheritableComponent<T extends Component> extends CStringUTF8
{
    protected final Entity parent;
    protected final Class<T> componentClass;
    protected boolean valid = false;

    public InheritableComponent(Entity parent, Class<T> componentClass)
    {
        this.parent = parent;
        this.componentClass = componentClass;
    }

    public final Class<T> getCalculatedComponentClass()
    {
        return componentClass;
    }

    /**
     * Overrides should check the underlying string and set the 'valid' field to true or false
     */
    public abstract void updateValidity();

    /**
     * @return A Component calculated from the internal String, or null if the internal String is invalid
     */
    public abstract T getCalculatedComponent();

    /**
     * @return Cached validity boolean; make sure to call updateValidity() when internal String is changed
     */
    public boolean isValid()
    {
        return valid;
    }
}
