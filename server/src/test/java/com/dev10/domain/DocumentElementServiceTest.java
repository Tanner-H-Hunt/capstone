package com.dev10.domain;

import com.dev10.TestDataHelper;
import com.dev10.models.DTO.Result;
import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.DocumentElement;
import com.dev10.models.docelements.implementations.AttributeConfiguration;
import com.dev10.repositories.DocumentElementRepository;
import com.dev10.repositories.DocumentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class DocumentElementServiceTest {
    @MockitoBean
    DocumentRepository documentRepository;

    @MockitoBean
    DocumentElementRepository documentElementRepository;

    @MockitoBean
    AttributeConfiguration attributeConfiguration;

    @Autowired
    DocumentElementService service;

    @Test
    void createElementHappyPath() throws Exception {
        DocumentElement element = TestDataHelper.getBoxNotInDatabase();

        DocumentElement createdElement = TestDataHelper.getBoxNotInDatabase();
        createdElement.setDocumentElementId(100);

        DocumentElement initializedElement = TestDataHelper.getBoxNotInDatabase();
        initializedElement.setDocumentElementId(100);
        initializedElement.setAttributes(
                TestDataHelper.getAttributesForElement1()
        );

        when(documentRepository.getDocumentById(2))
                .thenReturn(TestDataHelper.getDocumentsForUser1().get(1));

        when(documentElementRepository.createElement(element))
                .thenReturn(createdElement);

        when(attributeConfiguration.initAttributes(createdElement))
                .thenReturn(initializedElement);

        Result<DocumentElement> result = service.create(element);

        assertTrue(result.isSuccess());
        assertEquals(initializedElement, result.getPayload());

        verify(documentElementRepository).createElement(element);
        verify(attributeConfiguration).initAttributes(createdElement);
    }

    @Test
    void createElementRejectsNullInput() throws Exception {
        Result<DocumentElement> result = service.create(null);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());

        verifyNoInteractions(documentRepository);
        verifyNoInteractions(documentElementRepository);
    }

    @Test
    void createElementRejectsIfIdIsPreset() throws Exception {
        DocumentElement element = TestDataHelper.getBoxNotInDatabase();
        element.setDocumentElementId(99);

        when(documentRepository.getDocumentById(2))
                .thenReturn(TestDataHelper.getDocumentsForUser1().get(1));

        Result<DocumentElement> result = service.create(element);

        assertFalse(result.isSuccess());

        verify(documentElementRepository, never())
                .createElement(any());
    }

    @Test
    void createElementRejectsIfElementTypeIsNull() throws Exception {
        DocumentElement element = TestDataHelper.getBoxNotInDatabase();
        element.setDocumentElementType(null);

        when(documentRepository.getDocumentById(2))
                .thenReturn(TestDataHelper.getDocumentsForUser1().get(1));

        Result<DocumentElement> result = service.create(element);

        assertFalse(result.isSuccess());

        verify(documentElementRepository, never())
                .createElement(any());
    }

    @Test
    void createElementRejectsIfAttributesArePreset() throws Exception {
        DocumentElement element = TestDataHelper.getBoxNotInDatabase();
        element.setAttributes(TestDataHelper.getAttributesForElement1());

        when(documentRepository.getDocumentById(2))
                .thenReturn(TestDataHelper.getDocumentsForUser1().get(1));

        Result<DocumentElement> result = service.create(element);

        assertFalse(result.isSuccess());

        verify(documentElementRepository, never())
                .createElement(any());
    }

    @Test
    void createElementRejectsIfParentDocumentDoesNotExist() throws Exception {
        DocumentElement element = TestDataHelper.getBoxNotInDatabase();

        when(documentRepository.getDocumentById(2))
                .thenReturn(null);

        Result<DocumentElement> result = service.create(element);

        assertFalse(result.isSuccess());

        verify(documentElementRepository, never())
                .createElement(any());
    }

    @Test
    void updateElementHappyPath() throws Exception {
        DocumentElement element =
                TestDataHelper.getElementsForUser1Uml().get(0);

        Attribute updatedAttribute =
                TestDataHelper.getUpdatedAttribute1();

        element.setAttributes(List.of(updatedAttribute));

        Attribute databaseAttribute =
                TestDataHelper.getAttribute1();

        when(documentElementRepository.getAttributeById(1))
                .thenReturn(databaseAttribute);

        when(documentElementRepository.editAttribute(updatedAttribute))
                .thenReturn(true);

        Result<DocumentElement> result =
                service.updateElement(element);

        assertTrue(result.isSuccess());
        assertEquals(element, result.getPayload());

        verify(documentElementRepository)
                .editAttribute(updatedAttribute);
    }

    @Test
    void updateElementFailsIfArgsNull() throws Exception {
        Result<DocumentElement> result =
                service.updateElement(null);

        assertFalse(result.isSuccess());

        verifyNoInteractions(documentElementRepository);
    }

    @Test
    void updateElementFailsIfAttributeIsNotInDatabase() throws Exception {
        DocumentElement element =
                TestDataHelper.getElementsForUser1Uml().get(0);

        Attribute attribute =
                TestDataHelper.getAttributeNotInDatabase();

        element.setAttributes(List.of(attribute));

        when(documentElementRepository.getAttributeById(0))
                .thenReturn(null);

        Result<DocumentElement> result =
                service.updateElement(element);

        assertFalse(result.isSuccess());

        verify(documentElementRepository, never())
                .editAttribute(any());
    }

    @Test
    void updateElementFailsIfTryingToReassignParentDocument()
            throws Exception {

        DocumentElement element =
                TestDataHelper.getElementsForUser1Uml().get(0);

        Attribute modifiedAttribute =
                TestDataHelper.getUpdatedAttribute1();

        modifiedAttribute.setDocumentElementId(999);

        element.setAttributes(List.of(modifiedAttribute));

        when(documentElementRepository.getAttributeById(1))
                .thenReturn(TestDataHelper.getAttribute1());

        Result<DocumentElement> result =
                service.updateElement(element);

        assertFalse(result.isSuccess());

        verify(documentElementRepository, never())
                .editAttribute(any());
    }

    @Test
    void updateElementFailsIfTryingToChangeAttributeType()
            throws Exception {

        DocumentElement element =
                TestDataHelper.getElementsForUser1Uml().get(0);

        Attribute modifiedAttribute =
                TestDataHelper.getUpdatedAttribute1();

        modifiedAttribute.setValue("'differentType': 999");

        element.setAttributes(List.of(modifiedAttribute));

        when(documentElementRepository.getAttributeById(1))
                .thenReturn(TestDataHelper.getAttribute1());

        Result<DocumentElement> result =
                service.updateElement(element);

        assertFalse(result.isSuccess());

        verify(documentElementRepository, never())
                .editAttribute(any());
    }
}