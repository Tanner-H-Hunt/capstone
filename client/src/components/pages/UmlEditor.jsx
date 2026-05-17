import UmlScene from "../editors/UmlScene";
import { Canvas, useFrame } from '@react-three/fiber'
import { OrbitControls } from "@react-three/drei";
import * as THREE from 'three';
import LeftToolbar from "../editors/LeftToolbar";
import { useState } from "react";
import { useHotkeys } from "react-hotkeys-hook";

function UmlEditor(){
    const [elements, setElements] = useState([]);
    const [selected, setSelected] = useState([]);

    function addElement(element){
        // TODO: get element ID from the backend, save to backend
        setElements([...elements, element]);
    }

    function removeElement(element){
        //TODO delete on the backend
        const filteredElements = elements.filter(item => item != element);
        setElements(filteredElements);
    }

    function selectElement(element){

    }

    useHotkeys('delete', () => {
        selected.forEach((element) => removeElement(element));
    });


    return (
            <div className="container-fluid px-0 row"  style={{height: '100vh'}}>
                <div className="col-2"  style={{height: '100vh'}}>
                    <LeftToolbar addElement={addElement}/>
                </div>

                <div className="col-10"  style={{height: '100vh'}}>
                    <Canvas className="col-11" orthographic camera={{zoom: 50, position: [0, 0, 10]}}>

                        <OrbitControls 
                            enablePan 
                            enableZoom 
                            enableDamping 
                            dampingFactor={0.5} 
                            minZoom={10} 
                            maxZoom={150} 
                            enableRotate={false}
                            />
                        < UmlScene elements={elements} removeElement={removeElement}/>
                        
                    </Canvas>

                </div>


            </div>
    );
};

export default UmlEditor;