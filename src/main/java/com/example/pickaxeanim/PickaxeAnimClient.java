package com.example.pickaxeanim;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.network.chat.Component;

public class PickaxeAnimClient implements ClientModInitializer {
    public static boolean enabled = true;

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
            dispatcher.register(ClientCommandManager.literal("toggle").executes(ctx -> {
                enabled = !enabled;
                ctx.getSource().sendFeedback(Component.literal(
                    "Pickaxe animation: " + (enabled ? "ON" : "OFF")));
                return 1;
            }))
        );
    }
}
