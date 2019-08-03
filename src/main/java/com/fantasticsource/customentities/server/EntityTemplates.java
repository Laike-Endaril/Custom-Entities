package com.fantasticsource.customentities.server;

import com.fantasticsource.customentities.LivingData;
import com.fantasticsource.mctools.MCTools;

import java.io.*;
import java.util.LinkedHashMap;

public class EntityTemplates
{
    private static final File fileLiving = new File(MCTools.getConfigDir() + "customentities_livingtemplates.dat");

    public static LinkedHashMap<String, LivingData> livingEntities = new LinkedHashMap<>();

    public static void save() throws IOException
    {
        if (!fileLiving.exists()) fileLiving.createNewFile();

        BufferedWriter bw = new BufferedWriter(new FileWriter(fileLiving));

        for (LivingData data : livingEntities.values())
        {
            data.write(bw);
        }

        bw.close();
    }

    public static void load() throws IOException
    {
        livingEntities.clear();

        if (!fileLiving.exists())
        {
            fileLiving.createNewFile();
        }
        else
        {
            BufferedReader br = new BufferedReader(new FileReader(fileLiving));

            //TODO

            br.close();
        }
    }
}
