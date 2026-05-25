package com.dev10.models.docelements.implementations;

import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.Element;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClassBox extends Element {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Attribute position;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Attribute width;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Attribute height;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Attribute topText;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Attribute middleText;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Attribute bottomText;

}
