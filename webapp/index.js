const express = require("express");
const session = require("express-session");
const path = require("path");
const axios = require("axios");
const jwt = require("jsonwebtoken");

const app = express();
const port = process.env.PORT | 3000;

app.set("view engine", "ejs");
app.set("views", path.join(__dirname, "views"));

app.use(express.static(path.join(__dirname, "public")));

app.use(session({
    secret: "test",
    saveUninitialized: true,
    resave: true
}));

app.use(express.json());

app.get("/", (req, res) => {
    const token = jwt.sign({iss: "dailymath"}, process.env.SECRET);

    axios.get(`${process.env.API}/daily`, {headers: {Authorization: `Bearer ${token}`}})
    .then(resp => {
        // Save the exercise object in a session variable
        req.session.exercise = resp.data;
        req.session.save();

        res.render("pages/index", {
            exercise: resp.data.exercise,
            options: resp.data.options ? resp.data.options: null,
            source: resp.data.source,
            typeExercise: resp.data.typeExercise
        });
    });
});

app.post("/solution", (req, res) => {

    if(req.session.exercise == undefined){
        res.sendStatus(400);
    } else if(req.session.exercise.options == undefined){
        console.log("Exercise");
    
    } else{
        const token = jwt.sign({iss: "dailymath"}, process.env.SECRET);
        
        // get the solution and save it
        axios.get(`${process.env.API}/dailysolution?idSolution=${req.session.exercise.idSolution}`, 
                    {headers: {Authorization: `Bearer ${token}`}})
        .then(resp => {
            
            // Get solution index and index selected by the user
            const words = resp.data.solution.split(" ");
            const solution = words[words.length-1];
            const option = parseInt(req.body.option);

            if(option < 0 || option > req.session.exercise.options.length){
                res.sendStatus(400);
            } else{
                if(option == solution){
                    res.json({solution: resp.data.solution});
                } else{
                    res.json({message: "Try again!"});
                }
            }
        });
    }
});

app.listen(port, () => {
    console.log(`App running on port ${port}`);
});