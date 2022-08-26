package com.nmmoc7.itemexport.item_export.exporter;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;
import java.util.Base64;

public class Base64Helper {
    public static String getSmall(ItemStack itemStack) {
        return getItemBase64(itemStack, small());
    }

    public static String getLarge(ItemStack itemStack) {
        return getItemBase64(itemStack, large());
    }

    public static Framebuffer small() {
        return init(new SimpleFramebuffer(32, 32, true, false));
    }
    public static Framebuffer large() {
        return init(new SimpleFramebuffer(128, 128, true, false));
    }

    public static Framebuffer init(Framebuffer framebuffer) {
        framebuffer.beginRead();
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, framebuffer.textureWidth, framebuffer.textureHeight,
                0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);

        return framebuffer;
    }

    public static String getItemBase64(ItemStack stack, Framebuffer fbo) {
        fbo.beginWrite(true);
        GL11.glClearColor(0, 0, 0, 0);

        renderItem(stack, MinecraftClient.getInstance().getItemRenderer());

        try (NativeImage image = dumpFrom(fbo)) {
            return base64(image);
        }
        finally {
            fbo.endWrite();
        }
    }

    public static NativeImage dumpFrom(Framebuffer frame) {
        NativeImage img = new NativeImage(frame.textureWidth, frame.textureHeight, true);
        frame.beginRead();
        img.loadFromTextureImage(0, false);
        img.mirrorVertically();
        return img;
    }

    public static String base64(NativeImage img) {
        try (img) {
            return Base64.getEncoder().encodeToString(img.getBytes());
        } catch (Exception e) {
            return "";
        }
    }

    public static void renderItem(ItemStack itemStack, ItemRenderer itemRenderer) {
        Matrix4f backup = RenderSystem.getProjectionMatrix().copy();

        Matrix4f projection = Matrix4f.projectionMatrix(0, 16, 0, 16, -150, 150);
        Matrix4f modelview = Matrix4f.translate(0.0001F, 0.0001F, 0);

        projection.multiply(modelview);
        RenderSystem.setProjectionMatrix(projection);

        DiffuseLighting.enableGuiDepthLighting();

        itemRenderer.renderGuiItemIcon(itemStack, 0, 0);

        RenderSystem.setProjectionMatrix(backup);
    }
}
