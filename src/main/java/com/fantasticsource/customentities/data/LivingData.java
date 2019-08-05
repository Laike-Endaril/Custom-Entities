package com.fantasticsource.customentities.data;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.UUID;

public class LivingData extends LivingDataBase
{
    //Backend (entity)
    public UUID entityUniqueID; //Corresponds to the field of the same name in MC's Entity class; used by MC when saving an entity to NBT; WorldServer has a method for getting an entity via this, but World does not

    @Override
    public void writeGlobal(BufferedWriter bw) throws IOException
    {
        //This should only ever be called when an entity exists in the world (including unloaded but existing entities)
        bw.write(entityUniqueID + "\r\n");
        super.writeGlobal(bw);
    }
}
