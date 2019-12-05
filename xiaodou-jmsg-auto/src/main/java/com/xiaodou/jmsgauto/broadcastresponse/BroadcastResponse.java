package com.xiaodou.jmsgauto.broadcastresponse;

import java.util.ArrayList;
import java.util.List;

public class BroadcastResponse {
	private List<BroadcastResponseItem> broadcastResponseItemList; 
	private boolean error;
	private boolean failed;
	
	public BroadcastResponse(){
		this.error = false;
		this.failed = false;
		broadcastResponseItemList = new ArrayList<BroadcastResponseItem>();
	}

	public List<BroadcastResponseItem> getBroadcastResponseItemList() {
		return broadcastResponseItemList;
	}

	public void setBroadcastResponseItemList(
			List<BroadcastResponseItem> broadcastResponseItemList) {
		this.broadcastResponseItemList = broadcastResponseItemList;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public boolean isFailed() {
		return failed;
	}

	public void setFailed(boolean failed) {
		this.failed = failed;
	}
	
	public boolean isFailedOrError(){
        if (this.error == true || this.failed == true)
        {
            return true;
        }else
        {
            return false;
        }
	}
	
	public void addResponseItem(BroadcastResponseItem item){
		this.broadcastResponseItemList.add(item);
	}
}
