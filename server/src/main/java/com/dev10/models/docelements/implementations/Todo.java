package com.dev10.models.docelements.implementations;

import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.Element;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Todo extends Element {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Attribute parentTodoGroup;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Attribute name;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Attribute order;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Attribute complete;
}
