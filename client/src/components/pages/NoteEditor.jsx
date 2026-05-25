import { useEffect, useState } from "react";
import { useHotkeys } from "react-hotkeys-hook";
import { useContext } from "react";
import UserContext from "../contexts/UserContext";
import { useParams } from "react-router";
import JsonToShape from "../shapes/JsonToShapeConverter";
import NotesScene from "../editors/NotesScene";
import RelationshipToolbar from "../editors/relations/RelationshipToolbar";

function NoteEditor(){
    const [elements, setElements] = useState([]);
    const [selected, setSelected] = useState(null);
    const { loggedInUser } = useContext(UserContext);
    const { id } = useParams();

    function addElement(elementType){
        const requestBody = {
            "user": JSON.parse(loggedInUser).user,
            "element": {
                "documentId": id,
                "elementType": elementType
            }
        }

        const httpRequest = {
            method: "POST",
            headers: {
                'Authorization': JSON.parse(loggedInUser).bearer_token,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestBody)
        }
        
        const url = "http://localhost:8080/api/element"

        const fetchNewElement = async() => {
            const response = await fetch(url, httpRequest);
            const json = await response.json();
            if(response.status >=200 && response.status < 300){
                setElements([...elements, json])
                
            } else{
                console.log("Error fetching new element");
                console.log(json);
            }
        }
        fetchNewElement();
    }

    function removeElement(element){
        //TODO delete on the backend
        const filteredElements = elements.filter(item => item != element);
        setElements(filteredElements);
    }

    function selectElement(element){

    }

    useHotkeys('delete', () => {
        selected.forEach((element) => removeElement(element));
    });
    
    useEffect(() => {
        const httpRequest = {
            method: "POST",
            headers: {
                'Authorization': JSON.parse(loggedInUser).bearer_token,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(JSON.parse(loggedInUser).user),
        }
        
        const url = "http://localhost:8080/api/element/" + id;

        const fetchElements = async() => {
            const response = await fetch(url, httpRequest);
            const json = await response.json();
            if(response.status >=200 && response.status < 300){
                setElements([...json.elements])
            } else{
                console.log("Error fetching new element");
                console.log(json);
            }
        }
        fetchElements();
    }, []);

    return(
        <>
        <div className="container-fluid px-0 row">

            <div className="col-1">

            </div>
            <div className="col-10">
                <NotesScene notes={elements} addNote={addElement} selected={selected} setSelected={setSelected}/>
            </div>
            <div className="col-1">
                <RelationshipToolbar selectedElementId={selected} setSelectedElementId={setSelected} />
            </div>
        </div>
        </>
    );
}

export default NoteEditor;