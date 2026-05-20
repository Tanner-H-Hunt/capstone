import { Html } from "@react-three/drei";
import { useMeasure } from "@uidotdev/usehooks";
import { useState } from "react";
import { useThree } from "@react-three/fiber";
import { useDrag } from '@use-gesture/react';
import ClickAwayListener from '@mui/material/ClickAwayListener'
import { useContext } from "react";
import UserContext from "../contexts/UserContext";

function EditableText( {position, setPosition, innerText, setInnerText, attributes} ){

    const { loggedInUser } = useContext(UserContext);
    const { camera } = useThree();
    const [ref, { width, height }] = useMeasure();
    const [editing, setEditing] = useState(false);
    const [rows, setRows] = useState(1);
    const [cols, setCols] = useState(1);
    const dragAffordance = 0.5;
    
    function serialize(){
        if(attributes == undefined){
            return;
        }
                if(attributes == undefined){
            return;
        }
        attributes.xPos.value = position[0];
        attributes.yPos.value = position[1];
        attributes.innerText.value = innerText;

        const body = {
                "user": JSON.parse(loggedInUser).user,
                "element": {
                    "attributes": [
                    ]
                }
            }
            
            // nest the attributes back under the element
            for(const attribute in attributes){
                if(attribute === "documentElementId" || attribute === "elementType" || attribute === "documentId"){
                    body.element[attribute] = attributes[attribute];
                } else{
                    const expectedAttribute = {
                        "attributeId": attributes[attribute].attributeId,
                        "documentElementId": attributes[attribute].documentElementId,
                        "value": `${attribute}:${attributes[attribute].value}`
                    }
                    body.element.attributes.push(expectedAttribute);
                }
            }

            const httpRequest = {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': JSON.parse(loggedInUser).bearer_token
                },
                body: JSON.stringify(body)
            }

        const url = "http://localhost:8080/api/element"

        const updateRequest = async() => {
            const response = await fetch(url, httpRequest);
            if(response.status >= 200 && response.status < 300){
                console.log("successfully updated object data");
            } else{
                console.log("failed to update element data")
                const json = await response.json();
                console.log(json);
            }
        }
        updateRequest()

    }

    function updateTextContents(evt){
        const str = evt.target.value;
        const lines = str.split("\n").length;
        setInnerText(str);
        setCols(Math.max(str.length, 3));
        setRows(Math.max(lines, 1));
        serialize();
    }

    const dragBinding = useDrag(({first, last, movement: [mx, my], memo, event}) => {
        if(first){
            event.target.setPointerCapture(event.pointerId);
            event.stopPropagation();

            memo = { 
                startX: position[0], 
                startY: position[1] 
            }
        } if(last){
            serialize();
        }

        const newPosition = [memo.startX + mx /camera.zoom, memo.startY - my / camera.zoom];
        setPosition(newPosition);

        return memo;
    });

    return (
        <group>

            <mesh position={[position[0], position[1], -1]} {...dragBinding()} onDoubleClick={() => {setEditing(!editing)}}>
                <planeGeometry args={[(width / camera.zoom) + dragAffordance, (height / camera.zoom) + dragAffordance]}/>
                <meshBasicMaterial transparent opacity={0}/>

                <ClickAwayListener onClickAway={() => {setEditing(false)}}>
                <Html position={[0, 0, 0]} transform >
                    <div ref={ref} style={{background: 'white'}}>
                        {editing ? 
                            <textarea 
                                defaultValue={innerText} 
                                rows={rows} 
                                cols={cols} 
                                style={{
                                    resize: 'none', // disable a draggable element that lets users resize the text area 
                                    border: 'none', 
                                    overflow: 'hidden', // disable the scroll wheel inside the text area
                                    outline: 'none',  // disable the border that appears when the element is selected
                                    fontFamily: 'monospace' // the only way I've found so far of preventing the text area from growing faster than the content
                                }}  
                                onChange={(e) => {updateTextContents(e)}}
                                className="px-0 py-0"
                                />
   
                        : 
                        <p 
                            className="mb-0" 
                            style={{
                                userSelect: 'none', // prevents the text from becoming highlighted during drag events 
                                fontFamily: 'monospace' // keep the same font as the text area for consistency
                            }}
                            onDoubleClick={() => {
                                setEditing(!editing); // toggle between <p> and <textarea> 
                                setCols(Math.max(innerText.length, 3)); // precompute the text areas size so the size doesn't snap
                            }}
                            {...dragBinding()}
                            > {innerText}
                        </p> }

                    </div>
                </Html>
                </ClickAwayListener>

            </mesh>

        </group>
    );
}

export default EditableText;