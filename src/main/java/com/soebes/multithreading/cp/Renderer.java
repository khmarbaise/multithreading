package com.soebes.multithreading.cp;

import java.util.concurrent.ExecutorService;

/**
 * @author Karl Heinz Marbaise
 */
public class Renderer {
    private final ExecutorService executor;

    Renderer(ExecutorService executor) {
	this.executor = executor;
    }
//
//    void renderPage(CharSequence source) {
//	final List<ImageInfo> info = scanForImageInfo(source);
//	CompletionService<ImageData> completionService = new ExecutorCompletionService<ImageData>(
//		executor);
//	for (final ImageInfo imageInfo : info) {
//	    completionService.submit(new Callable<ImageData>() {
//		public ImageData call() {
//		    return imageInfo.downloadImage();
//		}
//	    });
//	}
//	renderText(source);
//	try {
//	    for (int t = 0, n = info.size(); t < n; t++) {
//		Future<ImageData> f = completionService.take();
//		ImageData imageData = f.get();
//		renderImage(imageData);
//	    }
//	} catch (InterruptedException e) {
//	    Thread.currentThread().interrupt();
//	} catch (ExecutionException e) {
//	    throw launderThrowable(e.getCause());
//	}
//    }
}