import Placeholder from '../../assets/placeholder.png'

function DocumentPreview(){

    return(
        <div className="card">
            <button className="btn" onClick={() => {console.log("Navigating to document page")}}>
                <img src={Placeholder} className="card-img-top p-2" alt=""/>
                <div className="card-body px-0 py-1">
                    <p className='fs-6 card-text text-start'>Document Title</p>
                </div>
            </button>
            <ul className='list-group list-group-flush'>
                <li className='list-group-item text-muted'>Document type</li>
                <li className='list-group-item text-muted'>Last edit date</li>
                <li className='list-group-item text-muted'>
                    <button className='btn btn-danger'>Delete</button>
                </li>
            </ul>
        </div>
    );
}

export default DocumentPreview;