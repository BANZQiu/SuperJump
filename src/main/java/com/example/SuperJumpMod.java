package com.example.mods;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SuperJumpMod implements ModInitializer {
    public static final String MOD_ID = "superjump";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // 注册一个“跳跳糖”物品，它的逻辑在下面的类中
    public static final Item JUMP_ITEM = new JumpItem(new Item.Settings().maxCount(1));

    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "jump_item"), JUMP_ITEM);
        LOGGER.info("🚀 超级跳跃模组已加载！吃了跳跳糖，一飞冲天！");
    }
}
