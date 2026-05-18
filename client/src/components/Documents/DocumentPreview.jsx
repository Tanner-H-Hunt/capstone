import { useNavigate } from 'react-router';
import Placeholder from '../../assets/placeholder.png'
import { useState } from 'react';
import ClickAwayListener from '@mui/material/ClickAwayListener'

function DocumentPreview({ document }){
    const navigate = useNavigate();
    const [editing, setEditing] = useState(false);
    const [docName, setDocName] = useState(document.name);
    const [previousDocName, setPreviousDocName] = useState(document.name);

    function redirect(){
        const id = document.id;
        navigate("/document/" + id);
    }

    function onSubmit(){
        event.preventDefault();

        if(!validate){
            setDocName(previousDocName);
        }
        
        //TODO send edit to server, validate
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