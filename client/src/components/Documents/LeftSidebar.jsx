import { div } from "three/tsl";

function LeftSidebar(){
    return (
        <ul className="me-2 bg-secondary vh-100 list-unstyled">
            <li>
                <button className="btn text-white">New Diagram</button>
            </li>
            <li>
                <button className="btn text-white">New Design Doc</button>

            </li>
            <li>
                <button className="btn text-white">New Todo List</button>
            </li>
        </ul>
    );
}

export default LeftSidebar;