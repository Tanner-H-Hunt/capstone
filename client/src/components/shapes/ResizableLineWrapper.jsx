import { useState } from "react";
import ResizableLine from "./ResizableLine";

function ResizableLineWrapper({startPosition, setStartPosition, endPosition, setEndPosition, attributes, selected, setSelected}){
    const [defaultStartPosition, defaultSetStartPosition] = useState([0, 0, 0]);
    const [defaultEndPosition, defaultSetEndPosition] = useState([2, 0, 0]);

    return (
            <ResizableLine 
                startPosition={ startPosition != undefined ? startPosition : defaultStartPosition}
                setStartPosition={ setStartPosition != undefined ? setStartPosition : defaultSetStartPosition }
                endPosition={ endPosition != undefined ? endPosition : defaultEndPosition }
                setEndPosition={ setEndPosition != undefined ? setEndPosition : defaultSetEndPosition }
                attributes = { attributes }
                setSelected={ setSelected }
                selected={ selected }
            />
    );
}

export default ResizableLineWrapper;