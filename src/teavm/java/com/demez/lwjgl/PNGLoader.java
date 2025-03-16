package com.demez.lwjgl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.LinkedList;

import org.teavm.interop.Async;
import org.teavm.interop.AsyncCallback;
import org.teavm.jso.JSBody;
import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;
import org.teavm.jso.ajax.ReadyStateChangeHandler;
import org.teavm.jso.ajax.XMLHttpRequest;
import org.teavm.jso.browser.TimerHandler;
import org.teavm.jso.browser.Window;
import org.teavm.jso.canvas.CanvasRenderingContext2D;
import org.teavm.jso.canvas.ImageData;
import org.teavm.jso.dom.events.Event;
import org.teavm.jso.dom.events.EventListener;
import org.teavm.jso.dom.events.KeyboardEvent;
import org.teavm.jso.dom.events.MessageEvent;
import org.teavm.jso.dom.events.MouseEvent;
import org.teavm.jso.dom.events.WheelEvent;
import org.teavm.jso.dom.html.HTMLCanvasElement;
import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.html.HTMLImageElement;
import org.teavm.jso.typedarrays.ArrayBuffer;
import org.teavm.jso.typedarrays.Float32Array;
import org.teavm.jso.typedarrays.Int32Array;
import org.teavm.jso.typedarrays.Uint8Array;
import org.teavm.jso.typedarrays.Uint8ClampedArray;
import org.teavm.jso.webaudio.AudioBuffer;
import org.teavm.jso.webaudio.AudioBufferSourceNode;
import org.teavm.jso.webaudio.AudioContext;
import org.teavm.jso.webaudio.AudioListener;
import org.teavm.jso.webaudio.DecodeErrorCallback;
import org.teavm.jso.webaudio.DecodeSuccessCallback;
import org.teavm.jso.webaudio.GainNode;
import org.teavm.jso.webaudio.MediaEvent;
import org.teavm.jso.webaudio.PannerNode;
import org.teavm.jso.webgl.WebGLBuffer;
import org.teavm.jso.webgl.WebGLFramebuffer;
import org.teavm.jso.webgl.WebGLProgram;
import org.teavm.jso.webgl.WebGLRenderbuffer;
import org.teavm.jso.webgl.WebGLShader;
import org.teavm.jso.webgl.WebGLTexture;
import org.teavm.jso.webgl.WebGLUniformLocation;
import org.teavm.jso.websocket.CloseEvent;
import org.teavm.jso.websocket.WebSocket;

import net.PeytonPlayz585.io.IndexedDBFilesystem;
import net.PeytonPlayz585.minecraft.AssetRepository;
import net.PeytonPlayz585.minecraft.Base64;
import net.PeytonPlayz585.opengl.MinecraftImageData;

import static org.teavm.jso.webgl.WebGLRenderingContext.*;
import static net.PeytonPlayz585.opengl.WebGL2RenderingContext.*;
import static net.PeytonPlayz585.opengl.RealOpenGLEmuns.*;

public class PNGLoader {
    private static HTMLCanvasElement imageLoadCanvas = null; // Canvas elementi, görüntü çizmek için kullanılır.
    private static CanvasRenderingContext2D imageLoadContext = null; // Canvas bağlamı (context), çizim işlemleri için kullanılır.
	public static HTMLDocument doc = null;

    /**
     * PNG verisini yükler.
     *
     * @param data PNG dosyasının bayt verisi.
     * @return MinecraftImageData formatında işlenmiş görüntü.
     */
    public static final MinecraftImageData loadPNG(byte[] data) {
		ArrayBuffer arr = ArrayBuffer.create(data.length);
		Uint8Array.create(arr).set(data);
		return loadPNG0(arr);
	}


	@Async
	private static native MinecraftImageData loadPNG0(ArrayBuffer data);

	private static void loadPNG0(ArrayBuffer data, final AsyncCallback<MinecraftImageData> ret) {
		final HTMLImageElement toLoad = (HTMLImageElement) doc.createElement("img");
		toLoad.addEventListener("load", new EventListener<Event>() {
			@Override
			public void handleEvent(Event evt) {
				if(imageLoadCanvas == null) {
					imageLoadCanvas = (HTMLCanvasElement) doc.createElement("canvas");
				}
				if(imageLoadCanvas.getWidth() < toLoad.getWidth()) {
					imageLoadCanvas.setWidth(toLoad.getWidth());
				}
				if(imageLoadCanvas.getHeight() < toLoad.getHeight()) {
					imageLoadCanvas.setHeight(toLoad.getHeight());
				}
				if(imageLoadContext == null) {
					imageLoadContext = (CanvasRenderingContext2D) imageLoadCanvas.getContext("2d");
				}
				imageLoadContext.clearRect(0, 0, toLoad.getWidth(), toLoad.getHeight());
				imageLoadContext.drawImage(toLoad, 0, 0, toLoad.getWidth(), toLoad.getHeight());
				ImageData pxlsDat = imageLoadContext.getImageData(0, 0, toLoad.getWidth(), toLoad.getHeight());
				Uint8ClampedArray pxls = pxlsDat.getData();
				int totalPixels = pxlsDat.getWidth() * pxlsDat.getHeight();
				freeDataURL(toLoad.getSrc());
				if(pxls.getByteLength() < totalPixels * 4) {
					ret.complete(null);
					return;
				}
				int[] pixels = new int[totalPixels];
				for(int i = 0; i < pixels.length; ++i) {
					pixels[i] = (pxls.get(i * 4) << 16) | (pxls.get(i * 4 + 1) << 8) | pxls.get(i * 4 + 2) | (pxls.get(i * 4 + 3) << 24);
				}
				ret.complete(new MinecraftImageData(pixels, pxlsDat.getWidth(), pxlsDat.getHeight(), true));
			}
		});
		toLoad.addEventListener("error", new EventListener<Event>() {
			@Override
			public void handleEvent(Event evt) {
				freeDataURL(toLoad.getSrc());
				ret.complete(null);
			}
		});
		String src = getDataURL(data, "image/png");
		if(src == null) {
			ret.complete(null);
		}else {
			toLoad.setSrc(src);
		}
	}
    /**
     * Kullanılmış bir veri URL'sini serbest bırakır.
     *
     * @param url Serbest bırakılacak veri URL'si.
     */
    @JSBody(params = { "url" }, script = "URL.revokeObjectURL(url);")
    private static native void freeDataURL(String url);

    /**
     * Verilen verilerden bir veri URL'si oluşturur.
     *
     * @param data     PNG verilerini içeren ArrayBuffer.
     * @param mimeType Veri tipi (örnek: "image/png").
     * @return Veri URL'si.
     */
    @JSBody(params = { "data", "mimeType" }, script = 
    	    "var blob = new Blob([data], { type: mimeType });" +
    	    "return URL.createObjectURL(blob);")
    	private static native String getDataURL(ArrayBuffer data, String mimeType);
    
    
    public static final byte[] loadResourceBytes(String path) {
		return AssetRepository.getResource(path);
	}
	

}
