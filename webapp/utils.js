const exp = require("constants");
const crypto = require("crypto")

function validateUser(user){
    const passwd = crypto.createHash("sha256").update(process.env.PASSWD).digest("hex");
    if(user.username == process.env.USER && user.password == passwd){
        return true;
    }
    return false;
}

module.exports = {
    validateUser
}