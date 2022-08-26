package com.nmmoc7.itemexport;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.nmmoc7.itemexport.item_export.command.CommandHelper;
import com.nmmoc7.itemexport.item_export.exporter.ExportHandler;
import com.nmmoc7.itemexport.item_export.exporter.FormatJson;
import com.nmmoc7.itemexport.item_export.exporter.LanguageHelper;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class SingleExportHelper {
    static LiteralArgumentBuilder<FabricClientCommandSource> export = CommandHelper.getModCommand()
            .then(ClientCommandManager.literal("single").executes(SingleExportCommand.INSTANCE));

    static class SingleExportCommand implements Command<FabricClientCommandSource> {
        public static SingleExportCommand INSTANCE = new SingleExportCommand();

        @Override
        public int run(CommandContext<FabricClientCommandSource> context) {
            RenderSystem.recordRenderCall(() -> exportSingleItem(MinecraftClient.getInstance().player.getMainHandStack()));
            return 0;
        }
    }

    private static void exportSingleItem(ItemStack itemStack) {
        FormatJson temp = new FormatJson(itemStack);

        temp.setEnName(LanguageHelper.INSTANCE.getName(LanguageHelper.Type.en_us, itemStack.getTranslationKey()));
        temp.setZhName(LanguageHelper.INSTANCE.getName(LanguageHelper.Type.zh_cn, itemStack.getTranslationKey()));

        ExportHandler.createIfNotExists();

        try(PrintWriter pw = new PrintWriter(ExportHandler
                .createFileIfNotExists(new File("export\\single_export_item.json")), "UTF-8")) {

            pw.println(temp.toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
