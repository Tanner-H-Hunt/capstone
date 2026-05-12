import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router";

function AccountForm(){

    const initialUser = {
        "account_id" : 0,
        "email" : "",
        "password" : ""
    }

    const navigate = useNavigate();
    const location = useLocation();
    const [path, setPath] = useState("");
    const [user, setUser] = useState(initialUser);
    const [errors, setErrors] = useState([]);
    const [serverErrors, setServerErrors] = useState([]);


    useEffect(() => {
        setPath(location.pathname);
        setErrors([]);
        setUser(initialUser);
    }, [location]);

    function handleInput(evt){
        let updatedUser = {...user};
        updatedUser[event.target.name] = event.target.value;
        setUser(updatedUser);
        detectErrors(evt);
    }

    function detectErrors(evt){
        let newErrors = new Set(errors);
        
        if(evt.target.name === 'email'){
            const errorMessage = "Email must not be blank";
            if(evt.target.value === ""){
                newErrors.add(errorMessage);
            } else{
                newErrors.delete(errorMessage);
                newErrors.delete("must contain a valid email");
            }
        }
        
        if(evt.target.name === 'password'){
            const errorMessage = "password must not be blank";
            if(evt.target.value === ""){
                newErrors.add(errorMessage);
            } else{
                newErrors.delete(errorMessage);
            }
        }
        
        setErrors([...newErrors]);
        setServerErrors([]);
    }

    function isCreatingAccount(){
        return path === '/user/create';
    }

    async function handleSubmit(evt){
        evt.preventDefault();

        if(errors.length > 0){
            return;
        }
        
        let url = "http://localhost:8080/api/user";
        if(isCreatingAccount()){
            url += '/create';
        }

        const httpRequest = {
            method: 'POST',
            headers: {
                'Content-Type' : 'application/json'
            },
            body: JSON.stringify(user),
        };

        const response = await fetch(url, httpRequest);
        
        if(response.status === 200){
            navigate("/")
        } else{
            const payload = await response.json();
            setServerErrors(payload);
            //console.log(serverErrors);
        }
    }


    return (
        <>
        {isCreatingAccount() ? <h1>Signup</h1> : <h1>Login</h1>}
        {errors.length === 0 && serverErrors.length === 0 ? 
        <></> : 
        <div>
            <h2>Errors:</h2>
            <ul>
                {errors.map((err, index) => <li key={'InputError: ' + index}>{err}</li>)}
                {serverErrors.map((serverErr, index) => {
                    console.log(`index: ${index}, Error: ${serverErr}`)
                    return <li key={'serverError: ' + index}>{serverErr}</li>;  })
                }
            </ul>
        </div>
        }
        <form action="">
            <div className="form-control">
                <label htmlFor="email">Email</label>
                <input 
                    id="email" 
                    type="text" 
                    name="email" 
                    value={user.email}
                    onChange={handleInput}
                    required/>
            </div>
            <div className="form-control">
                <label htmlFor="password">Password</label>
                <input 
                    id="password" 
                    type="password" 
                    name="password" 
                    value={user.password}
                    onChange={handleInput}
                    required/>
            </div>
            <button type="submit" onClick={handleSubmit}>{isCreatingAccount() ? 'Create Account' : 'Login'}</button>
        </form>
        </>
    );
}

export default AccountForm;