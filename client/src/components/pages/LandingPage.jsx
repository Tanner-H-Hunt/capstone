import { div } from "three/tsl";

function LandingPage(){
    return (
        <>
        <div className="jumbotron jumbotron-fluid bg-primary-subtle">
            <div className="container text-end">
                <h1 className="display-3 ">Design.app</h1>
                <div className="row">
                    <div className="col-7"></div>
                    <p className="lead col-5">Integrated tool for building UML diagrams, tracking issues, and writing design docs</p>
                </div>
            </div>
        </div>
        <div className="container-fluid">
            <div className="row">
                <div className="col-2"></div>
                <div className="col-8">
                    <h1 className="text-center">Build UML diagrams, design docs, tickets, and relationships between documents
                        in a full-suite design app
                    </h1>

                </div>
                <div className="col-2"></div>
            </div>
            <div className="row">
                <div className="col-6 ms-2">
                    <p>lorem ipsum dolore sur gutra </p>
                </div>
            </div>
        </div>
        </>
    );
}

export default LandingPage;