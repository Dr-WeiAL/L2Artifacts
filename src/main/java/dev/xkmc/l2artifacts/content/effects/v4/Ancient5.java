package dev.xkmc.l2artifacts.content.effects.v4;

import dev.xkmc.l2artifacts.content.capability.ArtifactData;
import dev.xkmc.l2artifacts.content.config.ArtifactSetConfig;
import dev.xkmc.l2artifacts.content.effects.attribute.AbstractCASetEffect;
import dev.xkmc.l2artifacts.content.effects.attribute.AttrSetEntry;
import dev.xkmc.l2artifacts.init.registrate.entries.LinearFuncEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

import java.util.List;


public class Ancient5 extends AbstractCASetEffect<AncientData5> {

    private final LinearFuncEntry level, threshold;

    public Ancient5(LinearFuncEntry level, LinearFuncEntry threshold,AttrSetEntry...entries) {
        super(entries);
        this.level = level;
        this.threshold = threshold;

    }
    @Override
    public void tick(Player player, ArtifactSetConfig.Entry ent, int rank, boolean enabled) {
        AncientData5 data = ArtifactData.HOLDER.get(player).getOrCreateData(this, ent);
        data.update(3, rank);
        if (player.isShiftKeyDown()||player.hasEffect(MobEffects.INVISIBILITY)) {
            data.time++;
            if(data.time>=threshold.getFromRank(rank)){
                addAttributes(player,ent,rank,data);
            }
        } else {
            data.time = 0;
        }
    }


    @Override
    protected AncientData5 getData() {
        return new AncientData5();
    }

    @Override
    public List<MutableComponent> getDetailedDescription(int rank) {
        int threshold = (int)Math.round(this.threshold.getFromRank(rank));
        int prot = (int) Math.round(level.getFromRank(rank) );
        return List.of(Component.translatable(getDescriptionId() + ".desc",threshold, prot));
    }



}
