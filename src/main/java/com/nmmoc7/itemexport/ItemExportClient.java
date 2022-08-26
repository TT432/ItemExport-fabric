package com.nmmoc7.itemexport;

import com.nmmoc7.itemexport.item_export.command.ExportCommand;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;

public class ItemExportClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientCommandManager.DISPATCHER.register(ExportCommand.COMMAND);
        ClientCommandManager.DISPATCHER.register(SingleExportHelper.export);
    }
}
