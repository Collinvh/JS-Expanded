package jp.jsexpanded.client.animations.animal;

import jp.jurassicsaga.client.v1.animation.JSClientAnimator;
import travelers.client.render.animation.entity.TravelersAnimationData;
import travelers.server.animal.entity.SmartAnimalBase;

public class PiranhadonAnimator extends JSClientAnimator {
    @Override
    public void updateModel(SmartAnimalBase animatable, float partialTick, TravelersAnimationData animData) {
        var headBones = getBones(animData, "body_1", "body_2", "neck_1", "neck_2", "head");
        var tailBones = getBones(animData, "tail_1", "tail_2", "tail_3", "tail_4", "tail_5");

        faceTarget(animatable, partialTick, 3F, headBones);
        animData.chainBuffer.applyChainSwingBuffer(partialTick, tailBones);
        applyTilt(animatable, 1f, 0.4F, 0.2F, 0.4F, partialTick, animatable::isInWater, getBones(animData, "hips"));
    }

    @Override
    public void clientTick(SmartAnimalBase base) {
        super.clientTick(base);
        this.getData(base).chainBuffer.calculateChainSwingBuffer(120.0F, 16, 0.05F, 0.3F, base);
    }
}
