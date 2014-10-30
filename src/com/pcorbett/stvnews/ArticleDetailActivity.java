package com.pcorbett.stvnews;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.pcorbett.stvnews.net.ImageLoader;
import com.pcorbett.stvnews.structures.Article;

public class ArticleDetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_detail);
		
		//get the passed news article
		Article article = null;
		Intent intent = getIntent();
		if(intent != null){
			Bundle data = intent.getExtras();
			article = (Article) data.getParcelable("ARTICLE");
		}

		if(article != null){			
			//show the news article
			//set the headline
			TextView headline = (TextView) findViewById(R.id.article_detail_title);
			headline.setText(article.getTitle());
			
			//set the article image
			//show in view
			ImageView articleImage = (ImageView) findViewById(R.id.article_detail_image);
			
			ImageLoader il = new ImageLoader(article.getImages()[0], articleImage);
			il.load();
			
			//set the article content using CSS Styles
			StringBuffer html = new StringBuffer();
			html.append("<html>");
			html.append("<head>");
			html.append("<style type=\"text/css\">body{color: #fff;}a{color: #6ABEDB;}");
			html.append("</style>");
			html.append("</head>");
			html.append("<body>"); 
			html.append(article.getContentHTML());
			html.append("</body>");
			html.append("</html>");
			
			WebView articleContent = (WebView) findViewById(R.id.article_content);
			articleContent.loadData(html.toString(), "text/html", null);
			articleContent.setBackgroundColor(Color.TRANSPARENT);
		}
		else{
			//show an error message
			TextView headline = (TextView) findViewById(R.id.article_detail_title);
			headline.setText(getText(R.string.content_not_found));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.article_detail, menu);
		return true;
	}

}
