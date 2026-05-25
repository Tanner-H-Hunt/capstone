package com.dev10.models.docelements.implementations;

import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.Element;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TodoGroup extends Element {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Attribute column;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Attribute row;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Attribute name;


}
