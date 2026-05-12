import DocumentPreview from "./DocumentPreview";

function DocumentPreviewTable(){
    return (
        <>
            <div className="flex-container">
                <div className="row">
                    <div className="col-4">
                        <DocumentPreview />
                    </div>
                    <div className="col-4">
                        <DocumentPreview />
                    </div>
                    <div className="col-4 mb-3">
                        <DocumentPreview />
                    </div>

                    <div className="col-4">
                        <DocumentPreview />
                    </div>
                    <div className="col-4">
                        <DocumentPreview />
                    </div>

                </div>

            </div>
        </>
    );
}

export default DocumentPreviewTable;