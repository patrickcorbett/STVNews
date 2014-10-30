package com.pcorbett.stvnews.structures;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {
	
	private String id;
	private String caption;
	private String description;
	private String dataUri = "";
	private Bitmap image = null;
	
	public static final Parcelable.Creator<Image> CREATOR = new Creator<Image>(){
		@Override
		public Image createFromParcel(Parcel source) {
			return new Image(source);
		}

		@Override
		public Image[] newArray(int size) {
			return new Image[size];
		}
	};
	
	public Image(Parcel source) {
		id = source.readString();
		caption = source.readString();
		description = source.readString();
		dataUri = source.readString();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(caption);
		dest.writeString(description);
		dest.writeString(dataUri);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDataUri() {
		//Format the base64 uri for use on android, remove the data:image/jpeg;base64, prefix
		if(dataUri != null){
			int lastComma = dataUri.lastIndexOf(',');
			if(lastComma != -1){	
				return dataUri.substring(++lastComma).trim();
			}
		}
		return "";
	}

	public void setDataUri(String dataUri) {
		this.dataUri = dataUri;
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}
}
