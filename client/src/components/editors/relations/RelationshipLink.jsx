import { useNavigate, useParams } from "react-router";
import Card from 'react-bootstrap/Card';
import ListGroup from 'react-bootstrap/ListGroup';
import { useState, useEffect, useContext } from "react";
import UserContext from "../../contexts/UserContext";

function RelationshipLink({ data }){
    const navigate = useNavigate();
    const { id } = useParams();

    const { loggedInUser } = useContext(UserContext);
    const [documentName, setDocumentName] = useState("");
    const [documentType, setDocumentType] = useState("");
    let linkedElementsDocumentId = -1;

    function onClickHandler(){
        let path = "/document/"

        // viewing elements connected to this document, so navigating
        // to data.documentId would navigate the user to the same page.
        // they should instead be navigated to the page that contains
        // the data.elementId element
        if(id === data.documentId){
            path += linkedElementsDocumentId;
        } else{
            path += data.documentId;
        }


        navigate(path);
    }

    useEffect(() => {
        let url = "";
        // this is the linked-to document, fetch the elements parent document
        if(id === data.documentId){
            url = "http://localhost:8080/api/relationship/element/doc/" + data.id;
        } else { // this is the linked-to element, fetch the documents details
            url = "http://localhost:8080/api/document/" + data.documentId;
            
        }
        const httpRequest = {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                'Authorization': JSON.parse(loggedInUser).bearer_token
            },
            body: JSON.stringify(JSON.parse(loggedInUser).user)
        }

        const fetchDocumentDetails = async () => {
            const response = await fetch(url, httpRequest);
            const json = await response.json();
            if(response.status >= 200 && response.status < 300){
                setDocumentName(json.name);
                setDocumentType(json.documentType);
                linkedElementsDocumentId = json.id;
            } else{
                console.log("Error fetching this cards data");
                console.log(json);
            }
        }
        fetchDocumentDetails();
    }, [])


    return (
        <button className="mx-1 my-2 px-0 py-0" onClick={() => onClickHandler()}>
            <Card>
                <Card.Body className="px-0 py-0">
                    <Card.Title>
                        {data.name}
                    </Card.Title>
                    <Card.Text>
                        {data.description}
                    </Card.Text>
                    <ListGroup>
                        <ListGroup.Item>{documentName}</ListGroup.Item>
                        <ListGroup.Item>{documentType}</ListGroup.Item>
                    </ListGroup>
                </Card.Body>
            </Card>
        </button>

    );
}

export default RelationshipLink;