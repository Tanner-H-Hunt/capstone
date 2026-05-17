import { Canvas, useFrame, useThree } from '@react-three/fiber'
import { useState } from 'react';
import ResizableBoxWrapper from '../shapes/ResizableBoxWrapper';

function UmlScene({ elements, removeElement }){

    return (    
            <>
                <ambientLight />
                
                {elements.map((element) => {return element})}
                
            </>
    );
}

export default UmlScene;