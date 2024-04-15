document.addEventListener("DOMContentLoaded", function() {
    console.log("Loading previewer");
    // Get the preview button
    const previewButton = document.getElementById("previewButton");
    
    // Get the preview paragraph
    const previewParagraph = document.getElementById("preview");

    // Get the exercise text area
    const exerciseTextArea = document.getElementById("exerciseText");

    // Add click event listener to the preview button
    previewButton.addEventListener("click", function() {
        // Get the content of the exercise text area
        const exerciseContent = exerciseTextArea.value;

        // Set the content of the preview paragraph to match the content of the text area
        previewParagraph.textContent = exerciseContent;
        
        // Call MathJax to render the newly added content
        MathJax.Hub.Queue(["Typeset", MathJax.Hub, previewParagraph]);
    });
});