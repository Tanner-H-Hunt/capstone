package com.dev10.models.docelements.implementations;

import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.DocumentElement;
import com.dev10.repositories.DocumentElementRepository;
import org.springframework.stereotype.Component;


public class InterfaceBox extends DocumentElement {
    private Attribute position;
    private Attribute width;
    private Attribute height;
    private Attribute topText;
    private Attribute bottomText;

}
