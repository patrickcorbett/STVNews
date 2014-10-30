package com.pcorbett.stvnews.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pcorbett.stvnews.structures.Image;

public class ImageLoader extends AsyncTask<String, Integer, String>{

	private Image image;
	private String imageUrl;
	private ImageView imageView;
	
	public ImageLoader(Image i_image, ImageView i_imageView){
		image = i_image;
		imageView = i_imageView;
		
		//prepare url
		String url = "http://api.stv.tv/images/%s/rendition/?width=640&height=360";
		imageUrl = String.format(url, i_image.getId());
	}
	
	protected String doInBackground(String... urls) {

		//holds the json received from the STV news Service
		StringBuilder strReceived = new StringBuilder();

		//build a http client for the web service request
		URL url = null;
		HttpURLConnection urlConnection = null;
		try {
			url = new URL(urls[0]);
			urlConnection = (HttpURLConnection) url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

			String line;
			while ((line = br.readLine()) != null) {
				strReceived.append(line+"\n");
			}
			br.close();
		}
		catch (MalformedURLException ex) {
			Logger.getLogger("HTTP_CONNECTION_IMAGE").log(Level.SEVERE, ex.getMessage(), ex);
		} catch (IOException ex) {
			Logger.getLogger("HTTP_CONNECTION_IMAGE").log(Level.SEVERE, ex.getMessage(), ex);
		}
		finally {
			if(urlConnection != null) urlConnection.disconnect();
		}
		
		//convert the JSON to Java Objects
		JsonParser parser = new JsonParser();
		JsonObject obj = parser.parse(strReceived.toString()).getAsJsonObject();
		String dataUri = obj.get("dataUri").toString();
		
		//return the image in base64
		return dataUri;
	}
	
	protected void onPostExecute(String result) {
		renderImage(result);
	}

	public void load() {
		execute(imageUrl);
	}
	
	public void renderImage(String i_dataUri){
		
		if(i_dataUri != null){
			int lastComma = i_dataUri.lastIndexOf(',');
			if(lastComma != -1){	
				i_dataUri = i_dataUri.substring(++lastComma).trim();
			}
			
			//convert to bitmap
			byte[] decodedString = Base64.decode(i_dataUri, Base64.DEFAULT);
			Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
			
			//save the image and base64 string
			image.setDataUri(i_dataUri);
			image.setImage(bitmap);
			
			//show in view
			imageView.setImageBitmap(bitmap);
			imageView.setScaleType(ScaleType.CENTER_CROP);
		}
	}
	
}
