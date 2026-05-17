import { div } from "three/tsl";

function LeftSidebar({ directoryStack }){
    function newDocument(type){
        const parentDirectoryId = directoryStack[directoryStack.length - 1].id;
        
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
            <li>
                <button className="btn text-white">New Directory</button>
            </li>
        </ul>
    );
}

export default LeftSidebar;