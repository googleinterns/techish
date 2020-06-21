"use strict";
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
//# sourceMappingURL=matches.js.map