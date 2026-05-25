package com.dev10.models.docelements.implementations;

import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.Element;
import com.fasterxml.jackson.annotation.JsonProperty;


public class InterfaceBox extends Element {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Attribute position;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Attribute width;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Attribute height;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Attribute topText;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Attribute bottomText;

}
