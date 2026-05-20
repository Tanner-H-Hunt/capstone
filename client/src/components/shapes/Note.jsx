import { useEffect, useState } from "react";
import ReactMarkDown from 'react-markdown';
import remarkGfm from 'remark-gfm';
import { useContext } from "react";
import UserContext from "../contexts/UserContext";

function Note({ attributes }){
    const innerTextValue = attributes.attributes.find(
        attribute => attribute.key === "innerText"
    ).value;

    const { loggedInUser } = useContext(UserContext);
    const minRows = 20;
    const [innerText, setInnerText] = useState(innerTextValue);
    const [rows, setRows] = useState(minRows);

    function serialize(){
        attributes.attributes.find(
            attribute => attribute.key === "innerText"
        ).value = innerText;
        
        const body = {
                "user": JSON.parse(loggedInUser).user,
                "element": {
                    "attributes": [
                    ]
                }
            }
            
            
            // nest the attributes back under the element the way the server expects
            for(const attribute in attributes){
                if(attribute === "documentElementId" || attribute === "documentElementType" || attribute === "documentId"){
                    body.element[attribute] = attributes[attribute];
                } else{
                    for(const subAttribute in attributes[attribute]){
                        const expectedAttribute = {
                            "attributeId": attributes[attribute][subAttribute].attributeId,
                            "documentElementId": attributes[attribute][subAttribute].documentElementId,
                            "value": `${attributes[attribute][subAttribute].key}:${attributes[attribute][subAttribute].value}`
                        }
                        body.element.attributes.push(expectedAttribute);

                    }
                }
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
                console.log("successfully updated object data");
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
        const numRows = str.split("\n").length;

        setInnerText(str);
        setRows(Math.max(numRows, minRows));
        
    }

    return (
        <div className="container-fluid mb-2">
            <div className="row">
                <div className="col-1"></div>
                <textarea name="" id="" defaultValue={innerText} className="col-10" rows={rows} onChange={onChangeHandler}></textarea>
            </div>
        </ div>
    );
}

export default Note;

// testing for rendering markdown
// <ReactMarkDown remarkPlugins={[remarkGfm]}>{innerText}</ReactMarkDown>