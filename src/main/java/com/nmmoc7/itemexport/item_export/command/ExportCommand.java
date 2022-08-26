package com.nmmoc7.itemexport.item_export.command;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.nmmoc7.itemexport.item_export.exporter.ExportHandler;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

public class ExportCommand implements Command<FabricClientCommandSource> {
    public static final ExportCommand INSTANCE = new ExportCommand();
    public static final LiteralArgumentBuilder<FabricClientCommandSource> COMMAND =
            CommandHelper.getModCommand().executes(INSTANCE);

    @Override
    public int run(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException {
        RenderSystem.recordRenderCall(ExportHandler.INSTANCE::doExport);
        return 0;
    }
}
