import Note from "../shapes/Note";

function NotesScene({ notes, addNote, selected, setSelected }){
    function createNewNote(){
        addNote("NOTE");
    }

    return (
        <div>

            {notes != undefined ? 
                notes.map(note => {
                    return (
                        <Note attributes={note} key={note.documentElementId} selected={selected} setSelected={setSelected}/>
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