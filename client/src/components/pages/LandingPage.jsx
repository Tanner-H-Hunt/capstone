import { div } from "three/tsl";
import Placeholder from '../../assets/placeholder.png'

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

            <div className="row py-2">
                <div className="col-6 px-3">
                    <p className="fs-4">Build software with context, not scattered documentation. Our platform gives development teams a unified workspace to create UML diagrams, manage tickets, and write technical design documents — all while connecting related pieces of information across your system architecture. Instead of treating diagrams, tasks, and documentation as isolated artifacts, users can create meaningful relationships between them, making it easier to trace features from initial design to implementation.</p>
                    <button className="btn btn-primary">Register</button>
                </div>
                <div className="col-6 d-flex justify-content-center align-items-center">
                    <img src={Placeholder} alt="" className="img-fluid"/>
                </div>

            </div>

            <div className="row">
                <div className="col-2"></div>
                <div className="col-8">
                    <h1 className="text-center">Features</h1>

                </div>
                <div className="col-2"></div>
            </div>
        </div>
        </>
    );
}

export default LandingPage;