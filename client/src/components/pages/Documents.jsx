import UserContext from "../contexts/UserContext";
import { useContext } from "react";


function Documents(){
    const { loggedInUser, setLoggedInUser } = useContext(UserContext);
    
    return (
        <h1>{'Documents for ' + JSON.parse(loggedInUser).user.email}</h1>
    );
}

export default Documents;