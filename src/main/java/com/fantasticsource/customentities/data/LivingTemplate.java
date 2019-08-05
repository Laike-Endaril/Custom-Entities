package com.fantasticsource.customentities.data;

import com.fantasticsource.customentities.CustomLivingEntity;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LivingTemplate extends LivingDataBase
{
    //Shared between all modpack save files
    public int templateID;
    ArrayList<LivingTemplate> inheritors = new ArrayList<>();

    //Specific to each modpack save file
    ArrayList<CustomLivingEntity> entities = new ArrayList<>();

    @Override
    public void writeGlobal(BufferedWriter bw) throws IOException
    {
        bw.write(templateID + "\r\n");

        bw.write(inheritors.size() + "\r\n");
        for (LivingTemplate template : inheritors) bw.write(template.templateID + "\r\n");

        super.writeGlobal(bw);
    }
}
