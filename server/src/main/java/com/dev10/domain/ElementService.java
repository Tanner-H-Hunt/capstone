package com.dev10.domain;

import com.dev10.models.DTO.Result;
import com.dev10.models.DataAccessException;
import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.Element;
import com.dev10.repositories.ElementRepository;
import com.dev10.repositories.DocumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElementService {

    private final DocumentRepository documentRepository;
    private final ElementRepository elementRepository;
    private final AttributeConfigurationService attributeConfiguration;

    public ElementService(DocumentRepository documentRepository,
                          ElementRepository elementRepository,
                          AttributeConfigurationService configuration) {
        this.documentRepository = documentRepository;
        this.elementRepository = elementRepository;
        this.attributeConfiguration = configuration;
    }


    public Element getElementById(int id) throws DataAccessException {
        return elementRepository.getElementById(id);
    }

    public Attribute getAttributeById(int id) throws DataAccessException {
        return elementRepository.getAttributeById(id);
    }

    public Result<Element> create(Element element) throws DataAccessException {
        Result<Element> result = new Result<>();
        if(element == null){
            result.addErrorMessage("Element cannot be null");
            return result;
        }

        if(element.getElementId() != 0){
            result.addErrorMessage("Cannot preemptively set the documents id");
        }

        if(element.getElementType() == null){
            result.addErrorMessage("Must specify the document elements type");
        }

        if(!element.getAttributes().isEmpty()){
            result.addErrorMessage("Cannot preemptively set the attributes for a newly created element (safe values will be auto-generated)");
        }

        if(documentRepository.getDocumentById(element.getDocumentId()) == null){
            result.addErrorMessage("Element must reference a valid document");
        }

        if(result.isSuccess()){
            Element createResult = elementRepository.createElement(element);
            Element elementWithAttributes = attributeConfiguration.initAttributes(createResult);
            result.setPayload(elementWithAttributes);
        }

        return result;
    }

    public Result<Element> updateElement(Element element) throws DataAccessException {
        Result<Element> result = new Result<>();
        if(element == null){
            result.addErrorMessage("element body is required");
            return result;
        }

        if(element.getAttributes() == null){
            result.addErrorMessage("Attributes are required");
            return result;
        }

        for(Attribute attribute : element.getAttributes()){
            Attribute attributeInDatabase = elementRepository.getAttributeById(attribute.getAttributeId());

            // make sure the attribute exists in the database
            if(attributeInDatabase == null){
                result.addErrorMessage("Cannot modify a non-existent attribute");
                return result;
            }

            // make sure the attribute actually references the element in question
            if(attributeInDatabase.getElementId() != attribute.getElementId()){
                result.addErrorMessage("Cannot modify the attributes of an element in another document");
            }

            // make sure they aren't trying to change the attribute type
            if(!attributeInDatabase.getKey().equals(attribute.getKey())){
                result.addErrorMessage("Cannot change attribute type "
                        + attributeInDatabase.getKey()
                        + " != " + attribute.getKey());
            }

        }

        if(result.isSuccess()){
            boolean updateSuccess = true;
            for(Attribute attribute : element.getAttributes()){
                updateSuccess = updateSuccess && elementRepository.editAttribute(attribute);
            }

            if(updateSuccess){
                result.setPayload(element);
            } else{
                result.addErrorMessage("Could not update an attribute in the database");
            }
        }

        return result;
    }

    public int delete(int id) throws DataAccessException {
        return elementRepository.deleteElement(id);
    }

    public List<Element> getElementsForDocument(int docId) throws DataAccessException{
        // fetch the elements for the document
        List<Element> elements = elementRepository.getElementsForDocument(docId);

        // fetch the attributes for each element
        for(Element element : elements){
            List<Attribute> attributes = elementRepository.getAttributesForElement(element.getElementId());
            element.setAttributes(attributes);
        }

        return elements;
    }
}
