package com.mojang.minecraft.renderer;

import org.teavm.jso.typedarrays.ArrayBuffer;
import org.teavm.jso.typedarrays.Float32Array;
import org.teavm.jso.typedarrays.Int32Array;

import net.lax1dude.eaglercraft.EaglerAdapter;

public class Tesselator {

    private Int32Array intBuffer;
    private Float32Array floatBuffer;

    private int vertexCount = 0;
    private float textureU;
    private float textureV;
    private int color;
    private boolean hasColor = false;
    private boolean hasTexture = false;
    private boolean hasNormals = false;
    private int rawBufferIndex = 0;
    private int addedVertices = 0;
    private boolean isColorDisabled = false;
    private int drawMode;
    private double xOffset;
    private double yOffset;
    private double zOffset;
    private int normal;
    public static final Tesselator instance = new Tesselator(525000);
    private boolean isDrawing = false;
    private int bufferSize;

    public Tesselator(int par1) {
        this.bufferSize = par1;
        ArrayBuffer a = ArrayBuffer.create(par1 * 4);
        this.intBuffer = Int32Array.create(a);
        this.floatBuffer = Float32Array.create(a);
    }

    public int end() {
        return this.draw();
    }

    public void begin() {
        this.startDrawingQuads();
    }

    public void color(float var1, float var2, float var3) {
        this.setColorOpaque_F(var1, var2, var3);
    }

    public void color(int var1, int var2, int var3) {
        this.setColorOpaque(var1, var2, var3);
    }

    public void vertexUV(float var1, float var2, float var3, float var4, float var5) {
        this.addVertexWithUV(var1, var2, var3, var4, var5);
    }

    public void vertex(float var1, float var2, float var3) {
        this.addVertex(var1, var2, var3);
    }

    public void color(int var1) {
        this.setColorOpaque_I(var1);
    }

    public void noColor() {
        this.disableColor();
    }

    public int draw() {
        if (!this.isDrawing) {
            return 0;
        } else {
            this.isDrawing = false;

            if (this.vertexCount > 0) {
                if (this.hasTexture) {
                    EaglerAdapter.glEnableVertexAttrib(EaglerAdapter.GL_TEXTURE_COORD_ARRAY);
                }

                if (this.hasColor) {
                    EaglerAdapter.glEnableVertexAttrib(EaglerAdapter.GL_COLOR_ARRAY);
                }

                if (this.hasNormals) {
                    EaglerAdapter.glEnableVertexAttrib(EaglerAdapter.GL_NORMAL_ARRAY);
                }

                if (this.hasTexture) {
                    EaglerAdapter.glDisableVertexAttrib(EaglerAdapter.GL_TEXTURE_COORD_ARRAY);
                }

                if (this.hasColor) {
                    EaglerAdapter.glDisableVertexAttrib(EaglerAdapter.GL_COLOR_ARRAY);
                }

                if (this.hasNormals) {
                    EaglerAdapter.glDisableVertexAttrib(EaglerAdapter.GL_NORMAL_ARRAY);
                }
            }

            int var1 = this.rawBufferIndex * 4;
            this.reset();
            return var1;
        }
    }

    private void reset() {
        this.vertexCount = 0;
        this.rawBufferIndex = 0;
        this.addedVertices = 0;
    }

    public void startDrawingQuads() {
        this.startDrawing(EaglerAdapter.GL_QUADS);
    }

    public void startDrawing(int par1) {
        if (this.isDrawing) {
            this.draw();
        }
        this.isDrawing = true;
        this.reset();
        this.drawMode = par1;
        this.hasNormals = false;
        this.hasColor = false;
        this.hasTexture = false;
        this.isColorDisabled = false;
    }

    public void setTextureUV(double par1, double par3) {
        this.hasTexture = true;
        this.textureU = (float) par1;
        this.textureV = (float) par3;
    }

    public void setColorOpaque_F(float par1, float par2, float par3) {
        this.setColorOpaque((int) (par1 * 255.0F), (int) (par2 * 255.0F), (int) (par3 * 255.0F));
    }

    public void setColorRGBA_F(float par1, float par2, float par3, float par4) {
        this.setColorRGBA((int) (par1 * 255.0F), (int) (par2 * 255.0F), (int) (par3 * 255.0F), (int) (par4 * 255.0F));
    }

    public void setColorOpaque(int par1, int par2, int par3) {
        this.setColorRGBA(par1, par2, par3, 255);
    }

    public void setColorRGBA(int par1, int par2, int par3, int par4) {
        if (!this.isColorDisabled) {
            this.hasColor = true;
            this.color = par4 << 24 | par3 << 16 | par2 << 8 | par1;
        }
    }

    public void addVertexWithUV(double par1, double par3, double par5, double par7, double par9) {
        this.setTextureUV(par7, par9);
        this.addVertex(par1, par3, par5);
    }

    public void addVertex(double par1, double par3, double par5) {
        if (this.addedVertices > 65534) return;
        ++this.addedVertices;
        ++this.vertexCount;

        int bufferIndex = this.rawBufferIndex;
        Float32Array floatBuffer0 = floatBuffer;

        floatBuffer0.set(bufferIndex + 0, (float) (par1 + this.xOffset));
        floatBuffer0.set(bufferIndex + 1, (float) (par3 + this.yOffset));
        floatBuffer0.set(bufferIndex + 2, (float) (par5 + this.zOffset));

        if (this.hasTexture) {
            floatBuffer0.set(bufferIndex + 3, this.textureU);
            floatBuffer0.set(bufferIndex + 4, this.textureV);
        }

        if (this.hasColor) {
            intBuffer.set(bufferIndex + 5, this.color);
        }

        if (this.hasNormals) {
            intBuffer.set(bufferIndex + 6, this.normal);
        }

        this.rawBufferIndex += 7;
    }

    public void setColorOpaque_I(int par1) {
        int var2 = par1 >> 16 & 255;
        int var3 = par1 >> 8 & 255;
        int var4 = par1 & 255;
        this.setColorOpaque(var2, var3, var4);
    }

    public void setColorRGBA_I(int par1, int par2) {
        int var3 = par1 >> 16 & 255;
        int var4 = par1 >> 8 & 255;
        int var5 = par1 & 255;
        this.setColorRGBA(var3, var4, var5, par2);
    }

    public void disableColor() {
        this.isColorDisabled = true;
    }

    public void setNormal(float par1, float par2, float par3) {
        this.hasNormals = true;
        float len = (float) Math.sqrt(par1 * par1 + par2 * par2 + par3 * par3);
        int var4 = (int)((par1 / len) * 127.0F) + 127;
        int var5 = (int)((par2 / len) * 127.0F) + 127;
        int var6 = (int)((par3 / len) * 127.0F) + 127;
        this.normal = var4 & 255 | (var5 & 255) << 8 | (var6 & 255) << 16;
    }

    public void setTranslationD(double par1, double par3, double par5) {
        this.xOffset = par1;
        this.yOffset = par3;
        this.zOffset = par5;
    }

    public void setTranslationF(float par1, float par2, float par3) {
        this.xOffset += (float) par1;
        this.yOffset += (float) par2;
        this.zOffset += (float) par3;
    }
}
