import { Html, Image, Text } from "@react-three/drei";

function NewElementButton({preview, name, element, addElement, enumValue}) { 
    return (
        <div className="bg-body-tertiary mb-0" onClick={() => addElement(element, enumValue)}>
            <p className="mb-0 py-1 text-center border border-bottom">{name}</p>
        </ div>
    );

}

export default NewElementButton;