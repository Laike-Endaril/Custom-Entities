package com.fantasticsource.customentities.server;

import com.fantasticsource.customentities.data.LivingTemplate;
import com.fantasticsource.mctools.MCTools;

import java.io.*;
import java.util.LinkedHashMap;

public class EntityTemplates
{
    private static final File fileLiving = new File(MCTools.getConfigDir() + "customentities_livingtemplates.dat");

    public static LinkedHashMap<String, LivingTemplate> livingEntities = new LinkedHashMap<>();

    public static void save() throws IOException
    {
        if (!fileLiving.exists()) fileLiving.createNewFile();

        BufferedWriter bw = new BufferedWriter(new FileWriter(fileLiving));

        for (LivingTemplate data : livingEntities.values())
        {
            data.writeGlobal(bw);
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
