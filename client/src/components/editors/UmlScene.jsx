import { Canvas, useFrame, useThree } from '@react-three/fiber'
import { useState } from 'react';
import ResizableBoxWrapper from '../shapes/ResizableBoxWrapper';
import JsonToShape from '../shapes/JsonToShapeConverter';
import { ClickAwayListener } from "@mui/material";


function UmlScene({ elements, removeElement, selected, setSelected }){

    return (    
            <>
                <ambientLight />
                
                {elements.map((element) => {
                    try{
                        return (
                            <JsonToShape
                                json={element}
                                key={element.elementId}
                                selected={selected}
                                setSelected={setSelected} />
                        )

                    } catch (error) {
                        console.log('ERROR CORRUPT DATA' + error);
                    }
                    
                    })}
                
            </>
    );
}

export default UmlScene;