package com.xiaodou.resources.timing;
import java.util.List;

import javax.annotation.Resource;

import com.xiaodou.esagent.enums.CommonEnum.DumpMethod;
import com.xiaodou.esagent.model.DumpType;
import com.xiaodou.esagent.util.FileUtil;
import com.xiaodou.resources.dao.search.ResourcesDao;
import com.xiaodou.resources.vo.search.ResourcesForEs;

public class ResourcesScheduledTask {  
	@Resource
	static
	ResourcesDao resourcesDao;
    private static final DumpType ResourcesForEs = new ResourcesForEs();
	public static void excute(){
    	FileUtil<ResourcesForEs> util=new FileUtil<ResourcesForEs>(ResourcesForEs.getClass().getSimpleName());
    	List<ResourcesForEs> list=util.findFile();
    	for(ResourcesForEs resources:list){
    		if(resources.getMethod().equals(DumpMethod.ADD)){
    			resourcesDao.addORUpdata( resources.getId(),resources);
    		}else if(resources.getMethod().equals(DumpMethod.DEL)){
    			resourcesDao.del(resources.getId());
    		}
    	}
    }
}  