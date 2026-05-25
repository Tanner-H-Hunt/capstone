import { useEffect, useState } from "react";
import ResizableBoxWrapper from "./ResizableBoxWrapper";
import ResizableLineWrapper from "./ResizableLineWrapper";
import EditableText from "./EditableText";
import Note from "./Note";

function JsonToShape({ json, selected, setSelected }){
    const useTextAttribute = (data, key) => {
        const [state, setState] = useState( json.attributes.find(attr => attr.key === key).value )

        useEffect(() => {
            data.attributes.find(attr => attr.key === key).value = state;
        }, [state])

        return [state, setState];
    }

    const useNumericAttribute = (data, key) => {
        const [state, setState] = useState( parseFloat(json.attributes.find(attr => attr.key === key).value ));
        
        useEffect(() => {
            data.attributes.find(attr => attr.key === key).value = state;
        }, [state])

        return [state, setState]
    }

    const useVector2 = (data, key1, key2) => {
        const [state, setState] = useState([            
            parseFloat(json.attributes.find(attr => attr.key === key1).value),
            parseFloat(json.attributes.find(attr => attr.key === key2).value),
            0]);

        useEffect(() => {
            data.attributes.find(attr => attr.key === key1).value = state[0];
            data.attributes.find(attr => attr.key === key2).value = state[1];
        }, [state[0], state[1]])
        
        return [state, setState];
    }

    switch (json.elementType) {
        case "BOX": {
            const [width, setWidth] = useNumericAttribute(json, "width");
            const [height, setHeight] = useNumericAttribute(json, "height");
            const [position, setPosition] = useVector2(json, "xPos", "yPos");
            let props = {selected, setSelected, width, setWidth, height, setHeight, position, setPosition, json};
            return < ResizableBoxWrapper {...props}/>
        }

        case "LINE": {
            const [startPosition, setStartPosition] = useVector2(json, "startXPos", "startYPos");
            const [endPosition, setEndPosition] = useVector2(json, "endXPos", "endYPos");
            let props = {selected, setSelected, startPosition, setStartPosition, endPosition, setEndPosition, json }
            return <ResizableLineWrapper {...props} />;
        }

        case "TEXT": {
            const [position, setPosition] = useVector2(json, "xPos", "yPos")
            const [innerText, setInnerText] = useTextAttribute(json, "innerText");

            const props = {selected, setSelected, position, setPosition, innerText, setInnerText, json }
            return <EditableText {...props} />
        }

        case "NOTE": {
            const [innerText, setInnerText] = useTextAttribute(json, "innerText");
            const [order, setOrder] = useNumericAttribute(json, "order");

            const props = {selected, setSelected, json, innerText, setInnerText, order, setOrder};
            return <Note {...props} key={json.elementId}/>
        }
    }

    return <></>;

}

export default JsonToShape;