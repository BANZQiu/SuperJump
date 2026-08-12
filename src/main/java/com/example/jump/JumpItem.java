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
    private static final double JUMP_SPEED = 2.5;

    public JumpItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        
        // 基础检查
        if (stack.isEmpty()) {
            return ActionResult.FAIL;
        }
        
        // 客户端直接返回成功
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        
        // === 以下仅在服务端执行 ===
        
        // 检查世界高度限制
        int maxHeight = world.getTopY();
        if (user.getY() > maxHeight - 10) {
            user.sendMessage(
                net.minecraft.text.Text.literal("⚠️ 已接近世界顶部，无法使用！"), 
                true
            );
            return ActionResult.FAIL;
        }
        
        // 执行跳跃（增强二段跳效果）
        double verticalSpeed = user.isOnGround() ? JUMP_SPEED : JUMP_SPEED * 1.3;
        user.setVelocity(user.getVelocity().x, verticalSpeed, user.getVelocity().z);
        user.fallDistance = 0;
        
        // 播放音效
        world.playSound(null, user.getBlockPos(), 
            SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST, 
            SoundCategory.PLAYERS, 1.5F, 0.8F);
        
        // 发送消息
        user.sendMessage(
            net.minecraft.text.Text.literal(user.isOnGround() ? "🚀 起飞！" : "🚀 二段跳！"), 
            true
        );
        
        // 消耗物品
        if (!user.isCreative()) {
            stack.decrement(1);
        }
        
        return ActionResult.SUCCESS;
    }
}
