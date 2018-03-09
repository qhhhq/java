package net.qhhhq.model.shop;

import java.io.Serializable;

public class ShopSetting implements Serializable {
    /**
	 *
	 */
	private static final long serialVersionUID = 4445677638538171051L;

	private Long id;

    private Long shopId;

    private Boolean open;

    private String openDesc;

    private String startTime;

    private String endTime;

    private Boolean share;

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

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public String getOpenDesc() {
        return openDesc;
    }

    public void setOpenDesc(String openDesc) {
        this.openDesc = openDesc == null ? null : openDesc.trim();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public Boolean getShare() {
        return share;
    }

    public void setShare(Boolean share) {
        this.share = share;
    }
}