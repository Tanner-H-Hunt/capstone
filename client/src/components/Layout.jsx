import { Outlet } from "react-router";
import Nav from './Nav';

function Layout(){
    return (
        <>
            < Nav />
            < Outlet />
        </>
    );
}

export default Layout;