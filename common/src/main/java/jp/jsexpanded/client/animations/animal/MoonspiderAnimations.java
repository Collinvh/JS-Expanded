package jp.jsexpanded.client.animations.animal;

import jp.jsexpanded.server.animals.entity.extinct.terrestial.moonspider.MoonspiderEntity;
import jp.jurassicsaga.client.v1.animation.JSClientAnimator;
import jp.jurassicsaga.server.v1.animal.entity.extant.BasiliskEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import travelers.client.render.animation.entity.TravelersAnimationData;
import travelers.server.animal.entity.SmartAnimalBase;

public class MoonspiderAnimations extends JSClientAnimator {
    @Override
    public void updateModel(SmartAnimalBase animatable, float partialTick, TravelersAnimationData animData) {
        var root = bone(animData, "root");
        var headBones = getBones(animData, "Neck", "Head");
        var tailBones = getBones(animData, "Abdomen");

        float t = 2.4F / (float) Math.max(1, Minecraft.getInstance().getFps());
        t = Math.min(1, t);

        if (animatable instanceof MoonspiderEntity e && e.isClimbing()) {
            float rot = (float) Math.toRadians(90);
            root.angleX = Mth.lerp(t, root.angleX, rot);
        } else {
            root.angleX = Mth.lerp(t, root.angleX, 0);
        }
        faceTarget(animatable, partialTick, 4F, headBones);
        animData.chainBuffer.applyChainSwingBuffer(partialTick, tailBones);
    }

    @Override
    public void clientTick(SmartAnimalBase base) {
        super.clientTick(base);
        this.getData(base).chainBuffer.calculateChainSwingBuffer(360.0F, 2, 0.9F, 1.8F, base);
    }
}
