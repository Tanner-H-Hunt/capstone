package com.dev10.models.docelements.implementations;

import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.Element;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Arrow extends Element {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Attribute startXPosition;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Attribute startYPosition;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Attribute endXPosition;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Attribute endYPosition;

}
