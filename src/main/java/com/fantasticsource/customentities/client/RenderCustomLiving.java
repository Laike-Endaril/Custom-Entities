package com.fantasticsource.customentities.client;

import com.fantasticsource.customentities.CustomLivingEntity;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;

public class RenderCustomLiving extends RenderBiped<CustomLivingEntity>
{
    private static final ResourceLocation TEXTURES = DefaultPlayerSkin.getDefaultSkinLegacy();

    public RenderCustomLiving(RenderManager rendermanagerIn)
    {
        super(rendermanagerIn, new ModelPlayer(0, false), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(CustomLivingEntity entity)
    {
        return TEXTURES;
    }
}
