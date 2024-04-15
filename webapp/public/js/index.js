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
        if(optionsContainer != undefined){
            const selected = document.querySelector('input[type="radio"][name="cardSelection"]:checked');

            if(selected){
                // Construct the request body
                const requestBody = JSON.stringify({ option: selected.value });

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
                            if(data.message == undefined && data.solution != undefined){
                                console.log(data.solution)
                            } else{
                                alert(data.message);
                            }
                        })
                        .catch(error => {
                            console.error("Error parsing JSON:", error);
                        });
                    } else {
                        // Request failed
                        console.error("Failed to submit option");
                    }
                })
                .catch(error => {
                    console.error("Error:", error);
                });
            }

        } else{
            console.log("Exercise listener");
        }

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