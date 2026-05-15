import { useThree } from "@react-three/fiber";
import NewElementButton from "./NewElementButton";
import { Html } from "@react-three/drei";

function LeftToolbar() {

    return (
        <>
        <div className="bg-secondary border border-1 border-black">
            < NewElementButton preview="../" name="Element 123123" />

        </div>
        </>
    );
}

export default LeftToolbar;