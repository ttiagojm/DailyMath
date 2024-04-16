document.addEventListener("DOMContentLoaded", function() {
    // Get the preview button
    const previewButton = document.getElementById("previewButton");
    
    // Get the preview paragraph
    const previewParagraph = document.getElementById("preview");

    // Get the exercise text area
    const exerciseTextArea = document.getElementById("exerciseText");

    // Get options Container
    const optionsContainer = document.getElementById("cardContainer");

    const submitBtn = document.getElementById("submitBtn");
    
    // Add click event listener to the submit button
    submitBtn.addEventListener("click", function() {
        let requestBody;
        
        if(optionsContainer != undefined){
            const selected = document.querySelector('input[type="radio"][name="cardSelection"]:checked');
            // Construct the request body
            requestBody = JSON.stringify({ option: selected.value });
        }

        // Make a POST request to the "/solution" URI
        fetch("/solution", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: requestBody
        })
        .then(response => {
            if (response.ok) {
                response.json()
                .then(data => {
                    if(data.message != undefined){
                        alert(data.message);
                    } else if(data.solution != undefined){
                        // Get the last paragraph element
                        const solutionParagraph = document.querySelector("p.lead:last-child");

                        // Set the content of the last paragraph to the solution text
                        solutionParagraph.textContent = data.solution;

                        // Call MathJax to render the newly added content
                        MathJax.Hub.Queue(["Typeset", MathJax.Hub, solutionParagraph]);
                    }
                })
                .catch(error => {
                    console.error("Error parsing JSON:", error);
                });
            
            } else {
                console.error("Failed to submit option");
            }
        })
        .catch(error => {
            console.error("Error:", error);
        });

    });

    // Add click event listener to the preview button
    if(previewButton != undefined){
        console.log("Preview listener")
        previewButton.addEventListener("click", function() {
            // Get the content of the exercise text area
            const exerciseContent = exerciseTextArea.value;

            // Set the content of the preview paragraph to match the content of the text area
            previewParagraph.textContent = exerciseContent;
            
            // Call MathJax to render the newly added content
            MathJax.Hub.Queue(["Typeset", MathJax.Hub, previewParagraph]);
        });
    }
    
});