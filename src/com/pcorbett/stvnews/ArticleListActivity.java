package com.pcorbett.stvnews;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.pcorbett.stvnews.list.ArticleListAdapter;
import com.pcorbett.stvnews.structures.Article;

public class ArticleListActivity extends ListActivity {

	//article list
	private List<Article> articles = new ArrayList<Article>();
	private ArticleListAdapter listAdapter;
	private ProgressDialog progressDialog;
	
	//STV API URL
	private final String stvNewsUrl = "http://api.stv.tv/articles/?count=50&navigationLevelId=1218&orderBy=ranking+DESC%2C+createdAt+DESC&full=1&count=20";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_list);
		
		//create a list adapter
		listAdapter = new ArticleListAdapter(ArticleListActivity.this, articles);
		
		//tell the list activity to use this adapter
		setListAdapter(listAdapter);
		
		// show a loading mask
		progressDialog = ProgressDialog.show(ArticleListActivity.this, "", getText(R.string.loading), true);
		
		//get the stv news from the web service
		loadNews();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.article_list, menu);
		return true;
	}
	
	protected void onListItemClick(ListView l, View v, int position, long id){
		//open the article detail view
		Intent detailScreen = new Intent(getApplicationContext(), ArticleDetailActivity.class);
		
		//pass the selected article to the detail activity
		detailScreen.putExtra("ARTICLE", (Article)l.getItemAtPosition(position));
		
		//start for result as when session ends all items can be stopped
		startActivity(detailScreen);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_refresh:
			loadNews();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void loadNews(){	
		//show the loading message
		progressDialog.show();
		
		//prepare the task to read the news
		AsyncTask<String, Integer, List<Article>> readNewsTask = new AsyncTask<String, Integer, List<Article>>() {
			protected List<Article> doInBackground(String... urls) {
				
				//holds the json received from the STV news Service
				StringBuilder jsonArticlesArray = new StringBuilder();
		
				List<Article> articleList = new ArrayList<Article>();
				
				//build a http client for the web service request
				URL url = null;
				HttpURLConnection urlConnection = null;
				try {
					url = new URL(stvNewsUrl);
					urlConnection = (HttpURLConnection) url.openConnection();
					BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
					
					String line;
					while ((line = br.readLine()) != null) {
						jsonArticlesArray.append(line+"\n");
					}
					br.close();
					
					//convert the JSON to Java Objects
					Type listType = new TypeToken<List<Article>>(){}.getType();
					articleList = new Gson().fromJson(jsonArticlesArray.toString(), listType);
				}
				catch (MalformedURLException ex) {
					Logger.getLogger("HTTP_CONNECTION").log(Level.SEVERE, ex.getMessage(), ex);
				}
				catch (JsonSyntaxException ex) {
					Logger.getLogger("JSON_EXCEPTION").log(Level.SEVERE, ex.getMessage(), ex);
				}
				catch (IOException ex) {
					Logger.getLogger("HTTP_CONNECTION").log(Level.SEVERE, ex.getMessage(), ex);
				}
				finally {
					if(urlConnection != null) urlConnection.disconnect();
				}
				
				//fix for weird formatted quotes in html content
				for(Article art : articleList){
					String htmlContent = art.getContentHTML().replaceAll("”", "\"");
					htmlContent = htmlContent.replaceAll("“", "\"");
					
					art.setContentHTML(htmlContent);
				}
				
				//return the list
				return articleList;
			}
			
			protected void onPostExecute(List<Article> result) {
				//add all items to the list
				articles.clear();//remove any previous items avoid duplicates
				articles.addAll(result);
				//update list
				listAdapter.notifyDataSetChanged();
				//load complete
				progressDialog.dismiss();
			}
		};
		readNewsTask.execute();
	}
	
}
