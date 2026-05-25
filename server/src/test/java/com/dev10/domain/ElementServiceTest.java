package com.dev10.domain;

import com.dev10.TestDataHelper;
import com.dev10.models.DTO.Result;
import com.dev10.models.DataAccessException;
import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.Element;
import com.dev10.repositories.ElementRepository;
import com.dev10.repositories.DocumentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ElementServiceTest {
    @MockitoBean
    DocumentRepository documentRepository;

    @MockitoBean
    ElementRepository elementRepository;

    @MockitoBean
    AttributeConfigurationService attributeConfiguration;

    @Autowired
    ElementService service;

    @Test
    void createElementHappyPath() throws DataAccessException {
        Element beforeCreation = TestDataHelper.getBoxNotInDatabase();
        Element expectedGenericElement = TestDataHelper.getBoxNotInDatabase();
        expectedGenericElement.setElementId(100);

        Element expectedSpecificElement = TestDataHelper.getBoxNotInDatabase();
        expectedSpecificElement.setElementId(100);
        expectedSpecificElement.setAttributes(TestDataHelper.getAttributesForElement1());

        when(documentRepository.getDocumentById(2)).thenReturn(TestDataHelper.getDocumentsForUser1().get(1));
        when(elementRepository.createElement(beforeCreation)).thenReturn(expectedGenericElement);
        when(attributeConfiguration.initAttributes(expectedGenericElement)).thenReturn(expectedSpecificElement);

        Result<Element> result = service.create(beforeCreation);

        assertTrue(result.isSuccess());
        assertEquals(expectedSpecificElement, result.getPayload());
        verify(elementRepository).createElement(beforeCreation);
        verify(attributeConfiguration).initAttributes(expectedGenericElement);
    }

    @Test
    void createElementRejectsNullInput() throws DataAccessException {
        Result<Element> result = service.create(null);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());

        verifyNoInteractions(elementRepository);
    }

    @Test
    void createElementRejectsIfIdIsPreset() throws DataAccessException {
        Element element = TestDataHelper.getBoxNotInDatabase();
        element.setElementId(99);
        when(documentRepository.getDocumentById(2)).thenReturn(TestDataHelper.getDocumentsForUser1().get(1));

        Result<Element> result = service.create(element);

        assertFalse(result.isSuccess());
        verify(elementRepository, never()).createElement(any());
    }

    @Test
    void createElementRejectsIfElementTypeIsNull() throws DataAccessException {
        Element element = TestDataHelper.getBoxNotInDatabase();
        element.setElementType(null);
        when(documentRepository.getDocumentById(2)).thenReturn(TestDataHelper.getDocumentsForUser1().get(1));

        Result<Element> result = service.create(element);

        assertFalse(result.isSuccess());
        verify(elementRepository, never()).createElement(any());
    }

    @Test
    void createElementRejectsIfAttributesArePreset() throws DataAccessException {
        Element element = TestDataHelper.getBoxNotInDatabase();
        element.setAttributes(TestDataHelper.getAttributesForElement1());
        when(documentRepository.getDocumentById(2)).thenReturn(TestDataHelper.getDocumentsForUser1().get(1));

        Result<Element> result = service.create(element);

        assertFalse(result.isSuccess());
        verify(elementRepository, never()).createElement(any());
    }

    @Test
    void createElementRejectsIfParentDocumentDoesNotExist() throws DataAccessException {
        Element element = TestDataHelper.getBoxNotInDatabase();
        when(documentRepository.getDocumentById(2)).thenReturn(null);

        Result<Element> result = service.create(element);

        assertFalse(result.isSuccess());
        verify(elementRepository, never()).createElement(any());
    }

    @Test
    void updateElementHappyPath() throws DataAccessException {
        Element element = TestDataHelper.getElementsForUser1Uml().get(0);
        Attribute updatedAttribute = TestDataHelper.getUpdatedAttribute1();
        element.setAttributes(List.of(updatedAttribute));
        Attribute databaseAttribute = TestDataHelper.getAttribute1();

        when(elementRepository.getAttributeById(1)).thenReturn(databaseAttribute);
        when(elementRepository.editAttribute(updatedAttribute)).thenReturn(true);

        Result<Element> result = service.updateElement(element);

        assertTrue(result.isSuccess());
        assertEquals(element, result.getPayload());
        verify(elementRepository).editAttribute(updatedAttribute);
    }

    @Test
    void updateElementFailsIfArgsNull() throws DataAccessException {
        Result<Element> result = service.updateElement(null);

        assertFalse(result.isSuccess());

        verifyNoInteractions(elementRepository);
    }

    @Test
    void updateElementFailsIfAttributeIsNotInDatabase() throws DataAccessException {
        Element element = TestDataHelper.getElementsForUser1Uml().get(0);
        Attribute attribute = TestDataHelper.getAttributeNotInDatabase();
        element.setAttributes(List.of(attribute));
        when(elementRepository.getAttributeById(0)).thenReturn(null);

        Result<Element> result = service.updateElement(element);

        assertFalse(result.isSuccess());
        verify(elementRepository, never()).editAttribute(any());
    }

    @Test
    void updateElementFailsIfTryingToReassignParentDocument() throws DataAccessException {

        Element element = TestDataHelper.getElementsForUser1Uml().get(0);
        Attribute modifiedAttribute = TestDataHelper.getUpdatedAttribute1();
        modifiedAttribute.setElementId(999);
        element.setAttributes(List.of(modifiedAttribute));
        when(elementRepository.getAttributeById(1)).thenReturn(TestDataHelper.getAttribute1());

        Result<Element> result = service.updateElement(element);

        assertFalse(result.isSuccess());
        verify(elementRepository, never()).editAttribute(any());
    }

    @Test
    void updateElementFailsIfTryingToChangeAttributeType() throws DataAccessException {

        Element element = TestDataHelper.getElementsForUser1Uml().get(0);
        Attribute modifiedAttribute = TestDataHelper.getUpdatedAttribute1();
        modifiedAttribute.setKey("differentType");
        element.setAttributes(List.of(modifiedAttribute));
        when(elementRepository.getAttributeById(1)).thenReturn(TestDataHelper.getAttribute1());

        Result<Element> result = service.updateElement(element);

        assertFalse(result.isSuccess());
        verify(elementRepository, never()).editAttribute(any());
    }
}