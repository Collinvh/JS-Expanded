package jp.jsexpanded.server.sound;

import com.google.common.base.Supplier;
import jp.jsexpanded.JSExpanded;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import travelers.util.helper.TravelersRegistry;

public class JSExpandedSounds {
    public static final TravelersRegistry<SoundEvent> SOUND_EVENTS = new TravelersRegistry<>(BuiltInRegistries.SOUND_EVENT, JSExpanded.MOD_ID);

    public static Supplier<SoundEvent> create(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(JSExpanded.createId(name)));
    }

    public static Supplier<SoundEvent> create(String name, int range) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createFixedRangeEvent(JSExpanded.createId(name), range));
    }

    public static Supplier<SoundEvent> VENATOSAURUS_ATTACK;
    public static Supplier<SoundEvent> VENATOSAURUS_DEATH;
    public static Supplier<SoundEvent> VENATOSAURUS_HURT;
    public static Supplier<SoundEvent> VENATOSAURUS_LIVING;
    public static Supplier<SoundEvent> VENATOSAURUS_THREAT;

    public static Supplier<SoundEvent> PIRANHADON_DEATH;
    public static Supplier<SoundEvent> PIRANHADON_HURT;
    public static Supplier<SoundEvent> PIRANHADON_LIVING;

    public static void init() {
        VENATOSAURUS_ATTACK = create("venatosaurus_attack");
        VENATOSAURUS_DEATH = create("venatosaurus_death");
        VENATOSAURUS_HURT = create("venatosaurus_hurt");
        VENATOSAURUS_LIVING = create("venatosaurus_living");
        VENATOSAURUS_THREAT = create("venatosaurus_threaten");

        PIRANHADON_DEATH = create("piranhadon_death");
        PIRANHADON_HURT = create("piranhadon_hurt");
        PIRANHADON_LIVING = create("piranhadon_living");
    }
}
