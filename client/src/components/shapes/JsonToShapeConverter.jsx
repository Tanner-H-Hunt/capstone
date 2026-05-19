import { useState } from "react";
import ResizableBoxWrapper from "./ResizableBoxWrapper";
import ResizableLineWrapper from "./ResizableLineWrapper";
import EditableText from "./EditableText";

function JsonToShape({ json }){
    
    let attributes = {
        documentElementId: json.documentElementId,
        elementType: json.documentElementType,
        documentId: json.documentElementId
    };

    for(const attribute of json.attributes){
        const fieldName = attribute.key;
        attributes[fieldName] = {
            attributeId: attribute.attributeId,
            documentElementId: attribute.documentElementId,
            value: attribute.value
        }
    }

    switch (attributes.elementType) {
        case "BOX": {
            // create hooks and set data to expected format, then return box
            const [width, setWidth] = useState(parseFloat(attributes.width.value.trim()));
            const [height, setHeight] = useState(parseFloat(attributes.height.value.trim()));
            const [position, setPosition] = useState([
                parseFloat(attributes.xPos.value.trim()), 
                parseFloat(attributes.yPos.value.trim()), 
                0]);
            let props = {width, setWidth, height, setHeight, position, setPosition, attributes};
            return < ResizableBoxWrapper {...props} />
        }

        case "LINE": {
            const [startPosition, setStartPosition] = useState([
                parseFloat(attributes.startXPos.value.trim()),
                parseFloat(attributes.startYPos.value.trim()),
                0
            ]);
            const [endPosition, setEndPosition] = useState([
                parseFloat(attributes.endXPos.value.trim()),
                parseFloat(attributes.endYPos.value.trim()),
                0
            ]);
            let props = {startPosition, setStartPosition, endPosition, setEndPosition, attributes}
            return <ResizableLineWrapper {...props}/>;
        }

        case "TEXT":
            const [position, setPosition] = useState([
                parseFloat(attributes.xPos.value.trim()), 
                parseFloat(attributes.yPos.value.trim()), 
                0]);
            const [innerText, setInnerText] = useState(attributes.innerText.value);
            const props = {position, setPosition, innerText, setInnerText, attributes}
            return <EditableText {...props} />
    }

    return (
        <>
        <ResizableBoxWrapper />
        </>
    );
}

export default JsonToShape;