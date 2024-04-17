document.addEventListener('DOMContentLoaded', function() {
    
    const exerciseForm = document.getElementById('exerciseForm');

    exerciseForm.addEventListener('submit', function(event) {
        event.preventDefault();

        // Get form values
        const exercise = document.getElementById('exercise').value;
        const source = document.getElementById('source').value;
        const type = document.getElementById('type').value;
        const options = document.getElementById('options').value;
        const solution = document.getElementById('solution').value;

        fetch("/add", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                exercise: exercise,
                source: source,
                typeExercise: type,
                solution: solution,
                options: options
            })
        })
        .then(response => {
            if(response.ok){
                alert("Adicionado com sucesso!");
                exerciseForm.reset();
            }
        })

        
    });

    document.getElementById('sendDoneExercises').addEventListener('click', function() {
        // Collect IDs of checked checkboxes
        const checkedIds = [];
        const checkboxes = document.querySelectorAll('input[name="cardSelection"]');
        checkboxes.forEach(function(checkbox) {
            const cardId = checkbox.parentNode.querySelector('input').value;
            checkedIds.push({
                id: cardId,
                isDone: checkbox.checked
            });
        });

        // Send the array to the URI
        fetch('/exercises/done', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ ids: checkedIds })
        })
        .then(response => {
            // Handle response if needed
            alert("Data updated")
        })
        .catch(error => {
            // Handle error if needed
            console.error('Error:', error);
        });
    });
});