package com.nmmoc7.itemexport.item_export.exporter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class ExportHandler {
    public static final ExportHandler INSTANCE = new ExportHandler();


    public void scan() {
        NameHelper.INSTANCE.scan();
    }

    public void doExport() {
        scan();
        NameHelper.INSTANCE.MOD_ITEMS.forEach(INSTANCE::exportItem);
    }

    public static void createIfNotExists() {
        File fileDir = new File("export\\");

        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
    }

    public static File createFileIfNotExists(File file) throws IOException {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if (!file.exists()) {
            file.createNewFile();
        }

        return file;
    }

    public void exportItem(String modId, NameHelper.ModItems modItems) {
        File fileDir = new File("export\\");

        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        try {
            StringBuilder builder = new StringBuilder("export\\");
            File export = new File(String.format(builder.append(modId).append("_item.json").toString(), modId.replaceAll("[^A-Za-z0-9()\\[\\]]", "")));
            if (!export.getParentFile().exists()) {
                export.getParentFile().mkdirs();
            }

            if (!export.exists()) {
                export.createNewFile();
            }

            PrintWriter pw = new PrintWriter(export, StandardCharsets.UTF_8);

            for (FormatJson json: modItems.map.values()) {
                pw.println(json.toJson());
            }

            pw.close();
        } catch (IOException var9) {
            var9.printStackTrace();
        }

    }
}
