document.addEventListener('DOMContentLoaded', function() {
    
    const exerciseForm = document.getElementById('exerciseForm');

    exerciseForm.addEventListener('submit', function(event) {
        event.preventDefault();

        // Get form values
        const exercise = document.getElementById('exercise').value;
        const source = document.getElementById('source').value;
        const type = document.getElementById('type').value;

        fetch("/add", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                exercise: exercise,
                source: source,
                type: type
            })
        })
        .then(response => {
            if(response.ok){
                alert("Adicionado com sucesso!");
                form.reset();
            }
        })

        
    });
});