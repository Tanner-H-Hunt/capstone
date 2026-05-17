import { Line } from "@react-three/drei";
import { useDrag } from '@use-gesture/react'
import { useThree } from "@react-three/fiber";


function ResizableLine({startPosition, setStartPosition, endPosition, setEndPosition}){
    const lineWidth = 2;
    const color = "black";
    const resizeHandlerClickAffordance = 0.5;
    const lineRepositionClickAffordance = 1;

    const { camera } = useThree();

    function serialize(){
        console.log("serializing");
    }

    const createVertexDrag = (position, setPosition) => 
        useDrag(({ first, last, movement: [mx, my], memo, event }) => {
                if(first){
                    event.stopPropagation();
                    event.target.setPointerCapture(event.pointerId);

                    memo = {
                        startX: position[0],
                        startY: position[1]
                    }
                    
                }
                if(last){
                    serialize();
                }

                const deltaX = mx / camera.zoom;
                const deltaY = my / camera.zoom;

                const newPosition = [memo.startX + deltaX, memo.startY - deltaY, 0];
                setPosition(newPosition)

                return memo;

            });

    const bindMeshReposition = useDrag(({ first, last, movement: [mx, my], memo, event }) => {
        if(first){
            event.stopPropagation();
            event.target.setPointerCapture(event.pointerId);

            memo = {
                startV1X: startPosition[0],
                startV1Y: startPosition[1],
                startV2X: endPosition[0],
                startV2Y: endPosition[1]
            }
        }

        if(last){
            serialize();
        }

        const deltaX = mx / camera.zoom;
        const deltaY = my / camera.zoom;

        const newStartVertexPosition = [memo.startV1X + deltaX, memo.startV1Y - deltaY, 0];
        const newEndVertexPosition = [memo.startV2X + deltaX, memo.startV2Y - deltaY, 0];

        setStartPosition(newStartVertexPosition);
        setEndPosition(newEndVertexPosition);
        return memo;
    });
    

    const bindStartVertex = createVertexDrag(startPosition, setStartPosition);
    const bindEndVertex = createVertexDrag(endPosition, setEndPosition);

    return (
        <group>
            <Line 
                points={[startPosition, endPosition]}
                lineWidth={lineWidth}
                color={color}   
            />
            {/* Collision handlers for repositioning and resizing the line */}
            {/* First vertex */}
            <mesh position={startPosition} 
            {...bindStartVertex()}>
                <planeGeometry args={[resizeHandlerClickAffordance, resizeHandlerClickAffordance]}/>
                <meshBasicMaterial transparent opacity={0} />
            </mesh>

            {/* second vertex */}
            <mesh position={endPosition}
            {...bindEndVertex()}>
                <planeGeometry args={[resizeHandlerClickAffordance, resizeHandlerClickAffordance]}/>
                <meshBasicMaterial transparent opacity={0} />
            </mesh>
            {/* Full line reposiiton */}
            <Line
                points={[startPosition, endPosition]}
                lineWidth={lineWidth + lineRepositionClickAffordance}
                visible={false}
                {...bindMeshReposition()}
            />
        </group>
    );
}

export default ResizableLine;