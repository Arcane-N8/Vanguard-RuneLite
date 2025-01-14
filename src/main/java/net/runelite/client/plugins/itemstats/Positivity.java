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

import java.awt.*;

/**
 * Positivity represents how positive or negative a stat change is. This is
 * turned into the color shown to the user in the toolip.
 */
public enum Positivity
{
	/**
	 * The stat is lower than it was before.
	 */
	WORSE,
	/**
	 * There is no change, ie: The stat is already capped.
	 */
	NO_CHANGE,
	/**
	 * The stat change goes over the cap, but does not net 0
	 */
	BETTER_CAPPED,
	/**
	 * Some stat changes were fully consumed, some were not. This should NOT
	 * be returned by a single stat change. This should only be used by a
	 * <code>StatChangeCalculator</code>
	 */
	BETTER_SOMECAPPED,
	/**
	 * The stat change is fully consumed. NB: a boost that hits the cap, but
	 * does not go over it is still considered <code>BETTER_UNCAPPED</code>
	 */
	BETTER_UNCAPPED;

	public static Color getColor(ItemStatConfig config, Positivity positivity)
	{
		switch (positivity)
		{
			case BETTER_UNCAPPED:
				return config.colorBetterUncapped();
			case BETTER_SOMECAPPED:
				return config.colorBetterSomeCapped();
			case BETTER_CAPPED:
				return config.colorBetterCapped();
			case NO_CHANGE:
				return config.colorNoChange();
			case WORSE:
				return config.colorWorse();
			default:
				return Color.WHITE;
		}
	}
}
