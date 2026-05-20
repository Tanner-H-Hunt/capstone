package com.dev10.models.docelements.implementations;

import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.DocumentElement;
import com.dev10.repositories.DocumentElementRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;


public class InterfaceBox extends DocumentElement {
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
