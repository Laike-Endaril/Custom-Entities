package com.fantasticsource.customentities.ecs.component;

import com.fantasticsource.customentities.ecs.component.base.CDouble;
import com.fantasticsource.customentities.ecs.component.base.CInt;
import com.fantasticsource.customentities.ecs.component.base.Component;
import io.netty.buffer.ByteBuf;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CPosition extends Component
{
    CInt dimension = new CInt();
    CDouble[] coords = new CDouble[]{new CDouble(), new CDouble(), new CDouble()};

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
}
