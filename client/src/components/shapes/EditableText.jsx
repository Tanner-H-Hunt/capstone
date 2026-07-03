import { Html } from "@react-three/drei";
import { useMeasure } from "@uidotdev/usehooks";
import { useEffect, useState } from "react";
import { useThree } from "@react-three/fiber";
import { useDrag } from '@use-gesture/react';
import ClickAwayListener from '@mui/material/ClickAwayListener'
import { useContext } from "react";
import UserContext from "../contexts/UserContext";

function EditableText( {position, setPosition, innerText, setInnerText, json, selected, setSelected} ){

    const { loggedInUser } = useContext(UserContext);
    const { camera } = useThree();
    const [ref, { width, height }] = useMeasure();
    const [editing, setEditing] = useState(false);
    const [rows, setRows] = useState(1);
    const [cols, setCols] = useState(1);
    const dragAffordance = 0.5;
    const [loaded, setLoaded] = useState(false);
    
    function serialize(){
        if(json == undefined){
            return;
        }

        const body = {
                "user": JSON.parse(loggedInUser).user,
                "element": json
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
                // console.log("successfully updated object data");
            } else{
                console.log("failed to update element data")
                const json = await response.json();
                console.log(json);
            }
        }
        updateRequest()

    }

    function updateTextContents(evt){
        let str = evt.target.value;

        setInnerText(str);
    }

    function updateRowCount(str){
        let rows = str.split("\n").length;
        rows = Math.max(rows, 1);
        setRows(rows);
    }

    function updateColumnCount(str){
        const MINIMUM_COLUMNS = 3;
        let cols = MINIMUM_COLUMNS;
        
        for(const line of str.split("\n")){
            cols = Math.max(cols, line.length);
        }

        setCols(cols);
    }

    function toggleTextEditing(){
        setEditing(!editing);
        updateRowCount(innerText);
        updateColumnCount(innerText);
    }

    useEffect(() => {
        // data will be redundantly saved the first time innerText loads
        // maybe there's a better way to go about preventing the extra save
        if(!loaded){
            setLoaded(true);
        } else{
            serialize();
        }

        updateColumnCount(innerText);
        updateRowCount(innerText);

    }, [innerText]) //serializing of position is handled in the drag functions for now because it would otherwise bombard the server with updates

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

    function select(){
        setSelected(json.elementId);
    }

    function deselect(evt){
        if(selected === json.elementId && evt.target.localName === "canvas"){
            setSelected(null);
        }
    }

    return (
        <ClickAwayListener onClickAway={(evt) => deselect(evt)}>
        <group onClick={() => select()}>

            <mesh position={[position[0], position[1], -1]} {...dragBinding()} onDoubleClick={() => {toggleTextEditing()}}>
                <planeGeometry args={[(width / camera.zoom) + dragAffordance, (height / camera.zoom) + dragAffordance]}/>
                <meshBasicMaterial transparent opacity={0}/>

                <ClickAwayListener onClickAway={() => {setEditing(false)}}>
                <Html position={[0, 0, 0]} transform >
                    <div ref={ref} style={{background: 'white'}}>
                        {editing ? 
                            <textarea 
                                onClick={() => select()} // onClick is also attached to the group, but inner html elements makes clicking it finicky.  The extra onclick addresses that
                                defaultValue={innerText} 
                                value={innerText}
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
                        <pre onClick={() => select()}
                            className="mb-0" 
                            style={{
                                userSelect: 'none', // prevents the text from becoming highlighted during drag events 
                                fontFamily: 'monospace' // keep the same font as the text area for consistency
                            }}
                            onDoubleClick={() => {
                                toggleTextEditing();
                            }}
                            {...dragBinding()}
                            >{innerText}
                        </pre> }

                    </div>
                </Html>
                </ClickAwayListener>

            </mesh>

        </group>
        </ClickAwayListener>
    );
}

export default EditableText;