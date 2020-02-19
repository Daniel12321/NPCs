package me.mrdaniel.npcs.actions.conditions;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.conditiontype.ConditionTypes;
import me.mrdaniel.npcs.utils.ServerUtils;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.math.BigDecimal;

public class ConditionMoney extends Condition {

	private static EconomyService ECONOMY;

	private final Currency currency;
	private final double amount;

	public ConditionMoney(ConfigurationNode node) {
		this(node.getNode("Currency").getValue(obj -> Sponge.getRegistry().getType(Currency.class, (String)obj)).get(), node.getNode("Amount").getDouble(1.0));
	}

	public ConditionMoney(Currency currency, double amount) {
		super(ConditionTypes.MONEY);

		this.currency = currency;
		this.amount = amount;
	}

	@Override
	public boolean isMet(Player p) {
		if (ECONOMY == null) {
			NPCs.getInstance().getLogger().error("Failed to take money: No economy plugin installed!");
			return false;
		}
		return ECONOMY.getOrCreateAccount(p.getUniqueId()).get().getBalance(this.currency).doubleValue() >= this.amount;
	}

	@Override
	public void take(Player p) {
		ECONOMY.getOrCreateAccount(p.getUniqueId()).get().withdraw(this.currency, new BigDecimal(this.amount), ServerUtils.getCause(NPCs.getInstance().getContainer()));
	}

	@Override
	public void serializeValue(ConfigurationNode node) {
		node.getNode("Currency").setValue(this.currency.getId());
		node.getNode("Amount").setValue(this.amount);
	}

	@Override
	public Text getLine() {
		return Text.of(TextColors.GOLD, "Money: ", TextColors.AQUA, this.currency.getSymbol(), this.amount);
	}

	public static void setEconomyService(EconomyService economy) {
		ECONOMY = economy;
	}
}
