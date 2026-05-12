import DocumentFilterButton from "./DocumentFilterButton";

function DocumentFilters(){
    return (
        <div className="container-fluid ps-0 d-flex">
            <h3>Filters:</h3>
            <div className="col-10 d-flex">
                <DocumentFilterButton innerText={"Diagrams"}></DocumentFilterButton>
                <DocumentFilterButton innerText={"Design Docs"}></DocumentFilterButton>
                <DocumentFilterButton innerText={"Todos"}></DocumentFilterButton>
            </div>
        </div>
    );
}

export default DocumentFilters;