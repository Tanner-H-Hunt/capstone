import { Canvas, useFrame, useThree } from '@react-three/fiber'
import { useState } from 'react';
import ClassBox from '../shapes/ClassBox';
import ResizableBoxWrapper from '../shapes/ResizableBoxWrapper';

function UmlScene(){

    return (    
            <>
                <ambientLight />
                <ResizableBoxWrapper />
                <ResizableBoxWrapper />
                
                {/* <mesh position={[0, 0, 0]}>
                    <sphereGeometry />
                    <meshStandardMaterial color="orange" />
                </mesh> */}
            </>
    );
}

export default UmlScene;