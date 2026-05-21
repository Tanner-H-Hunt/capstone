import { Line } from "@react-three/drei";
import { useDrag } from "@use-gesture/react";
import { useState } from "react";
import { useThree } from "@react-three/fiber";
import UserContext from "../contexts/UserContext";
import { useContext } from "react";
import { ClickAwayListener } from "@mui/material";

function ResizableBox({ width, setWidth, height, setHeight, position, setPosition, attributes, selected, setSelected }) {

    const { loggedInUser } = useContext(UserContext);

    function serialize(){
        if(attributes == undefined){
            return;
        }
                if(attributes == undefined){
            return;
        }
        attributes.xPos.value = position[0];
        attributes.yPos.value = position[1];
        attributes.width.value = width;
        attributes.height.value = height;

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
                // console.log("successfully updated object data");
            } else{
                const json = await response.json();
                console.log("failed to update element data")
                console.log(json);
            }
        }
        updateRequest()
    }

    const scene = useThree();

    const lineWidth = 2;
    const lineColor = "black";

    // collision mesh position
    const left = position[0];
    const top = position[1];
    const right = left + width;
    const bottom = top - height;
    const clickAffordance = 0.35;

    // line vertex positions
    const topLeft = [left, top, 0];
    const bottomLeft = [left, bottom, 0];
    const topRight = [right, top, 0];
    const bottomRight = [right, bottom, 0];

    const bindRight = useDrag(
        ({ first, last, movement: [mx], memo, event }) => {

            if (first) {
                event.target.setPointerCapture(event.pointerId);

                memo = {
                    startWidth: width
                };
            }

            if(last){
                serialize();
            }

            const newWidth = memo.startWidth + mx / (scene.camera.zoom);

            setWidth(Math.max(0.2, newWidth));

            return memo;
        }
    );

    const bindLeft = useDrag(({ first, last, movement: [mx], memo, event }) => {
        
        if(first){
            event.target.setPointerCapture(event.pointerId);
			event.stopPropagation();

            memo = { startWidth: width, startX: position[0] };

        }

        if(last){
            serialize();
        }

        const newWidth = memo.startWidth - mx / scene.camera.zoom;
        const newXPos = memo.startX + mx / scene.camera.zoom;

        setPosition([newXPos, position[1], 0]);
        setWidth(newWidth);

        return memo;
        });

    const bindTop = useDrag(({ first, last, movement: [mx, my], memo, event }) => {

        if(first){
            event.target.setPointerCapture(event.pointerId);
			event.stopPropagation();

            memo = {startHeight: height, startY: position[1] }
        }
        if(last){
            serialize();
        }

        const newHeight = memo.startHeight - my / scene.camera.zoom;
        const newTop = memo.startY - my / scene.camera.zoom;

        setPosition([left, newTop, 0]);
        setHeight(newHeight);
        return memo;
    });

    const bindBottom = useDrag(({ first, last, movement: [mx, my], memo, event }) => {
        if(first){
            event.target.setPointerCapture(event.pointerId);

            memo = {startHeight: height};
        }
        if(last){
            serialize();
        }

        const newHeight = memo.startHeight + my / scene.camera.zoom;

        setHeight(newHeight);
        return memo;
    });

    const bindDragging = useDrag(({ first, last, movement: [mx, my], memo, event }) => {
        if(first){
            event.target.setPointerCapture(event.pointerId);
			event.stopPropagation();

            memo = {startX: position[0], startY: position[1]}; 
		}
		if(last){
			serialize();
		}

		const newPosition = [memo.startX + mx / scene.camera.zoom, memo.startY - my / scene.camera.zoom];
		setPosition(newPosition);
		return memo;
    });

    function select(){
        setSelected(attributes.documentElementId);
    }

    function deselect(evt){
        if(selected === attributes.documentElementId && evt.target.localName === "canvas"){
            setSelected(null);
        }
    }

    return (
        <ClickAwayListener onClickAway={(evt) => deselect(evt)}>
        <group onClick={() => {select()}}>

            {/* box rendering */}
            {/* left line */}
            <Line
                points={[topLeft, bottomLeft]}
                lineWidth={lineWidth}
                color={lineColor}
            />
            {/* bottom line */}
            <Line
                points={[bottomLeft, bottomRight]}
                lineWidth={lineWidth}
                color={lineColor}
            />
            {/* right line */}
            <Line
                points={[topRight, bottomRight]}
                lineWidth={lineWidth}
                color={lineColor}
            />
            {/* top line */}
            <Line
                points={[topLeft, topRight]}
                lineWidth={lineWidth}
                color={lineColor}
            />

            {/* LEFT HANDLE */}
            <mesh
                position={[left, top - height / 2, 0]}
                {...bindLeft()}
            >
                <planeGeometry args={[clickAffordance, height]} />
                <meshBasicMaterial transparent opacity={0} />
            </mesh>

            {/* RIGHT HANDLE */}
            <mesh
                position={[right, top - height / 2, 0]}
                {...bindRight()}
            >
                <planeGeometry args={[clickAffordance, height]} />
                <meshBasicMaterial transparent opacity={0} />
            </mesh>

            {/* TOP HANDLE */}
            <mesh
                position={[left + width / 2, top, 0]}
                {...bindTop()}
            >
                <planeGeometry args={[width, clickAffordance]} />
                <meshBasicMaterial transparent opacity={0} />
            </mesh>

            {/* BOTTOM HANDLE */}
            <mesh
                position={[left + width / 2, bottom, 0]}
                {...bindBottom()}
            >
                <planeGeometry args={[width, clickAffordance]} />
                <meshBasicMaterial transparent opacity={0} />
            </mesh>

            {/* Drag and Drop Mesh */}
            <mesh position={[left + width / 2, top - height / 2, 0]}
                {...bindDragging()}
                >
                <planeGeometry args={[(width - clickAffordance), (height - clickAffordance)]}/>
                <meshBasicMaterial transparent opacity={0}/>
            </mesh>

        </group>
        </ClickAwayListener>
    );
}

export default ResizableBox;