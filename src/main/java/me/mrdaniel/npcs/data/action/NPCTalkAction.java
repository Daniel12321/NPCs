package me.mrdaniel.npcs.data.action;

import java.util.List;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import com.google.common.collect.Lists;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.data.MMOKeys;

public class NPCTalkAction implements NPCAction {

	private List<Text> messages;
	private boolean random;
	private boolean all;
	private int current;

	public NPCTalkAction(@Nonnull final List<Text> messages, final boolean random, final boolean all, final int current) {
		this.messages = messages;
		this.random = random;
		this.all = all;
		this.current = current;
	}

	@Nonnull public List<Text> getMessages() { return this.messages; }
	public void setMessages(@Nonnull final List<Text> messages) { this.messages = messages; this.current = 0; }
	public void addMessage(@Nonnull final Text message) { this.messages.add(message); }

	public void setRandom(final boolean random) { this.random = random; }
	public boolean isRandom() { return this.random; }

	public void setAll(final boolean all) { this.all = all; }
	public boolean isALl() { return this.all; }

	@Override
	public void execute(@Nonnull final NPCs npcs, @Nonnull final Player p) {
		if (this.random) { p.sendMessage(this.messages.get((int) (Math.random()*this.messages.size()))); }
		else if (this.all) { this.messages.forEach(message -> p.sendMessage(message)); }
		else {
			p.sendMessage(this.messages.get(this.current));
			this.current++;
			if (this.current >= this.messages.size()) { this.current = 0; }
		}
	}

	@Override
	public DataContainer addData(final DataContainer container) {
		return container.set(MMOKeys.MESSAGES.getQuery(), this.messages).set(MMOKeys.CURRENT.getQuery(), this.current);
	}

	@SuppressWarnings("unchecked")
	public static NPCTalkAction build(final DataView view) {
		return new NPCTalkAction(
				view.getList(MMOKeys.MESSAGES.getQuery()).map(l -> (List<Text>)l).orElse(Lists.newArrayList()),
				view.getBoolean(MMOKeys.RANDOM.getQuery()).orElse(false),
				view.getBoolean(MMOKeys.ALL.getQuery()).orElse(false),
				view.getInt(MMOKeys.CURRENT.getQuery()).orElse(0));
	}
}