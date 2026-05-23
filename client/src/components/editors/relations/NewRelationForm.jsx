import { useEffect, useState } from 'react';
import { Button } from 'react-bootstrap';
import Form from 'react-bootstrap/Form';
import { useContext } from 'react';
import UserContext from '../../contexts/UserContext';
import { useParams } from 'react-router';

function NewRelationForm({ selectedElement, setRelations }){

    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [document, setDocument] = useState(null);
    const [allDocuments, setAllDocuments] = useState([]);
    const { loggedInUser } = useContext(UserContext);
    const { id } = useParams();
    
    function handleSubmit(event){
        event.preventDefault();

        if(document === ""){
            return;
        }

        const url = "http://localhost:8080/api/relationship/create";
        const body={
            "user": JSON.parse(loggedInUser).user,
            "relationship": {
                "id": 0,
                "documentId": document,
                "elementId": selectedElement,
                "name": name,
                "description": description
            }
        }
        const httpRequest={
            method: 'POST',
            headers: {
                "Authorization": JSON.parse(loggedInUser).bearer_token,
                "Content-Type": 'application/json'
            },
            body: JSON.stringify(body)
        }

        const submit = async () => {
            const response = await fetch(url, httpRequest);
            const json = await response.json();
            if(response.status >= 200 && response.status < 300){
                setRelations();
            } else{
                console.log("error saving");
                console.log(json);
            }
        }
        submit();
    }

    function handleNameChange(evt){
        setName(evt.target.value);
    }

    function handleDescriptionChange(evt){
        setDescription(evt.target.value);
    }

    function handleDocumentChange(evt){
        setDocument(evt.target.value);
    }

    useEffect(() => {
        const url = "http://localhost:8080/api/document";
        const httpRequest = {
            method: 'POST',
            headers: {
                "Authorization": JSON.parse(loggedInUser).bearer_token,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(JSON.parse(loggedInUser).user)
        }

        const fetchDocuments = async () => {
            const response = await fetch(url, httpRequest);
            const json = await response.json();
            if(response.status >= 200 && response.status < 300){
                setAllDocuments(json);
            } else{
                console.log("something went wrong while fetching all document data");
                console.log(json);
            }
        }
        fetchDocuments();
    }, []);

    return (
        <Form onSubmit={handleSubmit}>
            <Form.Group>
                <Form.Label>Element Id</Form.Label>
                <Form.Control placeholder={selectedElement} disabled/>
            </Form.Group>
            <Form.Group>
                <Form.Label>Select Document</Form.Label>
                <Form.Select onChange={handleDocumentChange}>
                    <option value={null}></option>
                    {allDocuments.map(doc => {
                        // don't let the user make relationships to the same document
                        if(doc.id != id){
                            return <option key={doc.id} value={doc.id}> {doc.name} </option>
                        }
                    })}
                </Form.Select>
            </Form.Group>
            <Form.Group>
                <Form.Label>Relationship Name</Form.Label>
                <Form.Control value={name} onChange={handleNameChange}></Form.Control>
            </Form.Group>
            <Form.Group>
                <Form.Label>Relationship Description</Form.Label>
                <Form.Control value={description} onChange={handleDescriptionChange}></Form.Control>
            </Form.Group>
            <Button type='submit' className='mt-2'>Submit</Button>
        </Form>
    );
}

export default NewRelationForm;