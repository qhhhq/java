package net.qhhhq.model.shop;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

public class ShopType implements Serializable {
    /**
	 *
	 */
	private static final long serialVersionUID = 3336941520961708446L;

	@JSONField(serializeUsing = ToStringSerializer.class)
	private Long id;

    private String name;

    private Boolean valid = true;

    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }
}