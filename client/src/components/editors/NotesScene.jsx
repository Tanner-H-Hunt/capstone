import JsonToShape from "../shapes/JsonToShapeConverter";

function NotesScene({ notes, addNote, selected, setSelected }){
    function createNewNote(){
        addNote("NOTE");
    }

    return (
        <div>

            {notes != undefined ? 
                notes.map(note => {
                    return (
                        <JsonToShape key={note.elementId} selected={selected} setSelected={setSelected} json={note}/>
                    );
                })
            
                : 
            <></>}
            <div className="text-center">
                <button className="btn btn-secondary" onClick={createNewNote}>New note</button>

            </div>
        </div>
    );
}

export default NotesScene;