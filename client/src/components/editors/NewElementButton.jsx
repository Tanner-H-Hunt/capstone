import { Html, Image, Text } from "@react-three/drei";

function NewElementButton({preview, name}) { 
    return (
        <div className="bg-body-tertiary">
            <p>{name}</p>
        </ div>
    );

}

export default NewElementButton;