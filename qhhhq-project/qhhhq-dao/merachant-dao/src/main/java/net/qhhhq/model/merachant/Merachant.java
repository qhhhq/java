package net.qhhhq.model.merachant;

import java.io.Serializable;
import java.util.Date;

public class Merachant implements Serializable {
    /**
	 *
	 */
	private static final long serialVersionUID = 7602442544747288735L;

	private Integer id;

    private Integer type;

    private String name;

    private String province;

    private String city;

    private Integer rating = 0;

    private String status;

    private String statusDesc;

    private Date createDate = new Date();

    private String createUser;

    private String legalRep;

    private String repDocId;

    private String repPhone;

    private String contactPhone;

    private String contactName;

    private String contactEmail;

    private Double longitude;

    private Double dimension;

    private Integer class1;

    private Integer class2;

    private Integer class3;

    private Integer class4;

    private Integer class5;

    private Integer class6;

    private String businessTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
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

    public String getLegalRep() {
        return legalRep;
    }

    public void setLegalRep(String legalRep) {
        this.legalRep = legalRep == null ? null : legalRep.trim();
    }

    public String getRepDocId() {
        return repDocId;
    }

    public void setRepDocId(String repDocId) {
        this.repDocId = repDocId == null ? null : repDocId.trim();
    }

    public String getRepPhone() {
        return repPhone;
    }

    public void setRepPhone(String repPhone) {
        this.repPhone = repPhone == null ? null : repPhone.trim();
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

    public Integer getClass1() {
        return class1;
    }

    public void setClass1(Integer class1) {
        this.class1 = class1;
    }

    public Integer getClass2() {
        return class2;
    }

    public void setClass2(Integer class2) {
        this.class2 = class2;
    }

    public Integer getClass3() {
        return class3;
    }

    public void setClass3(Integer class3) {
        this.class3 = class3;
    }

    public Integer getClass4() {
        return class4;
    }

    public void setClass4(Integer class4) {
        this.class4 = class4;
    }

    public Integer getClass5() {
        return class5;
    }

    public void setClass5(Integer class5) {
        this.class5 = class5;
    }

    public Integer getClass6() {
        return class6;
    }

    public void setClass6(Integer class6) {
        this.class6 = class6;
    }

    public String getBusinessTime() {
        return businessTime;
    }

    public void setBusinessTime(String businessTime) {
        this.businessTime = businessTime == null ? null : businessTime.trim();
    }
}