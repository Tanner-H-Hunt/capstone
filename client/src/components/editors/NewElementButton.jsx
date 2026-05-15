import { Html, Image, Text } from "@react-three/drei";

function NewElementButton({preview, name, element, addElement}) { 
    return (
        <div className="bg-body-tertiary" onClick={() => addElement(element)}>
            <p>{name}</p>
        </ div>
    );

}

export default NewElementButton;