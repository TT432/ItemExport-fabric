package com.nmmoc7.itemexport.item_export.exporter;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.LanguageDefinition;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Language;

import java.util.List;

/**
 * @author DustW
 */
public class LanguageHelper {
    public static final LanguageHelper INSTANCE = new LanguageHelper();

    Language en_us;
    Language zh_cn;

    public LanguageHelper() {
        ResourceManager manager = MinecraftClient.getInstance().getResourceManager();

        List<LanguageDefinition> en = List.of(new LanguageDefinition("en_us", "", "", false));
        List<LanguageDefinition> zh = List.of(new LanguageDefinition("zh_cn", "", "", false));

        en_us = TranslationStorage.load(manager, en);
        zh_cn = TranslationStorage.load(manager, zh);
    }

    public String getName(Type type, String key) {
        if (type == Type.en_us) {
            return en_us.get(key);
        }
        else {
            if (zh_cn.hasTranslation(key)){
                return zh_cn.get(key);
            }
            else {
                return en_us.get(key);
            }
        }
    }

    public enum Type {
        en_us,
        zh_cn
    }
}
