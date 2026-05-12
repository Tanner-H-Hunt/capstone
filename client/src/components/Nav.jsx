import { NavLink, Link } from "react-router";
import { useContext } from "react";
import UserContext from "./contexts/UserContext";
import Logo from '../assets/logo.png'

function Nav(){
    const { loggedInUser, setLoggedInUser } = useContext(UserContext);

    function logout(){
        localStorage.removeItem("user");
        setLoggedInUser(null);
    }

    return (
        <nav className="navbar navbar-expand-lg bg-body-tertiary mb-1">
            <div className="container-fluid">
                <Link className="navbar-brand" to="/"><img src={Logo} alt="" width={35} height={30} /></Link>
                <div>
                    <ul className="navbar-nav me-3">
                        {loggedInUser ? 
                        // Logged in
                        <>
                            <li className="nav-item">
                                <NavLink className="nav-link" to="/" onClick={logout}>Logout</NavLink>
                            </li>
                        </> 
                        :
                        // logged out
                        <>
                            <li className="nav-item">
                                <NavLink className="nav-link" to="/user/create">Register</NavLink>
                            </li>
                            <li className="nav-item">
                                <NavLink className="nav-link" to="/user/login">Login</NavLink>
                            </li>  
                        </>}

                    </ul>
                </div>
            </div>
        </nav>
    );
}

export default Nav;