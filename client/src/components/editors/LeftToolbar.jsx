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
            < NewElementButton preview="../" name="Text" addElement={addElement} enumValue="TEXT" />
            < NewElementButton preview="../" name="Box" addElement={addElement} enumValue="BOX" />
            < NewElementButton preview="../" name="Line" addElement={addElement} enumValue="LINE" />
            < NewElementButton preview="../" name="Arrow" addElement={addElement} enumValue="ARROW" />
            < NewElementButton preview="../" name="Interface" addElement={addElement} enumValue="CLASS_BOX" />
            < NewElementButton preview="../" name="Class Diagram" addElement={addElement} enumValue="INTERFACE" />
        </div>
        </>
    );
}

export default LeftToolbar;