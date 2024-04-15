const express = require("express");
const session = require("express-session");
const path = require("path");
const axios = require("axios");
const jwt = require("jsonwebtoken");
const cookierParser = require("cookie-parser");

const app = express();
const port = process.env.PORT | 3000;

app.set("view engine", "ejs");
app.set("views", path.join(__dirname, "views"));

app.use(express.static(path.join(__dirname, "public")));

app.use(cookierParser());
app.use(session({
    secret: "test",
    saveUninitialized: true,
    resave: true
}));

app.get("/", (req, res) => {
    const token = jwt.sign({iss: "dailymath"}, process.env.SECRET);

    axios.get("http://localhost:8080/daily", {headers: {Authorization: `Bearer ${token}`}})
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

app.listen(port, () => {
    console.log(`App running on port ${port}`);
});