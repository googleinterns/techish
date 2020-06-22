interface Match {
    name: string;
}

function loadMatches() {
    fetch('/matches')
        .then(response => response.json())
        .then((matches) => {
            const matchListElement = document.getElementById('match-history')!;
            matchListElement.innerHTML = "";

            matches.forEach((match: Match) => {
                matchListElement.appendChild(createMatchElement(match));
            })
        });
}

function createMatchElement(match: Match) {
    const matchElement = document.createElement('li');
    matchElement.className = 'match';

    const nameElement = document.createElement('span');
    nameElement.innerText = match.name;


    matchElement.appendChild(nameElement);

    return matchElement;
}

function openForm() {
    document.getElementById("matchForm")!.style.display = "block";
}

function closeForm() {
    document.getElementById("matchForm")!.style.display = "none";
} 