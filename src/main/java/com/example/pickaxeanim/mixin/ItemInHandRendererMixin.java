package com.example.pickaxeanim.mixin;

import com.example.pickaxeanim.PickaxeAnimClient;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {
    @Unique private boolean pickaxeanim$pushed = false;

    @Inject(method = "renderArmWithItem", at = @At("HEAD"))
    private void pickaxeanim$head(CallbackInfo ci,
                                  @Local(argsOnly = true) ItemStack stack,
                                  @Local(argsOnly = true) PoseStack poseStack) {
        pickaxeanim$pushed = false;
        if (!PickaxeAnimClient.enabled) return;
        String id = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
        if (!id.endsWith("_pickaxe")) return;

        float t = (System.currentTimeMillis() % 100000L) / 1000f;
        float wave = Mth.sin(t * 25f);

        poseStack.pushPose();
        pickaxeanim$pushed = true;
        poseStack.scale(1.25f, 1.25f, 1.25f);
        poseStack.mulPose(Axis.XP.rotationDegrees(wave * 28f));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.sin(t * 25f + 1f) * 6f));
    }

    @Inject(method = "renderArmWithItem", at = @At("RETURN"))
    private void pickaxeanim$return(CallbackInfo ci,
                                    @Local(argsOnly = true) PoseStack poseStack) {
        if (pickaxeanim$pushed) {
            poseStack.popPose();
            pickaxeanim$pushed = false;
        }
    }
}
