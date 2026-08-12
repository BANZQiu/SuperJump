package com.example.jump;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class JumpItem extends Item {
    public JumpItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            // 给玩家一个超强的向上速度
            user.setVelocity(user.getVelocity().x, 2.5, user.getVelocity().z);
            user.fallDistance = 0; // 重置掉落距离，避免摔伤

            // 播放音效和提示
            world.playSound(null, user.getBlockPos(), 
                SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST, 
                SoundCategory.PLAYERS, 1.5F, 0.8F);
            user.sendMessage(net.minecraft.text.Text.literal("🚀 起飞！"), true);

            // 每次使用消耗一个物品
            user.getStackInHand(hand).decrement(1);
        }
        return ActionResult.SUCCESS;
    }
}
