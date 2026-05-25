import { useEffect, useState } from "react";
import ReactMarkDown from 'react-markdown';
import remarkGfm from 'remark-gfm';
import { useContext } from "react";
import UserContext from "../contexts/UserContext";
import { ClickAwayListener } from "@mui/material";

function Note({ json, selected, setSelected, innerText, setInnerText, order, setOrder }){

    const { loggedInUser } = useContext(UserContext);
    const minRows = 1;
    const [rows, setRows] = useState(minRows);

    function serialize(){
        console.log(json);
        const body = {
                "user": JSON.parse(loggedInUser).user,
                "element": json
            }

        const httpRequest = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': JSON.parse(loggedInUser).bearer_token
            },
            body: JSON.stringify(body)
        }

        const url = "http://localhost:8080/api/element"

        const updateRequest = async() => {
            const response = await fetch(url, httpRequest);
            if(response.status >= 200 && response.status < 300){
                // console.log("successfully updated object data");
            } else{
                console.log("failed to update element data")
                const json = await response.json();
                console.log(json);
            }
        }
        updateRequest()
    }

    useEffect(() => {
        serialize();
    }, [innerText])

    function onChangeHandler(evt){
        let str = evt.target.value;
        if(str.length === 0){
            str = " ";
        };

        // TODO: remove this str.replace methods.  These characters
        // can corrupt the JSON data, which will make fetching elements
        // from the server fail
        str = str.replace("\n", "");
        str = str.replace(":", "");
        str = str.replace("\"", "");

        const numRows = str.split("\n").length;
        setInnerText(str);
        setRows(Math.max(numRows, minRows));
        
    }

    function select(){
        setSelected(json.elementId);
    }

    function deselect(evt){
        if(selected === json.elementId && evt.target.localName === "canvas"){
            setSelected(null);
        }
    }

    return (
        <ClickAwayListener onClickAway={deselect}>
        <div className="container-fluid mb-2" onClick={select}>
            <div className="row">
                <div className="col-1"></div>
                <textarea 
                    name="" 
                    id="" 
                    value={innerText} className="col-10 form-control" rows={rows} onChange={onChangeHandler}></textarea>
            </div>
        </ div>
        </ClickAwayListener>
    );
}

export default Note;

// testing for rendering markdown
// <ReactMarkDown remarkPlugins={[remarkGfm]}>{innerText}</ReactMarkDown>