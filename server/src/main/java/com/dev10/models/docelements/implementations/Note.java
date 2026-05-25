package com.dev10.models.docelements.implementations;

import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.Element;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Note extends Element {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Attribute innerText;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Attribute order;

    public Attribute getInnerText() {
        return innerText;
    }

    public Attribute getOrder() {
        return order;
    }

    public void editInnerText(String value){
        this.innerText.setKey("innerText");
        this.innerText.setValue(value);
    }

    public void editOrder(Integer value){
        this.order.setKey("order");
        this.order.setValue(value.toString());
    }

    public void setInnerText(Attribute innerText) {
        this.innerText = innerText;
    }

    public void setOrder(Attribute order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
