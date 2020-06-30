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
    nameElement.innerText = match.name;


    matchElement.appendChild(nameElement);

    return matchElement;
}

function sendMatchRequest() {
    const productArea = (<HTMLInputElement>document.getElementById('product_area')).value;

  // Create the request to send to the server using the data we collected from
  // the web form.
  const matchRequest = new MatchRequest(productArea);

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
    let newOption = new Option(matchString, matchString);
    newMatchContainer.add(newOption, undefined);
  }
}


function matchToString(match : User) {
    return match.name;
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
          out.push(new User(range.name));
        });
        return out;
      });
}

class MatchRequest {
    productArea: string;
    constructor(productArea: string) {
        this.productArea = productArea;
    }
}

class User {
    name: string;
    constructor(name: string) {
        this.name = name;
    }
}

