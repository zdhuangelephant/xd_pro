package com.xiaodou.course.web.response.product;

import java.util.List;

import com.xiaodou.course.vo.product.ProductCategory;
import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.course.web.response.resultType.ProductResType;
import com.xiaodou.summer.vo.out.ResultType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProductCategoryListResponse extends BaseResponse {

	public ProductCategoryListResponse(ResultType type) {
		super(type);
	}

	public ProductCategoryListResponse(ProductResType productResType) {
		super(productResType);
	}

	public List<RegionVO> getRegionList() {
		return regionList;
	}

	public void setRegionList(List<RegionVO> regionList) {
		this.regionList = regionList;
	}

	public List<ProductCategory> getList() {
		return list;
	}

	public void setList(List<ProductCategory> list) {
		this.list = list;
	}

	private List<ProductCategory> list;

	private List<RegionVO> regionList;

	@Data
	public static class RegionVO {

		private String region;
		private String regionName;
		private List<Major> majorList;

		public String getRegion() {
			return region;
		}

		public void setRegion(String region) {
			this.region = region;
		}

		public String getRegionName() {
			return regionName;
		}

		public void setRegionName(String regionName) {
			this.regionName = regionName;
		}

		public List<Major> getMajorList() {
			return majorList;
		}

		public void setMajorList(List<Major> majorList) {
			this.majorList = majorList;
		}

		public RegionVO() {
		}

		public RegionVO(String region, String regionName, List<Major> majorList) {
			super();
			this.region = region;
			this.regionName = regionName;
			this.majorList = majorList;
		}

		@Data
		public static class Major {
			private String majorId;
			private String typeCode;
			private String majorName;
			private String showCover;
			private String chiefAcademy;

			public String getMajorId() {
				return majorId;
			}

			public void setMajorId(String majorId) {
				this.majorId = majorId;
			}

			public String getTypeCode() {
				return typeCode;
			}

			public void setTypeCode(String typeCode) {
				this.typeCode = typeCode;
			}

			public String getMajorName() {
				return majorName;
			}

			public void setMajorName(String majorName) {
				this.majorName = majorName;
			}

			public String getShowCover() {
				return showCover;
			}

			public void setShowCover(String showCover) {
				this.showCover = showCover;
			}

			public String getChiefAcademy() {
				return chiefAcademy;
			}

			public void setChiefAcademy(String chiefAcademy) {
				this.chiefAcademy = chiefAcademy;
			}

			public Major() {
			}

			public Major(String majorId, String typeCode, String majorName,
					String showCover, String chiefAcademy) {
				super();
				this.majorId = majorId;
				this.typeCode = typeCode;
				this.majorName = majorName;
				this.showCover = showCover;
				this.chiefAcademy = chiefAcademy;
			}
		}
	}

}
