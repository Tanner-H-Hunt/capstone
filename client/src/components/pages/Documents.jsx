import { div } from "three/tsl";
import UserContext from "../contexts/UserContext";
import { useContext, useEffect, useState } from "react";
import LeftSidebar from "../documents/LeftSidebar";
import DocumentsPane from "../documents/DocumentsPane";
import { useNavigate } from "react-router";

function Documents(){
    const { loggedInUser, setLoggedInUser } = useContext(UserContext);
    const [ directoryStack, setDirectoryStack ] = useState([]);
    const [ documents, setDocuments ] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        let url = 'http://localhost:8080/api/directory';
        const httpRequest = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': JSON.parse(loggedInUser).bearer_token
            },
            body: JSON.stringify(JSON.parse(loggedInUser).user)
        };

        const getPageContents = async () => {
            const response = await fetch(url, httpRequest);
            const json = await response.json();
            if(response.status === 200){
                setDocuments(json.documents);
                pushToDirectoryStack(json.directories)
            }
            else{
                //TODO add more robust error handeling
                console.log("ERROR FETCHING PAGE INFORMATION")
            }
        };
        getPageContents();
    }, []);

    function pushToDirectoryStack(path){
        const clonedStack = [...directoryStack, ...path];
        // clonedStack.concat(path);
        setDirectoryStack(clonedStack);
    }

    function addDocument(doc){
        setDocuments([...documents, doc]);
    }
    
    return (
        <div className="container-fluid row ps-0">
            <div  className="col-4 col-md-3 col-xl-2 ms-0 vh-100 mx-0">
                <LeftSidebar  directoryStack={directoryStack} addDocument={addDocument}/>
            </div>

            <div className="col-8 col-md-9 col-xl-10 mt-2 mx-0">
                <DocumentsPane documents={documents} setDocuments={setDocuments} directoryStack={directoryStack} setDirectoryStack={setDirectoryStack}/>

            </div>
        </div>
    );
}

export default Documents;