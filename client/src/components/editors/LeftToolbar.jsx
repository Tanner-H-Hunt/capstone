import { useThree } from "@react-three/fiber";
import NewElementButton from "./NewElementButton";
import { Html } from "@react-three/drei";
import ResizableBoxWrapper from "../shapes/ResizableBoxWrapper";
import ResizableLineWrapper from "../shapes/ResizableLineWrapper";

function LeftToolbar({ addElement }) {

    return (
        <>
        <div className="border border-1 border-black" style={{
            position: "absolute",

            top: "5%",
            left: '24px',

            width: '280px',
            height: '90%',

            zIndex: 10
        }}>
            < NewElementButton preview="../" name="Text" addElement={addElement} element={<ResizableBoxWrapper />} />
            < NewElementButton preview="../" name="Box" addElement={addElement} element={<ResizableBoxWrapper />} />
            < NewElementButton preview="../" name="Line" addElement={addElement} element={<ResizableLineWrapper />} />
            < NewElementButton preview="../" name="Arrow" addElement={addElement} element={<ResizableBoxWrapper />} />
            < NewElementButton preview="../" name="Interface" addElement={addElement} element={<ResizableBoxWrapper />} />
            < NewElementButton preview="../" name="Class Diagram" addElement={addElement} element={<ResizableBoxWrapper />} />
        </div>
        </>
    );
}

export default LeftToolbar;