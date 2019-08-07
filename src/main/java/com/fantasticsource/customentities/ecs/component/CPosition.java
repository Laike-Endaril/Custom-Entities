package com.fantasticsource.customentities.ecs.component;

import com.fantasticsource.customentities.ecs.component.base.CDouble;
import com.fantasticsource.customentities.ecs.component.base.CInt;
import com.fantasticsource.customentities.ecs.component.base.Component;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.Vec3d;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CPosition extends Component
{
    CInt dimension = new CInt();
    CDouble[] coords = new CDouble[]{new CDouble(), new CDouble(), new CDouble()};

    public int getDimension()
    {
        return dimension.value;
    }

    public double getX()
    {
        return coords[0].value;
    }

    public double getY()
    {
        return coords[1].value;
    }

    public double getZ()
    {
        return coords[2].value;
    }

    public Vec3d getPosition()
    {
        return new Vec3d(coords[0].value, coords[1].value, coords[2].value);
    }

    public CPosition set(int dimension, double x, double y, double z)
    {
        this.dimension.value = dimension;
        this.coords[0].value = x;
        this.coords[1].value = y;
        this.coords[2].value = z;
        return this;
    }

    @Override
    public void write(ByteBuf buf)
    {
        dimension.write(buf);
        for (CDouble cd : coords) cd.write(buf);
    }

    @Override
    public void read(ByteBuf buf)
    {
        dimension.read(buf);
        for (CDouble cd : coords) cd.read(buf);
    }

    @Override
    public void save(FileOutputStream stream) throws IOException
    {
        dimension.save(stream);
        for (CDouble cd : coords) cd.save(stream);
    }

    @Override
    public void load(FileInputStream stream) throws IOException
    {
        dimension.load(stream);
        for (CDouble cd : coords) cd.load(stream);
    }

    @Override
    public String toString()
    {
        return dimension.toString() + ", " + coords[0].toString() + ", " + coords[1].toString() + ", " + coords[2].toString();
    }

    @Override
    public void parse(String string)
    {
        String[] tokens = string.split(",");
        dimension.parse(tokens[0].trim());
        dimension.parse(tokens[1].trim());
        dimension.parse(tokens[2].trim());
        dimension.parse(tokens[3].trim());
    }

    @Override
    public String label()
    {
        return "Position";
    }

    @Override
    public Component copy()
    {
        return new CPosition().set(dimension.value, coords[0].value, coords[1].value, coords[2].value);
    }
}
