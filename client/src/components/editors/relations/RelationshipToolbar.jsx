import { useEffect, useContext, useState } from "react";
import RelationshipLink from "./RelationshipLink";
import { useParams } from "react-router";
import UserContext from "../../contexts/UserContext";
import Offcanvas from 'react-bootstrap/Offcanvas';
import Button from 'react-bootstrap/Button';
import Accordion from 'react-bootstrap/Accordion';
import NewRelationForm from "./NewRelationForm";

function RelationshipToolbar({ selectedElementId }){

    const [relations, setRelations] = useState([]);
    const [showMenu, setShowMenu] = useState(false);
    const { loggedInUser } = useContext(UserContext);
    const { id } = useParams();

    const fetchAllDocumentRelations = async () => {
        const url = "http://localhost:8080/api/relationship/document/" + id;
        const httpRequest = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': JSON.parse(loggedInUser).bearer_token
            },
            body: JSON.stringify(JSON.parse(loggedInUser).user)
        }

        const response = await fetch(url, httpRequest);
        const json = await response.json();
        if(response.status >= 200 && response.status < 300){
            setRelations(json);
        } else{
            console.log("Error fetching relations for this document");
            console.log(json);
        }
    };

    const fetchElementRelations = async () => {
        const url = "http://localhost:8080/api/relationship/element/" + selectedElementId;
        const httpRequest = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': JSON.parse(loggedInUser).bearer_token
            },
            body: JSON.stringify(JSON.parse(loggedInUser).user)
        }

        const response = await fetch(url, httpRequest);
        const json = await response.json();
        if(response.status >= 200 && response.status < 300){
            setRelations(json);
        } else{
            console.log("Error fetching relations for this document");
            console.log(json);
        }
    };

    useEffect(() => {
        if(selectedElementId == null){
            // fetchAllDocumentRelations()
        } else{
            fetchElementRelations()
        }
    }, 
    [selectedElementId]);

    function handleShow(){
        setShowMenu(true);
    }

    function handleClose(){
        setShowMenu(false);
    }

    function newRelation(){

    }

    return (
        <>
            <div className="d-flex align-items-center justify-content-end vh-100">
                <Button variant="primary" onClick={handleShow}>
                    Relations
                </Button>
            </div>

            <Offcanvas show={showMenu} onHide={handleClose} placement="end" backdrop={false} >
                <Offcanvas.Header closeButton>
                    <Offcanvas.Title>
                            {selectedElementId == null ? '' : 'Documents this element links to'}
                    </Offcanvas.Title>
                </Offcanvas.Header>
                <Offcanvas.Body>

                <Accordion defaultActiveKey="0">
                    <Accordion.Item eventKey="0">
                        <Accordion.Header>
                            New Relation
                        </Accordion.Header>
                        <Accordion.Body>
                            {selectedElementId == null ? "Select an element to make a new relation" : 
                            <NewRelationForm selectedElement={selectedElementId}/>}
                        </Accordion.Body>
                    </Accordion.Item>
                </Accordion>


                    <div className="container-fluid row">
                        { selectedElementId != null ?
                            relations.map(relation => {
                                return (
                                <div className="col-6">
                                    <RelationshipLink data={relation} key={relation.id}/>
                                </div>);
                            })
                            :
                            <p>Select an element to view its relations</p>
                        }
                    </div>
                </Offcanvas.Body>
            </Offcanvas>
        </>
    );
}

export default RelationshipToolbar;