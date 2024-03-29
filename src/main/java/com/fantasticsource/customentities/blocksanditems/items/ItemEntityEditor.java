package com.fantasticsource.customentities.blocksanditems.items;

import com.fantasticsource.customentities.CustomEntities;
import com.fantasticsource.customentities.CustomLivingEntity;
import com.fantasticsource.customentities.Network;
import com.fantasticsource.customentities.blocksanditems.BlocksAndItems;
import com.fantasticsource.mctools.MCTools;
import com.fantasticsource.tools.Tools;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ItemEntityEditor extends Item
{
    public ItemEntityEditor()
    {
        setCreativeTab(BlocksAndItems.creativeTab);

        setUnlocalizedName(CustomEntities.MODID + ":entityeditor");
        setRegistryName("entityeditor");
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void entityInteract(PlayerInteractEvent.EntityInteractSpecific event)
    {
        EntityPlayer player = event.getEntityPlayer();

        //Only for living entities
        if (!(event.getTarget() instanceof EntityLiving)) return;

        //Only if holding the entity editor
        ItemStack itemStack;
        if (event.getHand() == EnumHand.MAIN_HAND) itemStack = player.getHeldItemMainhand();
        else itemStack = player.getHeldItemOffhand();
        if (itemStack.getItem() != BlocksAndItems.entityeditor) return;


        //Success, but client stops here
        event.setCancellationResult(EnumActionResult.SUCCESS);
        if (event.getSide() != Side.SERVER)
        {
            event.setCanceled(true);
            return;
        }


        //OP players only (no way to check from client side afaik)
        if (!MCTools.isOP((EntityPlayerMP) player)) return;

        //Open for clicked entity
        Network.WRAPPER.sendTo(new Network.OpenLivingEntityGUIPacket((EntityLiving) event.getTarget()), (EntityPlayerMP) player);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void blockInteract(PlayerInteractEvent.RightClickBlock event)
    {
        EntityPlayer player = event.getEntityPlayer();

        //Only if holding the entity editor
        ItemStack itemStack;
        if (event.getHand() == EnumHand.MAIN_HAND) itemStack = player.getHeldItemMainhand();
        else itemStack = player.getHeldItemOffhand();
        if (itemStack.getItem() != BlocksAndItems.entityeditor) return;


        //Create mode, at position right clicked
        Vec3d vec = event.getHitVec();
        BlockPos pos = new BlockPos(event.getHitVec());
        if (Tools.posMod(vec.x, 1) == 0 && player.posX < vec.x) pos = pos.west();
        if (Tools.posMod(vec.z, 1) == 0 && player.posZ < vec.z) pos = pos.north();

        //If we clicked on the bottom of a block, shift y position down to where the "top" of the entity is touching the bottom of the block
        Vec3d vec2 = new Vec3d(pos).addVector(0.5, 0, 0.5);
        if (Tools.posMod(vec.y, 1) == 0 && player.posY + player.eyeHeight < vec.y) vec2 = vec2.addVector(0, -CustomLivingEntity.DEFAULT_HEIGHT, 0);


        //Success, but client stops here
        event.setCancellationResult(EnumActionResult.SUCCESS);
        if (event.getSide() != Side.SERVER)
        {
            event.setCanceled(true);
            return;
        }


        //OP players only (no way to check from client side afaik)
        if (!MCTools.isOP((EntityPlayerMP) player)) return;

        //Open for clicked position
        Network.WRAPPER.sendTo(new Network.OpenLivingEntityGUIPacket(vec2, (EntityPlayerMP) player), (EntityPlayerMP) player);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        //Only if holding the entity editor
        ItemStack itemstack = hand == EnumHand.MAIN_HAND ? player.getHeldItemMainhand() : player.getHeldItemOffhand();
        if (itemstack.getItem() != BlocksAndItems.entityeditor) return new ActionResult<>(EnumActionResult.FAIL, itemstack);

        //Success, but only server takes actions
        if (world.isRemote) return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);


        //OP players only (no way to check from client side afaik)
        if (!MCTools.isOP((EntityPlayerMP) player)) return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);

        //Create, either <where player is looking, a little ways out, looking back at player>, or <where player is, looking where player is looking>
        Network.WRAPPER.sendTo(new Network.OpenLivingEntityGUIPacket((EntityPlayerMP) player), (EntityPlayerMP) player);

        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }
}
