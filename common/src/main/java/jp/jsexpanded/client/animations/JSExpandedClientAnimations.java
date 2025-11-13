package jp.jsexpanded.client.animations;

import jp.jsexpanded.server.animals.JSExpandedAnimals;
import jp.jurassicsaga.client.v1.animation.obj.RaptorAnimator;
import jp.jurassicsaga.server.v1.animal.JSV1Animals;
import travelers.client.render.animation.entity.TravelersAnimationMap;

public class JSExpandedClientAnimations {
    public static void init() {
        TravelersAnimationMap.register(JSExpandedAnimals.VENATOSAURUS, new RaptorAnimator());
    }
}
