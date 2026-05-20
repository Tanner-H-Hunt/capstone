import { useState } from "react";
import ResizableBox from "./ResizableBox";

function ResizableBoxWrapper({ width, setWidth, 
                               height, setHeight, 
                               position, setPosition, 
                               selected, setSelected, 
                               attributes}){

    // rendering data
    const [defaultWidthProp, setDefaultWidthProp] = useState(2);
    const [defaultHeightProp, defaultSetHeightProp] = useState(2);
    const [defaultPositionProp, defaultSetPositionProp] = useState([0, 0, 0]);

    return (

        <>
            <ResizableBox 
                width={width != undefined ? width : defaultWidthProp}
                setWidth={setWidth != undefined ? setWidth : setDefaultWidthProp}
                height={height != undefined ? height : defaultHeightProp}
                setHeight={setHeight != undefined ? setHeight : defaultSetHeightProp}
                position={position != undefined ? position : defaultPositionProp}
                setPosition={setPosition != undefined ? setPosition : defaultSetPositionProp}
                attributes={attributes}
            />
        </>
    );
}

export default ResizableBoxWrapper;