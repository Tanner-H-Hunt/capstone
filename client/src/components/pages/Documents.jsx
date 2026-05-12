import { div } from "three/tsl";
import UserContext from "../contexts/UserContext";
import { useContext } from "react";
import LeftSidebar from "../Documents/LeftSidebar";
import DocumentsPane from "../Documents/DocumentsPane";

function Documents(){
    const { loggedInUser, setLoggedInUser } = useContext(UserContext);
    
    return (
        <div className="container-fluid row ps-0">
            <div  className="col-4 col-md-3 col-xl-2 ms-0 vh-100 mx-0">
                <LeftSidebar/>
            </div>

            <div className="col-8 col-md-9 col-xl-10 mt-2 mx-0">
                <DocumentsPane />

            </div>
        </div>
    );
}

export default Documents;