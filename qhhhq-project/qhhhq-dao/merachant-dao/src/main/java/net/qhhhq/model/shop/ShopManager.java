package net.qhhhq.model.shop;

import java.io.Serializable;
import java.util.Date;

public class ShopManager implements Serializable {
    /**
	 *
	 */
	private static final long serialVersionUID = -9158774981609815861L;

	private Long id;

    private Long shopId;

    private Long userId;

    private String status = "1";

    private Date createDate = new Date();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}