import { div } from "three/tsl";
import UserContext from "../contexts/UserContext";
import { useContext } from "react";

function LeftSidebar({ directoryStack, addDocument }){
    const { loggedInUser } = useContext(UserContext);
    
    function newDocument(type){
        const parentDirectoryId = directoryStack[directoryStack.length - 1].id;
        const fetchNewDocument = async() => {
            const url = "http://localhost:8080/api/document/create"
            const requestBody = {
                "user" : JSON.parse(loggedInUser).user,
                "document" : {
                    "name" : "My new document",
                    "parentDirectoryId": parentDirectoryId,
                    "documentType": type
                }
            }
            
            const httpRequest = {
                method: 'POST',
                headers: {
                    "Content-type": 'application/json',
                    "Authorization": JSON.parse(loggedInUser).bearer_token
                },
                body: JSON.stringify(requestBody)
            }

            const response = await fetch(url, httpRequest);
            const json = await response.json();
            if(response.status === 201){
                addDocument(json);
            } else{
                console.log("something went wrong creating this document");
            }
        }
        fetchNewDocument();
        
    }

    return (
        <ul className="me-2 bg-secondary vh-100 list-unstyled">
            <li>
                <button className="btn text-white" onClick={() => newDocument("UML")}>New Diagram</button>
            </li>
            <li>
                <button className="btn text-white" onClick={() => newDocument("NOTE")}>New Design Doc</button>

            </li>
            <li>
                <button className="btn text-white" onClick={() => newDocument("TODO")}>New Todo List</button>
            </li>
            {/* TODO: implement directories */}
            {/* <li>
                <button className="btn text-white">New Directory</button>
            </li> */}
        </ul>
    );
}

export default LeftSidebar;