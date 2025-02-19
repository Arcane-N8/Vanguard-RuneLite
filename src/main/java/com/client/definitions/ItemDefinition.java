package com.client.definitions;

import com.client.*;
import com.client.utilities.FileOperations;
import com.google.common.base.Preconditions;
import net.runelite.api.IterableHashTable;
import net.runelite.rs.api.RSItemComposition;
import net.runelite.rs.api.RSIterableNodeHashTable;

import java.util.HashMap;

public final class ItemDefinition implements RSItemComposition {

	public static ReferenceCache sprites = new ReferenceCache(100);
	public static ReferenceCache models = new ReferenceCache(50);
	public static boolean isMembers = true;
	public static int totalItems;
	public static ItemDefinition[] cache;
	private static int cacheIndex;
	private static Buffer1 item_data;
	private static int[] streamIndices;
	public int cost;
	public int[] colorReplace;
	public int id;
	public int[] colorFind;
	public boolean members;
	public int noted_item_id;
	public int femaleModel1;
	public int maleModel0;
	public String[] options;
	public int xOffset2d;
	public String name;
	public int modelId;
	public int maleHeadModel;
	public boolean stackable;
	public int unnoted_item_id;
	public int zoom2d;
	public int maleModel1;
	public String[] interfaceOptions;
	public int xan2d;
	public int[] countObj;
	public int yOffset2d;//
	public int femaleHeadModel;
	public int yan2d;
	public int femaleModel0;
	public int[] countCo;
	public int team;
	public int zan2d;
	public String[] equipActions;
	public boolean tradeable;
	public HashMap<Integer, Object> params;
	public int glowColor = -1;
	private short[] textureReplace;
	private short[] textureFind;
	private byte femaleOffset;
	private int femaleModel2;
	private int maleHeadModel2;
	private int resizeX;
	private int femaleHeadModel2;
	private int contrast;
	private int maleModel2;
	private int resizeZ;
	private int resizeY;
	private int ambient;
	private byte maleOffset;
	private int shiftClickIndex = -2;
	private int category;
	private int bought_id;
	private int bought_template_id;
	private int placeholder_id;
	private int placeholder_template_id;

	private ItemDefinition() {
		id = -1;
	}

	public void createCustomSprite(String img) {
		customSpriteLocation = getCustomSprite(img);
	}

	public void createSmallCustomSprite(String img) {
		customSmallSpriteLocation = getCustomSprite(img);
	}


	private byte[] getCustomSprite(String img) {
		String location = (Sprite.location + Configuration.CUSTOM_ITEM_SPRITES_DIRECTORY + img).toLowerCase();
		byte[] spriteData = FileOperations.readFile(location);
		Preconditions.checkState(spriteData != null, "No sprite: " + location);
		return spriteData;
	}

	public byte[] customSpriteLocation;
	public byte[] customSmallSpriteLocation;


	public static void clear() {
		models = null;
		sprites = null;
		streamIndices = null;
		cache = null;
		item_data = null;
	}

	public static void init(FileArchive archive) {
		item_data = new Buffer1(archive.readFile("obj.dat"));
		Buffer1 stream = new Buffer1(archive.readFile("obj.idx"));

		totalItems = stream.readUShort();
		streamIndices = new int[totalItems + 30_000];
		int offset = 2;

		for (int _ctr = 0; _ctr < totalItems; _ctr++) {
			streamIndices[_ctr] = offset;
			offset += stream.readUShort();
		}

		cache = new ItemDefinition[10];

		for (int _ctr = 0; _ctr < 10; _ctr++) {
			cache[_ctr] = new ItemDefinition();
		}

		System.out.println("Loaded: " + totalItems + " items");
	}


	public static ItemDefinition copy(ItemDefinition itemDef, int newId, int copyingItemId, String newName, String...actions) {
		ItemDefinition copyItemDef = lookup(copyingItemId);
		itemDef.id = newId;
		itemDef.name = newName;
		itemDef.colorFind = copyItemDef.colorFind;
		itemDef.colorReplace = copyItemDef.colorReplace;
		itemDef.modelId = copyItemDef.modelId;
		itemDef.maleModel0 = copyItemDef.maleModel0;
		itemDef.femaleModel0 = copyItemDef.femaleModel0;
		itemDef.zoom2d = copyItemDef.zoom2d;
		itemDef.xan2d = copyItemDef.xan2d;
		itemDef.yan2d = copyItemDef.yan2d;
		itemDef.xOffset2d = copyItemDef.xOffset2d;
		itemDef.yOffset2d = copyItemDef.yOffset2d;
		itemDef.interfaceOptions = copyItemDef.interfaceOptions;
		itemDef.interfaceOptions = new String[5];
		if (actions != null) {
			for (int index = 0; index < actions.length; index++) {
				itemDef.interfaceOptions[index] = actions[index];
			}
		}
		return itemDef;
	}


