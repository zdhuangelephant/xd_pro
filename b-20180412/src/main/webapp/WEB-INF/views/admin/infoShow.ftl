<#include "/common/layout.ftl" />
<@htmlHead title="登陆页">
<script type="text/javascript" src="${JS_PATH}validform/validform.js"></script>
</@htmlHead>
<@htmlBody bodyclass="page-content">

			<div class="page-content">

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->

								<div>
									<div id="user-profile-2" class="user-profile row">
										<div class="tabbable">
											<ul class="nav nav-tabs padding-18">
												<li class="active">
													<a data-toggle="tab" href="#home">
														<i class="green icon-user bigger-120"></i>
														基本信息
													</a>
												</li>

												<li>
													<a data-toggle="tab" href="#feed">
														<i class="orange icon-rss bigger-120"></i>
														动作信息
													</a>
												</li>

											</ul>

											<div class="tab-content no-border padding-24">
												<div id="home" class="tab-pane in active">
													<div class="row">
														<div class="col-xs-12 col-sm-3 center">
															<span class="profile-picture">
																<img class="editable img-responsive" alt="Alex's Avatar" id="avatar2" src="http://7xigj3.com1.z0.glb.clouddn.com/01785E69-C81F-4F18-8B57-07BB139B31A0" />
															</span>

															<div class="space space-4"></div>

															<a href="/admin/editInfo" class="btn btn-sm btn-block btn-success">
																<i class="icon-edit-sign bigger-120"></i>
																<span class="bigger-110">修改信息</span>
															</a>

															<a href="/admin/changePassword" class="btn btn-sm btn-block btn-primary">
																<i class="icon-eye-close bigger-110"></i>
																<span class="bigger-110">修改密码</span>
															</a>
														</div><!-- /span -->

														<div class="col-xs-12 col-sm-9">
															<h4 class="blue">
																<span class="middle">${admin.userName}</span>

																<span class="label label-purple arrowed-in-right">
																	<i class="icon-circle smaller-80 align-middle"></i>
																	online
																</span>
															</h4>

															<div class="profile-user-info">
																<div class="profile-info-row">
																	<div class="profile-info-name"> 姓名 </div>

																	<div class="profile-info-value">
																		<#if editInfo??>${admin.realName}<#else >${editInfo.realName}</#if>
																	</div>
																</div>

																<div class="profile-info-row">
																	<div class="profile-info-name"> 电子邮箱 </div>

																	<div class="profile-info-value">
																		<span><#if editInfo??>${admin.email}<#else >${editInfo.email}</#if></span>
																	</div>
																</div>
																
																<div class="profile-info-row">
																	<div class="profile-info-name"> 手机号 </div>

																	<div class="profile-info-value">
																		<span><#if editInfo??>${admin.telphone}<#else >${editInfo.telphone}</#if></span>
																	</div>
																</div>
																<div class="profile-info-row">
																	<div class="profile-info-name"> 学习机构 </div>

																	<div class="profile-info-value">
																		<span><#if editInfo??>${admin.merchant}<#else >${editInfo.merchant}</#if></span>
																	</div>
																</div>
																<div class="profile-info-row">
																	<div class="profile-info-name"> 加入时间 </div>

																	<div class="profile-info-value">
																		<span>20/06/2010</span>
																	</div>
																</div>

																<div class="profile-info-row">
																	<div class="profile-info-name"> 最近登录 </div>

																	<div class="profile-info-value">
																		<span>${admin.lastLoginTime}</span>
																	</div>
																</div>
															</div>

															<div class="hr hr-8 dotted"></div>

															<div class="profile-user-info">


															</div>
														</div><!-- /span -->
													</div><!-- /row-fluid -->

												</div><!-- #home -->

												<div id="feed" class="tab-pane">
													<div class="profile-feed row-fluid">
														<div class="span6">

															<div class="profile-activity clearfix">
																<div>
																	<i class="pull-left thumbicon icon-ok btn-success no-hover"></i>
																	<a class="user" href="#"> Alex Doe </a>
																	joined
																	<a href="#">Country Music</a>

																	group.
																	<div class="time">
																		<i class="icon-time bigger-110"></i>
																		5 hours ago
																	</div>
																</div>

																<div class="tools action-buttons">
																	<a href="#" class="blue">
																		<i class="icon-pencil bigger-125"></i>
																	</a>

																	<a href="#" class="red">
																		<i class="icon-remove bigger-125"></i>
																	</a>
																</div>
															</div>

															<div class="profile-activity clearfix">
																<div>
																	<i class="pull-left thumbicon icon-picture btn-info no-hover"></i>
																	<a class="user" href="#"> Alex Doe </a>
																	uploaded a new photo.
																	<a href="#">Take a look</a>

																	<div class="time">
																		<i class="icon-time bigger-110"></i>
																		5 hours ago
																	</div>
																</div>

																<div class="tools action-buttons">
																	<a href="#" class="blue">
																		<i class="icon-pencil bigger-125"></i>
																	</a>

																	<a href="#" class="red">
																		<i class="icon-remove bigger-125"></i>
																	</a>
																</div>
															</div>
														</div><!-- /span -->

														<div class="span6">
															<div class="profile-activity clearfix">
																<div>
																	<i class="pull-left thumbicon icon-edit btn-pink no-hover"></i>
																	<a class="user" href="#"> Alex Doe </a>
																	published a new blog post.
																	<a href="#">Read now</a>

																	<div class="time">
																		<i class="icon-time bigger-110"></i>
																		11 hours ago
																	</div>
																</div>

																<div class="tools action-buttons">
																	<a href="#" class="blue">
																		<i class="icon-pencil bigger-125"></i>
																	</a>

																	<a href="#" class="red">
																		<i class="icon-remove bigger-125"></i>
																	</a>
																</div>
															</div>

															<div class="profile-activity clearfix">
																<div>
																	<i class="pull-left thumbicon icon-key btn-info no-hover"></i>
																	<a class="user" href="#"> Alex Doe </a>

																	logged in.
																	<div class="time">
																		<i class="icon-time bigger-110"></i>
																		12 hours ago
																	</div>
																</div>

																<div class="tools action-buttons">
																	<a href="#" class="blue">
																		<i class="icon-pencil bigger-125"></i>
																	</a>

																	<a href="#" class="red">
																		<i class="icon-remove bigger-125"></i>
																	</a>
																</div>
															</div>

															<div class="profile-activity clearfix">
																<div>
																	<i class="pull-left thumbicon icon-off btn-inverse no-hover"></i>
																	<a class="user" href="#"> Alex Doe </a>

																	logged out.
																	<div class="time">
																		<i class="icon-time bigger-110"></i>
																		16 hours ago
																	</div>
																</div>

																<div class="tools action-buttons">
																	<a href="#" class="blue">
																		<i class="icon-pencil bigger-125"></i>
																	</a>

																	<a href="#" class="red">
																		<i class="icon-remove bigger-125"></i>
																	</a>
																</div>
															</div>

															<div class="profile-activity clearfix">
																<div>
																	<i class="pull-left thumbicon icon-key btn-info no-hover"></i>
																	<a class="user" href="#"> Alex Doe </a>

																	logged in.
																	<div class="time">
																		<i class="icon-time bigger-110"></i>
																		16 hours ago
																	</div>
																</div>

																<div class="tools action-buttons">
																	<a href="#" class="blue">
																		<i class="icon-pencil bigger-125"></i>
																	</a>

																	<a href="#" class="red">
																		<i class="icon-remove bigger-125"></i>
																	</a>
																</div>
															</div>
														</div><!-- /span -->
													</div><!-- /row -->

													<div class="space-12"></div>

													<div class="center">
														<a href="#" class="btn btn-small btn-primary">
															<i class="icon-rss bigger-150 middle"></i>

															View more activities
															<i class="icon-on-right icon-arrow-right"></i>
														</a>
													</div>
												</div><!-- /#feed -->
											</div>
										</div>
									</div>
								</div>

								<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div>

</@htmlBody>

