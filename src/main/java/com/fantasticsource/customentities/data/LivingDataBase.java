package com.fantasticsource.customentities.data;

import net.minecraft.util.math.Vec3d;

import java.io.BufferedWriter;
import java.io.IOException;

public class LivingDataBase
{
    //Main
    public String name;
    public int homeDimension;
    public Vec3d homePos, homeLookPos;
    public float maxHP;

    //Physics and Render
    public float eyeHeight;

    public void writeGlobal(BufferedWriter bw) throws IOException
    {
        //Main
        bw.write(name + "\r\n");
        bw.write(homeDimension + "\r\n");
        bw.write(homePos.x + "\r\n");
        bw.write(homePos.y + "\r\n");
        bw.write(homePos.z + "\r\n");
        bw.write(homeLookPos.x + "\r\n");
        bw.write(homeLookPos.y + "\r\n");
        bw.write(homeLookPos.z + "\r\n");
        bw.write(maxHP + "\r\n");

        //Physics and Render
        bw.write(eyeHeight + "\r\n");
    }
}
