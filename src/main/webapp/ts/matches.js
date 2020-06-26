function loadMatches() {
    fetch('/matches')
        .then(function (response) { return response.json(); })
        .then(function (matches) {
        var matchListElement = document.getElementById('match-history');
        matchListElement.innerHTML = "";
        matches.forEach(function (match) {
            matchListElement.appendChild(createMatchElement(match));
        });
    });
}
function createMatchElement(match) {
    var matchElement = document.createElement('li');
    matchElement.className = 'match';
    var nameElement = document.createElement('span');
    nameElement.innerText = match.name;
    matchElement.appendChild(nameElement);
    return matchElement;
}
function openForm() {
    document.getElementById("matchForm").style.display = "block";
}
function closeForm() {
    document.getElementById("matchForm").style.display = "none";
}
function sendMatchRequest() {
    closeForm();
    var productArea = document.getElementById('product_area').value;
    // Create the request to send to the server using the data we collected from
    // the web form.
    var matchRequest = new MatchRequest(productArea);
    queryServer(matchRequest);
}
/**
 * Sends the meeting request to the server and get back the matches.
 */
function queryServer(matchRequest) {
    var json = JSON.stringify(matchRequest);
    return fetch('/new-matches-query', { method: 'POST', body: json })
        .then(function (response) {
        return response.json();
    })
        .then(function (users) {
        // Convert the range from a json representation to our TimeRange class.
        var out = [];
        users.forEach(function (range) {
            out.push(new User(range.name));
        });
        return out;
    });
}
var MatchRequest = /** @class */ (function () {
    function MatchRequest(productArea) {
        this.productArea = productArea;
    }
    return MatchRequest;
}());
var User = /** @class */ (function () {
    function User(name) {
        this.name = name;
    }
    return User;
}());
