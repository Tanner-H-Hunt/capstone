import UmlScene from "../editors/UmlScene";
import { Canvas, useFrame } from '@react-three/fiber'
import { OrbitControls } from "@react-three/drei";
import * as THREE from 'three';
import LeftToolbar from "../editors/LeftToolbar";
import { useEffect, useState } from "react";
import { useHotkeys } from "react-hotkeys-hook";
import { useContext } from "react";
import UserContext from "../contexts/UserContext";
import { useParams } from "react-router";
import JsonToShape from "../shapes/JsonToShapeConverter";

function UmlEditor(){
    const [elements, setElements] = useState([]);
    const [selected, setSelected] = useState([]);
    const { loggedInUser } = useContext(UserContext);
    const { id } = useParams();

    function addElement(elementType){
        const requestBody = {
            "user": JSON.parse(loggedInUser).user,
            "element": {
                "documentId": id,
                "documentElementType": elementType
            }
        }

        const httpRequest = {
            method: "POST",
            headers: {
                'Authorization': JSON.parse(loggedInUser).bearer_token,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestBody)
        }
        
        const url = "http://localhost:8080/api/element"

        const fetchNewElement = async() => {
            const response = await fetch(url, httpRequest);
            const json = await response.json();
            if(response.status >=200 && response.status < 300){
                setElements([...elements, <JsonToShape json={json} key={json.documentElementId}/>])
                
            } else{
                console.log("Error fetching new element");
                console.log(json);
            }
        }
        fetchNewElement();
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

    // in development mode, useEffect runs twice, effectively doubling every UML element.
    // this prevents duplicates in development mode.
    const setOfIds = new Set([]);
    
    useEffect(() => {
        const httpRequest = {
            method: "POST",
            headers: {
                'Authorization': JSON.parse(loggedInUser).bearer_token,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(JSON.parse(loggedInUser).user),
        }
        
        const url = "http://localhost:8080/api/element/" + id;

        const fetchElements = async() => {
            const response = await fetch(url, httpRequest);
            const json = await response.json();
            if(response.status >=200 && response.status < 300){
                console.log(json);
                for(const element of json.elements){
                    if(setOfIds.has(element.documentElementId)){
                        continue;
                    } else{
                        setOfIds.add(element.documentElementId);
                        setElements(prev => [...prev, <JsonToShape json={element} key={element.documentElementId}/>])
                    }
                }
            } else{
                console.log("Error fetching new element");
                console.log(json);
            }
        }
        fetchElements();
    }, []);


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