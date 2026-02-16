package fr.stylobow.iyn.item;

import fr.stylobow.iyn.ImagineYourNight;
import fr.stylobow.iyn.item.custom.TournevisItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ImagineYourNight.MOD_ID);

    public static final DeferredItem<Item> TOURNEVIS = ITEMS.register("tournevis",
            () -> new TournevisItem(new Item.Properties().stacksTo(1).durability(2000)));

    public static final DeferredItem<Item> BAGUETTE_BLEUE = ITEMS.register("baguette_bleue",
            () -> new Item(new Item.Properties().stacksTo(1).durability(2000)));
    public static final DeferredItem<Item> BAGUETTE_JAUNE = ITEMS.register("baguette_jaune",
            () -> new Item(new Item.Properties().stacksTo(1).durability(2000)));
    public static final DeferredItem<Item> BAGUETTE_ORANGE = ITEMS.register("baguette_orange",
            () -> new Item(new Item.Properties().stacksTo(1).durability(2000)));
    public static final DeferredItem<Item> BAGUETTE_ROUGE = ITEMS.register("baguette_rouge",
            () -> new Item(new Item.Properties().stacksTo(1).durability(2000)));
    public static final DeferredItem<Item> BAGUETTE_VERTE = ITEMS.register("baguette_verte",
            () -> new Item(new Item.Properties().stacksTo(1).durability(2000)));
    public static final DeferredItem<Item> BAGUETTE_VIOLETTE = ITEMS.register("baguette_violette",
            () -> new Item(new Item.Properties().stacksTo(1).durability(2000)));

    public static final DeferredItem<Item> OBSIDIAN_INGOT = ITEMS.register("obsidian_ingot",
            () -> new Item(new Item.Properties().fireResistant()));
    public static final DeferredItem<Item> TOPAZE = ITEMS.register("topaze",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> RUBIS = ITEMS.register("rubis",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> SAPHIR = ITEMS.register("saphir",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> ADAMANTIUM_FRAGMENT = ITEMS.register("adamantium_fragment",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> STEEL_STICK = ITEMS.register("steel_stick",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> STEEL_INGOT = ITEMS.register("steel_ingot",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CHOPPE = ITEMS.register("choppe",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MAILLE = ITEMS.register("maille",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> LIGNITE = ITEMS.register("lignite",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> HOPS = ITEMS.register("hops",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> STONE_STICK = ITEMS.register("stone_stick",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CAFEINE = ITEMS.register("cafeine",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> COLA = ITEMS.register("cola",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CANETTE = ITEMS.register("canette",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> TAURINE = ITEMS.register("taurine",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> EMPTY_GLASS = ITEMS.register("empty_glass",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SLATE_ITEM = ITEMS.register("slate_item",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
