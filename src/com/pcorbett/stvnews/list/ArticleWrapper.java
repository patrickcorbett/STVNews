package com.pcorbett.stvnews.list;

import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.pcorbett.stvnews.R;
import com.pcorbett.stvnews.net.ImageLoader;
import com.pcorbett.stvnews.structures.Article;
import com.pcorbett.stvnews.structures.Image;

public class ArticleWrapper {

	private TextView title = null;
	private TextView shortHeadline = null;
	private ImageView image = null;
	private View row = null;

	public ArticleWrapper(View row) {
		this.row=row;
	}

	public ImageView getImageView() {
		if (image==null) {
			image=(ImageView)row.findViewById(R.id.article_image);
		}
		return(image);
	}
	
	public TextView getTitle() {
		if (title==null) {
			title=(TextView)row.findViewById(R.id.article_title);
		}
		return(title);
	}
	
	public TextView getShortHeadline() {
		if (shortHeadline==null) {
			shortHeadline=(TextView)row.findViewById(R.id.article_short_headline);
		}
		return(shortHeadline);
	}

	public void populateFrom(Article article) {
		//Set up the list
		getTitle().setText(article.getTitle());
		getShortHeadline().setText(article.getShortHeadline());
		
		if(article.getImages() != null){
			//get the images
			for(Image img : article.getImages()){
				
				if(img.getImage() == null){
					//load the image
					ImageLoader loadImageTask = new ImageLoader(img, getImageView());
					//start background task
					loadImageTask.load();
				}
				else{
					//render image
					getImageView().setImageBitmap(img.getImage());
					getImageView().setScaleType(ScaleType.CENTER_CROP);
				}
			}
		}
	}

}
