type authInfo = {
    loginUrl: string;
    logoutUrl: string;
};

async function loadMatches() {
    const logStatus = await getLogStatus();

    fetch('/matches')
        .then(response => response.json())
        .then((matches) => {
            if((logStatus.loginUrl === "") && (matches != null)) {
            const matchListElement = document.getElementById('match-history')!;
            matchListElement.innerHTML = "";

            matches.forEach((match: User) => {
                matchListElement.appendChild(createMatchElement(match));
            })
            } else {
                //redirect to logged out homepage because user is not logged in
                document.location.href = logStatus.loginUrl;
                alert("Please login or create an account.");
            }
        });
}

async function getLogStatus(): Promise<authInfo> {
    const response = await fetch('/userapi');
    const currentStatus = await response.json();
    let authStatus: authInfo = { loginUrl: currentStatus.LogInUrl, logoutUrl: currentStatus.LogOutUrl };
    return authStatus;
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
      if(matches.length == 0) {
        $('#noNewMatchModal').modal();
      } else {
        $('#newMatchModal').modal();
        displayNewMatchPopup(matches);
      }
  });
}

function displayNewMatchPopup(matches : Array<User>) {
  const newMatchContainer = <HTMLSelectElement>document.getElementById('new-matches');

  // clear out any old results
  newMatchContainer.innerHTML = '';

  // add results to the page
  for (const match of matches) {
    const matchString : string = JSON.stringify(match);
    let newOption = new Option(matchString, matchString);
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
      .then((users) => {
        //convert range from json to User
        const out : Array<User> = [];
        users.forEach((range: User) => {
          out.push(range);
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
    id: number;
    name: string;
    specialties: string[];
    constructor(id: number, name: string, specialties: string[]) {
        this.id = id;
        this.name = name;
        this.specialties = specialties;
    }
}
