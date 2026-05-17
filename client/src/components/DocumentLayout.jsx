import { useEffect, useState } from "react";
import { useParams } from "react-router";
import UserContext from "./contexts/UserContext";
import { useContext } from "react";
import UmlEditor from "./pages/UmlEditor";
import NoteEditor from "./pages/NoteEditor";
import TodoEditor from "./pages/TodoEditor";

function DocumentLayout(){

    const { loggedInUser, setLoggedInUser } = useContext(UserContext);
    const [doc, setDoc] = useState(null);
    let { id } = useParams();

    // figure out what type of document
    useEffect(() => {

        const url = "http://localhost:8080/api/document/" + id;
        const httpRequest = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': JSON.parse(loggedInUser).bearer_token
            },
            body: JSON.stringify(JSON.parse(loggedInUser).user)
        };

        const getDocument = async() => {
            const response = await fetch(url, httpRequest);
            const json = await response.json();

            if(response.status === 200){
                setDoc(json);
            } else{
                console.log("Error fetching document data: " + JSON.stringify(json));
            }
        };
        getDocument();
    }, []);


    function parseDocType(){
        switch (doc.documentType){
            case "UML":
                return <UmlEditor document={doc}/>
            case "TODO":
                return <TodoEditor document={doc}/>
            case "NOTE":
                return <NoteEditor document={doc}/>
        }
    }

    return(
        <>
        {doc === null ? <></> : parseDocType()}
        </>
    );
}

export default DocumentLayout;