function loadMatches() {
    fetch('/matches')
        .then(response => response.json())
        .then((matches) => {
            const matchListElement = document.getElementById('match-history')!;
            matchListElement.innerHTML = "";

            matches.forEach((match: User) => {
                matchListElement.appendChild(createMatchElement(match));
            })
        });
}

function createMatchElement(match: User) {
    const matchElement = document.createElement('li');
    matchElement.className = 'match';

    const nameElement = document.createElement('span');
    nameElement.innerText = JSON.stringify(match);


    matchElement.appendChild(nameElement);

    return matchElement;
}

function sendMatchRequest() {
    const specialty = (<HTMLInputElement>document.getElementById('specialty')).value;

  // Create the request to send to the server using the data we collected from
  // the web form.
  const matchRequest = new MatchRequest(specialty);

  queryServer(matchRequest).then((matches) => {
      displayNewMatchPopup(matches);
  });
}

function displayNewMatchPopup(matches : Array<string>) {
  const newMatchContainer = <HTMLSelectElement>document.getElementById('new-matches');

  // clear out any old results
  newMatchContainer.innerHTML = '';

  // add results to the page
  for (const match of matches) {
    let newOption = new Option(match, match);
    newMatchContainer.add(newOption, undefined);
  }
}

/**
 * Sends the match request to the server and get back the matches.
 */
async function queryServer(matchRequest: MatchRequest) {
  const json = JSON.stringify(matchRequest);
  return fetch('/new-matches-query', {method: 'POST', body: json})
      .then((response) => {
        return response.json();
      })
      .then((users) => {return users;});
}

class MatchRequest {
    specialty: string;
    constructor(specialty: string) {
        this.specialty = specialty;
    }
}

class User {
    name: string;
    specialties: string[];
    constructor(name: string, specialties: string[]) {
        this.name = name;
        this.specialties = specialties;
    }
}
