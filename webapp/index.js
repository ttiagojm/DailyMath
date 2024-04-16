const express = require("express");
const session = require("express-session");
const path = require("path");
const axios = require("axios");
const jwt = require("jsonwebtoken");
const utils = require("./utils");

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

app.get("/login", (req, res) => {
    res.render("pages/login");
});

app.post("/login", (req, res) => {
    if(req.body == undefined || !utils.validateUser({username: req.body.username, password: req.body.password})){
        res.sendStatus(401);
    } else {
        req.session.user = {username: req.body.username, password: req.body.password};
        res.sendStatus(200);
    }
});

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
    const token = jwt.sign({iss: "dailymath"}, process.env.SECRET);

    if(req.session.exercise == undefined){
        res.sendStatus(400);
    } else if(req.session.exercise.options == undefined){
        axios.get(`${process.env.API}/dailysolution?idSolution=${req.session.exercise.idSolution}`, 
                    {headers: {Authorization: `Bearer ${token}`}})
        .then(resp => {
            res.json({solution: resp.data.solution});
        })
    
    } else{
        // get the solution and save it
        axios.get(`${process.env.API}/dailysolution?idSolution=${req.session.exercise.idSolution}`, 
                    {headers: {Authorization: `Bearer ${token}`}})
        .then(resp => {
            
            // Get solution and index selected by the user
            const words = resp.data.solution.split(" ");
            const solution = parseInt(words[words.length-1]);
            const option = parseInt(req.body.option)-1;

            if(option < 0 || option > req.session.exercise.options.length-1){
                res.sendStatus(400);
            } else{
                if(parseInt(req.session.exercise.options[option]) == solution){
                    res.json({solution: resp.data.solution});
                } else{
                    res.json({message: "Try again!"});
                }
            }
        });
    }
});

app.get("/admin", (req, res) => {
    if(req.session.user == undefined){
        res.redirect("/login");
    } else{
        res.render("pages/admin");
    }
})

app.post("/add", (req, res) => {
    const token = jwt.sign({iss: "dailymath"}, process.env.SECRET);

    
})

app.listen(port, () => {
    console.log(`App running on port ${port}`);
});