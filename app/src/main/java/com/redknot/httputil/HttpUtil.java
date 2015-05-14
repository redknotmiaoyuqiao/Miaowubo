 package com.redknot.httputil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public final class HttpUtil {
	private HttpUtil() {
	}

	public static String decode(String unicodeStr) {
		if (unicodeStr == null) {
			return null;
		}
		StringBuffer retBuf = new StringBuffer();
		int maxLoop = unicodeStr.length();
		for (int i = 0; i < maxLoop; i++) {
			if (unicodeStr.charAt(i) == '\\') {
				if ((i < maxLoop - 5)
						&& ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr
								.charAt(i + 1) == 'U')))
					try {
						retBuf.append((char) Integer.parseInt(
								unicodeStr.substring(i + 2, i + 6), 16));
						i += 5;
					} catch (NumberFormatException localNumberFormatException) {
						retBuf.append(unicodeStr.charAt(i));
					}
				else
					retBuf.append(unicodeStr.charAt(i));
			} else {
				retBuf.append(unicodeStr.charAt(i));
			}
		}
		return retBuf.toString();
	}
	
	public static String getRequest(String url,Map<String, String> rawparams) throws Exception {
        String last = "?i=1";
        for (String key : rawparams.keySet()) {
            last = last + "&" + key + "=" + rawparams.get(key);
        }

        System.out.println(url + last);

        return "";

//        url = url + last;
//
//		HttpGet get = new HttpGet(url);
//		DefaultHttpClient httpclient = new DefaultHttpClient();
//		HttpResponse httpresponse = httpclient.execute(get);
//		if (httpresponse.getStatusLine().getStatusCode() == 200) {
//
//			StringBuffer sb = new StringBuffer();
//			HttpEntity entity = httpresponse.getEntity();
//			InputStream is = entity.getContent();
//			BufferedReader br = new BufferedReader(
//
//			new InputStreamReader(is, "UTF8"));
//			String data = "";
//
//			while ((data = br.readLine()) != null) {
//				sb.append(data);
//			}
//			String result = sb.toString();
//
//			result = HttpUtil.decode(result);
//			return result;
//		}
//		return null;
	}

	public static String postRequest(String url, Map<String, String> rawparams)
			throws Exception {
		HttpPost post = new HttpPost(url);
		// post.addHeader(name, value);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (String key : rawparams.keySet()) {
			params.add(new BasicNameValuePair(key, rawparams.get(key)));
		}
		post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpResponse httpresponse = httpclient.execute(post);

		if (httpresponse.getStatusLine().getStatusCode() == 200) {
			String result = EntityUtils.toString(httpresponse.getEntity());
			result = HttpUtil.decode(result);
			return result;
		}
		return null;
	}

	public static Bitmap loadImage(String url) throws Exception {
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = null;
		InputStream inputStream = null;

		response = client.execute(new HttpGet(url));
		HttpEntity entity = response.getEntity();
		inputStream = entity.getContent();
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 1;	
		Bitmap b = BitmapFactory.decodeStream(inputStream, null, options);
	
		return b;
	}
	

}
