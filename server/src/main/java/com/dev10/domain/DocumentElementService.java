package com.dev10.domain;

import com.dev10.models.DTO.Result;
import com.dev10.models.DataAccessException;
import com.dev10.models.User;
import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.DocumentElement;
import com.dev10.models.docelements.implementations.AttributeConfiguration;
import com.dev10.repositories.DocumentElementRepository;
import com.dev10.repositories.DocumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentElementService {

    private final DocumentRepository documentRepository;
    private final DocumentElementRepository documentElementRepository;
    private final AttributeConfiguration attributeConfiguration;

    public DocumentElementService(DocumentRepository documentRepository,
                                  DocumentElementRepository documentElementRepository,
                                  AttributeConfiguration configuration) {
        this.documentRepository = documentRepository;
        this.documentElementRepository = documentElementRepository;
        this.attributeConfiguration = configuration;
    }


    public DocumentElement getElementById(int id) throws DataAccessException {
        return documentElementRepository.getElementById(id);
    }

    public Attribute getAttributeById(int id) throws DataAccessException {
        return documentElementRepository.getAttributeById(id);
    }

    public Result<DocumentElement> create(DocumentElement element) throws DataAccessException {
        Result<DocumentElement> result = new Result<>();
        if(element == null){
            result.addErrorMessage("Element cannot be null");
            return result;
        }

        if(element.getDocumentElementId() != 0){
            result.addErrorMessage("Cannot preemptively set the documents id");
        }

        if(element.getDocumentElementType() == null){
            result.addErrorMessage("Must specify the document elements type");
        }

        if(!element.getAttributes().isEmpty()){
            result.addErrorMessage("Cannot preemptively set the attributes for a newly created element (safe values will be auto-generated)");
        }

        if(documentRepository.getDocumentById(element.getDocumentId()) == null){
            result.addErrorMessage("Element must reference a valid document");
        }

        if(result.isSuccess()){
            DocumentElement createResult = documentElementRepository.createElement(element);
            DocumentElement elementWithAttributes = attributeConfiguration.initAttributes(createResult);
            result.setPayload(elementWithAttributes);
        }

        return result;
    }

    public Result<DocumentElement> updateElement(DocumentElement element) throws DataAccessException {
        Result<DocumentElement> result = new Result<>();
        if(element == null){
            result.addErrorMessage("element body is required");
            return result;
        }

        if(element.getAttributes() == null){
            result.addErrorMessage("Attributes are required");
            return result;
        }

        for(Attribute attribute : element.getAttributes()){
            Attribute attributeInDatabase = documentElementRepository.getAttributeById(attribute.getAttributeId());

            // make sure the attribute exists in the database
            if(attributeInDatabase == null){
                result.addErrorMessage("Cannot modify a non-existent attribute");
                return result;
            }

            // make sure the attribute actually references the element in question
            if(attributeInDatabase.getDocumentElementId() != attribute.getDocumentElementId()){
                result.addErrorMessage("Cannot modify the attributes of an element in another document");
            }

            // make sure they aren't trying to change the attribute type
            String[] attributeValues = attribute.getValue().split(":");
            String[] databaseValues = attributeInDatabase.getValue().split(":");
            if(!attributeValues[0].equals(databaseValues[0])){
                result.addErrorMessage("Cannot change attribute type " + attributeValues[0] + " != " + databaseValues[0]);
            }

        }

        if(result.isSuccess()){
            boolean updateSuccess = true;
            for(Attribute attribute : element.getAttributes()){
                updateSuccess = updateSuccess && documentElementRepository.editAttribute(attribute);
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
        return documentElementRepository.deleteElement(id);
    }

    public List<DocumentElement> getElementsForDocument(int docId) throws DataAccessException{
        // fetch the elements for the document
        List<DocumentElement> elements = documentElementRepository.getElementsForDocument(docId);

        // fetch the attributes for each element
        for(DocumentElement element : elements){
            List<Attribute> attributes = documentElementRepository.getAttributesForElement(element.getDocumentElementId());
            element.setAttributes(attributes);
        }

        return elements;
    }
}
