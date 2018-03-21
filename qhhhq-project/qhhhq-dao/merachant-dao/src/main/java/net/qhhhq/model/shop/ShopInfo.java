package net.qhhhq.model.shop;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

public class ShopInfo implements Serializable {
    /**
	 *
	 */
	private static final long serialVersionUID = 4871766098067133458L;

	@JSONField(serializeUsing = ToStringSerializer.class)
	private Long id;

	@JSONField(serializeUsing = ToStringSerializer.class)
    private Long enterpriseId;

	@JSONField(serializeUsing = ToStringSerializer.class)
    private Long type;

    private String name;

    private String province;

    private String city;

    private String address;

    private String district;

    private Integer rating = 5;

    private String status = "1";

    private String statusDesc;

    private Date createDate = new Date();

    private String createUser;

    private String manager;

    private String contactPhone;

    private String contactName;

    private String contactEmail;

	@JSONField(serializeUsing = ToStringSerializer.class)
    private Double longitude;

	@JSONField(serializeUsing = ToStringSerializer.class)
    private Double dimension;

    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long class1;

    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long class2;


    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long class3;


    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long class4;


    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long class5;


    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long class6;


    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long sortNum = 1l;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc == null ? null : statusDesc.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager == null ? null : manager.trim();
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail == null ? null : contactEmail.trim();
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getDimension() {
        return dimension;
    }

    public void setDimension(Double dimension) {
        this.dimension = dimension;
    }

    public Long getClass1() {
        return class1;
    }

    public void setClass1(Long class1) {
        this.class1 = class1;
    }

    public Long getClass2() {
        return class2;
    }

    public void setClass2(Long class2) {
        this.class2 = class2;
    }

    public Long getClass3() {
        return class3;
    }

    public void setClass3(Long class3) {
        this.class3 = class3;
    }

    public Long getClass4() {
        return class4;
    }

    public void setClass4(Long class4) {
        this.class4 = class4;
    }

    public Long getClass5() {
        return class5;
    }

    public void setClass5(Long class5) {
        this.class5 = class5;
    }

    public Long getClass6() {
        return class6;
    }

    public void setClass6(Long class6) {
        this.class6 = class6;
    }

    public Long getSortNum() {
        return sortNum;
    }

    public void setSortNum(Long sortNum) {
        this.sortNum = sortNum;
    }
}