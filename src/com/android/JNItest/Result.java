package com.android.JNItest;

import android.os.Parcel;
import android.os.Parcelable;

public class Result implements Parcelable {
	
	double x, y, res;
	String operator;
		
	public Result(){
	}
	
	public Result(String oper, double x, double y, double res){
		setOperator(oper);
		setX(x);
		setY(y);
		setRes(res);		
	}
	
	private Result(Parcel in) {
		operator = in.readString();
		x=in.readDouble();
		y=in.readDouble();
		res=in.readDouble();
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(operator);
		dest.writeDouble(x);
		dest.writeDouble(y);
		dest.writeDouble(res);
	}
	
	
	public static final Parcelable.Creator<Result> CREATOR   = new Parcelable.Creator<Result>() {
		public Result createFromParcel(Parcel in) {
			return new Result(in);
		}
	
		public Result[] newArray(int size) {
		    return new Result[size];
		}
	};
	
	

	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * @return the res
	 */
	public double getRes() {
		return res;
	}

	/**
	 * @param res the res to set
	 */
	public void setRes(double res) {
		this.res = res;
	}

	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public String getCompleteOperation(){
		return x+" "+operator+" "+y;
	}
	
	public String getCompleteResult(){
		return "Resultado: "+res;
	}
	
}
