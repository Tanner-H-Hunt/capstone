import { Canvas, useFrame, useThree } from '@react-three/fiber'
import { useState } from 'react';
import ClassBox from '../shapes/ClassBox';
import ResizableBoxWrapper from '../shapes/ResizableBoxWrapper';

function UmlScene({ elements, removeElement }){

    return (    
            <>
                <ambientLight />
                
                {elements.map((element) => {return element})}
                {/* <ResizableBoxWrapper />
                <ResizableBoxWrapper /> */}
                
            </>
    );
}

export default UmlScene;