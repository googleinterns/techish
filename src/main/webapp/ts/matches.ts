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
    nameElement.innerText = matchToString(match);


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

function displayNewMatchPopup(matches : Array<User>) {
    const newMatchContainer = <HTMLSelectElement>document.getElementById('new-matches');

  // clear out any old results
  newMatchContainer.innerHTML = '';

  // add results to the page
  for (const match of matches) {
    const matchString : string = matchToString(match);
    let newOption = new Option(matchString, match.name);
    newMatchContainer.add(newOption, undefined);
  }
}


function matchToString(match : User) {
    let toReturn : string = "";
    toReturn += match.name;
    toReturn += ": ";
    
    if(match.specialties.length == 0) {
        toReturn += "no specialties";
    } else {
        for(let i = 0; i < match.specialties.length - 1; i++) {
            toReturn += match.specialties[i];
            toReturn += ", ";
        }
        toReturn += match.specialties[match.specialties.length - 1];
    }
    return toReturn;
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
      .then((users) => {
        // Convert the range from a json representation to our User class.
        const out : Array<User> = [];
        users.forEach((range: User) => {
          out.push(new User(range.name, range.specialties));
        });
        return out;
      });
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

