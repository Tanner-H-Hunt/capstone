import { Canvas, useFrame, useThree } from '@react-three/fiber'
import { useState } from 'react';
import ClassBox from '../shapes/ClassBox';

function UmlScene(){
    const { camera } = useThree();
    const minCameraZoom = 10;
    const maxCameraZoom = 75

    return (    
            <>
                <pointLight position={[1.5, 1, 1]} />
                <ambientLight />
                <ClassBox />
                
                {/* <mesh position={[0, 0, 0]}>
                    <sphereGeometry />
                    <meshStandardMaterial color="orange" />
                </mesh> */}
            </>
    );
}

export default UmlScene;