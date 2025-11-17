package jp.jsexpanded.client.animations;

import jp.jsexpanded.client.animations.animal.MoonspiderAnimations;
import jp.jsexpanded.client.animations.animal.SicklefinAnimator;
import jp.jsexpanded.server.animals.JSExpandedAnimals;
import jp.jurassicsaga.client.v1.animation.obj.BonitoAnimator;
import jp.jurassicsaga.client.v1.animation.obj.RaptorAnimator;
import travelers.client.render.animation.entity.TravelersAnimationMap;

public class JSExpandedClientAnimations {
    public static void init() {
        TravelersAnimationMap.register(JSExpandedAnimals.VENATOSAURUS, new RaptorAnimator());
        TravelersAnimationMap.register(JSExpandedAnimals.MOONSPIDER, new MoonspiderAnimations());
        TravelersAnimationMap.register(JSExpandedAnimals.SICKLEFIN, new SicklefinAnimator());
    }
}
