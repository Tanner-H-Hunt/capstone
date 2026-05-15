import { useNavigate } from 'react-router';
import Placeholder from '../../assets/placeholder.png'

function DocumentPreview({ document }){
    const navigate = useNavigate();

    function redirect(){
        const id = document.id;
        navigate("/document/" + id);
    }

    return(
        <div className="card">
            <button className="btn" onClick={() => redirect()}>
                <img src={Placeholder} className="card-img-top p-2" alt=""/>
                <div className="card-body px-0 py-1">
                    <p className='fs-6 card-text text-start'>{document.name}</p>
                </div>
            </button>
            <ul className='list-group list-group-flush'>
                <li className='list-group-item text-muted'>{document.documentType}</li>
                <li className='list-group-item text-muted'>
                    <button className='btn btn-danger'>Delete</button>
                </li>
            </ul>
        </div>
    );
}

export default DocumentPreview;