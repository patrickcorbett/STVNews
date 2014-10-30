package com.pcorbett.stvnews.structures;

import java.util.Arrays;

import android.os.Parcel;
import android.os.Parcelable;

public class Article implements Parcelable{
	
	private String id = "";
	private String guid = "";
	private String title = "";
	private String publishDate = "";
	private String shortHeadline = "";
	private String subHeadline = "";
	private String description = "";
	private String contentHTML = "";
	private String contentMarkdown = "";
	private String permalinkNavigationId = "";
	private String permalink = "";
	private String status = "";
	private String createdAt = "";
	private String modifiedAt = "";
	private String creatorUserId = "";
	private String modifierUserId = "";
	private String bylineUserId = "";
	private String publisher = "";
	private String seoTitle = "";
	private Image[] images = null;  
	private Integer syndicate = 0;
	private Integer mobileOnly = 0;
	
	public static final Parcelable.Creator<Article> CREATOR = new Creator<Article>(){
		@Override
		public Article createFromParcel(Parcel source) {
			return new Article(source);
		}

		@Override
		public Article[] newArray(int size) {
			return new Article[size];
		}
	};
	
	public Article(Parcel source) {
		id = source.readString();
		guid = source.readString();
		title = source.readString();
		publishDate = source.readString();
		shortHeadline = source.readString();
		subHeadline = source.readString();
		description = source.readString();
		contentHTML = source.readString();
		contentMarkdown = source.readString();
		permalinkNavigationId = source.readString();
		permalink = source.readString();
		status = source.readString();
		createdAt = source.readString();
		modifiedAt = source.readString();
		creatorUserId = source.readString();
		modifierUserId = source.readString();
		bylineUserId = source.readString();
		publisher = source.readString();
		seoTitle = source.readString();
		syndicate = source.readInt();
		mobileOnly = source.readInt();
		
		//read parcelable array
		Parcelable[] parcelableArray = source.readParcelableArray(Image.class.getClassLoader());
		if (parcelableArray != null) {
			images = Arrays.copyOf(parcelableArray, parcelableArray.length, Image[].class);
		}
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(guid);
		dest.writeString(title);
		dest.writeString(publishDate);
		dest.writeString(shortHeadline);
		dest.writeString(subHeadline);
		dest.writeString(description);
		dest.writeString(contentHTML);
		dest.writeString(contentMarkdown);
		dest.writeString(permalinkNavigationId);
		dest.writeString(permalink);
		dest.writeString(status);
		dest.writeString(createdAt);
		dest.writeString(modifiedAt);
		dest.writeString(creatorUserId);
		dest.writeString(modifierUserId);
		dest.writeString(bylineUserId);
		dest.writeString(publisher);
		dest.writeString(seoTitle);
		dest.writeInt(syndicate);
		dest.writeInt(mobileOnly);
		dest.writeParcelableArray(images, flags);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	public String getShortHeadline() {
		return shortHeadline;
	}
	public void setShortHeadline(String shortHeadline) {
		this.shortHeadline = shortHeadline;
	}
	public String getSubHeadline() {
		return subHeadline;
	}
	public void setSubHeadline(String subHeadline) {
		this.subHeadline = subHeadline;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getContentHTML() {
		return contentHTML;
	}
	public void setContentHTML(String contentHTML) {
		this.contentHTML = contentHTML;
	}
	public String getContentMarkdown() {
		return contentMarkdown;
	}
	public void setContentMarkdown(String contentMarkdown) {
		this.contentMarkdown = contentMarkdown;
	}
	public String getPermalinkNavigationId() {
		return permalinkNavigationId;
	}
	public void setPermalinkNavigationId(String permalinkNavigationId) {
		this.permalinkNavigationId = permalinkNavigationId;
	}
	public String getPermalink() {
		return permalink;
	}
	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getModifiedAt() {
		return modifiedAt;
	}
	public void setModifiedAt(String modifiedAt) {
		this.modifiedAt = modifiedAt;
	}
	public String getCreatorUserId() {
		return creatorUserId;
	}
	public void setCreatorUserId(String creatorUserId) {
		this.creatorUserId = creatorUserId;
	}
	public String getModifierUserId() {
		return modifierUserId;
	}
	public void setModifierUserId(String modifierUserId) {
		this.modifierUserId = modifierUserId;
	}
	public String getBylineUserId() {
		return bylineUserId;
	}
	public void setBylineUserId(String bylineUserId) {
		this.bylineUserId = bylineUserId;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getSeoTitle() {
		return seoTitle;
	}
	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}
	public Integer getSyndicate() {
		return syndicate;
	}
	public void setSyndicate(Integer syndicate) {
		this.syndicate = syndicate;
	}
	public Integer getMobileOnly() {
		return mobileOnly;
	}
	public void setMobileOnly(Integer mobileOnly) {
		this.mobileOnly = mobileOnly;
	}
   
	public Image[] getImages() {
		return images;
	}

	public void setImages(Image[] images) {
		this.images = images;
	}

}
