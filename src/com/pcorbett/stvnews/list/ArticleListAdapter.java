package com.pcorbett.stvnews.list;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.pcorbett.stvnews.ArticleListActivity;
import com.pcorbett.stvnews.R;
import com.pcorbett.stvnews.structures.Article;

public class ArticleListAdapter extends ArrayAdapter<Article> {

	private ArticleListActivity articleListActivity;

	public ArticleListAdapter(ArticleListActivity i_activity, List<Article> i_list) {
		super(i_activity, R.layout.activity_article_list_row, R.id.article_title, i_list);
		articleListActivity = i_activity;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ArticleWrapper wrapper=null;
		if (convertView==null) {
			convertView = articleListActivity.getLayoutInflater().inflate(R.layout.activity_article_list_row, parent, false);
			wrapper = new ArticleWrapper(convertView);
			convertView.setTag(wrapper);
		}
		else {
			wrapper = (ArticleWrapper) convertView.getTag();
		}
		wrapper.populateFrom(getItem(position));
		return convertView;

	}
	
}
