package jp.jsexpanded.client.animations.animal;

import jp.jurassicsaga.client.v1.animation.JSClientAnimator;
import travelers.client.render.animation.entity.TravelersAnimationData;
import travelers.server.animal.entity.SmartAnimalBase;

public class SicklefinAnimator extends JSClientAnimator {
    @Override
    public void updateModel(SmartAnimalBase animatable, float partialTick, TravelersAnimationData animData) {
        var tailBones = getBones(animData, "body_1", "body_2", "body_3", "tail_1");
        var headBones = getBones(animData, "head");

        faceTarget(animatable, partialTick, 3F, headBones);
        animData.chainBuffer.applyChainSwingBuffer(partialTick, tailBones);
        applyTilt(animatable, 2f, 0.8F, 0.1F, 0.3F, partialTick, animatable::isInWater, getBones(animData, "root"));
    }

    @Override
    public void clientTick(SmartAnimalBase base) {
        super.clientTick(base);
        this.getData(base).chainBuffer.calculateChainSwingBuffer(120.0F, 6, 0.5F, 0.7F, base);
    }
}
