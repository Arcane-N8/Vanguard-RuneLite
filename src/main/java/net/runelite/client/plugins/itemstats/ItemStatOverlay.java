/*
 * Copyright (c) 2018 Abex
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.itemstats;

import com.google.common.annotations.VisibleForTesting;
import com.google.inject.Inject;
import net.runelite.api.*;
import net.runelite.api.widgets.WidgetID;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.itemstats.potions.PotionDuration;
import net.runelite.client.ui.JagexColors;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.tooltip.Tooltip;
import net.runelite.client.ui.overlay.tooltip.TooltipManager;
import net.runelite.client.util.ColorUtil;
import net.runelite.client.util.QuantityFormatter;
import net.runelite.http.api.item.ItemEquipmentStats;
import net.runelite.http.api.item.ItemStats;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.awt.*;
import java.time.Duration;

public class ItemStatOverlay extends Overlay
{
	// Unarmed attack speed is 4
	@VisibleForTesting
	static final ItemStats UNARMED = new ItemStats(true, 0, 0,
		ItemEquipmentStats.builder()
			.aspeed(4)
			.build());

	@Inject
	private Client client;

	@Inject
	private ItemManager itemManager;

	@Inject
	private TooltipManager tooltipManager;

	@Inject
	private ItemStatChanges statChanges;

	@Inject
	private ItemStatConfig config;

	@Override
	public Dimension render(Graphics2D graphics)
	{


		return null;
	}

	private String getChangeString(
		final double value,
		final boolean inverse,
		final boolean showPercent)
	{
		final Color plus = Positivity.getColor(config, Positivity.BETTER_UNCAPPED);
		final Color minus = Positivity.getColor(config, Positivity.WORSE);

		if (value == 0)
		{
			return "";
		}

		final Color color;

		if (inverse)
		{
			color = value > 0 ? minus : plus;
		}
		else
		{
			color = value > 0 ? plus : minus;
		}

		final String prefix = value > 0 ? "+" : "";
		final String suffix = showPercent ? "%" : "";
		final String valueString = QuantityFormatter.formatNumber(value);
		return ColorUtil.wrapWithColorTag(prefix + valueString + suffix, color);
	}

	private String buildStatRow(
		final String label,
		final double value,
		final double diffValue,
		final boolean inverse,
		final boolean showPercent)
	{
		return buildStatRow(label, value, diffValue, inverse, showPercent, true);
	}

	private String buildStatRow(
		final String label,
		final double value,
		final double diffValue,
		final boolean inverse,
		final boolean showPercent,
		final boolean showBase)
	{
		final StringBuilder b = new StringBuilder();

		if (value != 0 || diffValue != 0)
		{
			final String changeStr = getChangeString(diffValue, inverse, showPercent);

			if (config.alwaysShowBaseStats() && showBase)
			{
				final String valueStr = QuantityFormatter.formatNumber(value);
				b.append(label).append(": ").append(valueStr).append((!changeStr.isEmpty() ? " (" + changeStr + ") " : "")).append("</br>");
			}
			else if (!changeStr.isEmpty())
			{
				b.append(label).append(": ").append(changeStr).append("</br>");
			}
		}

		return b.toString();
	}

	private ItemStats getItemStatsFromContainer(ItemContainer container, int slotID)
	{
		final Item item = container.getItem(slotID);
		return item != null ? itemManager.getItemStats(item.getId(), false) : null;
	}

	@VisibleForTesting
	String buildStatBonusString(ItemStats s)
	{
		ItemStats other = null;
		// Used if switching into a 2 handed weapon to store off-hand stats
		ItemStats offHand = null;
		final ItemEquipmentStats currentEquipment = s.getEquipment();

		ItemContainer c = client.getItemContainer(InventoryID.EQUIPMENT);
		if (s.isEquipable() && currentEquipment != null && c != null)
		{
			final int slot = currentEquipment.getSlot();

			other = getItemStatsFromContainer(c, slot);
			// Check if this is a shield and there's a two-handed weapon equipped
			if (other == null && slot == EquipmentInventorySlot.SHIELD.getSlotIdx())
			{
				other = getItemStatsFromContainer(c, EquipmentInventorySlot.WEAPON.getSlotIdx());
				if (other != null)
				{
					final ItemEquipmentStats otherEquip = other.getEquipment();
					if (otherEquip != null)
					{
						// Account for speed change when two handed weapon gets removed
						// shield - (2h - unarmed) == shield - 2h + unarmed
						other = otherEquip.isTwoHanded() ? other.subtract(UNARMED) : null;
					}
				}
			}

			if (slot == EquipmentInventorySlot.WEAPON.getSlotIdx())
			{
				if (other == null)
				{
					other = UNARMED;
				}

				// Get offhand's stats to be removed from equipping a 2h weapon
				if (currentEquipment.isTwoHanded())
				{
					offHand = getItemStatsFromContainer(c, EquipmentInventorySlot.SHIELD.getSlotIdx());
				}
			}
		}

		final ItemStats subtracted = s.subtract(other).subtract(offHand);
		final ItemEquipmentStats e = subtracted.getEquipment();

		final StringBuilder b = new StringBuilder();

		if (config.showWeight())
		{
			double sw = config.alwaysShowBaseStats() ? subtracted.getWeight() : s.getWeight();
			b.append(buildStatRow("Weight", s.getWeight(), sw, true, false, s.isEquipable()));
		}

		if (subtracted.isEquipable() && e != null)
		{
			b.append(buildStatRow("Prayer", currentEquipment.getPrayer(), e.getPrayer(), false, false));
			b.append(buildStatRow("Speed", currentEquipment.getAspeed(), e.getAspeed(), true, false));
			b.append(buildStatRow("Melee Str", currentEquipment.getStr(), e.getStr(), false, false));
			b.append(buildStatRow("Range Str", currentEquipment.getRstr(), e.getRstr(), false, false));
			b.append(buildStatRow("Magic Dmg", currentEquipment.getMdmg(), e.getMdmg(), false, true));

			final StringBuilder abb = new StringBuilder();
			abb.append(buildStatRow("Stab", currentEquipment.getAstab(), e.getAstab(), false, false));
			abb.append(buildStatRow("Slash", currentEquipment.getAslash(), e.getAslash(), false, false));
			abb.append(buildStatRow("Crush", currentEquipment.getAcrush(), e.getAcrush(), false, false));
			abb.append(buildStatRow("Magic", currentEquipment.getAmagic(), e.getAmagic(), false, false));
			abb.append(buildStatRow("Range", currentEquipment.getArange(), e.getArange(), false, false));

			if (abb.length() > 0)
			{
				b.append(ColorUtil.wrapWithColorTag("Attack Bonus</br>", JagexColors.MENU_TARGET)).append(abb);
			}

			final StringBuilder dbb = new StringBuilder();
			dbb.append(buildStatRow("Stab", currentEquipment.getDstab(), e.getDstab(), false, false));
			dbb.append(buildStatRow("Slash", currentEquipment.getDslash(), e.getDslash(), false, false));
			dbb.append(buildStatRow("Crush", currentEquipment.getDcrush(), e.getDcrush(), false, false));
			dbb.append(buildStatRow("Magic", currentEquipment.getDmagic(), e.getDmagic(), false, false));
			dbb.append(buildStatRow("Range", currentEquipment.getDrange(), e.getDrange(), false, false));

			if (dbb.length() > 0)
			{
				b.append(ColorUtil.wrapWithColorTag("Defence Bonus</br>", JagexColors.MENU_TARGET)).append(dbb);
			}
		}

		return b.toString();
	}

	private String buildStatChangeString(StatChange c)
	{
		StringBuilder b = new StringBuilder();
		b.append(ColorUtil.colorTag(Positivity.getColor(config, c.getPositivity())));

		if (config.relative())
		{
			b.append(c.getFormattedRelative());
		}

		if (config.theoretical())
		{
			if (config.relative())
			{
				b.append("/");
			}
			b.append(c.getFormattedTheoretical());
		}

		if (config.absolute() && (config.relative() || config.theoretical()))
		{
			b.append(" (");
		}
		if (config.absolute())
		{
			b.append(c.getAbsolute());
		}

		if (config.absolute() && (config.relative() || config.theoretical()))
		{
			b.append(")");
		}
		b.append(" ").append(c.getStat().getName());
		b.append("</br>");

		return b.toString();
	}
}