	private static void customItems(int itemId) {
		ItemDefinition itemDef = lookup(itemId);

		switch (itemId) {

			case 30000:
				copy(itemDef, 30_000, 11738, "Resource box(small)", "Open");
				break;
			case 30001:
				copy(itemDef, 30_001, 11738, "Resource box(medium)", "Open");
				break;
			case 30002:
				copy(itemDef, 30_002, 11738, "Resource box(large)", "Open");
				break;
			case 22375:
				copy(itemDef, 22375, 22374, "Mossy key");
				break;
			case 20999:
				copy(itemDef, 20999, 25865, "Bow of Faerdhinen", null, "Wear");
				break;

			case 33056:
				itemDef.setDefaults();
				itemDef.id = 33056;
				itemDef.modelId = 65270;
				itemDef.name = "Completionist cape";

				itemDef.zoom2d = 1385;
				itemDef.xan2d = 279;
				itemDef.yan2d = 948;
				itemDef.zan2d = 0;
				itemDef.xOffset2d = 0;
				itemDef.yOffset2d = 24;

				itemDef.maleModel0 = 65297;
				itemDef.femaleModel0 = 65316;
				//itemDef.groundActions = new String[5];
				//itemDef.groundActions[2] = "Take";
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = "Teleports";
				itemDef.interfaceOptions[3] = "Features";
				itemDef.interfaceOptions[4] = "Drop";
				break;
			case 33057:
				itemDef.setDefaults();
				itemDef.id = 33057;
				itemDef.modelId = 65273;
				itemDef.name = "Completionist hood";

				itemDef.zoom2d = 760;
				itemDef.xan2d = 11;
				itemDef.yan2d = 0;
				itemDef.zan2d = 0;
				itemDef.xOffset2d = 0;
				itemDef.yOffset2d = 0;

				itemDef.maleModel0 = 65292;
				itemDef.femaleModel0 = 65310;
				//itemDef.groundActions = new String[5];
				//itemDef.groundActions[2] = "Take";
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				break;


			case 26784://colossal pouch
				itemDef.interfaceOptions = new String[] { "Fill", "Empty", "Check", null, null };
				break;

			case 16012://Mini solak pet
				itemDef.name = "Mini Maledictus";
				itemDef.interfaceOptions = new String[]
					{null, null, null, null, "Drop"};
				itemDef.zoom2d = 1000;
				itemDef.xan2d = 100;
				itemDef.yan2d = 100;
				itemDef.xOffset2d = 0;
				itemDef.yOffset2d = 100;
				itemDef.createCustomSprite("maledictus.png");
				break;

			case 13204://platinum token
				//itemDef.description = "Consume for 1b gold coins.";
				itemDef.interfaceOptions = new String[]
					{null, null, "Consume", null, null};
				break;

			case 26571://Revenant key // NOT USED YET
				itemDef.name = "Revenant key";
				//itemDef.description = "Used to open the revenent chest.";
				break;

			case 681://ANCIENT TALISMAN
			case 11180://ANCIENT COIN
				//itemDef.inventoryOptions = new String[]
				//		{null, null, "Convert", null, null};
				break;

			case 23499://Kratos entry key
				itemDef.name = "Kratos entry key";
				//itemDef.description = "Used to fight the big boss kratos.";
				break;

			case 995:
				itemDef.interfaceOptions = new String[]
					{null, null, null, "Add-to-pouch", "Drop"};
				break;

			case 21126://Hazelmeres Signet
				itemDef.name = "Hazelmeres Signet";
				//itemDef.description = "Its an Hazelmeres Signet.";
				break;

			case 23145:
				itemDef.name = "Twisted crossbow";
				//itemDef.description = "Twisted crossbow.";
				itemDef.modelId = 62777;
				itemDef.maleModel0 = 62776;
				itemDef.femaleModel0 = 62776;
				itemDef.zoom2d = 926;
				itemDef.xan2d = 432;
				itemDef.yan2d = 258;
				itemDef.xOffset2d = 0;
				itemDef.yOffset2d = 3;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				// itemDef.aByte205 = 3;
				break;

			case 6821:
				itemDef.name = "Overload heart";
				itemDef.modelId = 65061;
				itemDef.zoom2d = 1168;
				itemDef.xan2d = 96;
				itemDef.yan2d = 1690;
				itemDef.xOffset2d = 0;
				itemDef.yOffset2d = 1;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[0] = "Intake-power";
				itemDef.interfaceOptions[1] = null;
				itemDef.interfaceOptions[2] = null;
				itemDef.glowColor = 0x000000;
				break;
			case 6902:
				itemDef.name = "Prayer heart";
				itemDef.modelId = 65060;
				itemDef.zoom2d = 1168;
				itemDef.xan2d = 96;
				itemDef.yan2d = 1690;
				itemDef.xOffset2d = 0;
				itemDef.yOffset2d = 1;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[0] = "Sip-on";
				itemDef.interfaceOptions[1] = null;
				itemDef.interfaceOptions[2] = null;
				itemDef.glowColor = 0x00FF00;
				break;


			case 16011://Mini solak pet
				itemDef.name = "Mini Solak";
				itemDef.interfaceOptions = new String[]
					{null, null, null, null, "Drop"};
				itemDef.zoom2d = 1000;
				itemDef.xan2d = 100;
				itemDef.yan2d = 100;
				itemDef.xOffset2d = 0;
				itemDef.yOffset2d = 100;
				itemDef.createCustomSprite("minisolak.png");
				break;

			case 12468://New dragon kite shield
				itemDef.name = "Dragon Kiteshield new";
				itemDef.modelId = 13701;
				itemDef.maleModel0 = 13700;
				itemDef.femaleModel0 = 13700;
				itemDef.zoom2d = 1560;
				itemDef.xan2d = 636;
				itemDef.yan2d = 0;
				itemDef.xOffset2d = -6;
				itemDef.yOffset2d = -14;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				break;


			case 7302://kratos key
				itemDef.name = "Kratos key";
				//itemDef.description = "Its an kratos key.";
				break;

			case 11040://tarn entry key
				itemDef.name = "Tarn Entry Key";
				//itemDef.description = "Use this to enter the tarn passage.";
				break;

			case 9722://tarn reward key
				itemDef.name = "Tarn Reward Key";
				//itemDef.description = "Use this on the tarn chest.";
				break;


			case 13248://Slayer key (tier 1)
				itemDef.name = "Slayer key (tier 1)";
				//itemDef.description = "Use this at the slayer chest at home.";
				break;

			case 21055://Slayer key (tier 2)
				itemDef.name = "Slayer key (tier 2)";
				//itemDef.description = "Use this at the slayer chest at home.";
				break;

			case 21054://Slayer key (tier 3)
				itemDef.name = "Slayer key (tier 3)";
				//itemDef.description = "Use this at the slayer chest at home.";
				break;

			case 21053://Slayer key (tier 4)
				itemDef.name = "Slayer key (tier 4)";
				//itemDef.description = "Use this at the slayer chest at home.";
				break;



//			case 20999:
//				itemDef.name = "Bow of faerdhinen";
//				itemDef.modelId = 42605;
//				itemDef.maleModel0 = 42602;
//				itemDef.femaleModel0 = 42602;
//				itemDef.zoom2d = 1570;
//				itemDef.xan2d = 636;
//				itemDef.yan2d = 1010;
//				itemDef.xOffset2d = 1;
//				itemDef.yOffset2d = 0;
//				itemDef.interfaceOptions = new String[5];
//				itemDef.interfaceOptions[1] = "Wear";
//				itemDef.interfaceOptions[2] = null;
//				break;




			case 11048://Zilyana's shard
				itemDef.name = "Zilyana's shard";
				//itemDef.description = "Can be used on the upgrade table only.";
				itemDef.interfaceOptions = new String[] { null, null, null, null, null};
				break;

			case 24111:
				itemDef.name = "Zilyana's godbow";
				//itemDef.description = "A bow that belonged to Zilyana.";
				itemDef.modelId = 53122;
				itemDef.maleModel0 = 53121;
				itemDef.femaleModel0 = 53121;
				itemDef.zoom2d = 2000;
				itemDef.xan2d = 636;
				itemDef.yan2d = 1010;
				itemDef.xOffset2d = 4;
				itemDef.yOffset2d = 3;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				// itemDef.aByte205 = 3;
				break;


			case 16017:
				itemDef.name = "Mini Galvek";
				itemDef.interfaceOptions = new String[]
					{null, null, null, null, "Drop"};
				itemDef.zoom2d = 1000;
				itemDef.xan2d = 100;
				itemDef.yan2d = 100;
				itemDef.xOffset2d = 0;
				itemDef.yOffset2d = 100;
				itemDef.createCustomSprite("minigalvek.png");
				break;



			case 16015:
				itemDef.name = "Dharok pet";
				itemDef.interfaceOptions = new String[]
					{null, null, null, null, "Drop"};
				itemDef.zoom2d = 1000;
				itemDef.xan2d = 100;
				itemDef.yan2d = 100;
				itemDef.xOffset2d = 0;
				itemDef.yOffset2d = 100;
				itemDef.createCustomSprite("Dharok_the_Wretched.png");
				break;

			case 24158:
				itemDef.name = "K'ril swords";
				//itemDef.description = "Swords dropped by the almighty K'ril.";
				itemDef.modelId = 62556;
				itemDef.maleModel0 = 62557;
				itemDef.femaleModel0 = 62557;
				itemDef.zoom2d = 1650;
				itemDef.xan2d = 498;
				itemDef.yan2d = 1300;
				itemDef.xOffset2d = 3;
				itemDef.yOffset2d = 0;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				//itemDef.aByte205 = 3;
				break;


			case 23582:
				itemDef.name = "Elder multi-axe";
				//itemDef.description = "A multifunctional axe where you can mine or woodcut with.";
				itemDef.modelId = 49503;
				itemDef.maleModel0 = 49502;
				itemDef.femaleModel0 = 49502;
				itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
				itemDef.zoom2d = 950;
				itemDef.xan2d = 1405;
				itemDef.yan2d = 330;
				itemDef.xOffset2d = 0;
				itemDef.yOffset2d = 0;
				itemDef.unnoted_item_id = -1;
				itemDef.noted_item_id = -1;
				itemDef.stackable = false;
				break;




			case 24101:
				itemDef.setDefaults();
				itemDef.name = "Vorkath platebody";
				//itemDef.description = "Vorkath armour.";
				itemDef.modelId = 53100;
				itemDef.maleModel0 = 53099;
				itemDef.femaleModel0 = 53099;
				itemDef.zoom2d = 1513;
				itemDef.xan2d = 566;
				itemDef.yan2d = 0;
				itemDef.xOffset2d = 1;
				itemDef.yOffset2d = -8;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				//itemDef.aByte205 = 3;
				break;

			case 24105:
				itemDef.name = "Vorkath helmet";
				//itemDef.description = "Vorkath armour.";
				itemDef.modelId = 53108;
				itemDef.maleModel0 = 53107;
				itemDef.femaleModel0 = 53107;
				itemDef.zoom2d = 1010;
				itemDef.xan2d = 16;
				itemDef.yan2d = 0;
				itemDef.xOffset2d = 2;
				itemDef.yOffset2d = -4;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				// itemDef.aByte205 = 3;
				break;

			case 24102:
				itemDef.name = "Vorkath platelegs";
				//itemDef.description = "Vorkath armour.";
				itemDef.modelId = 53102;
				itemDef.maleModel0 = 53101;
				itemDef.femaleModel0 = 53101;
				itemDef.zoom2d = 1753;
				itemDef.xan2d = 562;
				itemDef.yan2d = 1;
				itemDef.xOffset2d = 11;
				itemDef.yOffset2d = -3;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				// itemDef.aByte205 = 3;
				break;

			case 24103:
				itemDef.name = "Vorkath boots";
				//itemDef.description = "Vorkath armour.";
				itemDef.modelId = 53104;
				itemDef.maleModel0 = 53103;
				itemDef.femaleModel0 = 53103;
				itemDef.zoom2d = 855;
				itemDef.xan2d = 215;
				itemDef.yan2d = 94;
				itemDef.xOffset2d = 4;
				itemDef.yOffset2d = -32;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				// itemDef.aByte205 = 3;
				break;

			case 24104:
				itemDef.name = "Vorkath gloves";
				//itemDef.description = "Vorkath armour.";
				itemDef.modelId = 53106;
				itemDef.maleModel0 = 53105;
				itemDef.femaleModel0 = 53105;
				itemDef.zoom2d = 855;
				itemDef.xan2d = 215;
				itemDef.yan2d = 94;
				itemDef.xOffset2d = 4;
				itemDef.yOffset2d = -32;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				// itemDef.aByte205 = 3;
				break;

			case 24106:
				itemDef.name = "Vorkath blowpipe";
				//itemDef.description = "Vorkath blowpipe.";
				itemDef.modelId = 53126;
				itemDef.maleModel0 = 53125;
				itemDef.femaleModel0 = 53125;
				itemDef.zoom2d = 1509;
				itemDef.xan2d = 570;
				itemDef.yan2d = 286;
				itemDef.xOffset2d = -4;
				itemDef.yOffset2d = 0;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = "Check";
				itemDef.interfaceOptions[3] = "Unload";
				itemDef.interfaceOptions[4] = "Uncharge";
				// itemDef.aByte205 = 3;
				break;


			case 24107:
				itemDef.name = "Vorkath blowpipe(empty)";
				//itemDef.description = "Vorkath blowpipe.";
				itemDef.modelId = 53126;
				itemDef.maleModel0 = 53125;
				itemDef.femaleModel0 = 53125;
				itemDef.zoom2d = 1158;
				itemDef.xan2d = 768;
				itemDef.yan2d = 189;
				itemDef.xOffset2d = -7;
				itemDef.yOffset2d = 4;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				itemDef.interfaceOptions[3] = "Dismantle";
				// itemDef.aByte205 = 3;
				break;

			case 24119:
				itemDef.name = "Black dragon hunter crossbow";
				//itemDef.description = "Black dragon hunter crossbow.";
				itemDef.modelId = 53124;
				itemDef.maleModel0 = 53123;
				itemDef.femaleModel0 = 53123;
				itemDef.zoom2d = 1554;
				itemDef.xan2d = 636;
				itemDef.yan2d = 1010;
				itemDef.xOffset2d = 4;
				itemDef.yOffset2d = 3;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				//itemDef.aByte205 = 3;
				break;

			case 11200:
				itemDef.name = "Demon faceguard";
				itemDef.modelId = 65050;
				itemDef.maleModel0 = 65049;
				itemDef.femaleModel0 = 65049;
				itemDef.zoom2d = 984;
				itemDef.xan2d = 126;
				itemDef.yan2d = 129;
				itemDef.xOffset2d = -1;
				itemDef.yOffset2d = 1;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				break;

			case 34037:
				itemDef.setDefaults();
				itemDef.name = "@red@Death cape";
				//itemDef.description = "This cape gives 5% drop rate boost.";
				itemDef.modelId = 50205;
				itemDef.maleModel0 = 50205;
				itemDef.femaleModel0 = 50205;
				itemDef.zoom2d = 2300;
				itemDef.xan2d = 400;
				itemDef.yan2d = 1020;
				itemDef.xOffset2d = 3;
				itemDef.yOffset2d = 30;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				//itemDef.maleOffset = 5;
				break;

			case 6806://runerogue imbue scroll
				itemDef.name = "@red@Vanguard imbue scroll";
				//itemDef.description = "When used on the ring of Vanguard +12 droprate bonus.";
				itemDef.interfaceOptions = new String[] { null, null, null, null, null};
				break;

			case 21752:
				itemDef.name = "@red@Ring of Vanguard";
				//itemDef.description = "<img=10> <col=996633>This item will collect all drops and send them to inventory or bank when equipped.";
				itemDef.interfaceOptions = new String[] { null, "Wield", "Check config", "Toggle config", null};
				break;

			case 21753:
				itemDef.name = "Ring of Vanguard(i)";
				//itemDef.description = "<img=10> <col=996633>This item will collect all drops and send them to inventory or bank when equipped.";
				itemDef.interfaceOptions = new String[] { null, "Wield", "Check config", "Toggle config", "Dismantle"};
				break;

			case 8152:
				itemDef.name = "Vote boss chest";
				//itemDef.description = "Redeem this chest to be teleported to the vote boss.";
				itemDef.interfaceOptions = new String[] { "Redeem", null, null, null, null};
				break;


			case 22428:
				itemDef.name = "Solak key";
				//itemDef.description = "Its an solak event key.";
				break;

			case 4589:
				itemDef.name = "Glod key";
				//itemDef.description = "Its an glod event key.";
				break;


			case 21726:
			case 21728:
				itemDef.stackable = true;
				break;
			case 12863:
				itemDef.interfaceOptions = new String[] { "Open", null, null, null, null};
				break;
			case 13092: //this makes crystal halberds wieldable, weird af.
			case 13093:
			case 13094:
			case 13095:
			case 13096:
			case 13097:
			case 13098:
			case 13099:
			case 13100:
			case 13101:
				itemDef.interfaceOptions = new String[] { null, "Wield", null, null, null};
				break;
			case 23933:
				itemDef.name = "Vote crystal";
				break;
			case 9698:
				itemDef.name = "Unfired burning rune";
				//itemDef.description = "I should burn this.";
				itemDef.createCustomSprite("Unfired_burning_rune.png");
				break;
			case 9699:
				itemDef.name = "Burning rune";
				//itemDef.description = "Hot to the touch.";
				itemDef.createCustomSprite("Burning_rune.png");
				break;
			case 23778:
				itemDef.name = "Uncut toxic gem";
				//itemDef.description = "I should use a chisel on this.";
				break;
			case 22374:
				itemDef.name = "Hespori key";
				//itemDef.description = "Can be used on the Hespori chest.";
				break;
			case 23783:
				itemDef.name = "Toxic gem";
				//itemDef.description = "I should be careful with this.";
				break;
			case 9017:
				itemDef.name = "Hespori essence";
				//itemDef.description = "Maybe I should burn this.";
				itemDef.interfaceOptions = new String[] {  null, null, null, null, "Drop" };
				break;
			case 19473:
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				break;
			case 10556:
			case 10557:
			case 10558:
			case 10559:
				itemDef.interfaceOptions = new String[] { null, "Wear", "Feature", null, "Drop" };
				break;
			case 21898:
				itemDef.interfaceOptions = new String[] { null, "Wear", "Teleports", "Features", null };
				break;
			case 12873:
			case 12875:
			case 12877:
			case 12879:
			case 12881:
			case 12883:
				itemDef.interfaceOptions = new String[] { "Open", null, null, null, "Drop" };
				break;
			case 23804:
				itemDef.name = "Imbue Dust";
				break;
			case 22517:
				itemDef.name = "Crystal Shard";
				break;
			case 23951:
				itemDef.name = "Crystalline Key";
				break;
			case 691:
				itemDef.name = "@gre@10,000 FoE Point Certificate";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				break;
			case 692:
				itemDef.name = "@red@25,000 FoE Point Certificate";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				break;
			case 693:
				itemDef.name = "@cya@50,000 FoE Point Certificate";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				break;
			case 696:
				itemDef.name = "@yel@250,000 FoE Point Certificate";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				break;
			case 23877:
				itemDef.name = "Crystal Shard";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = true;
				break;
			case 23943:
				itemDef.interfaceOptions = new String[] { null, "Wear", "Uncharge", "Check", "Drop" };
				break;
			case 2996:
				itemDef.name = "PKP Ticket";
				break;
			case 23776:
				itemDef.name = "Hunllef's Key";
				break;
			case 13148:
				itemDef.name = "@red@Reset Lamp";
				break;
			case 6792:
				itemDef.name = "Seren's Key";
				break;
			case 4185:
				itemDef.name = "Nightmare key";
				//itemDef.description = "Use this key on the nightmare chest.";
				break;
			case 21880:
				itemDef.name = "Wrath Rune";
				itemDef.cost = 1930;
				break;
			case 12885:
			case 13277:
			case 19701:
			case 13245:
			case 12007:
			case 22106:
			case 12936:
			case 24495:
				itemDef.interfaceOptions = new String[] { null, null, "Open", null, "Drop" };
				break;
			case 21262:
				itemDef.name = "Vote Genie Pet";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Release" };
				break;
			case 21817:
				itemDef.interfaceOptions = new String[] { null, "Wear", "Dismantle", null, null, };
				break;
			case 21347:
				itemDef.interfaceOptions = new String[] { null, null, null, "Chisel-Options", null, };
				break;
			case 21259:
				itemDef.name = "@red@Name Change Scroll";
				itemDef.interfaceOptions = new String[] { null, null, "Read", null, null, };
				break;
			case 22547:
			case 22552:
			case 22542:
				itemDef.interfaceOptions = new String[] { null, null, null, null, null, };
				break;
			case 22555:
			case 22550:
			case 22545:
				itemDef.interfaceOptions = new String[] { null, "Wield", "Check", "Uncharge", null, };
				break;
			case 732:
				itemDef.name = "@blu@Imbuedeifer";
				itemDef.cost = 1930;
				break;
			case 21881:
				itemDef.name = "Wrath Rune";
				itemDef.cost = 1930;
				break;
			case 13226:
				itemDef.name = "Herb Sack";
				//itemDef.description = "Thats a nice looking sack.";
				break;
			case 3456:
				itemDef.name = "@whi@Common Raids Key";
				//itemDef.description = "Can be used on the storage unit.";
				break;
			case 3464:
				itemDef.name = "@pur@Rare Raids Key";
				//itemDef.description = "Can be used on the storage unit.";
				break;
			case 6829:
				itemDef.name = "@red@YT Video Giveaway Box";
				//itemDef.description = "Spawns items to giveaway for your youtube video.";
				itemDef.interfaceOptions = new String[] { "Giveaway", null, null, null, "Drop" };
				break;
			case 6831:
				itemDef.name = "@red@YT Video Giveaway Box (t2)";
				//itemDef.description = "Spawns items to giveaway for your youtube video.";
				itemDef.interfaceOptions = new String[] { "Giveaway", null, null, null, "Drop" };

				break;
			case 6832:
				itemDef.name = "@red@YT Stream Giveaway Box";
				//itemDef.description = "Spawns items to giveaway for your youtube stream.";
				itemDef.interfaceOptions = new String[] { "Giveaway", null, null, null, "Drop" };

				break;
			case 6833:
				itemDef.name = "@red@YT Stream Giveaway Box (t2)";
				//itemDef.description = "Spawns items to giveaway for your youtube stream.";
				itemDef.interfaceOptions = new String[] { "Giveaway", null, null, null, "Drop" };
				break;
			case 13190:
				itemDef.name = "@or1@Starter Box";
				itemDef.modelId = 65000;
				itemDef.zoom2d = 1180;
				itemDef.xan2d = 160;
				itemDef.yan2d = 172;
				itemDef.xOffset2d = 0;
				itemDef.yOffset2d = -14;
				itemDef.interfaceOptions = new String[] { "Redeem", null, null, null, "Drop" };
				break;
			case 6466:
				itemDef.name = "Shattered Shard";
				itemDef.modelId = 65001;
				itemDef.interfaceOptions = new String[] { "Open-rates", null, "Open-upgrades", null, "Drop" };
				break;
			case 6121:
				itemDef.name = "Break Vials Instruction";
				//itemDef.description = "How does one break a vial, its impossible?";
				break;
			case 2528:
				itemDef.name = "@red@Experience Lamp";
				//itemDef.description = "Should I rub it......";
				break;
			case 5509:
				itemDef.name = "Small Pouch";
				itemDef.createCustomSprite("Small_pouch.png");
				itemDef.interfaceOptions = new String[] { "Fill", "Empty", "Check", null, null };
				break;
			case 5510:
				itemDef.name = "Medium Pouch";
				itemDef.createCustomSprite("Medium_pouch.png");
				itemDef.interfaceOptions = new String[] { "Fill", "Empty", "Check", null, null };
				break;
			case 5512:
				itemDef.name = "Large Pouch";
				itemDef.createCustomSprite("Large_pouch.png");
				itemDef.interfaceOptions = new String[] { "Fill", "Empty", "Check", null, null };
				break;
			case 10724: //full skeleton
			case 10725:
			case 10726:
			case 10727:
			case 10728:
				itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
				break;
			case 5514:
				itemDef.name = "Giant Pouch";
				itemDef.createCustomSprite("Giant_pouch.png");
				break;
			case 22610: //vesta spear
				itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
				break;
			case 22613: //vesta longsword
				itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
				break;
			case 22504: //stat warhammer
				itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
				break;
			case 4224:
			case 4225:
			case 4226:
			case 4227:
			case 4228:
			case 4229:
			case 4230:
			case 4231:
			case 4232:
			case 4233:
			case 4234:
			case 4235://crystal sheild
				itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
				break;
			case 4212:
			case 4214:
			case 4215:
			case 4216:
			case 4217:
			case 4218:
			case 4219:
			case 4220:
			case 4221:
			case 4222:
			case 4223:
				itemDef.interfaceOptions = new String[] { null, "Wield", null, null, "Drop" };
				break;
			case 2841:
				itemDef.name = "@red@Bonus Exp Scroll";
				itemDef.interfaceOptions = new String[] { "@yel@Activate", null, null, null, "Drop" };
				//itemDef.description = "You will get double experience using this scroll.";
				break;
			case 21791:
			case 21793:
			case 21795:
				itemDef.interfaceOptions = new String[] { null, "Wear", null, null, "Drop" };
				break;
			case 19841:
				itemDef.name = "Master Casket";
				break;
			case 21034:
				itemDef.interfaceOptions = new String[] { "Read", null, null, null, "Drop" };
				break;
			case 6830:
				itemDef.name = "@red@@EVEN INSANER- @blu@BOX";
				itemDef.interfaceOptions = new String[] { "Open", null, null, null, "Drop" };
				break;
			case 6839:
				itemDef.name = "@blu@ Mystery Pack";
				itemDef.interfaceOptions = new String[] { "Open", null, null, null, "Drop" };
				break;
			case 21079:
				itemDef.interfaceOptions = new String[] { "Read", null, null, null, "Drop" };
				break;
			case 22093:
				itemDef.name = "@gre@Vote Streak Key";
				//itemDef.description = "Thanks for voting!";
				break;
			case 22885:
				itemDef.name = "@gre@Kronos seed";
				//itemDef.description = "Provides whole server with bonus xp for 1 skill for 5 hours!";
				break;
			case 23824:
				itemDef.name = "Slaughter charge";
				//itemDef.description = "Can be used on bracelet of slaughter to charge it.";
				break;
			case 22883:
				itemDef.name = "@gre@Iasor seed";
				//itemDef.description = "Increased drop rate (+10%) for whole server for 5 hours!";
				break;
			case 22881:
				itemDef.name = "@gre@Attas seed";
				//itemDef.description = "Provides the whole server with bonus xp for 5 hours!";
				break;
			case 20906:
				itemDef.name = "@gre@Golpar seed";
				//itemDef.description = "Provides whole server with double c keys, resource boxes, coin bags, and clues!";
				break;
			case 6112:
				itemDef.name = "@gre@Kelda seed";
				//itemDef.description = "Provides whole server with x2 Larren's keys for 1 hour!";
				break;
			case 20903:
				itemDef.name = "@gre@Noxifer seed";
				//itemDef.description = "Provides whole server with x2 Slayer points for 1 hour!";
				break;
			case 20909:
				itemDef.name = "@gre@Buchu seed";
				//itemDef.description = "Provides whole server with x2 Boss points for 1 hour!";
				break;
			case 22869:
				itemDef.name = "@gre@Celastrus seed";
				//itemDef.description = "Provides whole server with x2 Brimstone keys for 1 hour!";
				break;
			case 4205:
				itemDef.name = "@gre@Consecration seed";
				//itemDef.description = "Provides the whole server with +5 PC points for 1 hour.";
				itemDef.stackable = true;
				break;
			case 11864:
			case 11865:
			case 19639:
			case 19641:
			case 19643:
			case 19645:
			case 19647:
			case 19649:
			case 24444:
			case 24370:
			case 23075:
			case 23073:
			case 21888:
			case 21890:
			case 21264:
			case 21266:
				itemDef.equipActions[2] = "Log";
				itemDef.equipActions[1] = "Check";
				break;
			case 13136:
				itemDef.equipActions[2] = "Elidinis";
				itemDef.equipActions[1] = "Kalphite Hive";
				break;
			case 2550:
				itemDef.equipActions[2] = "Check";
				break;

			case 1712:
			case 1710:
			case 1708:
			case 1706:
			case 19707:
				itemDef.equipActions[1] = "Edgeville";
				itemDef.equipActions[2] = "Karamja";
				itemDef.equipActions[3] = "Draynor";
				itemDef.equipActions[4] = "Al-Kharid";
				break;
			case 21816:
				itemDef.interfaceOptions = new String[] { null, "Wear", "Uncharge", null, "Drop" };
				itemDef.equipActions[1] = "Check";
				itemDef.equipActions[2] = "Toggle-absorption";
				break;
			case 2552:
			case 2554:
			case 2556:
			case 2558:
			case 2560:
			case 2562:
			case 2564:
			case 2566: // Ring of duelling
				itemDef.equipActions[2] = "Shantay Pass";
				itemDef.equipActions[1] = "Clan wars";
				break;
			case 11739:
				itemDef.name = "@gre@Vote Mystery Box";
				//itemDef.description = "Probably contains cosmetics, or maybe not...";
				itemDef.interfaceOptions = new String[] { "Open", null, "View-Loots", null, "Drop" };
				break;
			case 6828:
				itemDef.name = "Super Mystery Box";
				//itemDef.description = "Mystery box that contains goodies.";
				itemDef.interfaceOptions = new String[] { "Open", null, "View-Loots", null, "Drop" };
				itemDef.createCustomSprite("Mystery_Box.png");
				itemDef.createSmallCustomSprite("Mystery_Box_Small.png");
				itemDef.stackable = false;
				break;
			case 30010:
				itemDef.setDefaults();
				itemDef.name = "Postie Pete";
				//itemDef.description = "50% chance to pick up crystal keys that drop.";
				itemDef.createCustomSprite("Postie_Pete.png");
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				break;
			case 30011:
				itemDef.setDefaults();
				itemDef.name = "Imp";
				//itemDef.description = "50% chance to pick up clue scrolls that drop.";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("Imp.png");
				break;
			case 30012:
				itemDef.setDefaults();
				itemDef.name = "Toucan";
				//itemDef.description = "50% chance to pick up resource packs.";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("Toucan.png");
				break;
			case 30013:
				itemDef.setDefaults();
				itemDef.name = "Penguin King";
				//itemDef.description = "50% chance to auto-pick up coin bags.";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("Penguin_King.png");
				break;
			case 30014:
				itemDef.setDefaults();
				itemDef.name = "K'klik";
				//itemDef.description = "An extra 5% in drop rate boost.";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("K'klik.png");
				break;
			case 30015:
				itemDef.setDefaults();
				itemDef.name = "Shadow warrior";
				//itemDef.description = "50% chance for an additional +10% strength bonus in pvm.";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("Shadow_warrior.png");
				break;
			case 30016:
				itemDef.setDefaults();
				itemDef.name = "Shadow archer";
				//itemDef.description = "50% chance for an additional +10% range str bonus in PvM.";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("Shadow_archer.png");
				break;
			case 30017:
				itemDef.setDefaults();
				itemDef.name = "Shadow wizard";
				//itemDef.description = "50% chance for an additional +10% mage str bonus in PvM.";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("Shadow_wizard.png");
				break;
			case 30018:
				itemDef.setDefaults();
				itemDef.name = "Healer Death Spawn";
				//itemDef.description = "5% chance hit restores HP.";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("Healer_Death_Spawn.png");
				break;
			case 30019:
				itemDef.setDefaults();
				itemDef.name = "Holy Death Spawn";
				//itemDef.description = "5% chance 1/2 of your hit is restored into prayer.";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("Holy_Death_Spawn.png");
				break;
			case 30020:
				itemDef.setDefaults();
				itemDef.name = "Corrupt beast";
				//itemDef.description = "50% chance for an additional +10% strength bonus for melee, mage, and range in pvm.";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("Corrupt_beast.png");
				break;
			case 30021:
				itemDef.setDefaults();
				itemDef.name = "Roc";
				//itemDef.description = "An extra 10% in drop rate boost.";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("Roc.png");
				break;
			case 30022:
				itemDef.setDefaults();
				itemDef.name = "@red@Kratos";
				//itemDef.description = "The most powerful pet, see ::foepets for full list of perks.";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("Yama.png");
				break;
			case 30023:
				itemDef.setDefaults();
				itemDef.name = "Rain cloud";
				//itemDef.description = "Don't worry be happy.";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("Rain_cloud.png");
				break;
			case 8866:
				itemDef.name = "Storage chest key (UIM)";
				//itemDef.description = "Used to open the UIM storage chest 1 time.";
				itemDef.stackable = true;
				break;
			case 8868:
				itemDef.name = "Perm. storage chest key (UIM)";
				//itemDef.description = "Permanently unlocks UIM storage chest.";
				break;
			case 771:
				itemDef.name = "@cya@Ancient branch";
				//itemDef.description = "Burning items in the FoE with this branch provides a 1 time +10% FoE value increase.";
				break;
			case 6199:
				itemDef.name = "Mystery Box";
				//itemDef.description = "Mystery box that contains goodies.";
				itemDef.interfaceOptions = new String[] { "Open", null, "View-Loots", null, "Drop" };
				break;
			case 12789:
				itemDef.name = "@red@Youtube Mystery Box";
				//itemDef.description = "Mystery box that contains goodies.";
				itemDef.interfaceOptions = new String[] { "Open", null, "View-Loots", null, "Drop" };
				break;
			case 13346:
				itemDef.name = "@red@Ultra Mystery Box";
				itemDef.interfaceOptions = new String[] { "Open", null, "View-Loots", null, "Drop" };
				break;
			case 8167://
				itemDef.name = "@red@Insane Mystery Box (locked)";
				itemDef.interfaceOptions = new String[] { "Unlock", null, "View-Loots", null, "Drop" };
				break;
			case 13438:
				itemDef.name = "Slayer Mystery Chest";
				itemDef.interfaceOptions = new String[] { "Open", null, null, null, "Drop" };
				break;
			case 2399:
				itemDef.name = "@or2@FoE Mystery Key";
				//itemDef.description = "Used to unlock the FoE Mystery Chest.";
				break;
			case 10832:
				itemDef.name = "Small coin bag";
				itemDef.interfaceOptions = new String[] { "Open", null, "Open-All", null, "Drop" };
				//itemDef.description = "I can see some coins inside.";
				break;
			case 10833:
				itemDef.name = "Medium coin bag";
				itemDef.interfaceOptions = new String[] { "Open", null, "Open-All", null, "Drop" };
				//itemDef.description = "I can see some coins inside.";
				break;
			case 10834:
				itemDef.name = "Large coin bag";
				itemDef.interfaceOptions = new String[] { "Open", null, "Open-All", null, "Drop" };
				//itemDef.description = "I can see some coins inside.";
				break;
			case 22316:
				itemDef.name = "Sword of Vanguard";
				//itemDef.description = "The Sword of Vanguard.";
				break;
			case 19942:
				itemDef.name = "Lil Mimic";
				//itemDef.description = "It's a lil mimic.";
				break;
			case 30110:
				itemDef.setDefaults();
				itemDef.name = "Dark postie pete";
				//itemDef.description = "Picks up all crystal keys and 25% chance to double.";
				itemDef.createCustomSprite("dark_Postie_Pete.png");
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				break;
			case 30111:
				itemDef.setDefaults();
				itemDef.name = "Dark imp";
				//itemDef.description = "Picks up all clue scrolls and 25% chance to double.";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("dark_Imp.png");
				break;
			case 30112:
				itemDef.setDefaults();
				itemDef.name = "Dark toucan";
				//itemDef.description = "Picks up all resource boxes and 25% chance to double.";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("dark_Toucan.png");
				break;
			case 30113:
				itemDef.setDefaults();
				itemDef.name = "Dark penguin King";
				//itemDef.description = "Picks up all coin bags and 25% chance to double.";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("dark_Penguin_King.png");
				break;
			case 30114:
				itemDef.setDefaults();
				itemDef.name = "Dark k'klik";
				//itemDef.description = "An extra 10% in drop rate boost.";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("dark_K'klik.png");
				break;
			case 30115:
				itemDef.setDefaults();
				itemDef.name = "Dark shadow warrior";
				//itemDef.description = "Gives constant +10% strength bonus in pvm.";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("dark_Shadow_warrior.png");
				break;
			case 30116:
				itemDef.setDefaults();
				itemDef.name = "Dark shadow archer";
				//itemDef.description = "Gives constant +10% range str bonus in PvM.";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("dark_Shadow_archer.png");
				break;
			case 30117:
				itemDef.setDefaults();
				itemDef.name = "Dark shadow wizard";
				//itemDef.description = "Gives constant +10% mage str bonus in PvM.";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("dark_Shadow_wizard.png");
				break;
			case 30118:
				itemDef.setDefaults();
				itemDef.name = "Dark healer death spawn";
				//itemDef.description = "10% chance hit restores HP.";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("dark_Healer_Death_Spawn.png");
				break;
			case 30119:
				itemDef.setDefaults();
				itemDef.name = "Dark holy death spawn";
				//itemDef.description = "10% chance 1/2 of your hit is restored into prayer.";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("dark_Holy_Death_Spawn.png");
				break;
			case 30120:
				itemDef.setDefaults();
				itemDef.name = "Dark corrupt beast";
				//itemDef.description = "Extra 10% in drop rate and constant +10% strength bonus for all styles in pvm.";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("dark_Corrupt_beast.png");
				break;
			case 30129:
				itemDef.setDefaults();
				itemDef.name = "Arks dark roc";
				//itemDef.description = "An extra 40% in drop rate boost.";
				itemDef.interfaceOptions = new String[] { null, null, null, "Bank", "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("dark_Roc.png");
				break;

			case 30121:
				itemDef.setDefaults();
				itemDef.name = "Dark roc";
				//itemDef.description = "An extra 20% in drop rate boost.";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("dark_Roc.png");
				break;

			case 30122:
				itemDef.setDefaults();
				itemDef.name = "@red@Dark kratos";
				//itemDef.description = "The most powerful pet, see ::foepets for full list of perks.";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("dark_yama.png");
				break;
			case 30123:
				itemDef.setDefaults();
				itemDef.name = "Dark seren";
				//itemDef.description = "85% chance for Wildy Event Boss to hit a 0 and 25% chance to double key.";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = false;
				itemDef.createCustomSprite("dark_seren.png");

				break;
			case 23939:
				itemDef.name = "Seren";
				//itemDef.description = "50% chance for wildy event bosses to hit a 0 on you.";
				itemDef.createCustomSprite("seren.png");
				break;
			case 21046:
				itemDef.name = "@cya@Chest rate bonus (+15%)";
				//itemDef.description = "A single use +15% chance from chests, or to receive a rare raids key.";
				itemDef.stackable = true;
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				break;
			case 11666:
				itemDef.name = "Full Elite Void Token";
				//itemDef.description = "Use this token to receive a full elite void set with all combat pieces.";
				itemDef.interfaceOptions = new String[] { "Activate", null, null, null, "Drop" };
				break;
			case 1004:
				itemDef.name = "@gre@20m Coins";
				//itemDef.description = "Lovely coins.";
				itemDef.stackable = false;
				itemDef.interfaceOptions = new String[] { "Claim", null, null, null, "Drop" };
				break;
			case 7629:
				itemDef.name = "@or3@2x Slayer point scroll";
				itemDef.interfaceOptions = new String[] { null, null, null, null, "Drop" };
				itemDef.stackable = true;
				break;
			case 24460:
				itemDef.name = "@or3@Faster clues (30 mins)";
				//itemDef.description = "Clue rates are halved for npcs and skilling.";
				itemDef.interfaceOptions = new String[] { "Boost", null, null, null, "Drop" };
				itemDef.stackable = true;
				break;
			case 7968:
				itemDef.name = "@or3@+25% Skilling pet rate (30 mins)";
				itemDef.interfaceOptions = new String[] { "Boost", null, null, null, "Drop" };
				itemDef.stackable = true;
				break;
			case 8899:
				itemDef.name = "@gre@50m Coins";
				//itemDef.description = "Lovely coins.";
				itemDef.stackable = false;
				itemDef.interfaceOptions = new String[] { "Claim", null, null, null, "Drop" };
				break;
			case 4035:
				itemDef.interfaceOptions = new String[] { "Teleport", null, null, null, null };
				break;
			case 10835:
				itemDef.name = "Buldging coin bag";
				itemDef.interfaceOptions = new String[] { "Open", null, "Open-All", null, "Drop" };
				//itemDef.description = "I can see some coins inside.";
				break;
			case 15098:
				itemDef.name = "Dice (up to 100)";
				//itemDef.description = "A 100-sided dice.";
				itemDef.modelId = 31223;
				itemDef.zoom2d = 1104;
				itemDef.yan2d = 215;
				itemDef.xan2d = 94;
				itemDef.yOffset2d = -5;
				itemDef.xOffset2d = -18;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Public-roll";
				itemDef.interfaceOptions[2] = null;
				itemDef.name = "Dice (up to 100)";
				itemDef.ambient = 15;
				itemDef.contrast = 25;
				itemDef.createCustomSprite("Dice_Bag.png");
				break;
			case 11773:
			case 11771:
			case 11770:
			case 11772:
				itemDef.ambient += 45;
				break;
			case 12792:
				itemDef.name = "Graceful Recolor Box";
				itemDef.interfaceOptions = new String[] { null, "Use", null, null, "Drop" };
				break;
			case 6769:
				itemDef.name = "@yel@$5 Scroll";
				//itemDef.description = "Claim this scroll to be rewarded with 5 donator points.";
				itemDef.interfaceOptions = new String[] { "Claim", null, null, null, "Drop" };
				break;
			case 2403:
				itemDef.name = "@yel@$10 Scroll";
				//itemDef.description = "Claim this scroll to be rewarded with 10 donator points.";
				itemDef.interfaceOptions = new String[] { "Claim", null, null, null, "Drop" };
				break;
			case 2396:
				itemDef.name = "@yel@$25 Scroll";
				//itemDef.description = "Claim this scroll to be rewarded with 25 donator points.";
				itemDef.interfaceOptions = new String[] { "Claim", null, null, null, "Drop" };
				break;
			case 786:
				itemDef.name = "@yel@$50 Donator";
				//itemDef.description = "Claim this scroll to be rewarded with 50 donator points.";
				itemDef.interfaceOptions = new String[] { "Claim", null, null, null, "Drop" };
				break;
			case 761:
				itemDef.name = "@red@$100 Donator";
				//itemDef.description = "Claim this scroll to be rewarded with 100 donator points.";
				itemDef.interfaceOptions = new String[] { "Claim", null, null, null, "Drop" };
				break;
			case 607:
				itemDef.name = "@red@$250 Scroll";
				//itemDef.description = "Claim this scroll to be rewarded with 250 donator points.";
				itemDef.interfaceOptions = new String[] { "Claim", null, null, null, "Drop" };
				break;
			case 608:
				itemDef.name = "@gre@$500 Scroll";
				//itemDef.description = "Claim this scroll to be rewarded with 500 donator points.";
				itemDef.interfaceOptions = new String[] { "Claim", null, null, null, "Drop" };
				break;
			case 1464:
				itemDef.name = "Vote ticket";
				//itemDef.description = "Exchange this for a Vote Point.";
				break;

			case 33049:
				itemDef.setDefaults();
				itemDef.name = "Agility master cape";
				//itemDef.description = "	A cape worn by those who've overachieved.";
				itemDef.colorFind = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
				itemDef.colorReplace = new int[] { 677, 801, 43540, 43543, 43546, 43549, 43550, 43552, 43554, 43558,
					43560, 43575 };
				itemDef.modelId = 50030;
				itemDef.maleModel0 = 50031;
				itemDef.femaleModel0 = 50031;
				itemDef.zoom2d = 2300;
				itemDef.xan2d = 400;
				itemDef.yan2d = 1020;
				itemDef.xOffset2d = 3;
				itemDef.yOffset2d = 30;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33033:
				itemDef.setDefaults();
				itemDef.name = "Attack master cape";
				//itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.colorFind = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
				itemDef.colorReplace = new int[] { 7104, 9151, 911, 914, 917, 920, 921, 923, 925, 929, 931, 946 };
				itemDef.modelId = 50032;
				itemDef.maleModel0 = 50033;
				itemDef.femaleModel0 = 50033;
				itemDef.zoom2d = 2300;
				itemDef.xan2d = 400;
				itemDef.yan2d = 1020;
				itemDef.xOffset2d = 3;
				itemDef.yOffset2d = 30;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33055:
				itemDef.setDefaults();
				itemDef.name = "Construction master cape";
				//itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.colorFind = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
				itemDef.colorReplace = new int[] { 6061, 5945, 6327, 6330, 6333, 6336, 6337, 6339, 6341, 6345, 6347,
					6362 };
				itemDef.modelId = 50034;
				itemDef.maleModel0 = 50035;
				itemDef.femaleModel0 = 50035;
				itemDef.zoom2d = 2300;
				itemDef.xan2d = 400;
				itemDef.yan2d = 1020;
				itemDef.xOffset2d = 3;
				itemDef.yOffset2d = 30;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33040:
				itemDef.setDefaults();
				itemDef.name = "Cooking master cape";
				//itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.colorFind = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
				itemDef.colorReplace = new int[] { 920, 920, 51856, 51859, 51862, 51865, 51866, 51868, 51870, 51874,
					51876, 51891 };
				itemDef.modelId = 50036;
				itemDef.maleModel0 = 50037;
				itemDef.femaleModel0 = 50037;
				itemDef.zoom2d = 2300;
				itemDef.xan2d = 400;
				itemDef.yan2d = 1020;
				itemDef.xOffset2d = 3;
				itemDef.yOffset2d = 30;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33045:
				itemDef.setDefaults();
				itemDef.name = "Crafting master cape";
				//itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.colorFind = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
				itemDef.colorReplace = new int[] { 9142, 9152, 4511, 4514, 4517, 4520, 4521, 4523, 4525, 4529, 4531,
					4546 };
				itemDef.modelId = 50038;
				itemDef.maleModel0 = 50039;
				itemDef.femaleModel0 = 50039;
				itemDef.zoom2d = 2300;
				itemDef.xan2d = 400;
				itemDef.yan2d = 1020;
				itemDef.xOffset2d = 3;
				itemDef.yOffset2d = 30;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33034:
				itemDef.setDefaults();
				itemDef.name = "Defence master cape";
				//itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.colorFind = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
				itemDef.colorReplace = new int[] { 10460, 10473, 41410, 41413, 41416, 41419, 41420, 41422, 41424,
					41428, 41430, 41445 };
				itemDef.modelId = 50040;
				itemDef.maleModel0 = 50041;
				itemDef.femaleModel0 = 50041;
				itemDef.zoom2d = 2300;
				itemDef.xan2d = 400;
				itemDef.yan2d = 1020;
				itemDef.xOffset2d = 3;
				itemDef.yOffset2d = 30;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33052:
				itemDef.setDefaults();
				itemDef.name = "Farming master cape";
				//itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.colorFind = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
				itemDef.colorReplace = new int[] { 14775, 14792, 22026, 22029, 22032, 22035, 22036, 22038, 22040,
					22044, 22046, 22061 };
				itemDef.modelId = 50042;
				itemDef.maleModel0 = 50043;
				itemDef.femaleModel0 = 50043;
				itemDef.zoom2d = 2300;
				itemDef.xan2d = 400;
				itemDef.yan2d = 1020;
				itemDef.xOffset2d = 3;
				itemDef.yOffset2d = 30;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33044:
				itemDef.setDefaults();
				itemDef.name = "Firemaking master cape";
				//itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.colorFind = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
				itemDef.colorReplace = new int[] { 8125, 9152, 4015, 4018, 4021, 4024, 4025, 4027, 4029, 4033, 4035,
					4050 };
				itemDef.modelId = 50044;
				itemDef.maleModel0 = 50045;
				itemDef.femaleModel0 = 50045;
				itemDef.zoom2d = 2300;
				itemDef.xan2d = 400;
				itemDef.yan2d = 1020;
				itemDef.xOffset2d = 3;
				itemDef.yOffset2d = 30;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33043:
				itemDef.setDefaults();
				itemDef.name = "Fishing master cape";
				//itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.colorFind = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
				itemDef.colorReplace = new int[] { 9144, 9152, 38202, 38205, 38208, 38211, 38212, 38214, 38216,
					38220, 38222, 38237 };
				itemDef.modelId = 50046;
				itemDef.maleModel0 = 50047;
				itemDef.femaleModel0 = 50047;
				itemDef.zoom2d = 2300;
				itemDef.xan2d = 400;
				itemDef.yan2d = 1020;
				itemDef.xOffset2d = 3;
				itemDef.yOffset2d = 30;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33042:
				itemDef.setDefaults();
				itemDef.name = "Fletching master cape";
				//itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.colorFind = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
				itemDef.colorReplace = new int[] { 6067, 9152, 33670, 33673, 33676, 33679, 33680, 33682, 33684,
					33688, 33690, 33705 };
				itemDef.modelId = 50048;
				itemDef.maleModel0 = 50049;
				itemDef.femaleModel0 = 50049;
				itemDef.zoom2d = 2300;
				itemDef.xan2d = 400;
				itemDef.yan2d = 1020;
				itemDef.xOffset2d = 3;
				itemDef.yOffset2d = 30;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33048:
				itemDef.setDefaults();
				itemDef.name = "Herblore master cape";
				//itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.colorFind = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
				itemDef.colorReplace = new int[] { 9145, 9156, 22414, 22417, 22420, 22423, 22424, 22426, 22428,
					22432, 22434, 22449 };
				itemDef.modelId = 50050;
				itemDef.maleModel0 = 50051;
				itemDef.femaleModel0 = 50051;
				itemDef.zoom2d = 2300;
				itemDef.xan2d = 400;
				itemDef.yan2d = 1020;
				itemDef.xOffset2d = 3;
				itemDef.yOffset2d = 30;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33036:
				itemDef.setDefaults();
				itemDef.name = "Hitpoints master cape";
				//itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.colorFind = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
				itemDef.colorReplace = new int[] { 818, 951, 8291, 8294, 8297, 8300, 8301, 8303, 8305, 8309, 8311,
					8319 };
				itemDef.modelId = 50052;
				itemDef.maleModel0 = 50053;
				itemDef.femaleModel0 = 50053;
				itemDef.zoom2d = 2300;
				itemDef.xan2d = 400;
				itemDef.yan2d = 1020;
				itemDef.xOffset2d = 3;
				itemDef.yOffset2d = 30;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				//itemDef.maleOffset = 5;
				//itemDef.femaleOffset = 4;
				break;
			case 33054:
				itemDef.setDefaults();
				itemDef.name = "Hunter master cape";
				//itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.colorFind = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
				itemDef.colorReplace = new int[] { 5262, 6020, 8472, 8475, 8478, 8481, 8482, 8484, 8486, 8490, 8492,
					8507 };
				itemDef.modelId = 50054;
				itemDef.maleModel0 = 50055;
				itemDef.femaleModel0 = 50055;
				itemDef.zoom2d = 2300;
				itemDef.xan2d = 400;
				itemDef.yan2d = 1020;
				itemDef.xOffset2d = 3;
				itemDef.yOffset2d = 30;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33039:
				itemDef.setDefaults();
				itemDef.name = "Magic master cape";
				//itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.colorFind = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
				itemDef.colorReplace = new int[] { 43569, 43685, 6336, 6339, 6342, 6345, 6346, 6348, 6350, 6354,
					6356, 6371 };
				itemDef.modelId = 50056;
				itemDef.maleModel0 = 50057;
				itemDef.femaleModel0 = 50057;
				itemDef.zoom2d = 2300;
				itemDef.xan2d = 400;
				itemDef.yan2d = 1020;
				itemDef.xOffset2d = 3;
				itemDef.yOffset2d = 30;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33047:
				itemDef.setDefaults();
				itemDef.name = "Mining master cape";
				//itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.colorFind = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
				itemDef.colorReplace = new int[] { 36296, 36279, 10386, 10389, 10392, 10395, 10396, 10398, 10400,
					10404, 10406, 10421 };
				itemDef.modelId = 50058;
				itemDef.maleModel0 = 50059;
				itemDef.femaleModel0 = 50059;
				itemDef.zoom2d = 2300;
				itemDef.xan2d = 400;
				itemDef.yan2d = 1020;
				itemDef.xOffset2d = 3;
				itemDef.yOffset2d = 30;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33038:
				itemDef.setDefaults();
				itemDef.name = "Prayer master cape";
				//itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.colorFind = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
				itemDef.colorReplace = new int[] { 9163, 9168, 117, 120, 123, 126, 127, 127, 127, 127, 127, 127 };
				itemDef.modelId = 50060;
				itemDef.maleModel0 = 50061;
				itemDef.femaleModel0 = 50061;
				itemDef.zoom2d = 2300;
				itemDef.xan2d = 400;
				itemDef.yan2d = 1020;
				itemDef.xOffset2d = 3;
				itemDef.yOffset2d = 30;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33037:
				itemDef.setDefaults();
				itemDef.name = "Range master cape";
				//itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.colorFind = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
				itemDef.colorReplace = new int[] { 3755, 3998, 15122, 15125, 15128, 15131, 15132, 15134, 15136,
					15140, 15142, 15157 };
				itemDef.modelId = 50062;
				itemDef.maleModel0 = 50063;
				itemDef.femaleModel0 = 50063;
				itemDef.zoom2d = 2300;
				itemDef.xan2d = 400;
				itemDef.yan2d = 1020;
				itemDef.xOffset2d = 3;
				itemDef.yOffset2d = 30;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33053:
				itemDef.setDefaults();
				itemDef.name = "Runecrafting master cape";
				//itemDef.description = "	A cape worn by those who've overachieved.";
				// 4 //7 //10 //13 //14//16//18//22 //24//39
				itemDef.colorFind = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
				itemDef.colorReplace = new int[] { 9152, 8128, 10318, 10321, 10324, 10327, 10328, 10330, 10332,
					10336, 10338, 10353 };
				itemDef.modelId = 50064;
				itemDef.maleModel0 = 50065;
				itemDef.femaleModel0 = 50065;
				itemDef.zoom2d = 2300;
				itemDef.xan2d = 400;
				itemDef.yan2d = 1020;
				itemDef.xOffset2d = 3;
				itemDef.yOffset2d = 30;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33051:
				itemDef.setDefaults();
				itemDef.name = "Slayer master cape";
				//itemDef.description = "	A cape worn by those who've overachieved.";
				itemDef.colorFind = new int[] { 57022, 48811 };
				itemDef.colorReplace = new int[] { 912, 920 };
				itemDef.modelId = 50066;
				itemDef.maleModel0 = 50067;
				itemDef.femaleModel0 = 50067;
				itemDef.zoom2d = 2300;
				itemDef.xan2d = 400;
				itemDef.yan2d = 1020;
				itemDef.xOffset2d = 3;
				itemDef.yOffset2d = 30;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33046:
				itemDef.setDefaults();
				itemDef.name = "Smithing master cape";
				//itemDef.description = "	A cape worn by those who've overachieved.";
				itemDef.colorFind = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
				itemDef.colorReplace = new int[] { 8115, 9148, 10386, 10389, 10392, 10395, 10396, 10398, 10400,
					10404, 10406, 10421 };
				itemDef.modelId = 50068;
				itemDef.maleModel0 = 50069;
				itemDef.femaleModel0 = 50069;
				itemDef.zoom2d = 2300;
				itemDef.xan2d = 400;
				itemDef.yan2d = 1020;
				itemDef.xOffset2d = 3;
				itemDef.yOffset2d = 30;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33035:
				itemDef.setDefaults();
				itemDef.name = "Strength master cape";
				//itemDef.description = "	A cape worn by those who've overachieved.";
				itemDef.colorFind = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
				itemDef.colorReplace = new int[] { 935, 931, 27538, 27541, 27544, 27547, 27548, 27550, 27552, 27556,
					27558, 27573 };
				itemDef.modelId = 50070;
				itemDef.maleModel0 = 50071;
				itemDef.femaleModel0 = 50071;
				itemDef.zoom2d = 2300;
				itemDef.xan2d = 400;
				itemDef.yan2d = 1020;
				itemDef.xOffset2d = 3;
				itemDef.yOffset2d = 30;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33050:
				itemDef.setDefaults();
				itemDef.name = "Thieving master cape";
				//itemDef.description = "	A cape worn by those who've overachieved.";
				itemDef.colorFind = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
				itemDef.colorReplace = new int[] { 11, 0, 58779, 58782, 58785, 58788, 58789, 57891, 58793, 58797,
					58799, 58814 };
				itemDef.modelId = 50072;
				itemDef.maleModel0 = 50073;
				itemDef.femaleModel0 = 50073;
				itemDef.zoom2d = 2300;
				itemDef.xan2d = 400;
				itemDef.yan2d = 1020;
				itemDef.xOffset2d = 3;
				itemDef.yOffset2d = 30;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				//itemDef.maleOffset = 5;
				break;
			case 33041:
				itemDef.setDefaults();
				itemDef.name = "Woodcutting master cape";
				//itemDef.description = "	A cape worn by those who've overachieved.";
				itemDef.colorFind = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
				itemDef.colorReplace = new int[] { 25109, 24088, 6693, 6696, 6699, 6702, 6703, 6705, 6707, 6711,
					6713, 6728 };
				itemDef.modelId = 50074;
				itemDef.maleModel0 = 50075;
				itemDef.femaleModel0 = 50075;
				itemDef.zoom2d = 2300;
				itemDef.xan2d = 400;
				itemDef.yan2d = 1020;
				itemDef.xOffset2d = 3;
				itemDef.yOffset2d = 30;
				itemDef.interfaceOptions = new String[5];
				itemDef.interfaceOptions[1] = "Wear";
				itemDef.interfaceOptions[2] = null;
				//itemDef.maleOffset = -2;
				break;
		}
	}

	public static ItemDefinition lookup(int itemId) {
		for (int count = 0; count < 10; count++)
			if (cache[count].id == itemId)
				return cache[count];

		if (itemId > streamIndices.length)
			itemId = 0;
		if (itemId == -1)
			itemId = 0;
		cacheIndex = (cacheIndex + 1) % 10;
		ItemDefinition itemDef = cache[cacheIndex];
		if (itemId > 0)
			item_data.currentPosition = streamIndices[itemId];
		itemDef.id = itemId;
		itemDef.setDefaults();
		itemDef.decode(item_data);

		if (itemDef.noted_item_id != -1) {
			itemDef.toNote();
		}

		customItems(itemId);


		return itemDef;
	}

	void method2790(ItemDefinition var1, ItemDefinition var2) {
		modelId = var1.modelId * 1;
		zoom2d = 1 * var1.zoom2d;
		xan2d = var1.xan2d * 1;
		yan2d = var1.yan2d * 1;
		zan2d = var1.zan2d * 1;
		xOffset2d = 1 * var1.xOffset2d;
		yOffset2d = var1.yOffset2d * 1;
		colorReplace = var1.colorReplace;
		colorFind = var1.colorFind;
		textureFind = var1.textureFind;
		textureReplace = var1.textureReplace;
		stackable = var1.stackable;
		name = var2.name;
		cost = 0;
	}

	void method2789(ItemDefinition var1, ItemDefinition var2) {
		modelId = var1.modelId * 1;
		zoom2d = var1.zoom2d * 1;
		xan2d = 1 * var1.xan2d;
		yan2d = 1 * var1.yan2d;
		zan2d = 1 * var1.zan2d;
		xOffset2d = 1 * var1.xOffset2d;
		yOffset2d = var1.yOffset2d * 1;
		colorReplace = var2.colorReplace;
		colorFind = var2.colorFind;
		// originalTextureColors = var2.originalTextureColors;
		// modifiedTextureColors = var2.modifiedTextureColors;
		name = var2.name;
		members = var2.members;
		stackable = var2.stackable;
		maleModel0 = 1 * var2.maleModel0;
		maleModel1 = 1 * var2.maleModel1;
		maleModel2 = 1 * var2.maleModel2;
		femaleModel0 = var2.femaleModel0 * 1;
		femaleModel1 = var2.femaleModel1 * 1;
		femaleModel2 = 1 * var2.femaleModel2;
		maleHeadModel = 1 * var2.maleHeadModel;
		maleHeadModel2 = var2.maleHeadModel2 * 1;
		femaleHeadModel = var2.femaleHeadModel * 1;
		femaleHeadModel2 = var2.femaleHeadModel2 * 1;
		team = var2.team * 1;
		options = var2.options;
		interfaceOptions = new String[5];
		equipActions = new String[5];
		if (null != var2.interfaceOptions) {
			for (int var4 = 0; var4 < 4; ++var4) {
				interfaceOptions[var4] = var2.interfaceOptions[var4];
			}
		}

		interfaceOptions[4] = "Discard";
		cost = 0;
	}

	void toPlaceholder(ItemDefinition var1, ItemDefinition var2) {
		modelId = var1.modelId * 1;
		zoom2d = 1 * var1.zoom2d;
		xan2d = var1.xan2d * 1;
		yan2d = var1.yan2d * 1;
		zan2d = var1.zan2d * 1;
		xOffset2d = 1 * var1.xOffset2d;
		yOffset2d = var1.yOffset2d * 1;
		colorReplace = var1.colorReplace;
		colorFind = var1.colorFind;
		textureFind = var1.textureFind;
		textureReplace = var1.textureReplace;
		stackable = var1.stackable;
		name = var2.name;
		cost = 0;
	}

	public static Sprite getSprite(int itemId, int stackSize, int outlineColor, boolean noted, int border,int shadow) {
		if (outlineColor == 0) {
			Sprite sprite = (Sprite) sprites.get(itemId);

			if (sprite != null && sprite.maxHeight != stackSize && sprite.maxHeight != -1) {
				sprite.unlink();
				sprite = null;
			}

			if (sprite != null) {
				return sprite;
			}
		}

		ItemDefinition definition = lookup(itemId);

		if (definition.countObj == null) {
			stackSize = -1;
		}

		if (stackSize > 1) {
			int stack_item_id = -1;

			for (int j1 = 0; j1 < 10; j1++) {
				if (stackSize >= definition.countCo[j1] && definition.countCo[j1] != 0) {
					stack_item_id = definition.countObj[j1];
				}
			}

			if (stack_item_id != -1) {
				definition = lookup(stack_item_id);
			}
		}

		Model model = definition.getModel(1);

		if (model == null) {
			return null;
		}

		Sprite sprite = null;


		Sprite enabledSprite = new Sprite(32, 32);
		int centerX = Rasterizer3D.originViewX;
		int centerY = Rasterizer3D.originViewY;
		int[] lineOffsets = Rasterizer3D.scanOffsets;
		int[] pixels = Rasterizer2D.pixels;
		int width = Rasterizer2D.width;
		int height = Rasterizer2D.height;
		int vp_left = Rasterizer2D.leftX;
		int vp_right = Rasterizer2D.bottomX;
		int vp_top = Rasterizer2D.topY;
		int vp_bottom = Rasterizer2D.bottomY;
		Rasterizer3D.world = false;
		Rasterizer3D.aBoolean1464 = false;
		Rasterizer2D.initDrawingArea(32, 32, enabledSprite.myPixels);
		Rasterizer2D.drawItemBox(0, 0, 32, 32, 0);
		Rasterizer3D.useViewport();
		int k3 = definition.zoom2d;

		if (outlineColor == -1) {
			k3 = (int) (k3 * 1.5D);
		}

		if (outlineColor > 0) {
			k3 = (int) (k3 * 1.04D);
		}

		int l3 = Rasterizer3D.SINE[definition.xan2d] * k3 >> 16;
		int i4 = Rasterizer3D.COSINE[definition.xan2d] * k3 >> 16;
		Rasterizer3D.renderOnGpu = true;
		model.renderModel(definition.yan2d, definition.zan2d, definition.xan2d, definition.xOffset2d,
			l3 + model.modelBaseY / 2 + definition.yOffset2d, i4 + definition.yOffset2d);
		Rasterizer3D.renderOnGpu = false;

		enabledSprite.highlight(1);

		if (outlineColor == 0) {
			enabledSprite.shadow(3153952);
		}

		Rasterizer2D.initDrawingArea(32, 32, enabledSprite.myPixels);

		if (noted) {
			int old_w = sprite.maxWidth;
			int old_h = sprite.maxHeight;
			sprite.maxWidth = 32;
			sprite.maxHeight = 32;
			sprite.drawSprite(0, 0);
			sprite.maxWidth = old_w;
			sprite.maxHeight = old_h;
		}

		if (outlineColor == 0) {
			sprites.put(enabledSprite, itemId);
		}

		Rasterizer2D.initDrawingArea(height, width, pixels);
		Rasterizer2D.setDrawingArea(vp_bottom, vp_left, vp_right, vp_top);
		Rasterizer3D.originViewX = centerX;
		Rasterizer3D.originViewY = centerY;
		Rasterizer3D.scanOffsets = lineOffsets;
		Rasterizer3D.aBoolean1464 = true;
		Rasterizer3D.world = true;
		enabledSprite.maxWidth = definition.stackable ? 33 : 32;
		enabledSprite.maxHeight = stackSize;
		return enabledSprite;
	}

	public static Sprite getSmallSprite(int itemId) {
		return getSmallSprite(itemId, 1);
	}

	public static Sprite getSmallSprite(int itemId, int stackSize) {

		ItemDefinition itemDef = lookup(itemId);
		if (itemDef.countObj == null)
			stackSize = -1;
		if (stackSize > 1) {
			int stack_item_id = -1;
			for (int j1 = 0; j1 < 10; j1++)
				if (stackSize >= itemDef.countCo[j1] && itemDef.countCo[j1] != 0)
					stack_item_id = itemDef.countObj[j1];

			if (stack_item_id != -1)
				itemDef = lookup(stack_item_id);
		}
		Model model = itemDef.getModel(1);
		if (model == null)
			return null;
		Sprite sprite = null;
		if (itemDef.noted_item_id != -1) {
			sprite = getSprite(itemDef.unnoted_item_id, 10, -1);
			if (sprite == null)
				return null;
		}
		Sprite enabledSprite = new Sprite(18, 18);
		int centerX = Rasterizer3D.originViewX;
		int centerY = Rasterizer3D.originViewY;
		int[] lineOffsets = Rasterizer3D.scanOffsets;
		int[] pixels = Rasterizer2D.pixels;
		int width = Rasterizer2D.width;
		int height = Rasterizer2D.height;
		int vp_left = Rasterizer2D.leftX;
		int vp_right = Rasterizer2D.bottomX;
		int vp_top = Rasterizer2D.topY;
		int vp_bottom = Rasterizer2D.bottomY;
		Rasterizer3D.world = false;
		Rasterizer3D.aBoolean1464 = false;
		Rasterizer2D.initDrawingArea(18, 18, enabledSprite.myPixels);
		Rasterizer2D.drawItemBox(0, 0, 18, 18, 0);
		Rasterizer3D.useViewport();
		int k3 = itemDef.zoom2d;

		int l3 = Rasterizer3D.SINE[itemDef.xan2d] * k3 >> 16;
		int i4 = Rasterizer3D.COSINE[itemDef.xan2d] * k3 >> 16;
		Rasterizer3D.renderOnGpu = true;
		model.renderModel(itemDef.yan2d, itemDef.zan2d, itemDef.xan2d, itemDef.xOffset2d,
			l3 + model.modelBaseY / 2 + itemDef.yOffset2d, i4 + itemDef.yOffset2d);
		Rasterizer3D.renderOnGpu = false;
		enabledSprite.outline(1);

		Rasterizer2D.initDrawingArea(32, 32, enabledSprite.myPixels);

		if (itemDef.noted_item_id != -1) {
			int old_w = sprite.maxWidth;
			int old_h = sprite.maxHeight;
			sprite.maxWidth = 18;
			sprite.maxHeight = 18;
			sprite.drawSprite(0, 0);
			sprite.maxWidth = old_w;
			sprite.maxHeight = old_h;
		}

		sprites.put(enabledSprite, itemId);
		Rasterizer2D.initDrawingArea(height, width, pixels);
		Rasterizer2D.setDrawingArea(vp_bottom, vp_left, vp_right, vp_top);
		Rasterizer3D.originViewX = centerX;
		Rasterizer3D.originViewY = centerY;
		Rasterizer3D.scanOffsets = lineOffsets;
		Rasterizer3D.aBoolean1464 = true;
		Rasterizer3D.world = true;
		if (itemDef.stackable)
			enabledSprite.maxWidth = 18;
		else
			enabledSprite.maxWidth = 18;
		enabledSprite.maxHeight = stackSize;
		return enabledSprite;
	}


	public static Sprite getSprite(int itemId, int stackSize, int outlineColor) {
		if (outlineColor == 0) {
			Sprite sprite = (Sprite) sprites.get(itemId);
			if (sprite != null && sprite.maxHeight != stackSize && sprite.maxHeight != -1) {

				sprite.unlink();
				sprite = null;
			}
			if (sprite != null)
				return sprite;
		}
		ItemDefinition itemDef = lookup(itemId);
		if (itemDef.countObj == null)
			stackSize = -1;
		if (stackSize > 1) {
			int stack_item_id = -1;
			for (int j1 = 0; j1 < 10; j1++)
				if (stackSize >= itemDef.countCo[j1] && itemDef.countCo[j1] != 0)
					stack_item_id = itemDef.countObj[j1];

			if (stack_item_id != -1)
				itemDef = lookup(stack_item_id);
		}
		Model model = itemDef.getModel(1);
		if (model == null)
			return null;
		Sprite sprite = null;
		if (itemDef.noted_item_id != -1) {
			sprite = getSprite(itemDef.unnoted_item_id, 10, -1);
			if (sprite == null)
				return null;
		}
		Sprite enabledSprite = new Sprite(32, 32);
		int centerX = Rasterizer3D.originViewX;
		int centerY = Rasterizer3D.originViewY;
		int[] lineOffsets = Rasterizer3D.scanOffsets;
		int[] pixels = Rasterizer2D.pixels;
		int width = Rasterizer2D.width;
		int height = Rasterizer2D.height;
		int vp_left = Rasterizer2D.leftX;
		int vp_right = Rasterizer2D.bottomX;
		int vp_top = Rasterizer2D.topY;
		int vp_bottom = Rasterizer2D.bottomY;
		Rasterizer3D.world = false;
		Rasterizer3D.aBoolean1464 = false;
		Rasterizer2D.initDrawingArea(32, 32, enabledSprite.myPixels);
		Rasterizer2D.drawItemBox(0, 0, 32, 32, 0);
		Rasterizer3D.useViewport();
		int k3 = itemDef.zoom2d;
		if (outlineColor == -1)
			k3 = (int) ((double) k3 * 1.5D);
		if (outlineColor > 0)
			k3 = (int) ((double) k3 * 1.04D);
		int l3 = Rasterizer3D.SINE[itemDef.xan2d] * k3 >> 16;
		int i4 = Rasterizer3D.COSINE[itemDef.xan2d] * k3 >> 16;
		Rasterizer3D.renderOnGpu = true;
		model.renderModel(itemDef.yan2d, itemDef.zan2d, itemDef.xan2d, itemDef.xOffset2d,
			l3 + model.modelBaseY / 2 + itemDef.yOffset2d, i4 + itemDef.yOffset2d);
		Rasterizer3D.renderOnGpu = false;
		enabledSprite.outline(1);
		if (outlineColor > 0) {
			enabledSprite.outline(16777215);
		}
		if (outlineColor == 0) {
			enabledSprite.shadow(3153952);
		}

		Rasterizer2D.initDrawingArea(32, 32, enabledSprite.myPixels);

		if (itemDef.noted_item_id != -1) {
			int old_w = sprite.maxWidth;
			int old_h = sprite.maxHeight;
			sprite.maxWidth = 32;
			sprite.maxHeight = 32;
			sprite.drawSprite(0, 0);
			sprite.maxWidth = old_w;
			sprite.maxHeight = old_h;
		}
		if (outlineColor == 0)
			sprites.put(enabledSprite, itemId);
		Rasterizer2D.initDrawingArea(height, width, pixels);
		Rasterizer2D.setDrawingArea(vp_bottom, vp_left, vp_right, vp_top);
		Rasterizer3D.originViewX = centerX;
		Rasterizer3D.originViewY = centerY;
		Rasterizer3D.scanOffsets = lineOffsets;
		Rasterizer3D.aBoolean1464 = true;
		Rasterizer3D.world = true;
		if (itemDef.stackable)
			enabledSprite.maxWidth = 33;
		else
			enabledSprite.maxWidth = 32;
		enabledSprite.maxHeight = stackSize;
		return enabledSprite;
	}

	public static Sprite getSprite(int itemId, int stackSize, int zoom, int outlineColor) {
		ItemDefinition itemDef = lookup(itemId);
		if (itemDef.countObj == null)
			stackSize = -1;
		if (stackSize > 1) {
			int stack_item_id = -1;
			for (int j1 = 0; j1 < 10; j1++)
				if (stackSize >= itemDef.countCo[j1] && itemDef.countCo[j1] != 0)
					stack_item_id = itemDef.countObj[j1];

			if (stack_item_id != -1)
				itemDef = lookup(stack_item_id);
		}
		Model model = itemDef.getModel(1);
		if (model == null)
			return null;
		Sprite sprite = new Sprite(90, 90);
		int centerX = Rasterizer3D.originViewX;
		int centerY = Rasterizer3D.originViewY;
		int[] lineOffsets = Rasterizer3D.scanOffsets;
		int[] pixels = Rasterizer2D.pixels;
		int width = Rasterizer2D.width;
		int height = Rasterizer2D.height;
		int vp_left = Rasterizer2D.leftX;
		int vp_right = Rasterizer2D.bottomX;
		int vp_top = Rasterizer2D.topY;
		int vp_bottom = Rasterizer2D.bottomY;
		Rasterizer3D.world = false;
		Rasterizer3D.aBoolean1464 = false;
		Rasterizer2D.initDrawingArea(90, 90, sprite.myPixels);
		Rasterizer2D.drawItemBox(0, 0, 90, 90, 0);
		Rasterizer3D.useViewport();
		int l3 = Rasterizer3D.SINE[itemDef.xan2d] * zoom >> 15;
		int i4 = Rasterizer3D.COSINE[itemDef.xan2d] * zoom >> 15;
		Rasterizer3D.renderOnGpu = true;

		model.renderModel(itemDef.yan2d, itemDef.zan2d, itemDef.xan2d, itemDef.xOffset2d,
			l3 + model.modelBaseY / 2 + itemDef.yOffset2d, i4 + itemDef.yOffset2d);

		Rasterizer3D.renderOnGpu = false;
		sprite.outline(1);
		if (outlineColor > 0) {
			sprite.outline(16777215);
		}
		if (outlineColor == 0) {
			sprite.shadow(3153952);
		}
		Rasterizer2D.initDrawingArea(90, 90, sprite.myPixels);
		Rasterizer2D.initDrawingArea(height, width, pixels);
		Rasterizer2D.setDrawingArea(vp_bottom, vp_left, vp_right, vp_top);
		Rasterizer3D.originViewX = centerX;
		Rasterizer3D.originViewY = centerY;
		Rasterizer3D.scanOffsets = lineOffsets;
		Rasterizer3D.aBoolean1464 = true;
		Rasterizer3D.world = true;
		if (itemDef.stackable)
			sprite.maxWidth = 33;
		else
			sprite.maxWidth = 32;
		sprite.maxHeight = stackSize;
		return sprite;
	}

	public boolean isDialogueModelCached(int gender) {
		int model_1 = maleHeadModel;
		int model_2 = maleHeadModel2;
		if (gender == 1) {
			model_1 = femaleHeadModel;
			model_2 = femaleHeadModel2;
		}
		if (model_1 == -1)
			return true;
		boolean cached = Model.isCached(model_1);
		if (model_2 != -1 && !Model.isCached(model_2))
			cached = false;
		return cached;
	}

	public Model getChatEquipModel(int gender) {
		int dialogueModel = maleHeadModel;
		int dialogueHatModel = maleHeadModel2;
		if (gender == 1) {
			dialogueModel = femaleHeadModel;
			dialogueHatModel = femaleHeadModel2;
		}
		if (dialogueModel == -1)
			return null;
		Model dialogueModel_ = Model.getModel(dialogueModel);
		if (dialogueHatModel != -1) {
			Model hatModel_ = Model.getModel(dialogueHatModel);
			Model[] models = {dialogueModel_, hatModel_};
			dialogueModel_ = new Model(2, models);
		}
		if (colorReplace != null) {
			for (int i1 = 0; i1 < colorReplace.length; i1++)
				dialogueModel_.recolor(colorReplace[i1], colorFind[i1]);

		}
		if (textureReplace != null) {
			for (int i1 = 0; i1 < textureReplace.length; i1++)
				dialogueModel_.retexture(textureReplace[i1], textureFind[i1]);
		}
		return dialogueModel_;
	}

	public boolean isEquippedModelCached(int gender) {
		int primaryModel = maleModel0;
		int secondaryModel = maleModel1;
		int emblem = maleModel2;
		if (gender == 1) {
			primaryModel = femaleModel0;
			secondaryModel = femaleModel1;
			emblem = femaleModel2;
		}
		if (primaryModel == -1)
			return true;
		boolean cached = Model.isCached(primaryModel);
		if (secondaryModel != -1 && !Model.isCached(secondaryModel))
			cached = false;
		if (emblem != -1 && !Model.isCached(emblem))
			cached = false;
		return cached;
	}

	public Model getEquippedModel(int gender) {
		int primaryModel = maleModel0;
		int secondaryModel = maleModel1;
		int emblem = maleModel2;

		if (gender == 1) {
			primaryModel = femaleModel0;
			secondaryModel = femaleModel1;
			emblem = femaleModel2;
		}

		if (primaryModel == -1)
			return null;
		Model primaryModel_ = Model.getModel(primaryModel);
		if (secondaryModel != -1)
			if (emblem != -1) {
				Model secondaryModel_ = Model.getModel(secondaryModel);
				Model emblemModel = Model.getModel(emblem);
				Model[] models = {primaryModel_, secondaryModel_, emblemModel};
				primaryModel_ = new Model(3, models);
			} else {
				Model model_2 = Model.getModel(secondaryModel);
				Model[] models = {primaryModel_, model_2};
				primaryModel_ = new Model(2, models);
			}
		if (gender == 0 && maleOffset != 0)
			primaryModel_.offsetBy(0, maleOffset, 0);
		if (gender == 1 && femaleOffset != 0)
			primaryModel_.offsetBy(0, femaleOffset, 0);

		if (colorReplace != null) {
			for (int i1 = 0; i1 < colorReplace.length; i1++)
				primaryModel_.recolor(colorReplace[i1], colorFind[i1]);

		}
		if (textureReplace != null) {
			for (int i1 = 0; i1 < textureReplace.length; i1++)
				primaryModel_.retexture(textureReplace[i1], textureFind[i1]);
		}
		return primaryModel_;
	}

	private void setDefaults() {
		customSpriteLocation = null;
		customSmallSpriteLocation = null;
		equipActions = new String[]{"Remove", null, "Operate", null, null};
		modelId = 0;
		name = null;
		colorReplace = null;
		colorFind = null;
		textureReplace = null;
		textureFind = null;

		zoom2d = 2000;
		xan2d = 0;
		yan2d = 0;
		zan2d = 0;
		xOffset2d = 0;
		yOffset2d = 0;
		stackable = false;
		cost = 1;
		members = false;
		options = null;
		interfaceOptions = null;
		maleModel0 = -1;
		maleModel1 = -1;
		maleOffset = 0;
		femaleModel0 = -1;
		femaleModel1 = -1;
		femaleOffset = 0;
		maleModel2 = -1;
		femaleModel2 = -1;
		maleHeadModel = -1;
		maleHeadModel2 = -1;
		femaleHeadModel = -1;
		femaleHeadModel2 = -1;
		countObj = null;
		countCo = null;
		unnoted_item_id = -1;
		noted_item_id = -1;
		resizeX = 128;
		resizeY = 128;
		resizeZ = 128;
		ambient = 0;
		contrast = 0;
		team = 0;
		glowColor = -1;
	}

	private void copy(ItemDefinition copy) {
		yan2d = copy.yan2d;
		xan2d = copy.xan2d;
		zan2d = copy.zan2d;
		resizeX = copy.resizeX;
		resizeY = copy.resizeY;
		resizeZ = copy.resizeZ;
		zoom2d = copy.zoom2d;
		xOffset2d = copy.xOffset2d;
		yOffset2d = copy.yOffset2d;
		modelId = copy.modelId;
		stackable = copy.stackable;

	}

	private void toNote() {
		ItemDefinition itemDef = lookup(noted_item_id);
		modelId = itemDef.modelId;
		zoom2d = itemDef.zoom2d;
		xan2d = itemDef.xan2d;
		yan2d = itemDef.yan2d;

		zan2d = itemDef.zan2d;
		xOffset2d = itemDef.xOffset2d;
		yOffset2d = itemDef.yOffset2d;

		ItemDefinition itemDef_1 = lookup(unnoted_item_id);
		name = itemDef_1.name;
		members = itemDef_1.members;
		cost = itemDef_1.cost;
		stackable = true;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public void setName(String name) {

	}

	@Override
	public int getId() {
		return 0;
	}

	@Override
	public int getNote() {
		return 0;
	}

	@Override
	public int getLinkedNoteId() {
		return 0;
	}

	@Override
	public int getPlaceholderId() {
		return 0;
	}

	@Override
	public int getPlaceholderTemplateId() {
		return 0;
	}

	@Override
	public int getPrice() {
		return 0;
	}

	@Override
	public boolean isMembers() {
		return false;
	}

	@Override
	public boolean isTradeable() {
		return false;
	}

	@Override
	public void setTradeable(boolean yes) {

	}

	@Override
	public int getIsStackable() {
		return 0;
	}

	@Override
	public int getMaleModel() {
		return 0;
	}

	@Override
	public String[] getInventoryActions() {
		return new String[0];
	}

	@Override
	public String[] getGroundActions() {
		return new String[0];
	}

	@Override
	public int getShiftClickActionIndex() {
		return 0;
	}

	@Override
	public void setShiftClickActionIndex(int shiftClickActionIndex) {

	}

	public Model getModel(int stack_size) {
		if (countObj != null && stack_size > 1) {
			int stack_item_id = -1;
			for (int k = 0; k < 10; k++)
				if (stack_size >= countCo[k] && countCo[k] != 0)
					stack_item_id = countObj[k];

			if (stack_item_id != -1)
				return lookup(stack_item_id).getModel(1);
		}
		Model model = (Model) models.get(id);
		if (model != null)
			return model;
		model = Model.getModel(modelId);
		if (model == null)
			return null;
		if (resizeX != 128 || resizeY != 128 || resizeZ != 128)
			model.scale(resizeX, resizeZ, resizeY);
		if (colorReplace != null) {
			for (int l = 0; l < colorReplace.length; l++)
				model.recolor(colorReplace[l], colorFind[l]);

		}
		if (textureReplace != null) {
			for (int i1 = 0; i1 < textureReplace.length; i1++)
				model.retexture(textureReplace[i1], textureFind[i1]);
		}
		int lightInt = 64 + ambient;
		int lightMag = 768 + contrast;
		model.light(lightInt, lightMag, -50, -10, -50, true);
		model.singleTile = true;
		models.put(model, id);
		return model;
	}

	@Override
	public int getInventoryModel() {
		return 0;
	}

	@Override
	public short[] getColorToReplaceWith() {
		return new short[0];
	}

	@Override
	public short[] getTextureToReplaceWith() {
		return new short[0];
	}

	@Override
	public RSIterableNodeHashTable getParams() {
		return null;
	}

	@Override
	public void setParams(IterableHashTable params) {

	}

	@Override
	public void setParams(RSIterableNodeHashTable params) {

	}

	public Model getUnshadedModel(int stack_size) {
		if (countObj != null && stack_size > 1) {
			int stack_item_id = -1;
			for (int count = 0; count < 10; count++)
				if (stack_size >= countCo[count] && countCo[count] != 0)
					stack_item_id = countObj[count];

			if (stack_item_id != -1)
				return lookup(stack_item_id).getUnshadedModel(1);
		}
		Model model = Model.getModel(modelId);
		if (model == null)
			return null;
		if (colorReplace != null) {
			for (int colorPtr = 0; colorPtr < colorReplace.length; colorPtr++)
				model.recolor(colorReplace[colorPtr], colorFind[colorPtr]);

		}
		return model;
	}

	private void decode(Buffer1 buffer) {
		while (true) {
			int opcode = buffer.readUnsignedByte();
			if (opcode == 0)
				return;
			if (opcode == 1)
				modelId = buffer.readUShort();
			else if (opcode == 2)
				name = buffer.readStrings();
			else if (opcode == 3)
				buffer.readStrings();
			else if (opcode == 4)
				zoom2d = buffer.readUShort();
			else if (opcode == 5)
				xan2d = buffer.readUShort();
			else if (opcode == 6)
				yan2d = buffer.readUShort();
			else if (opcode == 7) {
				xOffset2d = buffer.readUShort();
				if (xOffset2d > 32767)
					xOffset2d -= 0x10000;
			} else if (opcode == 8) {
				yOffset2d = buffer.readUShort();
				if (yOffset2d > 32767)
					yOffset2d -= 0x10000;
			} else if (opcode == 11)
				stackable = true;
			else if (opcode == 12) {
				cost = buffer.readInt();
			} else if (opcode == 16)
				members = true;
			else if (opcode == 23) {
				maleModel0 = buffer.readUShort();
				maleOffset = buffer.readSignedByte();
			} else if (opcode == 24)
				maleModel1 = buffer.readUShort();
			else if (opcode == 25) {
				femaleModel0 = buffer.readUShort();
				femaleOffset = buffer.readSignedByte();
			} else if (opcode == 26)
				femaleModel1 = buffer.readUShort();
			else if (opcode >= 30 && opcode < 35) {
				if (options == null)
					options = new String[5];
				options[opcode - 30] = buffer.readString();
				if (options[opcode - 30].equalsIgnoreCase("hidden"))
					options[opcode - 30] = null;
			} else if (opcode >= 35 && opcode < 40) {
				if (interfaceOptions == null)
					interfaceOptions = new String[5];
				interfaceOptions[opcode - 35] = buffer.readString();
			} else if (opcode == 40) {
				int length = buffer.readUnsignedByte();
				colorReplace = new int[length];
				colorFind = new int[length];
				for (int index = 0; index < length; index++) {
					colorFind[index] = buffer.readUShort();
					colorReplace[index] = buffer.readUShort();
				}
			} else if (opcode == 41) {
				int length = buffer.readUnsignedByte();
				textureFind = new short[length];
				textureReplace = new short[length];
				for (int index = 0; index < length; index++) {
					textureFind[index] = (short) buffer.readUShort();
					textureReplace[index] = (short) buffer.readUShort();
				}
			} else if (opcode == 42) {
				shiftClickIndex = buffer.readUnsignedByte();
			} else if (opcode == 65) {
				tradeable = true;
			} else if (opcode == 78)
				maleModel2 = buffer.readUShort();
			else if (opcode == 79)
				femaleModel2 = buffer.readUShort();
			else if (opcode == 90)
				maleHeadModel = buffer.readUShort();
			else if (opcode == 91)
				femaleHeadModel = buffer.readUShort();
			else if (opcode == 92)
				maleHeadModel2 = buffer.readUShort();
			else if (opcode == 93)
				femaleHeadModel2 = buffer.readUShort();
			else if (opcode == 94)
				category = buffer.readUShort();

			else if (opcode == 95)
				zan2d = buffer.readUShort();
			else if (opcode == 97)
				unnoted_item_id = buffer.readUShort();
			else if (opcode == 98)
				noted_item_id = buffer.readUShort();
			else if (opcode == 110)
				resizeX = buffer.readUShort();
			else if (opcode == 111)
				resizeY = buffer.readUShort();
			else if (opcode == 112)
				resizeZ = buffer.readUShort();
			else if (opcode == 113)
				ambient = buffer.readSignedByte();
			else if (opcode == 114)
				contrast = buffer.readSignedByte() * 5;
			else if (opcode >= 100 && opcode < 110) {
				if (countObj == null) {
					countObj = new int[10];
					countCo = new int[10];
				}
				countObj[opcode - 100] = buffer.readUShort();
				countCo[opcode - 100] = buffer.readUShort();
			} else if (opcode == 110)
				resizeX = buffer.readUShort();
			else if (opcode == 111)
				resizeY = buffer.readUShort();
			else if (opcode == 112)
				resizeZ = buffer.readUShort();
			else if (opcode == 113)
				ambient = buffer.readSignedByte();
			else if (opcode == 114)
				contrast = buffer.readSignedByte() * 5;
			else if (opcode == 115)
				team = buffer.readUnsignedByte();
			else if (opcode == 139)
				bought_id = buffer.readUShort();
			else if (opcode == 140)
				bought_template_id = buffer.readUShort();
			else if (opcode == 148)
				placeholder_id = buffer.readUShort();
			else if (opcode == 149) {
				placeholder_template_id = buffer.readUShort();
			} else if (opcode == 249) {
				int length = buffer.readUnsignedByte();

				params = new HashMap<>(length);

				for (int i = 0; i < length; i++) {
					boolean isString = buffer.readUnsignedByte() == 1;
					int key = buffer.read24Int();
					Object value;

					if (isString) {
						value = buffer.readString();
					} else {
						value = buffer.readInt();
					}

					params.put(key, value);
				}
			} else {
				System.err.printf("Error unrecognised {Items} opcode: %d%n%n", opcode);
			}
		}
	}

	@Override
	public int getHaPrice() {
		return 0;
	}

	@Override
	public boolean isStackable() {
		return false;
	}

	@Override
	public void resetShiftClickActionIndex() {

	}

	@Override
	public int getIntValue(int paramID) {
		return 0;
	}

	@Override
	public void setValue(int paramID, int value) {

	}

	@Override
	public String getStringValue(int paramID) {
		return null;
	}

	@Override
	public void setValue(int paramID, String value) {

	}
}