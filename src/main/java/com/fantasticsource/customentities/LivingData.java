package com.fantasticsource.customentities;

import net.minecraft.util.math.Vec3d;

import java.io.BufferedWriter;
import java.io.IOException;

public class LivingData
{
    //Backend (entity)
    public boolean hasID;
    public int entityID;

    //Main
    public String name;
    public int homeDimension;
    public Vec3d homePos, homeLookPos;
    public float maxHP;

    //Physics and Render
    public float eyeHeight;

    public void write(BufferedWriter bw) throws IOException
    {
        bw.write(name + "\r\n");
    }
}
