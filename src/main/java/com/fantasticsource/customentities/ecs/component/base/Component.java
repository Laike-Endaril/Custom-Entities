package com.fantasticsource.customentities.ecs.component.base;

import io.netty.buffer.ByteBuf;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class Component
{
    public abstract void write(ByteBuf buf);

    public abstract void read(ByteBuf buf);

    public abstract void save(FileOutputStream stream) throws IOException;

    public abstract void load(FileInputStream stream) throws IOException;

    /**
     * Correlates to toString(), for use in GUI editing
     */
    public abstract void parse(String string);

    public abstract Component copy();

    /**
     * The label for this component when editing it via GUI
     */
    public String label()
    {
        return getClass().getSimpleName();
    }
}
