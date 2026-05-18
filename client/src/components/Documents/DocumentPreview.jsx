import { useNavigate } from 'react-router';
import Placeholder from '../../assets/placeholder.png'
import { useState } from 'react';
import ClickAwayListener from '@mui/material/ClickAwayListener'
import { useContext } from 'react';
import UserContext from "../contexts/UserContext";

function DocumentPreview({ document }){
    const navigate = useNavigate();
    const [editing, setEditing] = useState(false);
    const [docName, setDocName] = useState(document.name);
    const [previousDocName, setPreviousDocName] = useState(document.name);
    const { loggedInUser } = useContext(UserContext);

    function redirect(){
        const id = document.id;
        navigate("/document/" + id);
    }

    function onSubmit(){
        event.preventDefault();

        if(!validate){
            setDocName(previousDocName);
        }

        const newDocumentRequest = {
            user: JSON.parse(loggedInUser).user,
            document: { ...document, name: docName }
        }

        const httpRequest = {
            method: "PUT",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': JSON.parse(loggedInUser).bearer_token
            },
            body: JSON.stringify(newDocumentRequest)
        };

        const url = "http://localhost:8080/api/document/" + document.id;
        console.log(httpRequest);

        const submitToServer = async () => {
            const response = await fetch(url, httpRequest);
            const json = await response.json();
            if(response.status >= 200 && response.status <= 300){
                setPreviousDocName(docName);
            } else{
                console.log("Error in editing document name");
                console.log(json);
                setDocName(previousDocName);
            }
        }
        submitToServer();
        setEditing(false);
    }

    function onDoubleClick(){
        setEditing(!editing);
    }

    function handleChange(evt){
        setDocName(evt.target.value);
    }

    function handleClickAway(){
        if(editing){
            setDocName(previousDocName);
            setEditing(false);
        }
    }

    function validate(){
        if(docName === undefined || docName === null){
            return false;
        }

        if(docName.trim().length === 0){
            return false;
        }

        return true;
    }

    return(
        <div className="card">
            <button className="btn" onClick={() => redirect()}>
                <img src={Placeholder} className="card-img-top p-2" alt=""/>
            </button>

            <ul className='list-group list-group-flush'>
                
                <ClickAwayListener onClickAway={() => handleClickAway()}>
                <li className='list-group-item' onDoubleClick={onDoubleClick}>
                    {editing ? 
                    <form onSubmit={onSubmit}>
                        <input 
                            id={document.id}
                            className=''
                            type="text" 
                            onChange={handleChange}
                            value={docName}
                            name='documentName'
                        />
                    </form> : <p className='my-0'>{docName}</p>}
                </li>
                </ClickAwayListener>

                <li className='list-group-item text-muted'>{document.documentType}</li>
                <li className='list-group-item text-muted'>
                    <button className='btn btn-danger'>Delete</button>
                </li>
            </ul>
        </div>
    );
}

export default DocumentPreview;