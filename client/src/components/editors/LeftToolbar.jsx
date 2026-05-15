import { useThree } from "@react-three/fiber";
import NewElementButton from "./NewElementButton";
import { Html } from "@react-three/drei";
import ResizableBoxWrapper from "../shapes/ResizableBoxWrapper";

function LeftToolbar({ addElement }) {

    return (
        <>
        <div className="bg-secondary border border-1 border-black">
            < NewElementButton preview="../" name="Text" addElement={addElement} element={<ResizableBoxWrapper />} />
            < NewElementButton preview="../" name="Box" addElement={addElement} element={<ResizableBoxWrapper />} />
            < NewElementButton preview="../" name="Line" addElement={addElement} element={<ResizableBoxWrapper />} />
            < NewElementButton preview="../" name="Arrow" addElement={addElement} element={<ResizableBoxWrapper />} />
            < NewElementButton preview="../" name="Interface" addElement={addElement} element={<ResizableBoxWrapper />} />
            < NewElementButton preview="../" name="Class Diagram" addElement={addElement} element={<ResizableBoxWrapper />} />
        </div>
        </>
    );
}

export default LeftToolbar;