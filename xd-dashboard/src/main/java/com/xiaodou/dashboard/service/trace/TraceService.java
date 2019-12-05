package com.xiaodou.dashboard.service.trace;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.dashboard.model.trace.TraceModel;
import com.xiaodou.dashboard.service.facade.LogServiceFacade;

@Service("traceService")
public class TraceService {
  @Resource
  LogServiceFacade logServiceFacade;

  public List<TraceModel> getTraceList(String traceId){
	  List<TraceModel> oldTraceModelList = logServiceFacade.getTraceModelListById(traceId);
/*	  TraceModel startTraceModel=this.getStartModel(oldTraceModelList);
	  LinkedList <TraceModel> newTraceModelList=new LinkedList<>();*/
	 /* List<Tree<TraceModel>> trees = new ArrayList<Tree<TraceModel>>();
	  for (TraceModel traceModel : oldTraceModelList) {
        Tree<TraceModel> tree = new Tree<TraceModel>();
        tree.setMyId(traceModel.getMyId());
        tree.setParentId(traceModel.getParentId());
        tree.setTraceModel(traceModel);
        tree.setName(traceModel.getExcutePoint());
        trees.add(tree);
	  }
      Tree<TraceModel> t = BuildTree.build(trees);
      System.out.print(t);*/
/*	  if(startTraceModel!=null){
		  this.packageNewTraceList(newTraceModelList, oldTraceModelList, startTraceModel);
	  }*/
	  for(TraceModel traceModel:oldTraceModelList){
		  if(traceModel.getErrorMsg().equals("null\n")){
			  traceModel.setErrorMsg("null");
		  }
	  }
	  return oldTraceModelList;
  }
  public  TraceModel getStartModel(List<TraceModel> oldTraceModelList){
	  for(TraceModel traceModel:oldTraceModelList){
		  if(traceModel.getParentId().equals("null")){
			  return traceModel;
		  }
	  }
	return null;
  }
  
  //递归排序
  public void packageNewTraceList(LinkedList <TraceModel> newTraceModelList,List<TraceModel> oldTraceModelList,TraceModel parentTraceModel){
	  oldTraceModelList.remove(parentTraceModel);
	  newTraceModelList.add(parentTraceModel);
	  if(oldTraceModelList.size()==0){
		  return;
	  }
	  TraceModel currentModel = null;
	  for(TraceModel traceModel:oldTraceModelList){
		  if(traceModel.getParentId()!=null&&traceModel.getMyId()!=null&&traceModel.getParentId().equals(parentTraceModel.getMyId())){	  
			  currentModel = traceModel;
			  break;
		  }
	  }
	  if(currentModel!=null){
		  this.packageNewTraceList(newTraceModelList,oldTraceModelList,currentModel);
	  }
  }
}
