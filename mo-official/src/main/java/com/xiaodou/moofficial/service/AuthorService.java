package com.xiaodou.moofficial.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.moofficial.dao.AuthorDao;
import com.xiaodou.moofficial.entity.AuthorModel;

@Service("authorService")
public class AuthorService {
	
	@Resource
	AuthorDao authorDao;
	
	public AuthorModel findAuthorById(Long authorId) {
		AuthorModel model = new AuthorModel();
		model.setId(authorId);
		return authorDao.findEntityById(model);
	}
	//@Resource
}
