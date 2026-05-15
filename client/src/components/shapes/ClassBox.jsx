import { Line, Text, Html } from "@react-three/drei";
import { useState } from "react";
import ResizableBoxWrapper from "./ResizableBoxWrapper";

function ClassBox(){
    const [width, setWidth] = useState(2);
    const [height, setHeight] = useState(2);
    const [position, setPosition] = useState([0, 1, 0]);

    //
    return (
        <>
         <ResizableBoxWrapper position={position} setPosition={setPosition}/>

        {/* <Html transform>
            <input type="text" />
        </Html> */}

        </>
    );
}

export default ClassBox;