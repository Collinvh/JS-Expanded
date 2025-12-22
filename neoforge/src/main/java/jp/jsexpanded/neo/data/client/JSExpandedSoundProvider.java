package jp.jsexpanded.neo.data.client;

import com.google.common.base.Supplier;
import jp.jsexpanded.JSExpanded;
import jp.jsexpanded.server.sound.JSExpandedSounds;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class JSExpandedSoundProvider extends SoundDefinitionsProvider {
    public JSExpandedSoundProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, JSExpanded.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        addAnimalSound("venatosaurus_attack", 1, "jsexpanded:animal/venatosaurus/", JSExpandedSounds.VENATOSAURUS_ATTACK);
        addAnimalSound("venatosaurus_death", 2, "jsexpanded:animal/venatosaurus/", JSExpandedSounds.VENATOSAURUS_DEATH);
        addAnimalSound("venatosaurus_living", 6, "jsexpanded:animal/venatosaurus/", JSExpandedSounds.VENATOSAURUS_LIVING);
        addAnimalSound("venatosaurus_threaten", 3, "jsexpanded:animal/venatosaurus/", JSExpandedSounds.VENATOSAURUS_THREAT);
        addAnimalSound("venatosaurus_hurt", 4, "jsexpanded:animal/venatosaurus/", JSExpandedSounds.VENATOSAURUS_HURT);

        addAnimalSound("piranhadon_death", 1, "jsexpanded:animal/piranhadon/", JSExpandedSounds.PIRANHADON_DEATH);
        addAnimalSound("piranhadon_hurt", 5, "jsexpanded:animal/piranhadon/", JSExpandedSounds.PIRANHADON_HURT);
        addAnimalSound("piranhadon_living", 4, "jsexpanded:animal/piranhadon/", JSExpandedSounds.PIRANHADON_LIVING);
    }

    private void addAnimalSound(String baseName, int amount, String prefix, Supplier<SoundEvent> event) {
        if(event == null) {
            JSExpanded.LOG.error("{} is null!", baseName);
            return;
        }
        if(amount <= 1) {
            add(event, definition().with(sound(prefix + baseName)));
            return;
        }

        var def = definition();
        for (int i = 1; i < amount + 1; i++) {
            def.with(sound(prefix + baseName + (i < 10 ? "0" + i : i)));
        }

        add(event, def);
    }
}
