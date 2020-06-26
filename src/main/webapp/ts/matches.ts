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

function sendMatchRequest() {
    closeForm();
    const productArea = (<HTMLInputElement>document.getElementById('product_area')).value;

  // Create the request to send to the server using the data we collected from
  // the web form.
  const matchRequest = new MatchRequest(productArea);

  queryServer(matchRequest)
}

/**
 * Sends the meeting request to the server and get back the matches.
 */
function queryServer(matchRequest: MatchRequest) {
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

      //TODO!!!!!!!!!!!!!!!!!!
      //add new matches to /matches post
}

class MatchRequest {
    productArea: String;
    constructor(productArea: String) {
        this.productArea = productArea;
    }
}

class User {
    name: String;
    constructor(name: String) {
        this.name = name;
    }
}

