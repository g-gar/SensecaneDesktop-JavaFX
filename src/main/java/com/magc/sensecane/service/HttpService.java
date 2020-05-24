package com.magc.sensecane.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;

import com.magc.sensecane.framework.http.HttpAsyncMethodExecutor;

public class HttpService {
	
	public static abstract class Method<T extends HttpRequestBase> {
		protected final List<Header> headers = new ArrayList<Header>();
		
		public <H extends Header> Method<T> addHeader(H header) {
			this.headers.add(header);
			return this;
		}
		
		public abstract T build(String url);
		public <R> T build(String url, R body) {
			return null;
		}
	}

	public static class GET extends Method<HttpGet> {

		@Override
		public HttpGet build(String url) {
			HttpGet get = new HttpGet(url);
			headers.forEach(header -> get.setHeader(header));
			return get;
		}

	}

	public static class POST extends Method<HttpPost> {

		@Override
		public HttpPost build(String url) {
			return this.build(url, new String());
		}
		
		@Override
		public <R> HttpPost build(String url, R body) {
			HttpPost post = new HttpPost(url);
			headers.forEach(header -> post.setHeader(header));
			try {
				post.setEntity(new StringEntity(body.toString()));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return post;
		}
	}

	public static <R, T extends HttpAsyncMethodExecutor<R>> void request(HttpRequestBase http, Class<T> executorClass, BiConsumer<T, HttpResponse> onComplete) {
		try {
			executorClass.newInstance().fetch(http, (T executor, HttpResponse response) -> {
				onComplete.accept(executor, response);
			});
		} catch (IOException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static <T, R extends HttpAsyncMethodExecutor<T>> void request(HttpRequestBase http, Class<R> executor, BiConsumer<R, HttpResponse> onComplete, BiConsumer<R, Exception> onError) {
		try {
			HttpAsyncMethodExecutor<T> exec = executor.newInstance();
			exec.fetch(http, onComplete, onError);
		} catch (IOException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
