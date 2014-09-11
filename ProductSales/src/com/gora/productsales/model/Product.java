package com.gora.productsales.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable{
	private int id = -1;
	private String name = "", description = "", regularPrice = "", salePrice = "";
	private String productPhoto = ""; 
	private String colors = "";
	private String stores = "";
	
	public Product(){
	}

	public Product(Parcel in) {
		readFromParcel(in);
	}

	private void readFromParcel(Parcel in) {
		id = in.readInt();
		name = in.readString();
		description = in.readString();
		regularPrice= in.readString();
		salePrice= in.readString();
		productPhoto = in.readString();
		colors = in.readString();
		stores = in.readString();
	}
	
	public int getId() {
		return id;
	}
	public Product setId(int id) {
		this.id = id;
		return this;
	}
	public String getName() {
		return name;
	}
	public Product setName(String name) {
		this.name = name;
		return this;
	}
	public String getDescription() {
		return description;
	}
	public Product setDescription(String description) {
		this.description = description;
		return this;
	}
	public String getRegularPrice() {
		return regularPrice;
	}
	public Product setRegularPrice(String regularPrice) {
		this.regularPrice = regularPrice;
		return this;
	}
	public String getSalePrice() {
		return salePrice;
	}
	public Product setSalePrice(String salePrice) {
		this.salePrice = salePrice;
		return this;
	}
	public String getProductPhoto() {
		return productPhoto;
	}
	public Product setProductPhoto(String productPhoto) {
		this.productPhoto = productPhoto;
		return this;
	}
	public String getColors() {
		return colors;
	}
	public Product setColors(String colors) {
		this.colors = colors;
		return this;
	}
	public String getStores() {
		return stores;
	}
	public Product setStores(String stores) {
		this.stores = stores;
		return this;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeString(description);
		dest.writeString(regularPrice);
		dest.writeString(salePrice);
		dest.writeString(productPhoto);
		dest.writeString(colors);
		dest.writeString(stores);
	}

	public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
		public Product createFromParcel(Parcel in) {
			return new Product(in);
		}

		public Product[] newArray(int size) {
			return new Product[size];
		}
	};

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}	
}
