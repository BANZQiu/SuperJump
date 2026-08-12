package com.example.jump;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.action.TypedActionResult; // 修正后的导入
import net.minecraft.world.World;

public class JumpItem extends Item {
    public JumpItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            user.setVelocity(user.getVelocity().x, 2.5, user.getVelocity().z);
            user.fallDistance = 0;

            world.playSound(null, user.getBlockPos(), 
                SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST, 
                SoundCategory.PLAYERS, 1.5F, 0.8F);
            user.sendMessage(net.minecraft.text.Text.literal("🚀 起飞！"), true);

            user.getStackInHand(hand).decrement(1);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
