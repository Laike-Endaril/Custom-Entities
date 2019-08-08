package com.fantasticsource.customentities.ecs.component;

import com.fantasticsource.customentities.ecs.component.base.CInt;
import com.fantasticsource.customentities.ecs.component.base.CVec3D;
import com.fantasticsource.customentities.ecs.component.base.Component;
import io.netty.buffer.ByteBuf;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CHome extends Component
{
    public CInt dimension = new CInt();
    public CVec3D position = new CVec3D(), lookPosition = new CVec3D();

    public CHome setDimension(int dimension)
    {
        this.dimension.value = dimension;
        return this;
    }

    public CHome setPosition(double x, double y, double z)
    {
        this.position.set(x, y, z);
        return this;
    }

    public CHome setLookPosition(double x, double y, double z)
    {
        this.lookPosition.set(x, y, z);
        return this;
    }

    @Override
    public void write(ByteBuf buf)
    {
        dimension.write(buf);
        position.write(buf);
        lookPosition.write(buf);
    }

    @Override
    public void read(ByteBuf buf)
    {
        dimension.read(buf);
        position.read(buf);
        lookPosition.read(buf);
    }

    @Override
    public void save(FileOutputStream stream) throws IOException
    {
        dimension.save(stream);
        position.save(stream);
        lookPosition.save(stream);
    }

    @Override
    public void load(FileInputStream stream) throws IOException
    {
        dimension.load(stream);
        position.load(stream);
        lookPosition.load(stream);
    }

    @Override
    public String toString()
    {
        return dimension + ", " + position + ", " + lookPosition;
    }

    @Override
    public void parse(String string)
    {
        String[] tokens = string.split(",");
        dimension.parse(tokens[0].trim());
        position.parse(tokens[1] + ", " + tokens[2] + ", " + tokens[3]);
        lookPosition.parse(tokens[4] + ", " + tokens[5] + ", " + tokens[6]);
    }

    @Override
    public Component copy()
    {
        return new CHome().setDimension(dimension.value).setPosition(position.x.value, position.y.value, position.z.value).setLookPosition(lookPosition.x.value, lookPosition.y.value, lookPosition.z.value);
    }

    @Override
    public String label()
    {
        return "Home";
    }
}
