import UmlScene from "../editors/UmlScene";
import { Canvas, useFrame } from '@react-three/fiber'
import { OrbitControls } from "@react-three/drei";
import * as THREE from 'three';

function UmlEditor(){
    return (
        <>
            <h1>In the UML editor</h1>
            <Canvas orthographic camera={{zoom: 50, position: [0, 0, 10]}}>

                <OrbitControls 
                    enablePan 
                    enableZoom 
                    enableDamping 
                    dampingFactor={0.5} 
                    minZoom={10} 
                    maxZoom={150} 
                    enableRotate={false}
                    />
                < UmlScene />
            </Canvas>
        </>
    );
};

export default UmlEditor;