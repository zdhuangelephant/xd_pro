package com.xiaodou.st.dashboard.domain.score;

import lombok.Data;

@Data
public class ImportScoreDTO {
	/* 姓名 */
	public String studentName;
	/* 准考证号 */
	public String admissionCardCode;
	/* 专业代码 */
	public String catCode;
	/* 专业名称 */
	public String catName;
	/* 课程代码 */
	public String productCode;
	/* 课程名称 */
	public String productName;
	/*线上成绩*/
	public String discountScore;
	/* 导入的线下成绩 */
	public String dailyScore;

	public String msg;
}
