package com.dev10.models.docelements.implementations;

import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.DocumentElement;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Note extends DocumentElement {
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
        String jsonFormattedValue = Attribute.formatAsJson("innerText", value);
        this.innerText.setValue(jsonFormattedValue);
    }

    public void editOrder(Integer value){
        String jsonFormattedValue = Attribute.formatAsJson("order", value);
        this.order.setValue(jsonFormattedValue);
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
