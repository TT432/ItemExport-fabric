package com.nmmoc7.itemexport.item_export.exporter;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NameHelper {
    public static Identifier getRegNameI(Item item) {
        return Registry.ITEM.getId(item);
    }

    public static String getRegName(Item item) {
        return getRegNameI(item).toString();
    }

    public static ArrayList<Identifier> getTags(Item item) {
        ArrayList<Identifier> result = new ArrayList<>();

        item.method_40131().method_40228().forEach(tag -> result.add(tag.comp_327()));

        return result;
    }

    public static final NameHelper INSTANCE = new NameHelper();

    public void scan() {
        DefaultedList<ItemStack> list = DefaultedList.of();

        Registry.ITEM.forEach(item -> {
            for (ItemGroup group : ItemGroup.GROUPS) {
                item.appendStacks(group, list);
            }
        });

        for (ItemStack itemStack : list) {
            getOrCreateModItems(itemStack).add(itemStack);
        }
    }

    public final Map<String, ModItems> MOD_ITEMS = new HashMap<>();

    public ModItems getOrCreateModItems(ItemStack itemStack) {
        String modName = getRegNameI(itemStack.getItem()).getNamespace();
        ModItems temp = MOD_ITEMS.get(modName);

        if (temp == null) {
            temp = new ModItems();
            MOD_ITEMS.put(modName, temp);
        }

        return temp;
    }

    public static class ModItems {
        Map<ItemStack, FormatJson> map = new HashMap<>();

        public void add(ItemStack itemStack) {
            FormatJson temp = new FormatJson(itemStack);

            temp.setEnName(LanguageHelper.INSTANCE.getName(LanguageHelper.Type.en_us, itemStack.getTranslationKey()));
            temp.setZhName(LanguageHelper.INSTANCE.getName(LanguageHelper.Type.zh_cn, itemStack.getTranslationKey()));

            map.put(itemStack, temp);
        }
    }
}
